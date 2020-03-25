package com.mukul.onnwaytransporter.webview;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mukul.onnwaytransporter.R;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class AboutOnwayFragment extends Fragment {
    WebView webView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.about_onnway_fragment,container,false);
        //shimmer effect
        final ShimmerLayout shimmerText = (ShimmerLayout)view. findViewById(R.id.shimmer_text);
       // shimmerText.startShimmerAnimation();
        //
        webView=view.findViewById(R.id.webview);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout);

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //improve performance of webview
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setEnableSmoothTransition(true);

        webView.loadUrl("https://www.onnway.com/aboutonway.php");
       WebViewClient webViewClient= new WebViewClient()
       {
           @Override
           public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);
               swipeRefreshLayout.setRefreshing(false);
              // shimmerText.stopShimmerAnimation();
           }

           @Override
           public void onPageCommitVisible(WebView view, String url) {
               super.onPageCommitVisible(view, url);
           }

           @Override
           public void onLoadResource(WebView view, String url) {
               super.onLoadResource(view, url);
           }

           @Override
           public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
               return super.shouldOverrideUrlLoading(view, request);
           }
       };

       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               webView.reload();
           }
       });
        webView.setWebViewClient(webViewClient);



        return view;
    }
}
