package com.test.listamultimedia;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.test.listamultimedia.model.Multimedia;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class VideoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "item";

    // TODO: Rename and change types of parameters
    private String ruta;
    private Multimedia item;

    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance (Multimedia item) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, item);
        fragment.setArguments(args);
        return fragment;
    }

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = (Multimedia) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_video, container, false);

        cargarDatos (vista);

        VideoView videoView = vista.findViewById(R.id.videoView);
        videoView.setVideoPath(item.getDireccion());
        videoView.start();

        Button boton = vista.findViewById(R.id.btnPausa);
        boton.setOnClickListener(v -> {

            if(videoView.isPlaying()){
                videoView.pause();
                boton.setText("Play");
            } else {
                videoView.start();
                boton.setText("Pause");
            }

        });

        Button botonVolver = vista.findViewById(R.id.btnVolver);
        botonVolver.setOnClickListener(v -> {

            requireActivity().getSupportFragmentManager().popBackStack();

        });

        return vista;
    }

    public void cargarDatos (View v){

        TextView tvTitulo = v.findViewById(R.id.tvNombre);
        tvTitulo.setText(item.getNombre());
        TextView tvDescripcion = v.findViewById(R.id.tvDescripcion);
        tvDescripcion.setText(item.getDescipcion());
        tvDescripcion.setVisibility(View.VISIBLE);
        ImageView img = v.findViewById(R.id.imgMultimedia);
        img.setImageResource(item.getImg());

    }

}