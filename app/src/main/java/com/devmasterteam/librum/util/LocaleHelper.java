package com.devmasterteam.librum.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.preference.PreferenceManager;

import java.util.Locale;

public class LocaleHelper {
    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    // Chame isso para atualizar o contexto com o idioma novo
    public static Context setLocale(Context context, String language) {
        persist(context, language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);
    }

    public static String getLanguage(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(SELECTED_LANGUAGE, Locale.getDefault().getLanguage());
    }

    private static void persist(Context context, String language) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(SELECTED_LANGUAGE, language).apply();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }
}