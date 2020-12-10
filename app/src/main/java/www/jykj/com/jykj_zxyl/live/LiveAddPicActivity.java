package www.jykj.com.jykj_zxyl.live;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.allen.library.utils.ToastUtils;
import com.blankj.utilcode.util.UriUtils;
import com.hyphenate.easeui.ui.EaseShowBigImageActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ImageInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.UploadLiveImageResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.FileUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.OnClickHelper;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.live.adapter.ImageViewRecycleAdapter;
import www.jykj.com.jykj_zxyl.util.BitmapUtil;
import www.jykj.com.jykj_zxyl.util.FullyGridLayoutManager;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:添加图片
 *
 * @author: qiuxinhai
 * @date: 2020-11-14 11:47
 */
public class LiveAddPicActivity extends AbstractMvpBaseActivity<AddLivePhotoContract.View
        , AddLivePhotoPresenter> implements AddLivePhotoContract.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.iv_live_empty_pic)
    ImageView ivLiveEmptyPic;
    @BindView(R.id.rl_empty_root)
    RelativeLayout rlEmptyRoot;
    @BindView(R.id.tv_add_btn)
    TextView tvAddBtn;
    @BindView(R.id.ll_content_root)
    LinearLayout llContentRoot;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private List<ImageInfoBean> mPhotoInfos;
    private ImageViewRecycleAdapter mImageViewRecycleAdapter;
    private String detailCode;
    private LiveProtromDetialBean liveProtromDetialBean;
    private List<ImageInfoBean> mUpdateInfos;
    private File mTempFile;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            detailCode = extras.getString("detailCode");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_live_add_pic;
    }


    @Override
    protected void initView() {
        super.initView();
        mPhotoInfos = new ArrayList<>();
        mUpdateInfos = new ArrayList<>();
        //初始化ToolBar
        setToolBar();
        //初始化RecyclerView
        initRecyclerView();
        //添加监听
        addListener();
        initDir();
    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendGetBroadcastDetailInfoRequest(detailCode, this);
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("上传照片");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());

    }

    private void initDir() {
        // 声明目录
        File tempDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/_tempphoto");
        if (!tempDir.exists()) {
            tempDir.mkdirs();// 创建目录
        }
        mTempFile = new File(tempDir, BitmapUtil.getPhotoFileName());// 生成临时文件
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        FullyGridLayoutManager mGridLayoutManager = new FullyGridLayoutManager(this, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        rvList.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvList.setHasFixedSize(true);
        ImageInfoBean imageInfoBean = new ImageInfoBean();
        imageInfoBean.setPhotoID("ADDPHOTO");
        mPhotoInfos.add(imageInfoBean);
        mImageViewRecycleAdapter = new ImageViewRecycleAdapter(mPhotoInfos);
        rvList.setAdapter(mImageViewRecycleAdapter);
    }


    /**
     * 添加监听
     */
    private void addListener() {
        mImageViewRecycleAdapter.setOnItemClickListener(new ImageViewRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                String photoID = mPhotoInfos.get(position).getPhotoID();
                if (StringUtils.isNotEmpty(photoID)&&photoID.equals("ADDPHOTO")) {
                   startTakePhotoRequest();

                } else {
                    Intent intent = new Intent(LiveAddPicActivity.this,
                            EaseShowBigImageActivity.class);
                    String photoUrl = mPhotoInfos.get(position).getPhotoUrl();
                    if(photoUrl.contains("http")){
                        intent.putExtra("path",photoUrl);
                    }else{
                        File file = new File(photoUrl);
                        if (file.exists()) {
                            Uri uri = Uri.fromFile(file);
                            intent.putExtra("uri", uri);
                        }
                    }
                    LiveAddPicActivity.this.startActivity(intent);

                }
            }

            @Override
            public void onLongClick(int position) {

            }

            @Override
            public void onClickDelete(int position) {
                ImageInfoBean imageInfoBean = mPhotoInfos.get(position);
                String photoUrl = imageInfoBean.getPhotoUrl();
                if (StringUtils.isNotEmpty(photoUrl)) {
                    mUpdateInfos.add(imageInfoBean);
                }
                mPhotoInfos.remove(position);
                mImageViewRecycleAdapter.setDate(mPhotoInfos);
                mImageViewRecycleAdapter.notifyDataSetChanged();
            }
        });
        tvAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnClickHelper.isFastDoubleClick()) {
                    //防止恶意点击
                    return;
                }
                if (mPhotoInfos.size() == 1 && mUpdateInfos.size() == 0) {
                    ToastUtils.showToast("请选择图片");
                    return;
                }
                if (liveProtromDetialBean != null
                        && !CollectionUtils.isEmpty(liveProtromDetialBean.getImageList())) {
                    String imageIds = buildBasicsImgId(mUpdateInfos);
                    List<String> imageUrls = getImageUrls(mPhotoInfos);
                    if (TextUtils.isEmpty(imageIds) && CollectionUtils.isEmpty(imageUrls)) {
                        LiveAddPicActivity.this.finish();
                        return;
                    }
//                    mPresenter.sendUpdateBroadcastImageRequest(
//                            liveProtromDetialBean.getImageCode()
//                            , detailCode, imageIds, imageDatas, LiveAddPicActivity.this);

                    mPresenter.sendUpdateBroadcastImageRequest2(
                            liveProtromDetialBean.getImageCode(),detailCode,
                            imageIds, imageUrls,LiveAddPicActivity.this);
                } else {
//                    String content = buildImageDatas(mPhotoInfos);
//                    mPresenter.sendAddBroadCastImageRequest(detailCode, content,
//                            LiveAddPicActivity.this);

                    mPresenter.sendAddBroadCastImageRequest2(detailCode,getImageUrls(mPhotoInfos)
                            ,LiveAddPicActivity.this);
                }

            }
        });
    }


    /**
     * 选择图库
     */
    private void choosePicture(){
        PictureSelector.create(LiveAddPicActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(30)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                //.selectionMedia(tempList)
                .isCloseMain(true)//是否关闭主页面
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 获取image List
     * @param imageInfoBeans 列表
     * @return List
     */
    private List<String> getImageUrls(List<ImageInfoBean> imageInfoBeans){
        List<String> imgList=new ArrayList<>();
        for (ImageInfoBean mUpdateInfo : imageInfoBeans) {
            String photoUrl = mUpdateInfo.getPhotoUrl();
            if (StringUtils.isNotEmpty(photoUrl)&&!photoUrl.startsWith("http")) {
                imgList.add(photoUrl);
            }
        }
        return imgList;
    }

    /**
     * 启动拍照
     */
    private void startTakePhotoRequest() {
        RxPermissions.getInstance(LiveAddPicActivity.this)
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {//允许权限，6.0以下默认true
                        String[] items = {"拍照", "从相册选择"};
                        new android.support.v7.app.AlertDialog.Builder(LiveAddPicActivity.this)
                                .setItems(items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                                StrictMode.setVmPolicy(builder.build());
                                                builder.detectFileUriExposure();
                                                // 添加Action类型：MediaStore.ACTION_IMAGE_CAPTURE
                                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                                // 指定调用相机拍照后照片(结果)的储存路径
                                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
                                                // 等待返回结果
                                                startActivityForResult(intent, Constant.SELECT_PIC_BY_TACK_PHOTO);

                                                break;
                                            case 1:
                                               // BitmapUtil.selectAlbum(LiveAddPicActivity.this);//从相册选择
                                                choosePicture();
                                                break;
                                        }
                                    }
                                }).show();
                    } else {
                        Toast.makeText(LiveAddPicActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {

           switch (requestCode){
               case PictureConfig.CHOOSE_REQUEST:
                   List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                   for (LocalMedia localMedia : selectList) {
                       ImageInfoBean imageInfoBean = new ImageInfoBean();
                       imageInfoBean.setPhotoUrl(localMedia.getCompressPath());
                       mPhotoInfos.add(imageInfoBean);
                   }

                   addLastImageBtn(mPhotoInfos);
                   mImageViewRecycleAdapter.setDate(mPhotoInfos);
                   mImageViewRecycleAdapter.notifyDataSetChanged();
                   break;
               case Constant.SELECT_PIC_BY_TACK_PHOTO:
                   imageCompressImg(mTempFile);
                   break;
                   default:

           }

        }

        // 如果是直接从相册获取
//        if (requestCode == Constant.SELECT_PIC_FROM_ALBUM
//                && resultCode == RESULT_OK
//                && data != null) {
//
//            final Uri uri = data.getData();//返回相册图片的Uri
//            imageCompressImg(uri);
//        }
//
//        // 处理拍照返回
//        if (requestCode == Constant.SELECT_PIC_BY_TACK_PHOTO
//                && resultCode == RESULT_OK) {// 拍照成功 RESULT_OK= -1
//           // final Uri uri = data.getData();//返回相册图片的Uri
//            imageCompressImg2(mTempFile);
//            // 剪裁图片
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    /**
     * 初始化RecyclerView
     */
    private void imageCompressImg(File file) {
        Luban.with(this)
                .load(file)
                .ignoreBy(100)
                .setTargetDir(FileUtils.getPath())
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        ImageInfoBean imageInfoBean = new ImageInfoBean();
                        imageInfoBean.setPhotoUrl(file.getAbsolutePath());
                        mPhotoInfos.add(imageInfoBean);
                        addLastImageBtn(mPhotoInfos);
                        mImageViewRecycleAdapter.setDate(mPhotoInfos);
                        mImageViewRecycleAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        System.out.println(e);
                    }
                }).launch();
    }


    /**
     * @param list 图片列表
     */
    private void addLastImageBtn(List<ImageInfoBean> list) {
        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                String photoID = list.get(i).getPhotoID();
                if (StringUtils.isNotEmpty(photoID)) {
                    list.remove(i);
                    break;
                }
            }

        }
        ImageInfoBean imageInfoBean = new ImageInfoBean();
        imageInfoBean.setPhotoID("ADDPHOTO");
        list.add(list.size(), imageInfoBean);
    }


    /**
     * 构建图片Id 列表
     *
     * @param list 图片数据list
     * @return 图片Id list
     */
    private String buildBasicsImgId(List<ImageInfoBean> list) {
        StringBuilder stringBuilder = new StringBuilder();
        if (null != list && !list.isEmpty())
            for (int i = 0; i < list.size(); i++) {
                int basicsImgId = list.get(i).getBasicsImgId();
                if (basicsImgId != 0) {
                    if (i == list.size() - 1) {
                        stringBuilder.append(basicsImgId);
                    } else {
                        stringBuilder.append(basicsImgId).append("^");
                    }
                }
            }
        return stringBuilder.toString();
    }


    @Override
    public void showLoading(int code) {
        showLoading("", null);
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void getAddBroadCastImageResult(UploadLiveImageResultBean uploadLiveImageResultBean) {
        this.finish();
    }

    @Override
    public void getAddBroadCastImageError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void getBroadcastDetailInfoResult(LiveProtromDetialBean liveProtromDetialBean) {
        this.liveProtromDetialBean = liveProtromDetialBean;
        List<LiveProtromDetialBean.ImageListBean> imageList = liveProtromDetialBean.getImageList();
        for (LiveProtromDetialBean.ImageListBean imageListBean : imageList) {
            ImageInfoBean imageInfoBean = new ImageInfoBean();
            imageInfoBean.setBasicsImgId(imageListBean.getBasicsImgId());
            imageInfoBean.setTableImgId(imageListBean.getTableImgId());
            imageInfoBean.setPhotoUrl(imageListBean.getImgUrl());
            mPhotoInfos.add(imageInfoBean);

        }
        addLastImageBtn(mPhotoInfos);
        mImageViewRecycleAdapter.setDate(mPhotoInfos);
        mImageViewRecycleAdapter.notifyDataSetChanged();

    }

}
