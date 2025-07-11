package com.devmasterteam.librum.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.devmasterteam.librum.R;
import com.devmasterteam.librum.databinding.FragmentHomeBinding;
import com.devmasterteam.librum.entity.BookEntity;
import com.devmasterteam.librum.helper.BookConstants;
import com.devmasterteam.librum.ui.adapter.BookAdapter;
import com.devmasterteam.librum.ui.listener.BookListener;
import com.devmasterteam.librum.viewmodel.HomeViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private final BookAdapter adapter = new BookAdapter();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle b) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Atribui um layout que diz como a RecyclerView se comporta
        binding.recyclerBooks.setLayoutManager(new LinearLayoutManager(getContext()));

        // Define um adapter - Faz a ligação da RecyclerView com a listagem de itens
        binding.recyclerBooks.setAdapter(adapter);

        // Atribui implementação de listener
        attachBookListener();

        // Cria os observadores
        observe();

        // Configura o clique do botão de adicionar livro
        setupFab();

        // Retorna elemento raiz
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getAll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Associa o listener de eventos de clique ao adapter de livros.
     * O listener captura os cliques no título do livro para navegar até a tela de detalhes e o
     * clique no ícone de favorito para alternar o status de favorito do livro.
     */
    private void attachBookListener() {
        adapter.attachListener(new BookListener() {
            @Override
            public void onClick(int id) {
                // Passando a informação do livro para a Fragment
                Bundle bundle = new Bundle();
                bundle.putInt(BookConstants.BOOK_ID, id);

                // Inicializa fragment de detalhes
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.navigation_book_details, bundle);
            }

            @Override
            public void onFavoriteClick(int id) {
                viewModel.favorite(id);
            }
        });
    }

    /**
     * Cria os observadores
     */
    private void observe() {
        viewModel.bookList.observe(getViewLifecycleOwner(), new Observer<>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                adapter.updateBooks(books);
            }
        });
    }

    /**
     * Configura o clique do botão flutuante para navegar até o fragmento de adicionar livro.
     */
    private void setupFab() {
        binding.fabAddBook.setOnClickListener(v ->
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_navigation_home_to_addBookFragment));
    }
}