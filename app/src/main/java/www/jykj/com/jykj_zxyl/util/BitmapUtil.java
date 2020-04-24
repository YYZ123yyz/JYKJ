package www.jykj.com.jykj_zxyl.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import www.jykj.com.jykj_zxyl.application.Constant;


/**
 * Created by admin on 2016/10/28.
 */

public class BitmapUtil {

    /**
      * 将base64转为bitmap
      *
      * @param string
      * @return
      */
    public static Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * byte转bitmap
     * @param b
     * @return
     */
    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * byte转base64
     */
    public static String byteToString(byte[] bytes){
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
      * 将Bitmap转换成字符串
      *
      * @param bitmap
      * @return
      */
    public static String bitmaptoString(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 生产临时照片名称
     * 使用系统当前日期加以调整作为照片的名称
     * @return
     */
    public static String getPhotoFileName() {
        // 获取系统的当前时间
        Date date = new Date(System.currentTimeMillis());
        // 系统时间--> 格式化
        SimpleDateFormat dateFormat
                = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 去系统的拍照界面
     * @param activity 当前Activity
     * @param permissionGrant 回调对象
     */
    public static void takePhoto(Activity activity, PermissionUtils.PermissionGrant permissionGrant) {
        try {
            //申请权限
            PermissionUtils.requestPermission(activity, PermissionUtils.CODE_CAMERA,permissionGrant);
        }catch (Exception e)
        {
            Log.i("异常","yichang ");
        }
    }

    /**
     * 从相册选照片(媒体库)
     * @param activity 当前Activity
     */
    public static void selectAlbum(Activity activity){
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, Constant.SELECT_PIC_FROM_ALBUM);
    }

    /**
     * 发送Intent打开系统剪裁界面
     * @param uri 原图地址
     * @param size 要剪裁完后的大小
     */
    public static void startPhotoZoom(Activity activity,Uri uri, int size) {
        //初始化imageUri
        Uri imageUri=Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg"));
        // 封装Intent
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.parse(uri.toString()), "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);// 宽高比例1:1
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);  //为false表示不返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将裁切的结果输出到指定的Uri
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection",true);
        // 等待返回
        activity.startActivityForResult(intent, Constant.REQUEST_PHOTO_CUT);
    }


    /**
     * 发送Intent打开系统剪裁界面
     * @param uri 原图地址
     * @param size 要剪裁完后的大小
     */
    public static void startPhotoCommodityZoom(Activity activity,Uri uri, int size) {
        //初始化imageUri
        Uri imageUri=Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg"));
        // 封装Intent
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 5);// 宽高比例1:1
        intent.putExtra("aspectY", 4);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);  //为false表示不返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将裁切的结果输出到指定的Uri
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection",true);
        // 等待返回
        activity.startActivityForResult(intent, Constant.REQUEST_PHOTO_CUT);
    }


    /**
     * 图片的质量压缩方法
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

        return bitmap;
    }

    /**
     * 通过图片路径获取图片并按比例压缩
     * @param srcPath
     * @return
     */
    public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        Bitmap bitmap = null;
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 通过图片路径获取图片并按比例压缩
     * @param bitmap
     * @return
     */
    public static Bitmap getimageBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        Bitmap mSrcBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return compressImage(mSrcBitmap);
    }
}
