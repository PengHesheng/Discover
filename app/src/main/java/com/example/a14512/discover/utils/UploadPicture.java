package com.example.a14512.discover.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import com.example.a14512.discover.BuildConfig;
import com.example.a14512.discover.C;
import com.example.a14512.discover.DiscoverApplication;
import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author myValentine on 16/8/3.
 */
public class UploadPicture {

    public static String cameraPath = DiscoverApplication.cacheDir + "/Data/cacheImage";
    public static String cropPath = DiscoverApplication.cacheDir + "/Data/cropImage";
    public static Uri cameraUri = Uri.fromFile(new File(cameraPath));
    public static Uri cropUri = Uri.fromFile(new File(cropPath));
    public static byte[] imageNoCrop;
    public static String imageUrl;
    public static String token;

    // 得到上传图片的intent
    public static Intent getUploadIntent(int uploadType) {
        switch (uploadType) {
            case C.PICK_FROM_FILE:
                Intent intentFile = new Intent(Intent.ACTION_PICK);
                intentFile.setType("image/*");
                return intentFile;
            case C.PICK_FROM_CAMERA:
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                } else {
                    cameraUri= FileProvider.getUriForFile(DiscoverApplication.getContext(),
                            BuildConfig.APPLICATION_ID + ".fileprovider",new File(cameraPath));
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                    intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intentCamera.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                return intentCamera;
            default:
                return null;
        }
    }

    // 得到凭证，并交由UpCompletionHandler后续处理
    public static void uploadPicture(final UpCompletionHandler completionHandler) {
        final String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSS").format(new Date());
        imageUrl = "http://oyojokgx1.bkt.clouddn.com/" + fileName;
        final UploadManager uploadManager = new UploadManager();

        RetrofitHelper.getInstance().getToken()
                .subscribe(new ApiSubscriber<String>(DiscoverApplication.getContext(), true,
                        true, "上传图片中...") {
                    @Override
                    public void onNext(String value) {
                        UploadPicture.token = value;
                    }

                    @Override
                    public void onComplete() {
                        uploadManager.put(cropPath, fileName, UploadPicture.token, completionHandler, null);
                    }
                });
    }

    public static void uploadPictureNoCrop(Uri uri, Context context, UpCompletionHandler completionHandler) {
        Bitmap bitmap = BitmapUtils.compressImage(uri, context);
        imageNoCrop = BitmapUtils.Bitmap2Bytes(bitmap);
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSS").format(new Date());
        imageUrl = "http://oyojokgx1.bkt.clouddn.com/" + fileName;
        UploadManager uploadManager = new UploadManager();
        RetrofitHelper.getInstance().getToken()
                .subscribe(new ApiSubscriber<String>(DiscoverApplication.getContext(), false, false) {
                    @Override
                    public void onNext(String value) {
                        UploadPicture.token = value;
                    }

                    @Override
                    public void onComplete() {
                        uploadManager.put(imageNoCrop, fileName, UploadPicture.token, completionHandler, null);
                    }
                });
    }

    public static void uploadPictureNoCrop(byte[] bytes, UpCompletionHandler completionHandler) {
        imageNoCrop = bytes;
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSS").format(new Date());
        imageUrl = "http://oyojokgx1.bkt.clouddn.com/" + fileName;
        UploadManager uploadManager = new UploadManager();
        RetrofitHelper.getInstance().getToken()
                .subscribe(new ApiSubscriber<String>(DiscoverApplication.getContext(), false, false) {
                    @Override
                    public void onNext(String value) {
                        UploadPicture.token = value;
                    }

                    @Override
                    public void onComplete() {
                        uploadManager.put(imageNoCrop, fileName, UploadPicture.token, completionHandler, null);
                    }
                });
    }


    public static Intent cropPicture(Uri uri, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        //这里是否会出现4.4分水岭导致的Uri问题，未验证
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    // 弹出选择弹框 相册，照相机，取消 并自动调用startActivityForResult得到相应intent进行跳转
    public static void showPictureSelectDialog(Context context, Activity activity) {
        new AlertDialog.Builder(context).setItems(new String[]{"相册", "照相机", "取消"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    activity.startActivityForResult(UploadPicture.getUploadIntent(C.PICK_FROM_FILE), C.PICK_FROM_FILE);
                    break;
                case 1:
                    activity.startActivityForResult(UploadPicture.getUploadIntent(C.PICK_FROM_CAMERA), C.PICK_FROM_CAMERA);
                    break;
                default:
                    dialog.dismiss();
                    break;
            }
        }).create().show();
    }

    // 弹出选择弹框 相册，照相机，取消 并自动调用startActivityForResult得到相应intent进行跳转
    public static void showPictureSelectDialog(Context context, Activity activity, int request) {
        new AlertDialog.Builder(context).setItems(new String[]{"相册", "照相机", "取消"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    activity.startActivityForResult(UploadPicture.getUploadIntent(C.PICK_FROM_FILE), C.PICK_FROM_FILE);
                    break;
                case 1:
                    activity.startActivityForResult(UploadPicture.getUploadIntent(C.PICK_FROM_CAMERA), C.PICK_FROM_CAMERA);
                    break;
                default:
                    dialog.dismiss();
                    break;

            }
        }).create().show();
    }

}
