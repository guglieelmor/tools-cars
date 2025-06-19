package com.example.tools_cars.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "carro_favorito")
public class CarroFavorito {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String codigoMarca;
    public String nomeMarca;
    public String codigoModelo;
    public String nomeModelo;
    public String codigoAno;
    public String nomeAno;
    public String codigoFipe;


    public CarroFavorito(String codigoMarca, String nomeMarca, String codigoModelo, String nomeModelo, String codigoAno, String nomeAno, String codigoFipe){
        this.codigoMarca = codigoMarca;
        this.nomeMarca = nomeMarca;
        this.codigoModelo = codigoModelo;
        this.nomeModelo = nomeModelo;
        this.codigoAno = codigoAno;
        this.nomeAno = nomeAno;
        this.codigoFipe = codigoFipe;
    }

    public String getCodigoFipe() {
        return codigoFipe;
    }

    public void setCodigoFipe(String codigoFipe) {
        this.codigoFipe = codigoFipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoMarca() {
        return codigoMarca;
    }

    public void setCodigoMarca(String codigoMarca) {
        this.codigoMarca = codigoMarca;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    public String getCodigoModelo() {
        return codigoModelo;
    }

    public void setCodigoModelo(String codigoModelo) {
        this.codigoModelo = codigoModelo;
    }

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public String getCodigoAno() {
        return codigoAno;
    }

    public void setCodigoAno(String codigoAno) {
        this.codigoAno = codigoAno;
    }

    public String getNomeAno() {
        return nomeAno;
    }

    public void setNomeAno(String nomeAno) {
        this.nomeAno = nomeAno;
    }
}
