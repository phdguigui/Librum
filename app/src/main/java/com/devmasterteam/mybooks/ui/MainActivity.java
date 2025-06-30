package com.devmasterteam.mybooks.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.devmasterteam.mybooks.R;
import com.google.android.material.appbar.MaterialToolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.devmasterteam.mybooks.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupNavigation();
    }

    private void setupToolbar() {
        MaterialToolbar topAppBar = binding.topAppBar;
        setSupportActionBar(topAppBar);

        // Oculta o título da Toolbar (deixa só o ícone)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_language) {
                showLanguagePopup(findViewById(R.id.topAppBar));
                return true;
            } else if (item.getItemId() == R.id.action_theme) {
                toggleTheme();
                return true;
            }
            return false;
        });
    }

    // Exibe menu dropbox para seleção de idioma
    private void showLanguagePopup(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add("Português");
        popup.getMenu().add("Inglês");
        popup.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getTitle().equals("Português")) {
                Toast.makeText(this, "Idioma: Português", Toast.LENGTH_SHORT).show();
                // TODO: Trocar idioma para pt
            } else if (menuItem.getTitle().equals("Inglês")) {
                Toast.makeText(this, "Idioma: Inglês", Toast.LENGTH_SHORT).show();
                // TODO: Trocar idioma para en
            }
            return true;
        });
        popup.show();
    }

    // Alterna entre modo noturno/diurno
    private void toggleTheme() {
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Toast.makeText(this, "Modo diurno ativado", Toast.LENGTH_SHORT).show();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Toast.makeText(this, "Modo noturno ativado", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupNavigation() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.navigation_home, R.id.navigation_favorite)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.topAppBar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        MenuItem themeItem = menu.findItem(R.id.action_theme);
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            themeItem.setIcon(R.drawable.ic_theme_night); // ícone lua
        } else {
            themeItem.setIcon(R.drawable.ic_theme_day);   // ícone sol
        }
        return true;
    }
}