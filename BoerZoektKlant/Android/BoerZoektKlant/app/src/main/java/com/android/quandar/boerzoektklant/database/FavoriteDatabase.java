package com.android.quandar.boerzoektklant.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.android.quandar.boerzoektklant.models.Favorite;
import com.android.quandar.boerzoektklant.models.FavoriteDao;

@Database(entities = {Favorite.class}, version = 2)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase instance;

    public abstract FavoriteDao favoriteDao();

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDatabase.class, "favorite_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteDao favoriteDao;

        private PopulateDbAsyncTask(FavoriteDatabase db) {
            favoriteDao = db.favoriteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDao.insert(new Favorite("Title 1", "Description 1", 1));
            favoriteDao.insert(new Favorite("Title 2", "Description 2", 2));
            favoriteDao.insert(new Favorite("Title 3", "Description 3", 3));
            return null;
        }
    }
}