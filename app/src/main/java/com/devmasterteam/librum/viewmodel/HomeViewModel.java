package com.devmasterteam.librum.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devmasterteam.librum.entity.BookEntity;
import com.devmasterteam.librum.repository.BookRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    // Acesso a dados
    private final BookRepository repository = BookRepository.getInstance();

    // Livro que será carregado
    private final MutableLiveData<List<BookEntity>> _bookList = new MutableLiveData<>();
    public final LiveData<List<BookEntity>> bookList = _bookList;

    /**
     * Busca todos os livros
     */
    public void getAll() {
        _bookList.setValue(repository.getAllBooks());
    }

    /**
     * Atualiza boolean de favorito
     */
    public void favorite(int bookId) {
        // Atualiza boolean de favorito
        repository.toggleFavoriteStatus(bookId);

        // Atualiza listagem para refletir as mudanças
        getAll();
    }
}