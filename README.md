# SurfaceView游戏应用

![SurfaceView案例](/readme_img/a0.png)

![SurfaceView案例](/readme_img/a1.png)


## 课程介绍

    在Android系统中，有一种特殊的视图：SurfaceView，它拥有独立的绘图表面，游戏等应用离不开它的身影。
    
## SurfaceView简介

1. SurfaceView与view的区别

   - 不使用onDraw
   -  非UI线程绘制 
   -  独立的Surface

2. SurfaceView的具体使用场景

    - 视频播放
    - 一些炫酷的动画效果
    - 小游戏

3. 如何使用SurfaceView

    - 利用SufaceHodler监听Surface创建完毕
    - 开启异步线程进行while循环
    - 通过SurfaceHolder获取Canvas进行绘制
    
4. 使用SurfaceView的步骤
    
    - 获取SurfaceHolder对象
    - 监听Surface的创建
    - 开启子线程,在线程中使用循环处理绘制
    - 获取Canvas进行绘制