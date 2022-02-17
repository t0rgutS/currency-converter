package com.example.currconverter.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.currconverter.model.Currency;
import com.example.currconverter.model.Exchange;
import com.example.currconverter.repository.network.CurrencyProvider;
import com.example.currconverter.repository.room.RoomConvertDatabase;
import com.example.currconverter.repository.room.dao.CurrencyDAO;
import com.example.currconverter.repository.room.dao.ExchangeDAO;
import com.example.currconverter.repository.room.model.ExchangeDTO;

import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {
    private CurrencyDAO currencyDAO;
    private ExchangeDAO exchangeDAO;
    private CurrencyProvider provider;

    public RepositoryImpl(Application application, CurrencyProvider currencyProvider) {
        RoomConvertDatabase database = RoomConvertDatabase.getDatabase(application);
        currencyDAO = database.currencyDAO();
        exchangeDAO = database.exchangeDAO();
        provider = currencyProvider;
    }

    @Override
    public LiveData<List<Currency>> getAllCurrencies() {
        LiveData<List<Currency>> remoteCurrencies = provider.getCurrencies();
        LiveData<List<Currency>> localCurrencies = currencyDAO.getAll();
        return Transformations.switchMap(remoteCurrencies, remoteList -> {
            if (remoteList.size() == 0)
                return localCurrencies;
            return Transformations.map(localCurrencies, localList -> {
                remoteList.forEach(remoteEl -> {
                    localList.stream()
                            .filter(el -> el.getName()
                                    .equals(remoteEl.getName())).findFirst()
                            .ifPresent(localEl -> remoteEl.setId(localEl.getId()));
                });
                return remoteList;
            });
        });
    }

    @Override
    public Currency getCurrencyByName(String name) {
        return currencyDAO.getByName(name);
    }

    @Override
    public Boolean isCurrencyExists(String name) {
        return currencyDAO.isExists(name);
    }

    @Override
    public LiveData<Exchange> getExchange(Currency source, Currency target, boolean remote) {
        return remote ? provider.getExchangeFor(source.getName(), target.getName()) : Transformations
                .map(exchangeDAO.getByIds(source.getId(), target.getId()), exchange -> {
                    exchange.setSource(source.getName());
                    exchange.setTarget(target.getName());
                    return exchange;
                });
    }

    @Override
    public LiveData<List<Exchange>> getAllExchangesFor(Currency source) {
        return provider.getAllExchangesFor(source.getName());
    }

    @Override
    public void addCurrency(Currency currency) {
        RoomConvertDatabase.getExecutorService().execute(() -> currencyDAO.addCurrency(currency));
    }

    @Override
    public void deleteCurrency(Currency currency) {
        RoomConvertDatabase.getExecutorService().execute(() -> currencyDAO.deleteCurrency(currency));
    }

    @Override
    public <T extends Exchange> void addExchange(T exchange) {
        RoomConvertDatabase.getExecutorService().execute(() -> exchangeDAO.addExchange((ExchangeDTO) exchange));
    }

    @Override
    public <T extends Exchange> void updateExchange(T exchange) {
        RoomConvertDatabase.getExecutorService().execute(() -> exchangeDAO.updateExchange((ExchangeDTO) exchange));
    }

    @Override
    public void deleteExchangeById(Long id) {
        RoomConvertDatabase.getExecutorService().execute(() -> exchangeDAO.deleteExchangeById(id));
    }


}
