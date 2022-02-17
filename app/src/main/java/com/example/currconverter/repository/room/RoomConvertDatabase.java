package com.example.currconverter.repository.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.currconverter.model.Currency;
import com.example.currconverter.model.Exchange;
import com.example.currconverter.repository.room.dao.CurrencyDAO;
import com.example.currconverter.repository.room.dao.ExchangeDAO;
import com.example.currconverter.repository.room.model.ExchangeDTO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;

@Database(entities = {Currency.class, ExchangeDTO.class}, version = 1, exportSchema = false)
public abstract class RoomConvertDatabase extends RoomDatabase {
    public abstract CurrencyDAO currencyDAO();

    public abstract ExchangeDAO exchangeDAO();

    private static volatile RoomConvertDatabase INSTANCE;

    private static final int THREAD_COUNT = 5;

    @Getter
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

    public static RoomConvertDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomConvertDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomConvertDatabase.class, "CurrencyExchangeDatabase")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
