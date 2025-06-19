package com.example.tools_cars.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools_cars.R;
import com.example.tools_cars.adapter.DefaultAdapter;
import com.example.tools_cars.retrofit.RetrofitClient;
import com.example.tools_cars.retrofit.response.DefaultResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private DefaultAdapter adapter;
    private List<DefaultResponse> lista = new ArrayList<>();

    private String codigoMarca;
    private String nomeMarca;
    private String codigoModelo;
    private String nomeModelo;

    public AnoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MarcasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnoFragment newInstance(String param1, String param2) {
        AnoFragment fragment = new AnoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codigoMarca = getArguments().getString("codigo_marca");
            nomeMarca = getArguments().getString("nome_marca");
            codigoModelo = getArguments().getString("codigo_modelo");
            nomeModelo = getArguments().getString("nome_modelo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ano, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView tituloMarcas = view.findViewById(R.id.tituloMarcas);
        tituloMarcas.setText(nomeMarca.toUpperCase());

        TextView tituloModelos = view.findViewById(R.id.tituloModelos);
        tituloModelos.setText(nomeModelo);

        RetrofitClient.getApi().getAnos(codigoMarca, codigoModelo).enqueue(new Callback<List<DefaultResponse>>() {
            @Override
            public void onResponse(Call<List<DefaultResponse>> call, Response<List<DefaultResponse>> response) {
                if (response.isSuccessful()) {
                    lista = response.body();

                    adapter = new DefaultAdapter(lista, selecionada -> {
                        CarroFragment carroFragment = new CarroFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("codigo_marca", codigoMarca);
                        bundle.putString("nome_marca", nomeMarca);
                        bundle.putString("codigo_modelo", codigoModelo);
                        bundle.putString("nome_modelo", nomeModelo);
                        bundle.putString("codigo_ano", selecionada.codigo);
                        bundle.putString("nome_ano", selecionada.nome);
                        carroFragment.setArguments(bundle);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, carroFragment)
                                .addToBackStack(null)
                                .commit();
                    });

                    recyclerView.setAdapter(adapter);

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            adapter.filtrar(newText);
                            return true;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<DefaultResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Erro ao carregar marcas", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}