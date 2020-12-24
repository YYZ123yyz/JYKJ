package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.RxHttpUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UriUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_utils.RxUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiService;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.contract.AddBankcardContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.AddBankcardPresenter;
import www.jykj.com.jykj_zxyl.capitalpool.contract.CollectionCodeContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.CollectionCodePresenter;
import www.jykj.com.jykj_zxyl.util.BitmapUtil;

public class CollectionCodeActivity extends AbstractMvpBaseActivity<CollectionCodeContract.View
        , CollectionCodePresenter> implements CollectionCodeContract.View {


    @BindView(R.id.bind_type_tv)
    TextView bindTypeTv;
    @BindView(R.id.et_input)
    EditText inputEt;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.bind_tv)
    TextView bindTv;
    private int mType;
    private Bitmap cordBitmap;
    private JYKJApplication mApp;
    private File bitmapFile;
    private String imgUrl;
    private boolean isShow =false;
    private String id;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_collection_code;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        mType = getIntent().getIntExtra("type", 0);
        if (getIntent().hasExtra("img")){
            isShow =true;
            imgUrl = getIntent().getStringExtra("img");
            id = getIntent().getStringExtra("id");
        }
        setDeftInfo();
    }

    private void setDeftInfo() {
        if (mType == 1) {
            bindTypeTv.setText("微信账号");
        } else {
            bindTypeTv.setText("支付宝账号");
        }
        if (isShow){
            bindTv.setVisibility(View.GONE);
            addIv.setClickable(false);
            Glide.with(this).load(imgUrl).into(addIv);
            inputEt.setText(id);
            inputEt.setFocusable(false);
            inputEt.setClickable(false);
        }
    }

    @OnClick({R.id.bind_tv,R.id.add_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_tv:

                checkInfo();
//                startActivity(new Intent(CollectionCodeActivity.this, VerificationActivity.class));
                break;
            case R.id.add_iv:
                BitmapUtil.selectAlbum(CollectionCodeActivity.this);//从相册选择
                break;
        }

    }

    private void checkInfo() {
        if (TextUtils.isEmpty(inputEt.getText().toString().trim())){
            ToastUtils.showShort("请输入账号");
            return;
        }
        if (cordBitmap ==null){
            ToastUtils.showShort("请选择二维码");
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), bitmapFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img", bitmapFile.getName(), requestBody);

        mPresenter.bindCode(getParams(),body);
    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();

        stringStringHashMap.put("loginUserPosition", ParameUtil.loginDoctorPosition);
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());//mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
        stringStringHashMap.put("operDoctorName",mApp.mViewSysUserDoctorInfoAndHospital.getUserName());//
        stringStringHashMap.put("requestClientType", "1");
        stringStringHashMap.put("assetsCode", "c877fc2a03bf4552ad070fb112794246");//SPUtils.getInstance().getString("assetsCode")==null ? "":SPUtils.getInstance().getString("assetsCode")
        stringStringHashMap.put("account",inputEt.getText().toString().trim());//
        stringStringHashMap.put("withdrawalType", mType);//mType


        return RetrofitUtil.encodeParam(stringStringHashMap);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == Constant.SELECT_PIC_FROM_ALBUM
                && resultCode == RESULT_OK
                && data != null) {

            final Uri uri = data.getData();//返回相册图片的Uri


            bitmapFile = UriUtils.uri2File(uri);
            if (bitmapFile !=null){
                cordBitmap = ImageUtils.compressBySampleSize(ImageUtils.getBitmap(bitmapFile), 200, 200);
                addIv.setImageBitmap(cordBitmap);
            }


        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void bindSucess() {
        ToastUtils.showShort("绑定成功");
        finish();
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(msg);
    }
}

