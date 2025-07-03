package com.devmasterteam.librum.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devmasterteam.librum.entity.BookEntity;
import com.devmasterteam.librum.repository.BookRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsViewModel extends AndroidViewModel {

    // Acesso a dados
    private final BookRepository repository = BookRepository.getInstance();

    // Livro que será carregado
    private final MutableLiveData<BookEntity> _book = new MutableLiveData<>();
    public LiveData<BookEntity> book = _book;

    // Livro que será removido
    private final MutableLiveData<Boolean> _bookDeleted = new MutableLiveData<>();
    public LiveData<Boolean> bookDeleted = _bookDeleted;

    // Livro atualizado
    private final MutableLiveData<Boolean> _bookUpdated = new MutableLiveData<>();
    public LiveData<Boolean> bookUpdated = _bookUpdated;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Carrega livro do repositório
     */
    public void getBook(int bookId) {
        repository.getBookById(bookId, new retrofit2.Callback<BookEntity>() {
            @Override
            public void onResponse(Call<BookEntity> call, Response<BookEntity> response) {
                if (response.isSuccessful()) {
                    _book.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BookEntity> call, Throwable t) {
                _book.postValue(null);
            }
        });
    }

    public void favorite(int bookId) {
        repository.toggleFavoriteStatus(bookId, new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getBook(bookId); // Atualiza o LiveData após favoritar
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Erro ao favoritar - opcionalmente logar ou exibir mensagem
            }
        });
    }

    public void delete(int bookId) {
        repository.deleteBook(bookId, new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                _bookDeleted.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                _bookDeleted.postValue(false);
            }
        });
    }

    public void updateBook(BookEntity bookEntity) {
        repository.updateBook(bookEntity, new retrofit2.Callback<BookEntity>() {
            @Override
            public void onResponse(Call<BookEntity> call, Response<BookEntity> response) {
                if (response.isSuccessful()) {
                    _bookUpdated.postValue(true);
                    _book.postValue(response.body());
                } else {
                    _bookUpdated.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<BookEntity> call, Throwable t) {
                _bookUpdated.postValue(false);
            }
        });
    }
}