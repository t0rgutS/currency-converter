package com.example.currconverter.repository.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.currconverter.BuildConfig;
import com.example.currconverter.model.Currency;
import com.example.currconverter.model.Exchange;
import com.example.currconverter.repository.network.api.ExchangeApi;
import com.example.currconverter.repository.network.model.ConvertListResponse;
import com.example.currconverter.repository.network.model.ConvertPairResponse;
import com.example.currconverter.repository.network.model.GetCodesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyProvider {
    private ExchangeApi api;
    private MutableLiveData<List<Currency>> supportedCodes;

    public CurrencyProvider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ExchangeApi.class);
        supportedCodes = new MutableLiveData<>();
    }

    public LiveData<List<Currency>> getCurrencies() {
        api.getSupportedCodes(BuildConfig.EXCHANGE_RATE_API_KEY).enqueue(new Callback<GetCodesResponse>() {
            @Override
            public void onResponse(Call<GetCodesResponse> call, Response<GetCodesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Currency> currencies = new ArrayList<>();
                    response.body().getSupportedCodes().forEach(codePair -> currencies.add(new Currency(codePair.get(0))));
                    supportedCodes.setValue(currencies);
                }
            }

            @Override
            public void onFailure(Call<GetCodesResponse> call, Throwable t) {

            }
        });
        return supportedCodes;
    }

    public LiveData<Exchange> getExchangeFor(String sourceCurrency, String targetCurrence) {
        MutableLiveData<Exchange> exchangeLive = new MutableLiveData<>();
        api.getConvertPairRate(BuildConfig.EXCHANGE_RATE_API_KEY, sourceCurrency, targetCurrence)
                .enqueue(new Callback<ConvertPairResponse>() {
                    @Override
                    public void onResponse(Call<ConvertPairResponse> call, Response<ConvertPairResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Exchange exchange = new Exchange();
                            exchange.setRate(response.body().conversionRate);
                            exchange.setLastModifiedOn(response.body().timeLastUpdateUnix);
                            exchangeLive.setValue(exchange);
                        }
                    }

                    @Override
                    public void onFailure(Call<ConvertPairResponse> call, Throwable t) {

                    }
                });
        return exchangeLive;
    }

    public LiveData<List<Exchange>> getAllExchangesFor(String currency) {
        MutableLiveData<List<Exchange>> exchangesLive = new MutableLiveData<>();
        api.getConvertListRate(BuildConfig.EXCHANGE_RATE_API_KEY, currency).enqueue(new Callback<ConvertListResponse>() {
            @Override
            public void onResponse(Call<ConvertListResponse> call, Response<ConvertListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Exchange> exchanges = new ArrayList<>();
                    response.body().getConversionRates().forEach((k, v) -> {
                        Exchange exchange = new Exchange();
                        exchange.setSource(currency);
                        exchange.setTarget(k);
                        exchange.setLastModifiedOn(response.body().getTimeLastUpdateUnix());
                        exchange.setNextUpdateOn(response.body().getTimeNextUpdateUnix());
                        exchanges.add(exchange);
                    });
                    exchangesLive.setValue(exchanges);
                }
            }

            @Override
            public void onFailure(Call<ConvertListResponse> call, Throwable t) {

            }
        });
        return exchangesLive;
    }

}
