package com.example.camera.mycamera;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by madmatrix on 2014/24/02.
 */
public class CameraManager {
    private static final String TAG = CameraActivity.TAG;

    private Camera camera;

    /**
     * 前置/后置摄像头
     */
    private int facing;
    private CameraActivity cameraActivity;
    private CameraConfiguration configuration;
    private CameraPreview cameraPreview;
    private MyOrientationEventListener orientationEventListener;
    private AutoFocusManager autoFocusManager;

    /**
     * 记录手机的上一个方向
     */
    private int lastAutofocusOrientation = 0;

    /**
     * 按钮的上一次角度
     */
    private int lastBtOrientation = 0;

    private MyCameraButtonAnimation buttonAnimation;

    private SurfaceView surfaceView;

    public CameraManager(CameraActivity cameraActivity, SurfaceView surfaceView, MyCameraButtonAnimation buttonAnimation) {
        this.cameraActivity = cameraActivity;
        configuration = new CameraConfiguration(cameraActivity);
        facing = Camera.CameraInfo.CAMERA_FACING_BACK;
        this.surfaceView = surfaceView;
        cameraPreview = new CameraPreview();
        orientationEventListener = new MyOrientationEventListener(cameraActivity);
        this.buttonAnimation = buttonAnimation;
    }

    public Camera openCamera() {
        releaseCamera();
        Log.d(TAG, "open the " + getFacingDesc(facing) + " camera");

        if (facing != Camera.CameraInfo.CAMERA_FACING_BACK && facing != Camera.CameraInfo.CAMERA_FACING_FRONT) {
            Log.w(TAG, "invalid facing " + facing + ", use default CAMERA_FACING_BACK");
            facing = Camera.CameraInfo.CAMERA_FACING_BACK;
        }

        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            return null;
        }

