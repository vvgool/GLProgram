# 知识笔记

1. 在不同的GlSurfaceView中不可共用ProgramId 和TextureId，否则导致
绘制失败
2. 开启透明通道使背景透明，并设置使GlSurfaceView显示在顶层方法：
```
    setEGLConfigChooser(8, 8, 8, 8, 16, 0);//使用8888 (RGBA) 格式，Alpha通道启用
    getHolder().setFormat(PixelFormat.TRANSLUCENT);//是为GLView指定透明通道
    setZOrderOnTop(true);//必须，调用此方法才能让背景透明
```

>>注意：setZOrderOnTop(true);必须在setRender之前调用
使用后遇到问题：
其他view和viewGroup无法遮挡GlSurfaceView,其永远悬浮在
顶层。
使用dialog可以避免此问题。

开启透明通道后可以避免的一些问题：
view切换到GlSurfaceView的过程中会黑屏问题，
使GlSurfaceView背景透明

3. setZOrderOnTop 和 setZOrderMediaOverlay的区别，
setZOrderOnTop：在多层GlSurfaceView重叠时，使当前层
显示在顶层：（GlSurfaceView在添加到window时时纵深添加，
每次添加到都在GlSurfaceView的底层）
setZOrderMediaOverlay：使用效果类似setZOrderOnTop，
不同的地方在于绘制在GlSurfaceView顶层的普通view和viewGroup
会被重新GlSurfaceView中的内容覆盖
