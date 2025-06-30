package com.devmasterteam.librum.repository;

import com.devmasterteam.librum.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por armazenar e manipular os livros.
 * Simula um banco de dados local usando uma lista mutável.
 */
public class BookRepository {

    // Lista mutável que armazena os livros
    private final List<BookEntity> books = new ArrayList<>();

    /**
     * Construtor privado
     */
    private BookRepository() {
        books.addAll(getInitialBooks());
    }

    private static BookRepository instance;

    public static BookRepository getInstance() {
        synchronized (BookRepository.class) {
            if (instance == null) {
                instance = new BookRepository();
            }
        }
        return instance;
    }

    private List<BookEntity> getInitialBooks() {
        List<BookEntity> initialBooks = new ArrayList<>();
        initialBooks.add(new BookEntity(1, "To Kill a Mockingbird", "Harper Lee", true, "Ficção"));
        initialBooks.add(new BookEntity(2, "Dom Casmurro", "Machado de Assis", false, "Romance"));
        initialBooks.add(new BookEntity(3, "O Hobbit", "J.R.R. Tolkien", true, "Fantasia"));
        initialBooks.add(new BookEntity(4, "Cem Anos de Solidão", "Gabriel García Márquez", false, "Romance"));
        initialBooks.add(new BookEntity(5, "O Pequeno Príncipe", "Antoine de Saint-Exupéry", false, "Fantasia"));
        initialBooks.add(new BookEntity(6, "Crime e Castigo", "Fiódor Dostoiévski", false, "Ficção policial"));
        initialBooks.add(new BookEntity(7, "Frankenstein", "Mary Shelley", false, "Terror"));
        initialBooks.add(new BookEntity(8, "Harry Potter e a Pedra Filosofal", "J.K. Rowling", false, "Fantasia"));
        initialBooks.add(new BookEntity(9, "Neuromancer", "William Gibson", false, "Cyberpunk"));
        initialBooks.add(new BookEntity(10, "Senhor dos Anéis", "J.R.R. Tolkien", false, "Fantasia"));
        initialBooks.add(new BookEntity(11, "Drácula", "Bram Stoker", false, "Terror"));
        initialBooks.add(new BookEntity(12, "Orgulho e Preconceito", "Jane Austen", false, "Romance"));
        initialBooks.add(new BookEntity(13, "Harry Potter e a Câmara Secreta", "J.K. Rowling", false, "Fantasia"));
        initialBooks.add(new BookEntity(14, "As Crônicas de Nárnia", "C.S. Lewis", false, "Fantasia"));
        initialBooks.add(new BookEntity(15, "O Código Da Vinci", "Dan Brown", false, "Mistério"));
        initialBooks.add(new BookEntity(16, "It: A Coisa", "Stephen King", false, "Terror"));
        initialBooks.add(new BookEntity(17, "Moby Dick", "Herman Melville", true, "Aventura"));
        initialBooks.add(new BookEntity(18, "O Nome do Vento", "Patrick Rothfuss", true, "Fantasia"));
        initialBooks.add(new BookEntity(19, "O Conde de Monte Cristo", "Alexandre Dumas", true, "Aventura"));
        initialBooks.add(new BookEntity(20, "Os Miseráveis", "Victor Hugo", false, "Romance"));
        return initialBooks;
    }

    public List<BookEntity> getAllBooks() {
        return books;
    }

    public List<BookEntity> getFavoriteBooks() {
        List<BookEntity> favoriteBooks = new ArrayList<>();
        for (BookEntity book : books) {
            if (book.isFavorite()) {
                favoriteBooks.add(book);
            }
        }
        return favoriteBooks;
    }

    public BookEntity getBookById(int id) {
        BookEntity book = null;
        for (BookEntity b : books) {
            if (b.getId() == id) {
                book = b;
                break;
            }
        }
        return book;
    }

    public void toggleFavoriteStatus(int id) {
        for (BookEntity book : books) {
            if (book.getId() == id) {
                book.setFavorite(!book.isFavorite());
                break;
            }
        }
    }

    public boolean deleteBook(int id) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                books.remove(i);
                return true;
            }
        }
        return false;
    }

    public void addBook(BookEntity book) {
        int nextId = 1;
        for (BookEntity b : books) {
            if (b.getId() >= nextId) {
                nextId = b.getId() + 1;
            }
        }
        BookEntity bookWithId = new BookEntity(nextId, book.getTitle(), book.getAuthor(), book.isFavorite(), book.getGenre());
        books.add(bookWithId);
    }

    /**
     * Atualiza um livro existente na lista pelo id.
     * Retorna true se o livro foi encontrado e atualizado, false caso contrário.
     */
    public boolean updateBook(BookEntity book) {
        for (BookEntity b : books) {
            if (b.getId() == book.getId()) {
                b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setGenre(book.getGenre());
                b.setFavorite(book.isFavorite());
                return true;
            }
        }
        return false;
    }
}