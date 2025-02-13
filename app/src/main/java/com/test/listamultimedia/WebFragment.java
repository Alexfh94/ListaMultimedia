package com.test.listamultimedia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ruta";

    // TODO: Rename and change types of parameters
    private String ruta;

    public WebFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment webFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebFragment newInstance(String param1) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ruta = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_web, container, false);

        /* Ejemplo WebView */
        WebView webView = vista.findViewById(R.id.webContainer);
        // Habilita javascript en el webview
        webView.getSettings().setJavaScriptEnabled(true);
        // Establece el cliente web para manejar las redirecciones
        webView.setWebViewClient(new WebViewClient());
        // Establece el cliente web para manejar las alertas de confirmacion
        webView.setWebChromeClient(new WebChromeClient());

        // Carga la web de Distancia en el WebView
        webView.loadUrl(ruta);

        Button botonVolver = vista.findViewById(R.id.btnVolver);
        botonVolver.setOnClickListener(v -> {

            requireActivity().getSupportFragmentManager().popBackStack();

        });

        return vista;
    }
}