package util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * Created by xianglijiaxing on 16-7-4.
 */
public class QRCodeUtil {

    public static Bitmap getImageBitmap(String text, int size){
        if(text == null || "".equals(text) || text.length() < 1){
            return null;
        }
        //获取像素矩阵
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints);
            // 转换为bitmap可用的数据
            int[] pixels = new int[size * size];
            for(int y = 0; y < size; y++){
                for(int x = 0; x < size; x++){
                    if(bitMatrix.get(x, y)){
                        pixels[y *size +x] = 0xff000000;
                    }else{
                        pixels[y *size +x] = 0xffffffff;
                    }
                }
            }
            //　生成bitmap
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Bitmap getImageBitmap(Bitmap logoBitmap,String text, int size){
        if(text == null || "".equals(text) || text.length() < 1){
            return null;
        }
        //获取像素矩阵
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints);
            // 转换为bitmap可用的数据
            int[] pixels = new int[size * size];
            for(int y = 0; y < size; y++){
                for(int x = 0; x < size; x++){
                    if(bitMatrix.get(x, y)){
                        pixels[y *size +x] = 0xff000000;
                    }else{
                        pixels[y *size +x] = 0xffffffff;
                    }
                }
            }
            //　生成bitmap
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            if(bitmap!=null){
                bitmap = addLogo(bitmap,logoBitmap);
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        // 获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        // logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 9 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }
}
