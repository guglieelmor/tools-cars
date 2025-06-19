package com.example.tools_cars.retrofit.response;
import com.google.gson.annotations.SerializedName;

public class CarroResponse {

    @SerializedName("TipoVeiculo")
    public int tipoVeiculo;

    @SerializedName("Valor")
    public String valor;

    @SerializedName("Marca")
    public String marca;

    @SerializedName("Modelo")
    public String modelo;

    @SerializedName("AnoModelo")
    public int anoModelo;

    @SerializedName("Combustivel")
    public String combustivel;

    @SerializedName("CodigoFipe")
    public String codigoFipe;

    @SerializedName("MesReferencia")
    public String mesReferencia;

    @SerializedName("SiglaCombustivel")
    public String siglaCombustivel;
}
