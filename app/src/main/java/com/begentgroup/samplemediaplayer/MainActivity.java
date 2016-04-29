package com.begentgroup.samplemediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    enum PlayState {
        IDLE,
        INITIALIZED,
        PREPARED,
        STARTED,
        PAUSED,
        STOPPED,
        ERROR,
        END
    }

    PlayState mState = PlayState.IDLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mPlayer = new MediaPlayer();
//        mState = PlayState.IDLE;
//
//        AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.winter_blues);
//        try {
//            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//            mState = PlayState.INITIALIZED;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (mState == PlayState.INITIALIZED) {
//            try {
//                mPlayer.prepare();
//                mState = PlayState.PREPARED;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
        mState = PlayState.PREPARED;

        Button btn = (Button)findViewById(R.id.btn_play);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mState == PlayState.INITIALIZED || mState == PlayState.STOPPED) {
                    try {
                        mPlayer.prepare();
                        mState = PlayState.PREPARED;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (mState == PlayState.PREPARED || mState == PlayState.PAUSED) {
                    mPlayer.start();
                    mState = PlayState.STARTED;
                }
            }
        });

        btn = (Button)findViewById(R.id.btn_pause);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mState == PlayState.STARTED) {
                    mPlayer.pause();
                    mState = PlayState.PAUSED;
                }
            }
        });

        btn = (Button)findViewById(R.id.btn_stop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mState == PlayState.STARTED || mState == PlayState.PAUSED) {
                    mPlayer.stop();
                    mState = PlayState.STOPPED;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
        mState = PlayState.END;
        mPlayer = null;
    }
}
