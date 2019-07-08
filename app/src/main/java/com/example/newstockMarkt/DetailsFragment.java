package com.example.newstockMarkt;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.newstockmarkt.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements IOnBackPressed{


    public DetailsFragment() {
        // Required empty public constructor
    }

    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        String url = bundle.getString("url");
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        WebView webView = view.findViewById(R.id.webView);
        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new CustomWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        view.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(DetailsFragment.this).commit();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().findViewById(R.id.fragment_container).setVisibility(View.GONE);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    private class CustomWebViewClient extends WebViewClient{

        CustomWebViewClient(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.GONE);
        }

    }
}
