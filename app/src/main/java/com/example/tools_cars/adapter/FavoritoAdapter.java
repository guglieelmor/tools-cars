package com.example.tools_cars.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools_cars.R;
import com.example.tools_cars.entity.CarroFavorito;

import java.util.List;

public class FavoritoAdapter extends RecyclerView.Adapter<FavoritoAdapter.FavoritoViewHolder> {

    public interface OnFavoritoClickListener {
        void onFavoritoClick(CarroFavorito carro);
    }

    private List<CarroFavorito> lista;
    private final OnFavoritoClickListener listener;

    public FavoritoAdapter(List<CarroFavorito> lista, OnFavoritoClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorito, parent, false);
        return new FavoritoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritoViewHolder holder, int position) {
        CarroFavorito carro = lista.get(position);
        holder.nomeFavorito.setText(carro.getNomeMarca() + " " + carro.getNomeModelo() + " (" + carro.getNomeAno() + ")");

        holder.itemView.setOnClickListener(v -> listener.onFavoritoClick(carro));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void removerItem(int position) {
        lista.remove(position);
        notifyItemRemoved(position);
    }

    static class FavoritoViewHolder extends RecyclerView.ViewHolder {
        TextView nomeFavorito;

        public FavoritoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeFavorito = itemView.findViewById(R.id.nomeFavorito);
        }
    }
}
