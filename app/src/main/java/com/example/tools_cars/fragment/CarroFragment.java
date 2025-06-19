package com.example.tools_cars.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools_cars.R;
import com.example.tools_cars.adapter.DefaultAdapter;
import com.example.tools_cars.entity.CarroFavorito;
import com.example.tools_cars.retrofit.RetrofitClient;
import com.example.tools_cars.retrofit.response.CarroResponse;
import com.example.tools_cars.retrofit.response.DefaultResponse;
import com.example.tools_cars.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarroFragment extends Fragment {

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
    private String codigoAno;
    private String nomeAno;
    private CarroResponse carro;
    private boolean favoritoSalvo = false;

    public CarroFragment() {
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
    public static CarroFragment newInstance(String param1, String param2) {
        CarroFragment fragment = new CarroFragment();
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
            codigoAno = getArguments().getString("codigo_ano");
            nomeAno = getArguments().getString("nome_ano");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro, container, false);

        TextView tituloMarca = view.findViewById(R.id.tituloMarca);
        TextView valor = view.findViewById(R.id.valor);
        TextView textMarca = view.findViewById(R.id.textMarca);
        TextView textModelo = view.findViewById(R.id.textModelo);
        TextView textCodigoFipe = view.findViewById(R.id.textCodigoFipe);
        TextView textAnoCombustivel = view.findViewById(R.id.textAnoCombustivel);
        TextView textMesReferencia = view.findViewById(R.id.textMesReferencia);
        ImageButton btnFavorito = view.findViewById(R.id.btnFavorito);

        tituloMarca.setText(nomeMarca);

        RetrofitClient.getApi().getCarro(codigoMarca, codigoModelo, codigoAno).enqueue(new Callback<CarroResponse>() {
            @Override
            public void onResponse(Call<CarroResponse> call, Response<CarroResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    carro = response.body();
                    valor.setText(carro.valor);
                    textMarca.setText(carro.marca);
                    textModelo.setText(carro.modelo);
                    textAnoCombustivel.setText(carro.anoModelo + " • " + carro.combustivel);
                    textCodigoFipe.setText("Código FIPE: " + carro.codigoFipe);
                    textMesReferencia.setText("Referência: " + carro.mesReferencia);

                    new Thread(() -> {
                        CarroFavorito carroFavorito = AppDatabase.getDatabase(requireContext())
                                .carroFavoritoDao()
                                .buscarPorCodigoFipe(carro.codigoFipe);

                        favoritoSalvo = carroFavorito != null;

                        requireActivity().runOnUiThread(() -> {
                            if (favoritoSalvo) {
                                btnFavorito.setColorFilter(getResources().getColor(android.R.color.holo_red_dark));
                            } else {
                                btnFavorito.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                            }
                        });
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<CarroResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Erro ao carregar carro", Toast.LENGTH_SHORT).show();
            }
        });


        btnFavorito.setOnClickListener(v -> {
            if (carro == null) {
                Toast.makeText(getContext(), "Carro ainda não carregado!", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                if (!favoritoSalvo) {
                    CarroFavorito favorito = new CarroFavorito(
                            codigoMarca,
                            nomeMarca,
                            codigoModelo,
                            nomeModelo,
                            codigoAno,
                            nomeAno,
                            carro.codigoFipe
                    );
                    AppDatabase.getDatabase(requireContext()).carroFavoritoDao().inserir(favorito);

                    favoritoSalvo = true;

                    requireActivity().runOnUiThread(() -> {
                        btnFavorito.setColorFilter(getResources().getColor(android.R.color.holo_red_dark));
                        Toast.makeText(getContext(), "Favorito salvo!", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    AppDatabase.getDatabase(requireContext()).carroFavoritoDao().deletarPorCodigoFipe(carro.codigoFipe);

                    favoritoSalvo = false;

                    requireActivity().runOnUiThread(() -> {
                        btnFavorito.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                        Toast.makeText(getContext(), "Favorito removido!", Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        });

        return view;
    }

}