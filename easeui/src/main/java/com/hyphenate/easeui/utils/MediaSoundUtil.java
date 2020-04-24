package com.hyphenate.easeui.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.hyphenate.easeui.R;

import java.util.HashMap;

public class MediaSoundUtil {

    private static final int KEY_RING = 1;
    private static final int KEY_REJECT = 2;

    private static final String TAG = MediaSoundUtil.class.getSimpleName();

    private static volatile MediaSoundUtil sSoundPoolUtil;
    private final Context mContext;
    private final SoundPool mSoundPool;
    private final HashMap<Integer, Integer> mSoundMap;
    private int mCurrentId;
    //声音资源加载完成的标识位
    private boolean mHasLoaded;

    public MediaSoundUtil(Context context) {
        mContext = context.getApplicationContext();
        mSoundPool = new SoundPool(2, AudioManager.STREAM_VOICE_CALL,0);
        mSoundMap = new HashMap<>();

        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.i(TAG,"onLoadComplete "+sampleId+"staus "+status);
                mHasLoaded = true;
            }
        });

        mSoundMap.put(KEY_RING,mSoundPool.load(mContext, R.raw.em_outgoing,1));
        mSoundMap.put(KEY_REJECT,mSoundPool.load(mContext,R.raw.em_outgoing,1));
        Log.i(TAG,"MediaSoundUtil init map = "+mSoundMap);
    }

    public static MediaSoundUtil getInstance(Context context) {
        if (sSoundPoolUtil == null) {
            synchronized (MediaSoundUtil.class) {
                if (sSoundPoolUtil == null) {
                    sSoundPoolUtil = new MediaSoundUtil(context);
                }
            }
        }
        return sSoundPoolUtil;
    }

    public void playRingSound() {
        stopPlay();
        Log.i(TAG, "playRingSound " + mCurrentId+",hasload "+mHasLoaded);
        if (!mHasLoaded) {
            mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    if (sampleId == mSoundMap.get(KEY_RING)) {
                        playSoundId(mSoundMap.get(KEY_RING), true);
                    }
                    Log.i(TAG, "playRingSound onLoadComplete " + sampleId + "staus " + status);
                    mHasLoaded = true;
                }
            });
        } else {
            playSoundId(mSoundMap.get(KEY_RING), true);
        }
    }

    public void playRejectSound(){
        stopPlay();
        Log.i(TAG,"playRejectSound "+mCurrentId+",hasload "+mHasLoaded);
        if(!mHasLoaded){
            mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    if (sampleId == mSoundMap.get(KEY_REJECT)) {
                        playSoundId(mSoundMap.get(KEY_REJECT), false);
                    }
                    Log.i(TAG, "playRejectSound onLoadComplete " + sampleId + "staus " + status);
                    mHasLoaded = true;
                }
            });
        }else{
            playSoundId(mSoundMap.get(KEY_REJECT),false);
        }
    }

    public void stopPlay() {
        Log.i(TAG,"stopPlay "+mCurrentId);
        if (mCurrentId != 0) {
            mSoundPool.stop(mCurrentId);
        }
    }

    private int playSoundId(int sampleId,boolean isLoop) {
        mCurrentId = mSoundPool.play(sampleId, 1f, 1f, 1, isLoop ? -1 : 0, 1);
        Log.i(TAG,"playSoundId =  "+mCurrentId);
        return mCurrentId;
    }
}
