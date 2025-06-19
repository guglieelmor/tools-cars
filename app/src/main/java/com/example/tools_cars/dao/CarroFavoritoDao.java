package com.example.tools_cars.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.tools_cars.entity.CarroFavorito;
import java.util.List;

@Dao
public interface CarroFavoritoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserir(CarroFavorito carroFavorito);

    @Query("SELECT * FROM carro_favorito")
    List<CarroFavorito> listarTodos();

    @Query("SELECT * FROM carro_favorito WHERE codigoFipe = :codigoFipe LIMIT 1")
    CarroFavorito buscarPorCodigoFipe(String codigoFipe);

    @Query("DELETE FROM carro_favorito WHERE codigoFipe = :codigoFipe")
    void deletarPorCodigoFipe(String codigoFipe);

    @Delete
    void remover(CarroFavorito carroFavorito);
}
