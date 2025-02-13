package com.test.listamultimedia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.listamultimedia.model.Multimedia;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "lista";

    // TODO: Rename and change types of parameters
    private ArrayList <Multimedia> lista = new ArrayList<>();

    public ListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(ArrayList <Multimedia> lista) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, lista);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            lista = (ArrayList <Multimedia> ) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_inicial, container, false);

        MultimediaAdapter.OnItemClickListener listener = (MultimediaAdapter.OnItemClickListener) getActivity();

        MultimediaAdapter multimediaAdapter = new MultimediaAdapter(lista, listener);
        RecyclerView rvMultimedia = vista.findViewById(R.id.rvCatalogo);
        rvMultimedia.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvMultimedia.setAdapter(multimediaAdapter);


        return vista;
    }
}