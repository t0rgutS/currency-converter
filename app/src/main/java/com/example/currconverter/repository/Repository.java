package com.example.currconverter.repository;

import androidx.lifecycle.LiveData;

import com.example.currconverter.model.Currency;
import com.example.currconverter.model.Exchange;

import java.util.List;

public interface Repository {
    LiveData<List<Currency>> getAllCurrencies();
    Currency getCurrencyByName(String name);
    LiveData<Exchange> getExchange(Currency source, Currency target, boolean remote);
    LiveData<List<Exchange>> getAllExchangesFor(Currency source);
    Boolean isCurrencyExists(String name);
    void addCurrency(Currency currency);
    void deleteCurrency(Currency currency);
    <T extends Exchange> void addExchange(T exchange);
    <T extends Exchange> void updateExchange(T exchange);
    void deleteExchangeById(Long id);

}
