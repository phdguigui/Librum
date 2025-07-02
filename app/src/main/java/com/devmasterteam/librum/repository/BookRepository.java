package com.devmasterteam.librum.repository;

import com.devmasterteam.librum.client.RetrofitClient;
import com.devmasterteam.librum.entity.BookEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRepository {

    private static BookRepository instance;
    private BookApi bookApi;

    private BookRepository() {
        bookApi = RetrofitClient.getClient().create(BookApi.class);
    }

    public static BookRepository getInstance() {
        synchronized (BookRepository.class) {
            if (instance == null) {
                instance = new BookRepository();
            }
        }
        return instance;
    }

    public void getAllBooks(Callback<List<BookEntity>> callback) {
        Call<List<BookEntity>> call = bookApi.getAllBooks();
        call.enqueue(callback); // executa async e chama callback
    }

    public void getFavoriteBooks(Callback<List<BookEntity>> callback) {
        Call<List<BookEntity>> call = bookApi.getFavoriteBooks();
        call.enqueue(callback);
    }

    public void getBookById(int id, Callback<BookEntity> callback) {
        Call<BookEntity> call = bookApi.getBookById(id);
        call.enqueue(callback);
    }

    public void toggleFavoriteStatus(int id, Callback<Void> callback) {
        Call<Void> call = bookApi.toggleFavoriteStatus(id);
        call.enqueue(callback);
    }

    public void deleteBook(int id, Callback<Void> callback) {
        Call<Void> call = bookApi.deleteBook(id);
        call.enqueue(callback);
    }

    public void addBook(BookEntity book, Callback<BookEntity> callback) {
        Call<BookEntity> call = bookApi.addBook(book);
        call.enqueue(callback);
    }

    public void updateBook(BookEntity book, Callback<BookEntity> callback) {
        Call<BookEntity> call = bookApi.updateBook(book);
        call.enqueue(callback);
    }
}
