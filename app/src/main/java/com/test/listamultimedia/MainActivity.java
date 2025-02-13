package com.test.listamultimedia;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.test.listamultimedia.model.Multimedia;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements MultimediaAdapter.OnItemClickListener {

    ArrayList<Multimedia> multimedia = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private VideoView videoView;
    private int contador = 0;
    private int lastPosition = -1;
    private int currentAudioItem = -1;

    private View previousAudioItemView = null; // Variable para guardar el item de audio anterior

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

    // Método que carga el recyclerView con el adaptador del catálogo
    public void cargarRecyclerView(ArrayList<Multimedia> multimedia) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("lista", multimedia);

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.contenedor, ListFragment.class, bundle)
                .commit();
    }

    public ArrayList<Multimedia> cargarDatos() {
        return new ArrayList<>(Arrays.asList(
                new Multimedia("Video dados", getString(R.string.txt_dados), "android.resource://" + getPackageName() + "/" + R.raw.dados, R.drawable.ic_video_foreground, Multimedia.VIDEO),
                new Multimedia("Video Dublin", getString(R.string.txt_dublin), "android.resource://" + getPackageName() + "/" + R.raw.dublin, R.drawable.ic_video_foreground, Multimedia.VIDEO),
                new Multimedia("Web Distancia", "Primera web", "https://fpadistancia.edu.xunta.gal/", R.drawable.ic_web_foreground, Multimedia.WEB),
                new Multimedia("Audio Mind", "Primer audio", String.valueOf(R.raw.mind), R.drawable.ic_audio_foreground, Multimedia.AUDIO),
                new Multimedia("Web Youtube", "Enlace a Youtube", "https://www.youtube.com/", R.drawable.ic_web_foreground, Multimedia.WEB),
                new Multimedia("Audio Creep", "Segundo audio", String.valueOf(R.raw.creep), R.drawable.ic_audio_foreground, Multimedia.AUDIO)
        ));
    }

    @Override
    public void onItemClick(View view, int position) {
        Multimedia item = multimedia.get(position);






        if (item.getTipo() == Multimedia.VIDEO) {

            if (position != currentAudioItem) {
                // Si hay un item de audio previo, ejecutar la acción del botón "volver"
                if (previousAudioItemView != null) {
                    // Ejecutar el "volver" del item anterior
                    Button returnButton = previousAudioItemView.findViewById(R.id.volverButton);
                    if (returnButton != null) {
                        returnButton.performClick(); // Simular el clic del botón "volver"
                    }
                }
            }
            currentAudioItem = position;


            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, VideoFragment.class, bundle);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            contador = 0;
        } else if (item.getTipo() == Multimedia.AUDIO) {

            // Si es un item de audio y es diferente al actual
            if (position != currentAudioItem) {
                // Si hay un item de audio previo, ejecutar la acción del botón "volver"
                if (previousAudioItemView != null) {
                    // Ejecutar el "volver" del item anterior
                    Button returnButton = previousAudioItemView.findViewById(R.id.volverButton);
                    if (returnButton != null) {
                        returnButton.performClick(); // Simular el clic del botón "volver"
                    }
                }

                // Cambiar al nuevo item
                currentAudioItem = position;

                // Detener cualquier reproducción en curso antes de procesar el nuevo clic
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                // Inicializamos el nuevo audio
                MediaPlayer mp = MediaPlayer.create(this, Integer.parseInt(item.getDireccion()));
                videoView = new VideoView(this);
                videoView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 100)); // Ajustar altura pequeña

                // Mostrar la descripción
                view.findViewById(R.id.tvDescripcion).setVisibility(View.VISIBLE);

                CardView cardView = view.findViewById(R.id.cardView2);
                VideoView videoView = view.findViewById(R.id.videoView);
                cardView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.VISIBLE);

                // Cargar video desde raw
                String videoPath = "android.resource://" + getPackageName() + "/raw/waveline"; // Video desde raw
                videoView.setVideoURI(Uri.parse(videoPath));

                // Mostrar botones de control
                LinearLayout contenedorBotones = view.findViewById(R.id.contenedorBotones);
                contenedorBotones.setVisibility(View.VISIBLE);
                Button playButton = view.findViewById(R.id.playButton);
                Button pauseButton = view.findViewById(R.id.pauseButton);
                Button stopButton = view.findViewById(R.id.stopButton);
                Button returnButton = view.findViewById(R.id.volverButton);

                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                returnButton.setVisibility(View.VISIBLE);

                // Play button
                playButton.setOnClickListener(v -> {
                    if ((mediaPlayer == null || !mediaPlayer.isPlaying()) && (mp != null && !mp.isPlaying())) {
                        mediaPlayer = mp; // Usamos el mismo MediaPlayer para audio y video
                        videoView.start();
                        mp.start();
                    }
                });

                // Pause button
                returnButton.setOnClickListener(v -> {
                    mediaPlayer.stop();
                    videoView.stopPlayback();
                    mp.stop();
                    contador = 0;

                    contenedorBotones.setVisibility(View.GONE);
                    playButton.setVisibility(View.GONE);
                    pauseButton.setVisibility(View.GONE);
                    stopButton.setVisibility(View.GONE);
                    returnButton.setVisibility(View.GONE);

                    cardView.setVisibility(View.GONE);
                    videoView.setVisibility(View.GONE);

                    view.findViewById(R.id.tvDescripcion).setVisibility(View.GONE);
                });

                // Hacemos que el video se reproduzca en bucle hasta que acabe el audio.
                videoView.setOnCompletionListener(mp1 -> videoView.start());
                mp.setOnCompletionListener(mp1 -> {
                    videoView.stopPlayback();
                });

                // Pause button
                pauseButton.setOnClickListener(v -> {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        videoView.pause();
                    }
                    if (mp != null && mp.isPlaying()) {
                        mp.pause();
                        videoView.pause();
                    }
                });

                // Stop button
                stopButton.setOnClickListener(v -> {
                    // Verifica si el mediaPlayer y el mp están en reproducción antes de intentar detenerlos
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        videoView.stopPlayback();
                    }
                    if (mp != null && mp.isPlaying()) {
                        mp.stop();
                    }
                });

                // Guardar la vista del item actual para poder manejar la acción "volver"
                previousAudioItemView = view;
            }




        } else if (item.getTipo() == Multimedia.WEB) {

            if (position != currentAudioItem) {
                // Si hay un item de audio previo, ejecutar la acción del botón "volver"
                if (previousAudioItemView != null) {
                    // Ejecutar el "volver" del item anterior
                    Button returnButton = previousAudioItemView.findViewById(R.id.volverButton);
                    if (returnButton != null) {
                        returnButton.performClick(); // Simular el clic del botón "volver"
                    }
                }
            }

            currentAudioItem = position;

            Bundle bundle = new Bundle();
            bundle.putString("ruta", item.getDireccion());

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.contenedor, WebFragment.class, bundle).commit();
            contador = 0;
        }






    }
}