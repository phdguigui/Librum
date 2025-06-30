package com.devmasterteam.librum.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devmasterteam.librum.R;
import com.devmasterteam.librum.databinding.FragmentDetailsBinding;
import com.devmasterteam.librum.entity.BookEntity;
import com.devmasterteam.librum.helper.BookConstants;
import com.devmasterteam.librum.util.LocaleHelper;
import com.devmasterteam.librum.viewmodel.DetailsViewModel;

public class DetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentDetailsBinding binding;
    private DetailsViewModel viewModel;
    private int bookId = 0;
    private BookEntity currentBook = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleHelper.setLocale(context, LocaleHelper.getLanguage(context)));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle b) {
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        binding = FragmentDetailsBinding.inflate(inflater, container, false);

        // Busca ID recebido por Bundle e carrega informações
        bookId = getArguments() != null ? getArguments().getInt(BookConstants.BOOK_ID) : 0;
        viewModel.getBook(bookId);

        setListeners();
        setObservers();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_delete) {
            handleBookRemoval();
        } else if (id == R.id.imageview_back) {
            requireActivity().getSupportFragmentManager().popBackStack();
        } else if (id == R.id.checkbox_favorite) {
            handleToggleFavorite();
        } else if (id == R.id.button_save) {
            handleBookEdit();
        }
    }

    private void setListeners() {
        binding.buttonDelete.setOnClickListener(this);
        binding.imageviewBack.setOnClickListener(this);
        binding.checkboxFavorite.setOnClickListener(this);
        binding.buttonSave.setOnClickListener(this);
    }

    private void setObservers() {
        viewModel.book.observe(getViewLifecycleOwner(), new Observer<>() {
            @Override
            public void onChanged(BookEntity book) {
                currentBook = book;
                // Preenche campos editáveis com dados do livro
                binding.edittextTitle.setText(book.getTitle());
                binding.edittextAuthor.setText(book.getAuthor());
                binding.edittextGenre.setText(book.getGenre());
                binding.checkboxFavorite.setChecked(book.isFavorite());

                setGenreBackgroundColor(book.getGenre());
            }
        });

        viewModel.bookDeleted.observe(getViewLifecycleOwner(), new Observer<>() {
            @Override
            public void onChanged(Boolean isDeleted) {
                Toast.makeText(getContext(), getString(R.string.msg_removed_successfully), Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        viewModel.bookUpdated.observe(getViewLifecycleOwner(), new Observer<>() {
            @Override
            public void onChanged(Boolean isUpdated) {
                if (isUpdated) {
                    Toast.makeText(getContext(), getString(R.string.book_saved_success), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.error_update_book), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleBookRemoval() {
        Context context = getContext();
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(getString(R.string.dialog_message_delete_item))
                    .setPositiveButton(getString(R.string.dialog_positive_button_yes), (dialog, id) -> {
                        viewModel.delete(bookId);
                    })
                    .setNegativeButton(getString(R.string.dialog_negative_button_no), (dialog, id) -> {
                        dialog.dismiss();
                    });
            builder.create().show();
        }
    }

    private void handleToggleFavorite() {
        viewModel.favorite(bookId);
    }

    private void handleBookEdit() {
        if (currentBook == null) return;
        String newTitle = binding.edittextTitle.getText().toString().trim();
        String newAuthor = binding.edittextAuthor.getText().toString().trim();
        String newGenre = binding.edittextGenre.getText().toString().trim();
        boolean isFavorite = binding.checkboxFavorite.isChecked();

        // Atualiza o objeto atual
        currentBook.setTitle(newTitle);
        currentBook.setAuthor(newAuthor);
        currentBook.setGenre(newGenre);
        currentBook.setFavorite(isFavorite);

        viewModel.updateBook(currentBook);
    }

    private void setGenreBackgroundColor(String genre) {
        if ("Terror".equals(genre)) {
            binding.edittextGenre.setBackgroundResource(R.drawable.rounded_label_red);
        } else if ("Fantasia".equals(genre)) {
            binding.edittextGenre.setBackgroundResource(R.drawable.rounded_label_fantasy);
        } else {
            binding.edittextGenre.setBackgroundResource(R.drawable.rounded_label_teal);
        }
    }
}