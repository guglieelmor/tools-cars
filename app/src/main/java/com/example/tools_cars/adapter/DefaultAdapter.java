package com.example.tools_cars.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools_cars.R;
import com.example.tools_cars.retrofit.response.DefaultResponse;

import java.util.ArrayList;
import java.util.List;

public class DefaultAdapter extends RecyclerView.Adapter<DefaultAdapter.DefaultViewHolder> {

    private List<DefaultResponse> listaOriginal;
    private List<DefaultResponse> listaFiltrada;
    private final OnDefaultClickListener listener;

    public interface OnDefaultClickListener {
        void onMarcaClick(DefaultResponse marca);
    }

    public DefaultAdapter(List<DefaultResponse> lista, OnDefaultClickListener listener) {
        this.listaOriginal = lista;
        this.listaFiltrada = new ArrayList<>(lista);
        this.listener = listener;
    }

    public void filtrar(String texto) {
        listaFiltrada.clear();
        if (texto.isEmpty()) {
            listaFiltrada.addAll(listaOriginal);
        } else {
            texto = texto.toLowerCase();
            for (DefaultResponse m : listaOriginal) {
                if (m.nome.toLowerCase().contains(texto)) {
                    listaFiltrada.add(m);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new DefaultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        DefaultResponse marca = listaFiltrada.get(position);
        holder.nome.setText(capitalizeWords(marca.nome));
        holder.itemView.setOnClickListener(v -> listener.onMarcaClick(marca));
    }

    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView nome;

        public DefaultViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textMarca);
        }
    }

    private String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) return "";
        String[] words = input.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

}
