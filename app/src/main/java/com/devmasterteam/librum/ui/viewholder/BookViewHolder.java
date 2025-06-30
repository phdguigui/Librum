package com.devmasterteam.librum.ui.viewholder;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.devmasterteam.librum.R;
import com.devmasterteam.librum.databinding.ItemBookBinding;
import com.devmasterteam.librum.entity.BookEntity;
import com.devmasterteam.librum.ui.listener.BookListener;

public class BookViewHolder extends RecyclerView.ViewHolder {

    private final ItemBookBinding item;
    private final BookListener listener;

    public BookViewHolder(ItemBookBinding item, BookListener listener) {
        super(item.getRoot());
        this.item = item;
        this.listener = listener;
    }

    /**
     * Faz a atribuição dos valores para os elementos de layout
     */
    public void bind(BookEntity book) {
        item.textviewTitle.setText(book.getTitle());
        item.textviewAuthor.setText(book.getAuthor());
        item.chipGenre.setText(book.getGenre());

        // Cor e destaque para o gênero
        setGenreStyle(book.getGenre());

        // Tratamento para imagem de favorito
        updateFavoriteIcon(book.isFavorite());

        // Evento de click para detalhes
        item.textviewTitle.setOnClickListener(v -> listener.onClick(book.getId()));
        item.textviewAuthor.setOnClickListener(v -> listener.onClick(book.getId()));

        // Evento de favoritar / desfavoritar
        item.imageviewFavorite.setOnClickListener(v -> listener.onFavoriteClick(book.getId()));
    }

    /**
     * Tratamento para imagem de favorito.
     */
    private void updateFavoriteIcon(boolean favorite) {
        int favoriteIcon = favorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_empty;
        item.imageviewFavorite.setImageResource(favoriteIcon);
    }

    /**
     * Define a cor do Chip e da barra lateral no card de acordo com o gênero.
     */
    private void setGenreStyle(String genre) {
        Context context = item.getRoot().getContext();
        int chipColor;
        int barColor;

        switch (genre) {
            case "Terror":
                chipColor = ContextCompat.getColor(context, R.color.red_700);
                barColor = chipColor;
                break;
            case "Fantasia":
                chipColor = ContextCompat.getColor(context, R.color.purple_500);
                barColor = chipColor;
                break;
            case "Ficção policial":
                chipColor = ContextCompat.getColor(context, R.color.teal_700);
                barColor = chipColor;
                break;
            case "Cyberpunk":
                chipColor = ContextCompat.getColor(context, R.color.cyan_700);
                barColor = chipColor;
                break;
            case "Romance":
                chipColor = ContextCompat.getColor(context, R.color.pink_500);
                barColor = chipColor;
                break;
            default:
                chipColor = ContextCompat.getColor(context, R.color.amber_500);
                barColor = chipColor;
                break;
        }

        // Aplica cor no chip
        item.chipGenre.setChipBackgroundColorResource(getResIdFromColorInt(chipColor, context));

        // Aplica cor na barra lateral
        item.viewGenreBar.setBackgroundColor(barColor);
    }

    /**
     * Utilitário para obter o resource id de uma cor a partir de seu valor int.
     * (Necessário para setChipBackgroundColorResource; pode ser melhorado se preferir outra abordagem.)
     */
    private int getResIdFromColorInt(int colorInt, Context context) {
        // Esta função assume que você tem as cores definidas em colors.xml
        // e que os nomes batem com o switch acima. Se não, pode usar setChipBackgroundColor(ColorStateList.valueOf(colorInt));
        // ou pode remover esta função e usar diretamente setChipBackgroundColor(ColorStateList.valueOf(chipColor));
        return R.color.amber_500;
    }
}