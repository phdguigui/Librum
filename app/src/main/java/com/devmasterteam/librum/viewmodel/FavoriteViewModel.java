package com.devmasterteam.librum.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devmasterteam.librum.entity.BookEntity;
import com.devmasterteam.librum.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteViewModel extends ViewModel {

    // Acesso a dados
    private final BookRepository repository = BookRepository.getInstance();

    // Livro que será carregado
    private final MutableLiveData<List<BookEntity>> _bookList = new MutableLiveData<>();
    public final LiveData<List<BookEntity>> bookList = _bookList;

    /**
     * Busca todos os livros
     */
    public void getFavoriteBooks() {
        repository.getFavoriteBooks(new retrofit2.Callback<List<BookEntity>>() {
            @Override
            public void onResponse(Call<List<BookEntity>> call, Response<List<BookEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _bookList.setValue(response.body());
                } else {
                    _bookList.setValue(new ArrayList<>()); // evita null
                }
            }

            @Override
            public void onFailure(Call<List<BookEntity>> call, Throwable t) {
                _bookList.setValue(new ArrayList<>()); // erro de rede
            }
        });
    }
    /**
     * Atualiza boolean de favorito
     */
    public void favorite(int bookId) {
        repository.toggleFavoriteStatus(bookId, new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getFavoriteBooks(); // Atualiza após toggle
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Lidar com erro
            }
        });
    }
}