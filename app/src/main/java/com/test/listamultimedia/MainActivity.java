package com.test.listamultimedia;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.listamultimedia.model.Multimedia;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements MultimediaAdapter.OnItemClickListener{

    ArrayList <Multimedia> multimedia = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        multimedia = cargarDatos();
        cargarRecyclerView(multimedia);

    }

    //método que carga el recyclerView con el adaptador del catálogo
    public void cargarRecyclerView (ArrayList <Multimedia> multimedia){

        Bundle bundle = new Bundle();
        bundle.putSerializable("lista", multimedia);

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.contenedor, InicialFragment.class, bundle)
                .commit();

    }

    public ArrayList <Multimedia> cargarDatos () {

        ArrayList <Multimedia> multimedia = new ArrayList<>(Arrays.asList(new Multimedia[]{
                new Multimedia("Video dados", getString(R.string.txt_dados), "android.resource://" + getPackageName() + "/" + R.raw.dados, R.drawable.dados, Multimedia.VIDEO),
                new Multimedia("Web 1", "Primera web", "https://fpadistancia.edu.xunta.gal/", R.drawable.dados, Multimedia.WEB),
                new Multimedia("Audio 1", "Primer audio", String.valueOf(R.raw.mind), R.drawable.dados, Multimedia.AUDIO),
                new Multimedia("Video babosa", getString(R.string.txt_babosa), "android.resource://" + getPackageName() + "/" + R.raw.babosa, R.drawable.dados, Multimedia.VIDEO),
                new Multimedia("Web 1", "Primera web", "https://fpadistancia.edu.xunta.gal/", R.drawable.dados, Multimedia.WEB),
                new Multimedia("Audio 2", "Primer audio", String.valueOf(R.raw.pajaros), R.drawable.dados, Multimedia.AUDIO),
                new Multimedia("Video comida", getString(R.string.txt_plato), "android.resource://" + getPackageName() + "/" + R.raw.comida, R.drawable.dados, Multimedia.VIDEO),
                new Multimedia("Web 1", "Primera web", "https://fpadistancia.edu.xunta.gal/", R.drawable.dados, Multimedia.WEB),
                new Multimedia("Audio 1", "Primer audio", String.valueOf(R.raw.mind), R.drawable.dados, Multimedia.AUDIO),
                new Multimedia("Video paisaje", getString(R.string.txt_paisaje), "android.resource://" + getPackageName() + "/" + R.raw.paisaje, R.drawable.dados, Multimedia.VIDEO),
                new Multimedia("Web 1", "Primera web", "https://fpadistancia.edu.xunta.gal/", R.drawable.dados, Multimedia.WEB),
                new Multimedia("Audio 1", "Primer audio", String.valueOf(R.raw.mind), R.drawable.dados, Multimedia.AUDIO),
                new Multimedia("Video perro", getString(R.string.txt_perro), "android.resource://" + getPackageName() + "/" + R.raw.perro, R.drawable.dados, Multimedia.VIDEO),
                new Multimedia("Web 1", "Primera web", "https://fpadistancia.edu.xunta.gal/", R.drawable.dados, Multimedia.WEB),
                new Multimedia("Audio 1", "Primer audio", String.valueOf(R.raw.mind), R.drawable.dados, Multimedia.AUDIO),

        }));

        return multimedia;

    }

    @Override
    public void onItemClick(View view, int position) {

        Multimedia item = multimedia.get(position);

        if(item.getTipo() == Multimedia.VIDEO){

            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Agrega la transacción a la pila
            fragmentTransaction.replace(R.id.contenedor, VideoFragment.class, bundle);
            fragmentTransaction.addToBackStack(null); // Agregar a la pila de retroceso
            fragmentTransaction.commit();

        } else if (item.getTipo() == Multimedia.AUDIO){

            MediaPlayer mp = MediaPlayer.create(this, Integer.parseInt(item.getDireccion()));

            if(view.findViewById(R.id.tvDescripcion).getVisibility() == View.VISIBLE){
                Log.i("prueba", "antes: " + String.valueOf(view.findViewById(R.id.tvDescripcion).getVisibility()));
                view.findViewById(R.id.tvDescripcion).setVisibility(View.GONE);
                Log.i("prueba", "despues: " + String.valueOf(view.findViewById(R.id.tvDescripcion).getVisibility()));
                mp.pause();
            } else {
                Log.i("prueba",  "antes: " + String.valueOf(view.findViewById(R.id.tvDescripcion).getVisibility()));
                view.findViewById(R.id.tvDescripcion).setVisibility(View.VISIBLE);
                Log.i("prueba", "despues: " + String.valueOf(view.findViewById(R.id.tvDescripcion).getVisibility()));
                mp.start();
            }

        } else if (item.getTipo() == Multimedia.WEB){

            Bundle bundle = new Bundle();
            bundle.putString("ruta", item.getDireccion());

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.contenedor, WebFragment.class, bundle).commit();

        }

    }

}