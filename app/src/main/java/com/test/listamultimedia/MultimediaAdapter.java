package com.test.listamultimedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.listamultimedia.model.Multimedia;

import java.util.ArrayList;

public class MultimediaAdapter extends RecyclerView.Adapter <MultimediaAdapter.MultimediaViewHolder>{

    ArrayList <Multimedia> multimedia;

    OnItemClickListener onItemClickListener;

    public MultimediaAdapter(ArrayList<Multimedia> multimedia, OnItemClickListener onItemClickListener) {
        this.multimedia = multimedia;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MultimediaAdapter.MultimediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MultimediaAdapter.MultimediaViewHolder multimediaHolder =
                new MultimediaViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_multimedia, parent, false)
                );

        return multimediaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MultimediaAdapter.MultimediaViewHolder holder, int position) {

        Multimedia item = multimedia.get(position);

        holder.tvNombre.setText(item.getNombre());
        holder.tvDescripcion.setText(item.getDescipcion());
        holder.tvDescripcion.setVisibility(View.GONE);
        holder.imgMultimedia.setImageResource(item.getImg());

    }

    @Override
    public int getItemCount() {
        return multimedia.size();
    }

    public class MultimediaViewHolder extends RecyclerView.ViewHolder{

        TextView tvNombre;
        TextView tvDescripcion;
        ImageView imgMultimedia;

        public MultimediaViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            imgMultimedia = itemView.findViewById(R.id.imgMultimedia);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClick (View view, int position);
    }

}
