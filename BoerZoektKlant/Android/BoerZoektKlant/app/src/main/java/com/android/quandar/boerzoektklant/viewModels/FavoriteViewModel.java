package com.android.quandar.boerzoektklant.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.android.quandar.boerzoektklant.models.Favorite;
import com.android.quandar.boerzoektklant.repositories.FavoriteRepository;


import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository repository;
    private LiveData<List<Favorite>> allNotes;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Favorite favorite) {
        repository.insert(favorite);
    }

    public void update(Favorite favorite) {
        repository.update(favorite);
    }

    public void delete(Favorite favorite) {
        repository.delete(favorite);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Favorite>> getAllNotes() {
        return allNotes;
    }
}
