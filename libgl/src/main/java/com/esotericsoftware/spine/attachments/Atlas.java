package com.esotericsoftware.spine.attachments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import com.esotericsoftware.spine.include.Disposable;
import com.esotericsoftware.spine.include.files.GdxRuntimeException;
import com.esotericsoftware.spine.include.files.StreamUtils;
import com.esotericsoftware.spine.include.texture.ObjectSet;
import com.esotericsoftware.spine.include.texture.Texture;
import com.esotericsoftware.spine.include.texture.TextureRegion;
import com.esotericsoftware.spine.include.utils.Array;
import com.esotericsoftware.spine.include.utils.ObjectMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Comparator;


/**
 * created by wiesen
 * time : 2018/6/1
 */
public class Atlas implements Disposable{
    static final String[] tuple = new String[4];
    private final ObjectSet<Texture> textures = new ObjectSet(4);
    private final Array<AtlasRegion> regions = new Array<>();

    @Override
    public void dispose() {

    }

    public static class AtlasData{
        private Array<Page> pages = new Array<>();
        private Array<Region> regions = new Array<>();

        public static class Page{
            public final Texture texture;
            public final float width, height;
            public final boolean useMipMaps;
            public final AtlasFormat format;
            public final AtlasFilter minFilter;
            public final AtlasFilter magFilter;
            public final AtlasWrap uWrap;
            public final AtlasWrap vWrap;


            public Page(Texture texture, float width, float height,
                        boolean useMipMaps, AtlasFormat format,
                        AtlasFilter minFilter, AtlasFilter magFilter,
                        AtlasWrap uWrap, AtlasWrap vWrap) {
                this.texture = texture;
                this.width = width;
                this.height = height;
                this.useMipMaps = useMipMaps;
                this.format = format;
                this.minFilter = minFilter;
                this.magFilter = magFilter;
                this.uWrap = uWrap;
                this.vWrap = vWrap;
            }
        }

        public static class Region{
            public Page page;
            public int index;
            public String name;
            public float offsetX;
            public float offsetY;
            public int originalWidth;
            public int originalHeight;
            public boolean rotate;
            public int left;
            public int top;
            public int width;
            public int height;
            public boolean flip;
            public int[] splits;
            public int[] pads;
        }

        public AtlasData(String atlasStr, byte[] imgByte, boolean flip) {
            StringReader stringReader = new StringReader(atlasStr);
            BufferedReader reader = new BufferedReader(stringReader, 64);
            try {
                Page pageImage = null;
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;
                    if (line.trim().length() == 0)
                        pageImage = null;
                    else if (pageImage == null) {

                        float width = 0, height = 0;
                        if (readTuple(reader) == 2) { // size is only optional for an atlas packed with an old TexturePacker.
                            width = Integer.parseInt(tuple[0]);
                            height = Integer.parseInt(tuple[1]);
                            readTuple(reader);
                        }
                        AtlasFormat format = AtlasFormat.valueOf(tuple[0]);

                        readTuple(reader);
                        AtlasFilter min = AtlasFilter.valueOf(tuple[0]);
                        AtlasFilter max = AtlasFilter.valueOf(tuple[1]);

                        String direction = readValue(reader);
                        AtlasWrap repeatX = AtlasWrap.ClampToEdge;
                        AtlasWrap repeatY = AtlasWrap.ClampToEdge;
                        if (direction.equals("x"))
                            repeatX = AtlasWrap.Repeat;
                        else if (direction.equals("y"))
                            repeatY = AtlasWrap.Repeat;
                        else if (direction.equals("xy")) {
                            repeatX = AtlasWrap.Repeat;
                            repeatY = AtlasWrap.Repeat;
                        }

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.outWidth = (int) width;
                        options.outHeight = (int) height;
                        Bitmap textureBmp = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);
                        Texture texture = new Texture(textureBmp, min.isMipMap(), format, min, max, repeatX, repeatY);
                        pageImage = new Page(texture, width, height, min.isMipMap(), format, min, max, repeatX, repeatY);
                        pages.add(pageImage);
                    } else {
                        boolean rotate = Boolean.valueOf(readValue(reader));

                        readTuple(reader);
                        int left = Integer.parseInt(tuple[0]);
                        int top = Integer.parseInt(tuple[1]);

                        readTuple(reader);
                        int width = Integer.parseInt(tuple[0]);
                        int height = Integer.parseInt(tuple[1]);

                        Region region = new Region();
                        region.page = pageImage;
                        region.left = left;
                        region.top = top;
                        region.width = width;
                        region.height = height;
                        region.name = line;
                        region.rotate = rotate;

                        if (readTuple(reader) == 4) { // split is optional
                            region.splits = new int[] {Integer.parseInt(tuple[0]), Integer.parseInt(tuple[1]),
                                    Integer.parseInt(tuple[2]), Integer.parseInt(tuple[3])};

                            if (readTuple(reader) == 4) { // pad is optional, but only present with splits
                                region.pads = new int[] {Integer.parseInt(tuple[0]), Integer.parseInt(tuple[1]),
                                        Integer.parseInt(tuple[2]), Integer.parseInt(tuple[3])};

                                readTuple(reader);
                            }
                        }

                        region.originalWidth = Integer.parseInt(tuple[0]);
                        region.originalHeight = Integer.parseInt(tuple[1]);

                        readTuple(reader);
                        region.offsetX = Integer.parseInt(tuple[0]);
                        region.offsetY = Integer.parseInt(tuple[1]);

                        region.index = Integer.parseInt(readValue(reader));

                        if (flip) region.flip = true;

                        regions.add(region);
                    }
                }
            } catch (Exception ex) {
                throw new GdxRuntimeException("Error reading pack file: ", ex);
            } finally {
                StreamUtils.closeQuietly(reader);
            }

