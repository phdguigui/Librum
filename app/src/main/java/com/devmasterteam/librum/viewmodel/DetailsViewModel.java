package com.devmasterteam.librum.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devmasterteam.librum.entity.BookEntity;
import com.devmasterteam.librum.repository.BookRepository;

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
        _book.setValue(repository.getBookById(bookId));
    }

    /**
     * Atualiza boolean de favorito
     */
    public void favorite(int bookId) {
        repository.toggleFavoriteStatus(bookId);
        // Após favoritar, atualiza o objeto book
        _book.setValue(repository.getBookById(bookId));
    }

    /**
     * Faz a remoção do livro por ID
     */
    public void delete(int bookId) {
        _bookDeleted.setValue(repository.deleteBook(bookId));
    }

    /**
     * Atualiza um livro no repositório
     */
    public void updateBook(BookEntity bookEntity) {
        boolean success = repository.updateBook(bookEntity);
        _bookUpdated.setValue(success);
        // Atualiza o LiveData do livro para refletir possíveis mudanças
        _book.setValue(repository.getBookById(bookEntity.getId()));
    }
}