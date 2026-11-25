package kr.yearend4cut.app;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            WebView wv = getBridge().getWebView();
            WebSettings s = wv.getSettings();
            s.setTextZoom(100);
            // 카메라 미리보기 영상이 손대지 않아도 바로 재생되도록
            s.setMediaPlaybackRequiresUserGesture(false);
            // 화면(JS)에서 window.PhotoSaver.save(...) 로 갤러리에 저장해요
            wv.addJavascriptInterface(new PhotoSaver(this), "PhotoSaver");
        } catch (Exception e) {
            // 무시
        }
    }
}
