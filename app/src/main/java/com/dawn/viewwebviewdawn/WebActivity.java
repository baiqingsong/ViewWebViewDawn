package com.dawn.viewwebviewdawn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by 90449 on 2017/7/6.
 */

public class WebActivity extends AppCompatActivity {
    private WebView mWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = (WebView) findViewById(R.id.web_view);
        setWebView();
    }
    private void setWebView(){
        mWebView.loadUrl("http://www.baidu.com");//加载网络页面
//        mWebView.loadUrl("file:///android_asset/dawn.html");//加载assets下的页面文件
//        mWebView.loadUrl("file:///sdcard/dawn.html");//加载sd卡中的页面文件
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);
        setWebSetting();
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView)v).getHitTestResult();
                if (null == result)
                    return false;
                int type = result.getType();
                if (type == WebView.HitTestResult.UNKNOWN_TYPE)
                    return false;

                // 这里可以拦截很多类型，我们只处理图片类型就可以了
                switch (type) {
                    case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE: // 地图类型
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE://带有链接的图片类型
//                        break;
                    case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                        // 获取图片的路径
                        String saveImgUrl = result.getExtra();
                        // 跳转到图片详情页，显示图片
                        Intent i = new Intent(WebActivity.this, ImageActivity.class);
                        i.putExtra("imgUrl", saveImgUrl);
                        startActivity(i);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
    private void setWebSetting(){
        WebSettings webSettings = mWebView .getSettings();

        //支持获取手势焦点，输入用户名、密码或其他
//        mWebView.requestFocusFromTouch();

        webSettings.setJavaScriptEnabled(true);  //支持js

//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);  //提高渲染的优先级
//
//        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//
        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
        webSettings.setTextZoom(2);//设置文本的缩放倍数，默认为 100
//
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
//        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
//        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//
        webSettings.setStandardFontFamily("sans-serif");//设置 WebView 的字体，默认字体为 "sans-serif"
        webSettings.setDefaultFontSize(16);//设置 WebView 字体的大小，默认大小为 16
        webSettings.setMinimumFontSize(8);//设置 WebView 支持的最小字体大小，默认为 8
    }

    WebViewClient mWebViewClient = new WebViewClient(){
        //最常用的，在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
        //比如获取url，查看url.contains(“add”)，进行添加操作
        //这个是让app的webView显示地址代替系统自带浏览器
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWebView.loadUrl(url);
            return true;
        }
        //重写此方法才能够处理在浏览器中的按键事件。
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }
        //这个事件就是开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        //在页面加载结束时调用。同样道理，我们可以关闭loading 条，切换程序动作。
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
        // 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
        // 拦截替换网络请求数据,  API 11开始引入，API 21弃用
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }
        // 拦截替换网络请求数据,  从API 21开始引入
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }
        // (报告错误信息)
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
        //(更新历史记录)
        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }
        //(应用程序重新请求网页数据)
        @Override
        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            super.onFormResubmission(view, dontResend, resend);
        }
        //（获取返回信息授权请求）
        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
        //重写此方法可以让webview处理https请求。
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }
        // (WebView发生改变时调用)
        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }
        //（Key事件未被加载时调用）
        @Override
        public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
            super.onUnhandledKeyEvent(view, event);
        }
    };
    WebChromeClient mWebChromeClient = new WebChromeClient(){
        //获得网页的加载进度，显示在右上角的TextView控件中
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
        //获取Web页中的title用来设置自己界面中的title
        //当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为 找不到该网页,
        //因此建议当触发onReceiveError时，不要使用获取到的title
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
        //获取网站图标
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }
    };
}