        int index = 0;
        while (index < numCameras) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(index, cameraInfo);
            if (cameraInfo.facing == facing) {
                break;
            }
            ++index;
        }

        if (index < numCameras) {
            camera = Camera.open(index);
        } else {
            camera = Camera.open(0);
        }

        configuration.config(camera);
        cameraPreview.startPreview();
        orientationEventListener.enable();

        return camera;
    }

    /**
     * 拍照
     */
    public void takePicture() {
        camera.takePicture(null, null, pictureCallback);
    }

    /**
     * 切换前置/后置摄像头
     */
    public void switchCamera() {
        if (Camera.CameraInfo.CAMERA_FACING_BACK == facing) {
            facing = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            facing = Camera.CameraInfo.CAMERA_FACING_BACK;
        }

        releaseCamera();
        openCamera();
        cameraPreview.startPreview();
    }

    public void releaseCamera() {
        orientationEventListener.disable();

        if (null != camera) {
            cameraPreview.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private class PicSaveTask extends AsyncTask<byte[], Void, File> {
        @Override
        protected File doInBackground(byte[]... datas) {
            byte[] data = datas[0];
            File pictureFile = null;
            try {
                pictureFile = configuration.getPictureStorageFile();
            } catch (Exception e) {
                Log.e(TAG, "failed to create file: " + e.getMessage());
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();

                //通知更新图库
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(pictureFile);
                mediaScanIntent.setData(contentUri);
                cameraActivity.sendBroadcast(mediaScanIntent);

            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }

            return pictureFile;
        }

        @Override
        protected void onPostExecute(File pictureFile) {
            super.onPostExecute(pictureFile);

            Toast.makeText(cameraActivity, "图片保存图库成功！", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "the picture saved in " + pictureFile.getAbsolutePath());

            camera.startPreview();
        }
    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if (null == data || data.length == 0) {
                Toast.makeText(cameraActivity, "拍照失败，请重试！", Toast.LENGTH_SHORT).show();

                Log.e(TAG, "No media data returned");
                return;
            }

            new PicSaveTask().execute(data);
        }
    };

    private class MyOrientationEventListener extends OrientationEventListener {

        public MyOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation == ORIENTATION_UNKNOWN) return;

            // 设置camera旋转角度以使最终照片与预览界面方向一致
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(facing, info);
            int fixedOrientation = (orientation + 45) / 90 * 90;

            int rotation = 0;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                rotation = (info.orientation - fixedOrientation + 360) % 360;
            } else {  // back-facing camera
                rotation = (info.orientation + fixedOrientation) % 360;
            }

            Camera.Parameters cameraParameters = camera.getParameters();
            cameraParameters.setRotation(rotation);
            camera.setParameters(cameraParameters);


            // 手机旋转超过30°就重新对焦
            // 处理启动时orientation临界值情况
            if (orientation > 180 && lastAutofocusOrientation == 0) {
                lastAutofocusOrientation = 360;
            }
            if (Math.abs(orientation - lastAutofocusOrientation) > 30) {
                Log.d(TAG, "orientation=" + orientation + ", lastAutofocusOrientation=" + lastAutofocusOrientation);

                autoFocusManager.autoFocus();
                lastAutofocusOrientation = orientation;
            }


            // 使按钮随手机转动方向旋转
            // 按钮图片的旋转方向应当与手机的旋转方向相反，这样最终方向才能保持一致
            int phoneRotation = 0;
            if (orientation > 315 && orientation <= 45) {
                phoneRotation = 0;
            } else if (orientation > 45 && orientation <= 135) {
                phoneRotation = 90;
            } else if (orientation > 135 && orientation <= 225) {
                phoneRotation = 180;
            } else if (orientation > 225 && orientation <= 315) {
                phoneRotation = 270;
            }

            // 恢复自然方向时置零
            if (phoneRotation == 0 && lastBtOrientation == 360) {
                lastBtOrientation = 0;
            }

            // "就近处理"：为了让按钮旋转走"捷径"，如果起始角度与结束角度差超过180，则将为0的那个值换为360
            if ((phoneRotation == 0 || lastBtOrientation == 0) && (Math.abs(phoneRotation - lastBtOrientation) > 180)) {
                phoneRotation = phoneRotation == 0 ? 360 : phoneRotation;
                lastBtOrientation = lastBtOrientation == 0 ? 360 : lastBtOrientation;
            }

            if (phoneRotation != lastBtOrientation) {
                int fromDegress = 360 - lastBtOrientation;
                int toDegrees = 360 - phoneRotation;

                Log.i(TAG, "fromDegress=" + fromDegress + ", toDegrees=" + toDegrees);

                RotateAnimation animation = new RotateAnimation(fromDegress, toDegrees,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                buttonAnimation.executeAnimation(animation);
                lastBtOrientation = phoneRotation;
            }
        }
    }

    private static String getFacingDesc(int facing) {
        if (facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            return "front";
        } else {
            return "back";
        }
    }

    /**
     * 固定portrait模式下，无需调用此函数
     */
    private void setCameraDisplayOrientation() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(facing, info);
        int rotation = cameraActivity.getWindowManager().getDefaultDisplay().getRotation();

        Log.d(TAG, "[1492]rotation=" + rotation);

        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        Log.d(TAG, "[1492]info.orientation=" + info.orientation + ", degrees=" + degrees);

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        Log.d(TAG, "[1492]setCameraDisplayOrientation, result=" + result);

        camera.setDisplayOrientation(result);
    }


    private class CameraPreview implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;

        public CameraPreview() {
            mHolder = surfaceView.getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void startPreview() {
            try {
                camera.setPreviewDisplay(mHolder);
                camera.setDisplayOrientation(90);
                camera.startPreview();

                autoFocusManager = new AutoFocusManager(camera);
                autoFocusManager.startAutoFocus();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        public void stopPreview() {
            try {
                camera.stopPreview();
                autoFocusManager.stopAutoFocus();
            } catch (Exception e) {

            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            Log.d(TAG, "surfaceCreated");
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
            Log.d(TAG, "surfaceChanged");
            if (surfaceHolder.getSurface() == null) {
                return;
            }

            stopPreview();
            startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            Log.d(TAG, "surfaceDestroyed");
        }
    }


}
