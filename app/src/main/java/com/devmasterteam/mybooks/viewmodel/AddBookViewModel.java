package com.devmasterteam.mybooks.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devmasterteam.mybooks.entity.BookEntity;
import com.devmasterteam.mybooks.repository.BookRepository;

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
        // Cria o livro com id dummy (repository define o id correto)
        BookEntity book = new BookEntity(0, title, author, isFavorite, genre);
        repository.addBook(book);
        _bookSaved.setValue(true);
    }
}