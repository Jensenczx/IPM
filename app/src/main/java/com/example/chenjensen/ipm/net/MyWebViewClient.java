package com.example.chenjensen.ipm.net;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by chenjensen on 16/3/3.
 */
public class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
