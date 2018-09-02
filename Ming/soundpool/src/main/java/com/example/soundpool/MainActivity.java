package com.example.soundpool;

import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button mBtnStart;
    private SoundPool mSoundPool;
    private int mSoundID_fdBgSound;
    private int mSoundID_fdBjbs;
    private Button mBtnStopBg;
    private Button mBtnStopTx;
    private Handler mHandler;
    private int mStreamId_fdBgSound;
    private int mStreamId_fdBjbs;
    private Button mBtnChangeBgVolume;
    private Button mBtnChangeTxVolume;
    private float bgSoundVolumeValue = 1f;
    private float txVolumeValue = 1f;
    private SoundPool mSoundPoolTX;
    private int mSoundID_tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mHandler = new Handler();

        mBtnStart = findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initSoundPoolBG();
                initSoundPoolTX();

            }
        });

        mBtnStopBg = findViewById(R.id.btn_stopBg);
        mBtnStopBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSoundPoolTX.stop(mStreamId_fdBjbs);
            }
        });

        mBtnStopTx = findViewById(R.id.btn_stopTx);
        mBtnStopTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mSoundPool.resume(mSoundID_fdBgSound);
            }
        });

        mBtnChangeBgVolume = findViewById(R.id.btn_changeBgVolume);
        mBtnChangeBgVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bgSoundVolumeValue>=0){
                    bgSoundVolumeValue = bgSoundVolumeValue -0.2f;
                    mSoundPool.setVolume(mStreamId_fdBgSound, bgSoundVolumeValue, bgSoundVolumeValue);
                }


            }
        });

        mBtnChangeTxVolume = findViewById(R.id.btn_changeTxVolume);
        mBtnChangeTxVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txVolumeValue>=0){
                    txVolumeValue = txVolumeValue -0.2f;
                    mSoundPoolTX.setVolume(mStreamId_fdBjbs, txVolumeValue, txVolumeValue);
                }

            }
        });
    }

    private void initSoundPoolTX() {
        mSoundPoolTX = new SoundPool(100, AudioManager.STREAM_MUSIC, 100);
        try {
            AssetFileDescriptor fdBjbs = getAssets().openFd("GameSound/FT/bgSound.mp3");
            mSoundPoolTX.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                    mStreamId_fdBjbs = mSoundPoolTX.play(mSoundID_fdBjbs, 1f, 1f, 1, 0, 1);

                }
            });

            mSoundID_fdBjbs = mSoundPoolTX.load(fdBjbs, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initSoundPoolBG() {
        //适用与5.0以下
        mSoundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 100);

        //从5.0开始支持
        /*AudioAttributes abs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build() ;

        SoundPool soundPool =  new SoundPool.Builder()
                .setMaxStreams(100)   //设置允许同时播放的流的最大值
                .setAudioAttributes(abs)   //完全可以设置为null
                .build() ;*/

        try {
            AssetFileDescriptor fdBgSound = getAssets().openFd("GameSound/21D/bgSound.mp3");
            //AssetFileDescriptor fdBjbs = getAssets().openFd("GameSound/FT/bgSound.mp3");

            mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                    // TODO  播放失败判断

                    //mSoundPool = soundPool;

                    mStreamId_fdBgSound = mSoundPool.play(mSoundID_fdBgSound, 1f, 1f, 1, 0, 1);

                    //mStreamId_fdBjbs = mSoundPool.play(mSoundID_fdBjbs, 1f, 1f, 1, 0, 1);

                }
            });


            //mSoundID_fdBjbs = mSoundPool.load(fdBjbs, 1);
            mSoundID_fdBgSound = mSoundPool.load(fdBgSound, 1);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
