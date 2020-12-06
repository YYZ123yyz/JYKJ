package www.jykj.com.jykj_zxyl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import entity.EducationBean;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterListBean;

public class VideoChapterAdapter extends BaseQuickAdapter<ChapterListBean.SecondListBean, BaseViewHolder> {
    public VideoChapterAdapter(int layoutResId, @Nullable List<ChapterListBean.SecondListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChapterListBean.SecondListBean item) {
        String playStr = "";
        helper.setText(R.id.serial_num,String.valueOf(helper.getAdapterPosition()+1))
                .setText(R.id.second_tv,item.getTitle())
                .setText(R.id.tv_play_num,String.valueOf(item.getClickCount()))
                .setText(R.id.tv_play_time,item.getDuration());
        int freeType = item.getFreeType();
        if (freeType==0){
            playStr ="播放";
        }else {
            if (item.getFlagUserHasBuy() ==1){
                playStr ="播放";
            }else {
                playStr =String.format("¥ %s元", item.getPrice());
            }
        }
        helper.setText(R.id.tv_price,playStr);
        helper.addOnClickListener(R.id.tv_price);
    }
}
