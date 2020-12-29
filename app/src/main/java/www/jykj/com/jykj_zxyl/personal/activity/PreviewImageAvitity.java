package www.jykj.com.jykj_zxyl.personal.activity;

import android.view.View;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;

public class PreviewImageAvitity extends BaseActivity {


    @BindView(R.id.iv_preview)
    PhotoView ivPreview;
    private String url;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_preview_image;
    }


    @Override
    protected void initView() {
        super.initView();

        ivPreview.enable();
    }


    @Override
    protected void initData() {
        super.initData();
        url = getIntent().getStringExtra("url");
//        ivPreview.setImageDrawable(getResources().getDrawable(R.mipmap.home_img));
        Glide.with(this).load(url).into(ivPreview);
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
