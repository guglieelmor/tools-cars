package com.example.tools_cars.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools_cars.R;
import com.example.tools_cars.adapter.DefaultAdapter;
import com.example.tools_cars.retrofit.response.DefaultResponse;
import com.example.tools_cars.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarcasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarcasFragment extends Fragment {

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

    public MarcasFragment() {
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
    public static MarcasFragment newInstance(String param1, String param2) {
        MarcasFragment fragment = new MarcasFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marcas, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RetrofitClient.getApi().getMarcas().enqueue(new Callback<List<DefaultResponse>>() {
            @Override
            public void onResponse(Call<List<DefaultResponse>> call, Response<List<DefaultResponse>> response) {
                if (response.isSuccessful()) {
                    lista = response.body();

                    adapter = new DefaultAdapter(lista, selecionada -> {
                        ModelosFragment modelosFragment = new ModelosFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("codigo_marca", selecionada.codigo);
                        bundle.putString("nome_marca", selecionada.nome);
                        modelosFragment.setArguments(bundle);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, modelosFragment)
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