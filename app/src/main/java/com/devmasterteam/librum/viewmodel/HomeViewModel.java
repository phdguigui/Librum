package com.devmasterteam.librum.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devmasterteam.librum.client.RetrofitClient;
import com.devmasterteam.librum.entity.BookEntity;
import com.devmasterteam.librum.repository.BookApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private final BookApi api = RetrofitClient.getBookApi();

    private final MutableLiveData<List<BookEntity>> _bookList = new MutableLiveData<>();
    public final LiveData<List<BookEntity>> bookList = _bookList;

    /**
     * Busca todos os livros da API
     */
    public void getAll() {
        api.getAllBooks().enqueue(new Callback<List<BookEntity>>() {
            @Override
            public void onResponse(Call<List<BookEntity>> call, Response<List<BookEntity>> response) {
                if (response.isSuccessful()) {
                    _bookList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<BookEntity>> call, Throwable t) {
                // Você pode logar o erro ou usar um LiveData de erro
                t.printStackTrace();
            }
        });
    }

    /**
     * Alterna favorito e atualiza a lista
     */
    public void favorite(int bookId) {
        api.toggleFavoriteStatus(bookId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getAll(); // Atualiza após marcar como favorito
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
