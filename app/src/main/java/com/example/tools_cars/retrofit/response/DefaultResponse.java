package com.example.tools_cars.retrofit.response;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefaultResponse {
    private List<Modelo> modelos;

    @SerializedName("codigo")
    public String codigo;

    @SerializedName("nome")
    public String nome;

    public List<Modelo> getModelos() {
        return modelos;
    }

    public static class Modelo {
        private String codigo;
        private String nome;

        public String getCodigo() {
            return codigo;
        }

        public String getNome() {
            return nome;
        }
    }
}
