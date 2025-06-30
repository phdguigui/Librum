package com.devmasterteam.librum.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.devmasterteam.librum.R;
import com.devmasterteam.librum.databinding.FragmentAddBookBinding;
import com.devmasterteam.librum.viewmodel.AddBookViewModel;

public class AddBookFragment extends Fragment {

    private FragmentAddBookBinding binding;
    private AddBookViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddBookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddBookViewModel.class);

        binding.buttonSave.setOnClickListener(v -> {
            String title = binding.edittextTitle.getText().toString();
            String author = binding.edittextAuthor.getText().toString();
            String genre = binding.edittextGenre.getText().toString();
            boolean isFavorite = binding.checkboxFavorite.isChecked();

            viewModel.saveBook(title, author, genre, isFavorite);
        });

        viewModel.bookSaved.observe(getViewLifecycleOwner(), saved -> {
            if (saved) {
                Toast.makeText(getContext(), R.string.book_saved_success, Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}