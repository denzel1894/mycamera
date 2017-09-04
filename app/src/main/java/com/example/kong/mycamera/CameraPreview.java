package com.example.kong.mycamera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by fanxu.kong on 17/9/4.
 *
 * camera 预览view
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{
    private final static String tag = "CameraPreview";
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.mCamera = camera;

        // 配置回调从而当surface创建或者销毁等时候会收到通知来进行相应的处理
        mHolder = getHolder();
        mHolder.addCallback(this);
        // 3.0版本以下需要以下设置
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();;
        } catch (Exception e) {
            Log.d(tag, "error setting camera preview "+ e.getMessage());
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (mHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            Log.d(tag, "stopPreview() failed");
        }

        /**
         * 在这里可以做一些preview的改变动作
         */

        // 在新设置下重新预览
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e){
            Log.d(tag, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(tag, "surfaceDestroyed");
    }
}
