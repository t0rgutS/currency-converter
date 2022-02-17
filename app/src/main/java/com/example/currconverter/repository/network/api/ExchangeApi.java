package com.example.currconverter.repository.network.api;

import com.example.currconverter.repository.network.model.ConvertListResponse;
import com.example.currconverter.repository.network.model.ConvertPairResponse;
import com.example.currconverter.repository.network.model.GetCodesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExchangeApi {
    @GET("/v6/{key}/codes")
    Call<GetCodesResponse> getSupportedCodes(@Path("key") String apiKey);

    @GET("/v6/{key}/pair/{source}/{target}")
    Call<ConvertPairResponse> getConvertPairRate(@Path("key") String apiKey,
                                                 @Path("source") String sourceCurrency,
                                                 @Path("target") String targetCurrency);

    @GET("/v6/{key}/latest/{currency}")
    Call<ConvertListResponse> getConvertListRate(@Path("key") String apiKey,
                                                 @Path("currency") String currency);
}
