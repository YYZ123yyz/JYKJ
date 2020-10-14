package www.jykj.com.jykj_zxyl.fragment.home.adapter;

import android.text.TextUtils;

import com.allin.commonadapter.IMulItemViewType;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MultiItemEntity;

/**
 * Description:类型
 *
 * @author: qiuxinhai
 * @date: 2020-10-13 14:38
 */
public class HealthEducationItemType implements IMulItemViewType<MultiItemEntity> {
    /**
     * 图文
     */
    public static final String MULTIPLE_PICTURE_TEXT_TYPE="0";
    /**
     * 视频
     */
    public static final String MULTIPLE_VIDEO_TYPE="1";

    /**
     * 音频
     */
    public static final String MULTIPLE_AUDIO_TYPE="2";

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int i, MultiItemEntity multiItemEntity) {
        String itemType = multiItemEntity.getItemType();
        if (TextUtils.isEmpty(itemType)) {
            return -1;
        }
        switch (itemType) {
            case MULTIPLE_PICTURE_TEXT_TYPE://文字
                return 0;
            case MULTIPLE_VIDEO_TYPE://一张图片
                return 1;
            case MULTIPLE_AUDIO_TYPE://两张图片
                return 2;

            default:
                return -1;
        }

    }

    @Override
    public int getLayoutId(int viewType) {
        int layoutId = -1;
        switch (viewType) {
            case 0:
                layoutId = R.layout.item_health_education_picture_text;
                break;
            case 1:
                layoutId = R.layout.item_health_education_video;
                break;
            case 2:
                layoutId = R.layout.item_health_education_audio;
                break;

            default:
        }
        return layoutId;
    }
}
