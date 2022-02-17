package com.example.currconverter.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.currconverter.model.Currency;
import com.example.currconverter.repository.ServiceLocator;
import com.example.currconverter.repository.room.model.ExchangeDTO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ConvertViewModel extends ViewModel {

    public LiveData<List<Currency>> getAllCurrencies() {
        return ServiceLocator.getRepository().getAllCurrencies();
    }

    public LiveData<Double> convert(Double value, Currency source, Currency target) {
        if (source.getId() != null && target.getId() != null) {
            return Transformations.switchMap(ServiceLocator.getRepository()
                    .getExchange(source, target, false), localExchange -> {
                if (localExchange != null) {
                    LocalDate nextUpdate = Instant.ofEpochMilli(localExchange.getNextUpdateOn())
                            .atZone(ZoneId.systemDefault()).toLocalDate();
                    if (nextUpdate.isAfter(LocalDate.now())) {
                        return new MutableLiveData<>(value * localExchange.getRate());
                    }
                }
                return Transformations.map(ServiceLocator.getRepository()
                        .getExchange(source, target, true), remoteExchange -> {
                    if (localExchange != null) {
                        ExchangeDTO exchangeDTO = new ExchangeDTO();
                        exchangeDTO.setSourceId(source.getId());
                        exchangeDTO.setTargetId(target.getId());
                        exchangeDTO.setLastModifiedOn(remoteExchange.getLastModifiedOn());
                        exchangeDTO.setNextUpdateOn(remoteExchange.getNextUpdateOn());
                        exchangeDTO.setRate(remoteExchange.getRate());
                        ServiceLocator.getRepository().updateExchange(exchangeDTO);
                    }
                    return value * remoteExchange.getRate();
                });
            });
        }
        return Transformations.map(ServiceLocator.getRepository().getExchange(source, target,
                true), remoteExchange -> value * remoteExchange.getRate());
    }
}