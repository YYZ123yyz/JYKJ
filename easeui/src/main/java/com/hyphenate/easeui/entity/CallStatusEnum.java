package com.hyphenate.easeui.entity;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author qiuxinhai
 * @version 1.0
 * @date 2019-12-11 14:46
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({CallStatusEnum.TALK_TIME_VIDEO_CODE,
        CallStatusEnum.TALK_TIME_AUDIO_CODE,
        CallStatusEnum.CANCEL_BY_SELF_VIDEO_CODE,
        CallStatusEnum.CANCEL_BY_SELF_AUDIO_CODE,
        CallStatusEnum.OTHER_SIDE_REFUSED_VIDEO_CODE,
        CallStatusEnum.OTHER_SIDE_REFUSED_AUDIO_CODE,
        CallStatusEnum.OTHER_SIDE_NO_ANSER_VIDEO_CODE,
        CallStatusEnum.OTHER_SIDE_NO_ANSER_AUDIO_CODE,
        })
public @interface CallStatusEnum {

    int TALK_TIME_VIDEO_CODE=1;//视频通话时长
    int TALK_TIME_AUDIO_CODE=2;//音频通话时长


    int CANCEL_BY_SELF_VIDEO_CODE=3;//自己取消视频
    int CANCEL_BY_SELF_AUDIO_CODE=4;//自己取消音频

    int OTHER_SIDE_REFUSED_VIDEO_CODE=5;//对方拒绝视频
    int OTHER_SIDE_REFUSED_AUDIO_CODE=6;//对方拒绝音频

    int OTHER_SIDE_NO_ANSER_VIDEO_CODE=7;//对方未接听视频
    int OTHER_SIDE_NO_ANSER_AUDIO_CODE=8;//对方未接听音频







}
