package com.example.currconverter.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.currconverter.model.Currency;
import com.example.currconverter.repository.ServiceLocator;
import com.example.currconverter.repository.room.model.ExchangeDTO;

import java.util.List;

public class CurrencyViewModel extends ViewModel {

    public LiveData<List<Currency>> getAllCurrencies() {
        return ServiceLocator.getRepository().getAllCurrencies();
    }

    public void synchronizeCurrency(Currency currency) {
        ServiceLocator.getRepository().addCurrency(currency);
        Transformations.switchMap(ServiceLocator.getRepository().getAllExchangesFor(currency),
                exchangeList -> {
                    exchangeList.forEach(exchange -> {
                        Currency localSource = ServiceLocator.getRepository()
                                .getCurrencyByName(exchange.getSource());
                        if (!ServiceLocator.getRepository().isCurrencyExists(exchange.getTarget()))
                            ServiceLocator.getRepository().addCurrency(new Currency(exchange.getTarget()));
                        Currency localTarget = ServiceLocator.getRepository()
                                .getCurrencyByName(exchange.getTarget());
                        ExchangeDTO exchangeDTO = new ExchangeDTO();
                        exchangeDTO.setSourceId(localSource.getId());
                        exchangeDTO.setTargetId(localTarget.getId());
                        exchangeDTO.setLastModifiedOn(exchange.getLastModifiedOn());
                        exchangeDTO.setNextUpdateOn(exchange.getNextUpdateOn());
                        ServiceLocator.getRepository().addExchange(exchangeDTO);
                    });
                    return null;
                });
    }

    public void removeCurrency(Currency currency) {
        ServiceLocator.getRepository().deleteExchangeById(currency.getId());
        ServiceLocator.getRepository().deleteCurrency(currency);
    }
}