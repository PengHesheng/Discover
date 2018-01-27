package com.example.a14512.discover.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * @author wz on 2016/1/28.
 */
public class BitmapUtils {

    public static byte[] Bitmap2Bytes(Bitmap bm) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);

        return baos.toByteArray();
    }

    //byte[] → Bitmap

    public static Bitmap Bytes2Bimap(byte[] b) {

        if (b.length != 0) {

            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {

            return null;
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(

            drawable.getIntrinsicWidth(),

            drawable.getIntrinsicHeight(),

            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;
    }

    public static Drawable bitmapToDrawable(Resources resources, Bitmap bitmap) {

        return new BitmapDrawable(resources, bitmap);
    }

    /**
     * 从原bitmap头部开始 截取n行像素，生成新的bitmap
     *
     * @param bitmap 原始图像
     * @param rows 需要截取的行数(从顶部开始)
     */
    public static Bitmap createRowPixsBitmap(Bitmap bitmap, int rows) {

        if (bitmap == null) {
            return null;
        } else {

            int height = bitmap.getHeight();
            int width = bitmap.getWidth();

            rows = Math.min(Math.abs(rows), height);
            int colors[] = new int[width * rows];

            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < width; y++) {
                    colors[x * width + y] = bitmap.getPixel(y, x);
                }
            }

            return Bitmap.createBitmap(colors, width, rows, bitmap.getConfig());
        }
    }

    // 缓存bitmap 字节流
    public static byte[] getBytes(Bitmap bitmap) {
        //实例化字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
        return baos.toByteArray();//创建分配字节数组
    }

    public static Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
    }

    /**
     * 在内存中压缩图片
     *
     * @param resourceId 资源的id
     * @param requestWidth 需要压缩后的图片宽度
     * @param requestHeight 需要压缩后的图片高度
     * @return 压缩后的bitmap
     */
    public static Bitmap compressBitmapInMemory(Context context, int resourceId, int requestWidth, int requestHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        options.inJustDecodeBounds = false;

        int width = options.outWidth;
        int height = options.outHeight;

        options.inSampleSize = Math.max(width / requestWidth, height / requestHeight);

        return BitmapFactory.decodeResource(context.getResources(), resourceId, options);
    }

    //根据路径获取用户选择的图片
    public static Bitmap sampleSizeImage(String imgPath, int ratio) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = ratio;//直接设置它的压缩率，表示1/2
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeFile(imgPath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    public static Bitmap decodeUriAsBitmap(Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static Bitmap compressImage(Uri uri, Context context) {
        Bitmap pic = null;
        try{
            pic =  BitmapUtils.decodeUriAsBitmap(uri, context);
        }catch (Exception e){
            PLog.e("TAG", e.toString());
        }
        int imgWidth = pic.getWidth();
        int imgHeight = pic.getHeight();
        // 只对大尺寸图片进行下面的压缩，小尺寸图片使用原图
        if (imgWidth >= 360f) {
            float scale =  360f / imgWidth;
            Matrix mx = new Matrix();
            mx.setScale(scale, scale);
            pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true);
        }

        return pic;
    }


    public static Bitmap compressImage(Uri uri, Context context, float insertedImgWidth) {

        Bitmap pic = null;
        try{
            pic =  BitmapUtils.decodeUriAsBitmap(uri, context);
        }catch (Exception e){
            PLog.e("TAG", e.toString());
        }
        int imgWidth = pic.getWidth();
        int imgHeight = pic.getHeight();
        // 只对大尺寸图片进行下面的压缩，小尺寸图片使用原图

        if (imgWidth >= insertedImgWidth) {
            float scale =  insertedImgWidth / imgWidth;
            Matrix mx = new Matrix();
            mx.setScale(scale, scale);
            pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true);
        }
        return pic;
    }

}
