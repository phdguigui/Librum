package com.devmasterteam.librum.repository;

import com.devmasterteam.librum.entity.BookEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface BookApi {

    @GET("books")
    Call<List<BookEntity>> getAllBooks();

    @GET("books/favorites")
    Call<List<BookEntity>> getFavoriteBooks();

    @GET("books/{id}")
    Call<BookEntity> getBookById(@Path("id") int id);

    @PUT("books/{id}/toggle-favorite")
    Call<Void> toggleFavoriteStatus(@Path("id") int id);

    @DELETE("books/{id}")
    Call<Void> deleteBook(@Path("id") int id);

    @POST("books")
    Call<BookEntity> addBook(@Body BookEntity book);

    @PUT("books")
    Call<BookEntity> updateBook(@Body BookEntity book);
}
