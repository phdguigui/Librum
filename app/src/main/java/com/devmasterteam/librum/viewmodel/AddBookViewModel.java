package com.devmasterteam.librum.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devmasterteam.librum.entity.BookEntity;
import com.devmasterteam.librum.repository.BookRepository;

public class AddBookViewModel extends ViewModel {

    private final BookRepository repository = BookRepository.getInstance();
    private final MutableLiveData<Boolean> _bookSaved = new MutableLiveData<>(false);
    public final LiveData<Boolean> bookSaved = _bookSaved;

    public void saveBook(String title, String author, String genre, boolean isFavorite) {
        // Validação simples
        if (title == null || title.trim().isEmpty() ||
                author == null || author.trim().isEmpty() ||
                genre == null || genre.trim().isEmpty()) {
            _bookSaved.setValue(false);
            return;
        }

        // Cria o livro (id será ignorado pelo backend)
        BookEntity book = new BookEntity(0, title, author, isFavorite, genre);

        // Chama o repositório e espera a resposta da API
        repository.addBook(book, new retrofit2.Callback<BookEntity>() {
            @Override
            public void onResponse(retrofit2.Call<BookEntity> call, retrofit2.Response<BookEntity> response) {
                if (response.isSuccessful()) {
                    _bookSaved.postValue(true);
                } else {
                    _bookSaved.postValue(false);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BookEntity> call, Throwable t) {
                _bookSaved.postValue(false);
            }
        });
    }
}