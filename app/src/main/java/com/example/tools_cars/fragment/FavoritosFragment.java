package com.example.tools_cars.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools_cars.R;
import com.example.tools_cars.adapter.FavoritoAdapter;
import com.example.tools_cars.entity.CarroFavorito;
import com.example.tools_cars.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoritosFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoritoAdapter adapter;
    private List<CarroFavorito> lista = new ArrayList<>();

    public FavoritosFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFavoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        carregarFavoritos();

        return view;
    }

    private void carregarFavoritos() {
        new Thread(() -> {
            lista = AppDatabase.getDatabase(requireContext()).carroFavoritoDao().listarTodos();

            requireActivity().runOnUiThread(() -> {
                adapter = new FavoritoAdapter(lista, new FavoritoAdapter.OnFavoritoClickListener() {
                    @Override
                    public void onFavoritoClick(CarroFavorito carro) {
                        CarroFragment fragment = new CarroFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("codigo_marca", carro.getCodigoMarca());
                        bundle.putString("nome_marca", carro.getNomeMarca());
                        bundle.putString("codigo_modelo", carro.getCodigoModelo());
                        bundle.putString("nome_modelo", carro.getNomeModelo());
                        bundle.putString("codigo_ano", String.valueOf(carro.getCodigoAno()));
                        bundle.putString("nome_ano", carro.getNomeAno());
                        fragment.setArguments(bundle);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });

                recyclerView.setAdapter(adapter);

                ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        CarroFavorito carroRemovido = lista.get(position);
                        removerFavorito(carroRemovido);
                        adapter.removerItem(position);
                        Toast.makeText(getContext(), "Favorito removido!", Toast.LENGTH_SHORT).show();
                    }
                };

                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                itemTouchHelper.attachToRecyclerView(recyclerView);
            });
        }).start();
    }

    private void removerFavorito(CarroFavorito carro) {
        new Thread(() -> {
            AppDatabase.getDatabase(requireContext()).carroFavoritoDao().remover(carro);
            lista.remove(carro);

            requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
        }).start();
    }
}
