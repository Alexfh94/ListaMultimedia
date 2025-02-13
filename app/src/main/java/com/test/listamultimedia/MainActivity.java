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

        multimedia = cargarDatos(); // Cargar datos de la lista multimedia
        cargarRecyclerView(multimedia); // Cargar el RecyclerView con los datos
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

    // Método que carga los datos multimedia (videos, audios y webs)
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

    // Método que maneja el clic en los elementos de la lista multimedia
    @Override
    public void onItemClick(View view, int position) {
        Multimedia item = multimedia.get(position);

        // Si el item es un video
        if (item.getTipo() == Multimedia.VIDEO) {

            // Si es diferente al audio previamente seleccionado, detenerlo
            if (position != currentAudioItem) {
                if (previousAudioItemView != null) {
                    Button returnButton = previousAudioItemView.findViewById(R.id.volverButton);
                    if (returnButton != null) {
                        returnButton.performClick(); // Simular el clic del botón "volver"
                    }
                }
            }
            currentAudioItem = position;

            // Crear el fragmento del video y pasarle los datos
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, VideoFragment.class, bundle);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            contador = 0; // Reiniciar el contador
        }
        // Si el item es un audio
        else if (item.getTipo() == Multimedia.AUDIO) {

            if (position != currentAudioItem) {
                // Detener cualquier audio previo
                if (previousAudioItemView != null) {
                    Button returnButton = previousAudioItemView.findViewById(R.id.volverButton);
                    if (returnButton != null) {
                        returnButton.performClick(); // Simular el clic del botón "volver"
                    }
                }

                // Cambiar al nuevo item
                currentAudioItem = position;

                // Detener cualquier reproducción en curso
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                // Inicializar y configurar el nuevo audio
                MediaPlayer mp = MediaPlayer.create(this, Integer.parseInt(item.getDireccion()));
                videoView = new VideoView(this);
                videoView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 100)); // Ajustar altura pequeña

                // Mostrar la descripción
                view.findViewById(R.id.tvDescripcion).setVisibility(View.VISIBLE);

                CardView cardView = view.findViewById(R.id.cardView2);
                videoView = view.findViewById(R.id.videoView);
                cardView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.VISIBLE);

                // Cargar el video desde raw
                String videoPath = "android.resource://" + getPackageName() + "/raw/waveline";
                videoView.setVideoURI(Uri.parse(videoPath));

                // Mostrar los botones de control (play, pause, stop, volver)
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

                // Configurar el botón "play"
                playButton.setOnClickListener(v -> {
                    if ((mediaPlayer == null || !mediaPlayer.isPlaying()) && (mp != null && !mp.isPlaying())) {
                        mediaPlayer = mp; // Usamos el mismo MediaPlayer para audio y video
                        videoView.start();
                        mp.start();
                    }
                });

                // Configurar el botón "pause"
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

                // Configurar el botón "stop"
                stopButton.setOnClickListener(v -> {
                    // Detener la reproducción si está en curso
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        videoView.stopPlayback();
                    }
                    if (mp != null && mp.isPlaying()) {
                        mp.stop();
                    }
                });

                // Configurar el botón "volver"
                returnButton.setOnClickListener(v -> {
                    mediaPlayer.stop();
                    videoView.stopPlayback();
                    mp.stop();
                    contador = 0;

                    // Ocultar botones y video
                    contenedorBotones.setVisibility(View.GONE);
                    playButton.setVisibility(View.GONE);
                    pauseButton.setVisibility(View.GONE);
                    stopButton.setVisibility(View.GONE);
                    returnButton.setVisibility(View.GONE);

                    cardView.setVisibility(View.GONE);
                    videoView.setVisibility(View.GONE);

                    view.findViewById(R.id.tvDescripcion).setVisibility(View.GONE);
                });

                // Hacer que el video se reproduzca en bucle hasta que acabe el audio
                videoView.setOnCompletionListener(mp1 -> videoView.start());
                mp.setOnCompletionListener(mp1 -> {
                    videoView.stopPlayback();
                });

                // Guardar la vista del item actual para poder manejar la acción "volver"
                previousAudioItemView = view;
            }

        }
        // Si el item es una web
        else if (item.getTipo() == Multimedia.WEB) {

            if (position != currentAudioItem) {
                // Detener cualquier audio previo
                if (previousAudioItemView != null) {
                    Button returnButton = previousAudioItemView.findViewById(R.id.volverButton);
                    if (returnButton != null) {
                        returnButton.performClick(); // Simular el clic del botón "volver"
                    }
                }
            }

            currentAudioItem = position;

            // Crear el fragmento de la web y pasarle la URL
            Bundle bundle = new Bundle();
            bundle.putString("ruta", item.getDireccion());

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.contenedor, WebFragment.class, bundle).commit();
            contador = 0; // Reiniciar contador
        }
    }
}
