package com.example.tools_cars.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tools_cars.dao.CarroFavoritoDao;
import com.example.tools_cars.entity.CarroFavorito;

@Database(entities = {CarroFavorito.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CarroFavoritoDao carroFavoritoDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized(AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "db_carro").build();
                }
            }
        }
        return INSTANCE;
    }
}
