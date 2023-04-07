package ru.mirea.markinaa.mireaproject.ui.brouser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.mirea.markinaa.mireaproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrouserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrouserFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private WebView webView;

    public BrouserFragment() {

    }
    public static BrouserFragment newInstance(String param1, String param2) {
        BrouserFragment fragment = new BrouserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_brouser, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        webView.loadUrl("https://ya.ru/");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        return view;
    }
}