package com.example.camera.sensor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.camera.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 2.深度揭秘android摄像头的autoFocus-----循环自动聚焦的实现（Android Camera AutoFocus on Demand）
 * https://blog.csdn.net/yanzi1225627/article/details/8577682
 * <p>
 *   第四：通过监听传感器来触发autoFocus。常用的是角度传感器或加速度传感器，当监听的值超过一定阈值时触发。这也是国外程序员们常用的思路。
 * 参见：http://stackoverflow.com/questions/5878042/android-camera-autofocus-on-demand
 */
public class CameraPreviewActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "CameraPreviewActivity";
    private Preview mPreview;
    private ImageView mTakePicture;
    private TouchView mTouchView;

    private boolean mAutoFocus = true;

    private boolean mFlashBoolean = false;

    private SensorManager mSensorManager;
    private Sensor mAccel;
    private boolean mInitialized = false;
    private float mLastX = 0;
    private float mLastY = 0;
    private float mLastZ = 0;
    private Rect rec = new Rect();

    private int mScreenHeight;
    private int mScreenWidth;

    private boolean mInvalidate = false;

    private File mLocation = new File(Environment.
            getExternalStorageDirectory(), "test.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i(TAG, "onCreate()");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //
        getSupportActionBar().hide();

        // display our (only) XML layout - Views already ordered
        setContentView(R.layout.activity_camera_preview);

        // the accelerometer is used for autofocus
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // get the window width and height to display buttons
        // according to device screen size
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mScreenHeight = displaymetrics.heightPixels;
        mScreenWidth = displaymetrics.widthPixels;
        // I need to get the dimensions of this drawable to set margins
        // for the ImageView that is used to take pictures
        Log.d(TAG, "onCreate: mScreenHeight = " + mScreenHeight + " mScreenWidth = " + mScreenWidth);

        Drawable mButtonDrawable = this.getResources().
                getDrawable(R.drawable.camera);

        mTakePicture = findViewById(R.id.startcamerapreview);

        // setting where I will draw the ImageView for taking pictures
        /*RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)(mTakePicture.getLayoutParams());
        lp.setMargins((int) ((double) mScreenWidth * .85),
                (int) ((double) mScreenHeight * .70),
                (int) ((double) mScreenWidth * .85) + mButtonDrawable.
                        getMinimumWidth(),
                (int) ((double) mScreenHeight * .70) + mButtonDrawable.
                        getMinimumHeight());
        mTakePicture.setLayoutParams(lp);*/


        // rec is used for onInterceptTouchEvent. I pass this from the
        // highest to lowest layer so that when this area of the screen
        // is pressed, it ignores the TouchView events and passes it to
        // this activity so that the button can be pressed.
        //设置拍照按钮区域事件拦截

        // TODO ming
        mTakePicture.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mTakePicture.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                rec.set(mTakePicture.getLeft(), mTakePicture.getTop(), mTakePicture.getRight(), mTakePicture.getBottom());
                mTouchView.setRec(rec);
            }
        });

        //
        /*rec.set((int) ((double) mScreenWidth * .85),
                (int) ((double) mScreenHeight * .10),
                (int) ((double) mScreenWidth * .85) + mButtonDrawable.getMinimumWidth(),
                (int) ((double) mScreenHeight * .70) + mButtonDrawable.getMinimumHeight());*/

        mTakePicture.setOnClickListener(previewListener);
        // get our Views from the XML layout
        mPreview = findViewById(R.id.preview);
        mTouchView = findViewById(R.id.left_top_view);

        //
        //mTouchView.setRec(rec);

    }

    // this is the autofocus call back
    private Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback() {

        public void onAutoFocus(boolean autoFocusSuccess, Camera arg1) {
            //Wait.oneSec();
            mAutoFocus = true;
        }
    };

    // with this I get the ratio between screen size and pixels
    // of the image so I can capture only the rectangular area of the
    // image and save it.
    public Double[] getRatio() {
        Camera.Size s = mPreview.getCameraParameters().getPreviewSize();
        double heightRatio = (double) s.height / (double) mScreenHeight;
        double widthRatio = (double) s.width / (double) mScreenWidth;
        Double[] ratio = {heightRatio, widthRatio};
        return ratio;
    }

    // I am not using this in this example, but its there if you want
    // to turn on and off the flash.
    private View.OnClickListener flashListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mFlashBoolean) {
                mPreview.setFlash(false);
            } else {
                mPreview.setFlash(true);
            }
            mFlashBoolean = !mFlashBoolean;
        }

    };

    // This method takes the preview image, grabs the rectangular
    // part of the image selected by the bounding box and saves it.
    // A thread is needed to save the picture so not to hold the UI thread.
    private View.OnClickListener previewListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mAutoFocus) {
                mAutoFocus = false;
                //mPreview.setCameraFocus(myAutoFocusCallback);
                Wait.oneSec();

                Thread tGetPic = new Thread(new Runnable() {
                    public void run() {
                        Double[] ratio = getRatio();
                        int left = (int) (ratio[1] * (double) mTouchView.getmLeftTopPosX());
                        // 0 is height
                        int top = (int) (ratio[0] * (double) mTouchView.getmLeftTopPosY());

                        int right = (int) (ratio[1] * (double) mTouchView.getmRightBottomPosX());

                        int bottom = (int) (ratio[0] * (double) mTouchView.getmRightBottomPosY());

                        savePhoto(mPreview.getPic(left, top, right, bottom));

                        mAutoFocus = true;
                    }
                });
                tGetPic.start();
            }
            boolean pressed = false;
            if (!mTakePicture.isPressed()) {
                pressed = true;
            }
        }
    };

    // just to close the app and release resources.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean savePhoto(Bitmap bm) {
        FileOutputStream image = null;
        File outputMediaFile = getOutputMediaFile();
        try {
            image = new FileOutputStream(outputMediaFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bm.compress(Bitmap.CompressFormat.JPEG, 100, image);
        try {
            image.flush();
            image.close();
            //通知图库更新
            notifyAlbum(outputMediaFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bm != null) {
            int h = bm.getHeight();
            int w = bm.getWidth();
            //Log.i(TAG, "savePhoto(): Bitmap WxH is " + w + "x" + h);
        } else {
            //Log.i(TAG, "savePhoto(): Bitmap is null..");
            return false;
        }
        return true;
    }

    /**
     * 在手机相册中显示刚拍摄的图片
     */
    private void notifyAlbum(File pictureFile) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(pictureFile);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CameraPreviewActivity.this, "拍照成功", Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ming");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("ming", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }


    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        boolean intercept = false;
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                float x = ev.getX();
                float y = ev.getY();
                // here we intercept the button press and give it to this
                // activity so the button press can happen and we can take
                // a picture.
                if ((x >= rec.left) && (x <= rec.right) && (y >= rec.top) && (y <= rec.bottom)) {
                    intercept = true;
                }
                break;
        }
        return intercept;
    }

    // mainly used for autofocus to happen when the user takes a picture
    // I also use it to redraw the canvas using the invalidate() method
    // when I need to redraw things.
    public void onSensorChanged(SensorEvent event) {

        if (mInvalidate == true) {
            mTouchView.invalidate();
            mInvalidate = false;
        }
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            mInitialized = true;
        }
        float deltaX = Math.abs(mLastX - x);
        float deltaY = Math.abs(mLastY - y);
        float deltaZ = Math.abs(mLastZ - z);

        if (deltaX > .5 && mAutoFocus) { //AUTOFOCUS (while it is not autofocusing)
            mAutoFocus = false;
            mPreview.setCameraFocus(myAutoFocusCallback);
        }
        if (deltaY > .5 && mAutoFocus) { //AUTOFOCUS (while it is not autofocusing)
            mAutoFocus = false;
            mPreview.setCameraFocus(myAutoFocusCallback);
        }
        if (deltaZ > .5 && mAutoFocus) { //AUTOFOCUS (while it is not autofocusing) */
            mAutoFocus = false;
            mPreview.setCameraFocus(myAutoFocusCallback);
        }

        mLastX = x;
        mLastY = y;
        mLastZ = z;

    }

    // extra overrides to better understand app lifecycle and assist debugging
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.i(TAG, "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.i(TAG, "onPause()");
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_UI);
        //Log.i(TAG, "onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.i(TAG, "onStop()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.i(TAG, "onStart()");
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

}
