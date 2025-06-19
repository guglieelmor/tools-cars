package com.example.tools_cars.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tools_cars.R;
import com.example.tools_cars.adapter.DefaultAdapter;
import com.example.tools_cars.retrofit.RetrofitClient;
import com.example.tools_cars.retrofit.response.DefaultResponse;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModelosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModelosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String codigoMarca;
    private String nomeMarca;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private DefaultAdapter adapter;
    private List<DefaultResponse> lista = new ArrayList<>();

    public ModelosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModelosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModelosFragment newInstance(String param1, String param2) {
        ModelosFragment fragment = new ModelosFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_modelos, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MaterialToolbar toolbar = requireActivity().findViewById(R.id.main_toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        TextView tituloMarcas = view.findViewById(R.id.tituloMarcas);
        tituloMarcas.setText(nomeMarca);

        RetrofitClient.getApi().getModelos(codigoMarca).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DefaultResponse.Modelo> listaModelos = response.body().getModelos();

                    List<DefaultResponse> listaConvertida = converterModeloParaDefault(listaModelos);

                    adapter = new DefaultAdapter(listaConvertida, selecionada -> {
                        AnoFragment anoFragment = new AnoFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("codigo_marca", codigoMarca);
                        bundle.putString("nome_marca", nomeMarca);
                        bundle.putString("codigo_modelo", selecionada.codigo);
                        bundle.putString("nome_modelo", selecionada.nome);
                        anoFragment.setArguments(bundle);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, anoFragment)
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
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Erro ao carregar modelos", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private List<DefaultResponse> converterModeloParaDefault(List<DefaultResponse.Modelo> modelos) {
        List<DefaultResponse> listaConvertida = new ArrayList<>();
        for (DefaultResponse.Modelo m : modelos) {
            DefaultResponse dr = new DefaultResponse();
            dr.codigo = m.getCodigo();
            dr.nome = m.getNome();
            listaConvertida.add(dr);
        }
        return listaConvertida;
    }
}