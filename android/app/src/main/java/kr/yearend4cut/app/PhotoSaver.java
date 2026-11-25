package kr.yearend4cut.app;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import java.io.OutputStream;

/** 완성된 네컷 사진을 갤러리(사진/연말네컷 앨범)에 저장하고, 원하면 공유 창을 띄워요. */
public class PhotoSaver {
    private final Activity activity;

    public PhotoSaver(Activity activity) {
        this.activity = activity;
    }

    private Uri write(String dataUrl, String name) throws Exception {
        String b64 = dataUrl.substring(dataUrl.indexOf(',') + 1);
        byte[] bytes = Base64.decode(b64, Base64.DEFAULT);
        ContentResolver r = activity.getContentResolver();
        ContentValues v = new ContentValues();
        v.put(MediaStore.Images.Media.DISPLAY_NAME, name);
        v.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= 29) {
            v.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/연말네컷");
            v.put(MediaStore.Images.Media.IS_PENDING, 1);
        }
        Uri uri = r.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, v);
        if (uri == null) throw new Exception("저장 위치를 만들지 못했어요");
        try (OutputStream os = r.openOutputStream(uri)) {
            if (os == null) throw new Exception("파일을 열지 못했어요");
            os.write(bytes);
        }
        if (Build.VERSION.SDK_INT >= 29) {
            ContentValues done = new ContentValues();
            done.put(MediaStore.Images.Media.IS_PENDING, 0);
            r.update(uri, done, null, null);
        }
        return uri;
    }

    @JavascriptInterface
    public String save(String dataUrl, String name) {
        try {
            write(dataUrl, name);
            return "ok";
        } catch (Exception e) {
            return "error:" + e.getMessage();
        }
    }

    @JavascriptInterface
    public String share(String dataUrl, String name) {
        try {
            final Uri uri = write(dataUrl, name);
            activity.runOnUiThread(() -> {
                Intent send = new Intent(Intent.ACTION_SEND);
                send.setType("image/jpeg");
                send.putExtra(Intent.EXTRA_STREAM, uri);
                send.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                activity.startActivity(Intent.createChooser(send, "네컷 사진 보내기"));
            });
            return "ok";
        } catch (Exception e) {
            return "error:" + e.getMessage();
        }
    }
}