            regions.sort(indexComparator);
        }

        public Array<Page> getPages() {
            return pages;
        }

        public Array<Region> getRegions() {
            return regions;
        }
    }

    public Atlas(String atlasStr, byte[] imgStream) {
        load(new AtlasData(atlasStr, imgStream, false));
    }

    private void load (AtlasData data) {
        ObjectMap<AtlasData.Page, Texture> pageToTexture = new ObjectMap<>();
        for (AtlasData.Page page : data.pages) {
            textures.add(page.texture);
            pageToTexture.put(page, page.texture);
        }

        for (AtlasData.Region region : data.regions) {
            int width = region.width;
            int height = region.height;
            AtlasRegion atlasRegion = new AtlasRegion(pageToTexture.get(region.page), region.left, region.top,
                    region.rotate ? height : width, region.rotate ? width : height);
            atlasRegion.index = region.index;
            atlasRegion.name = region.name;
            atlasRegion.offsetX = region.offsetX;
            atlasRegion.offsetY = region.offsetY;
            atlasRegion.originalHeight = region.originalHeight;
            atlasRegion.originalWidth = region.originalWidth;
            atlasRegion.rotate = region.rotate;
            atlasRegion.splits = region.splits;
            atlasRegion.pads = region.pads;
            if (region.flip) atlasRegion.flip(false, true);
            regions.add(atlasRegion);
        }
    }


    /** Returns all regions in the atlas. */
    public Array<AtlasRegion> getRegions () {
        return regions;
    }

    /** Returns the first region found with the specified name. This method uses string comparison to find the region, so the result
     * should be cached rather than calling this method multiple times.
     * @return The region, or null. */
    public AtlasRegion findRegion (String name) {
        for (int i = 0, n = regions.size; i < n; i++)
            if (regions.get(i).name.equals(name)) return regions.get(i);
        return null;
    }

    /** Returns the first region found with the specified name and index. This method uses string comparison to find the region, so
     * the result should be cached rather than calling this method multiple times.
     * @return The region, or null. */
    public AtlasRegion findRegion (String name, int index) {
        for (int i = 0, n = regions.size; i < n; i++) {
            AtlasRegion region = regions.get(i);
            if (!region.name.equals(name)) continue;
            if (region.index != index) continue;
            return region;
        }
        return null;
    }

    /** Returns all regions with the specified name, ordered by smallest to largest {@link AtlasRegion#index index}. This method
     * uses string comparison to find the regions, so the result should be cached rather than calling this method multiple times. */
    public Array<AtlasRegion> findRegions (String name) {
        Array<AtlasRegion> matched = new Array();
        for (int i = 0, n = regions.size; i < n; i++) {
            AtlasRegion region = regions.get(i);
            if (region.name.equals(name)) matched.add(new AtlasRegion(region));
        }
        return matched;
    }


    public enum AtlasWrap{
        MirroredRepeat(GLES20.GL_MIRRORED_REPEAT), ClampToEdge(GLES20.GL_CLAMP_TO_EDGE), Repeat(GLES20.GL_REPEAT);

        final int glEnum;

        AtlasWrap (int glEnum) {
            this.glEnum = glEnum;
        }

        public int getGLEnum () {
            return glEnum;
        }
    }

    public enum AtlasFormat{
        Alpha,
        Intensity,
        LuminanceAlpha,
        RGB565,
        RGBA4444,
        RGB888,
        RGBA8888;
    }

    public static int toGLFormat(AtlasFormat format){
        switch (format) {
            case Alpha:
                return GLES20.GL_ALPHA;
            case LuminanceAlpha:
                return GLES20.GL_LUMINANCE_ALPHA;
            case RGB888:
            case RGB565:
                return GLES20.GL_RGB;
            case RGBA8888:
            case RGBA4444:
                return GLES20.GL_RGBA;
            default:
                throw new GdxRuntimeException("unknown format: " + format);
        }
    }

    public enum AtlasFilter{
        Nearest(GLES20.GL_NEAREST), Linear(GLES20.GL_LINEAR), MipMap(GLES20.GL_LINEAR_MIPMAP_LINEAR), MipMapNearestNearest(
                GLES20.GL_NEAREST_MIPMAP_NEAREST), MipMapLinearNearest(GLES20.GL_LINEAR_MIPMAP_NEAREST), MipMapNearestLinear(
                GLES20.GL_NEAREST_MIPMAP_LINEAR), MipMapLinearLinear(GLES20.GL_LINEAR_MIPMAP_LINEAR);

        final int glEnum;

        AtlasFilter (int glEnum) {
            this.glEnum = glEnum;
        }

        public boolean isMipMap () {
            return glEnum != GLES20.GL_NEAREST && glEnum != GLES20.GL_LINEAR;
        }

        public int getGLEnum () {
            return glEnum;
        }
    }


    static final Comparator<AtlasData.Region> indexComparator = new Comparator<AtlasData.Region>() {
        public int compare (AtlasData.Region region1, AtlasData.Region region2) {
            int i1 = region1.index;
            if (i1 == -1) i1 = Integer.MAX_VALUE;
            int i2 = region2.index;
            if (i2 == -1) i2 = Integer.MAX_VALUE;
            return i1 - i2;
        }
    };

    static String readValue (BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int colon = line.indexOf(':');
        if (colon == -1) throw new GdxRuntimeException("Invalid line: " + line);
        return line.substring(colon + 1).trim();
    }

    /** Returns the number of tuple values read (1, 2 or 4). */
    static int readTuple (BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int colon = line.indexOf(':');
        if (colon == -1) throw new GdxRuntimeException("Invalid line: " + line);
        int i = 0, lastMatch = colon + 1;
        for (i = 0; i < 3; i++) {
            int comma = line.indexOf(',', lastMatch);
            if (comma == -1) break;
            tuple[i] = line.substring(lastMatch, comma).trim();
            lastMatch = comma + 1;
        }
        tuple[i] = line.substring(lastMatch).trim();
        return i + 1;
    }


    /** Describes the region of a packed image and provides information about the original image before it was packed. */
    static public class AtlasRegion extends TextureRegion {
        /** The number at the end of the original image file name, or -1 if none.<br>
         * <br>
         * When sprites are packed, if the original file name ends with a number, it is stored as the index and is not considered as
         * part of the sprite's name. This is useful for keeping animation frames in order.
         * @see Atlas#findRegions(String) */
        public int index;

        /** The name of the original image file, up to the first underscore. Underscores denote special instructions to the texture
         * packer. */
        public String name;

        /** The offset from the left of the original image to the left of the packed image, after whitespace was removed for packing. */
        public float offsetX;

        /** The offset from the bottom of the original image to the bottom of the packed image, after whitespace was removed for
         * packing. */
        public float offsetY;

        /** The width of the image, after whitespace was removed for packing. */
        public int packedWidth;

        /** The height of the image, after whitespace was removed for packing. */
        public int packedHeight;

        /** The width of the image, before whitespace was removed and rotation was applied for packing. */
        public int originalWidth;

        /** The height of the image, before whitespace was removed for packing. */
        public int originalHeight;

        /** If true, the region has been rotated 90 degrees counter clockwise. */
        public boolean rotate;

        /** The ninepatch splits, or null if not a ninepatch. Has 4 elements: left, right, top, bottom. */
        public int[] splits;

        /** The ninepatch pads, or null if not a ninepatch or the has no padding. Has 4 elements: left, right, top, bottom. */
        public int[] pads;


        public AtlasRegion (Texture texture, int x, int y, int width, int height) {
            super(texture, x, y, width, height);
            originalWidth = width;
            originalHeight = height;
            packedWidth = width;
            packedHeight = height;
        }

        public AtlasRegion (AtlasRegion region) {
            setRegion(region);
            index = region.index;
            name = region.name;
            offsetX = region.offsetX;
            offsetY = region.offsetY;
            packedWidth = region.packedWidth;
            packedHeight = region.packedHeight;
            originalWidth = region.originalWidth;
            originalHeight = region.originalHeight;
            rotate = region.rotate;
            splits = region.splits;
        }

        @Override
        /** Flips the region, adjusting the offset so the image appears to be flip as if no whitespace has been removed for packing. */
        public void flip (boolean x, boolean y) {
            super.flip(x, y);
            if (x) offsetX = originalWidth - offsetX - getRotatedPackedWidth();
            if (y) offsetY = originalHeight - offsetY - getRotatedPackedHeight();
        }

        /** Returns the packed width considering the rotate value, if it is true then it returns the packedHeight, otherwise it
         * returns the packedWidth. */
        public float getRotatedPackedWidth () {
            return rotate ? packedHeight : packedWidth;
        }

        /** Returns the packed height considering the rotate value, if it is true then it returns the packedWidth, otherwise it
         * returns the packedHeight. */
        public float getRotatedPackedHeight () {
            return rotate ? packedWidth : packedHeight;
        }

        public String toString () {
            return name;
        }
    }
}
