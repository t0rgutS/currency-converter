package com.example.currconverter.repository;

import android.app.Application;

import com.example.currconverter.repository.network.CurrencyProvider;
import com.google.gson.Gson;

import lombok.Getter;

public class ServiceLocator {
    private static Gson gson;
    @Getter
    private static Repository repository;

    public static void init(Application application) {
        repository = new RepositoryImpl(application, new CurrencyProvider());
    }

    public static Gson getGson() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }
}
