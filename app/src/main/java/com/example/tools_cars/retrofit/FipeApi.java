package com.example.tools_cars.retrofit;

import com.example.tools_cars.retrofit.response.CarroResponse;
import com.example.tools_cars.retrofit.response.DefaultResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FipeApi {
    @GET("carros/marcas")
    Call<List<DefaultResponse>> getMarcas();

    @GET("carros/marcas/{codigo}/modelos")
    Call<DefaultResponse> getModelos(
        @Path("codigo") String codigoMarca
    );

    @GET("carros/marcas/{codigoMarca}/modelos/{codigoModelo}/anos")
    Call<List<DefaultResponse>> getAnos(
        @Path("codigoMarca") String codigoMarca,
        @Path("codigoModelo") String codigoModelo
    );

    @GET("carros/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{codigoAno}")
    Call<CarroResponse> getCarro(
        @Path("codigoMarca") String codigoMarca,
        @Path("codigoModelo") String codigoModelo,
        @Path("codigoAno") String codigoAno
    );
}
