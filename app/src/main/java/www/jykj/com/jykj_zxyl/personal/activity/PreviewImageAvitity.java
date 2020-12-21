package www.jykj.com.jykj_zxyl.personal.activity;

import android.view.View;

import com.bm.library.PhotoView;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;

public class PreviewImageAvitity extends BaseActivity {


    @BindView(R.id.iv_preview)
    PhotoView ivPreview;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_preview_image;
    }


    @Override
    protected void initView() {
        super.initView();
        ivPreview.setImageDrawable(getResources().getDrawable(R.mipmap.home_img));
        ivPreview.enable();
    }

    @OnClick({R.id.iv_close})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
        }
    }
}
