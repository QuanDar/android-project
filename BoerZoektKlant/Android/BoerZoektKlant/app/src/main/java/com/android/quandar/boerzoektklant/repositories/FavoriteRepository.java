package com.android.quandar.boerzoektklant.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.android.quandar.boerzoektklant.database.FavoriteDatabase;
import com.android.quandar.boerzoektklant.models.Favorite;
import com.android.quandar.boerzoektklant.models.FavoriteDao;

import java.util.List;

public class FavoriteRepository {
    private FavoriteDao favoriteDao;
    private LiveData<List<Favorite>> allNotes;

    public FavoriteRepository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        favoriteDao = database.favoriteDao();
        allNotes = favoriteDao.getAllNotes();
    }

    public void insert(Favorite favorite) {
        new InsertNoteAsyncTask(favoriteDao).execute(favorite);
    }

    public void update(Favorite favorite) {
        new UpdateNoteAsyncTask(favoriteDao).execute(favorite);
    }

    public void delete(Favorite favorite) {
        new DeleteNoteAsyncTask(favoriteDao).execute(favorite);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(favoriteDao).execute();
    }

    public LiveData<List<Favorite>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private InsertNoteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.insert(favorites[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private UpdateNoteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.update(favorites[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private DeleteNoteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.delete(favorites[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteDao favoriteDao;

        private DeleteAllNotesAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDao.deleteAllNotes();
            return null;
        }
    }
}