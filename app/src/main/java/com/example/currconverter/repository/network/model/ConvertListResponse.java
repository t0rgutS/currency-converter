package com.example.currconverter.repository.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConvertListResponse implements Serializable {
    private String result;
    private String documentation;
    @SerializedName("terms_of_use")
    public String termsOfUse;
    @SerializedName("time_last_update_unix")
    public Long timeLastUpdateUnix;
    @SerializedName("time_last_update_utc")
    public String timeLastUpdateUtc;
    @SerializedName("time_next_update_unix")
    public Long timeNextUpdateUnix;
    @SerializedName("time_next_update_utc")
    public String timeNextUpdateUtc;
    @SerializedName("base_code")
    public String baseCode;
    @SerializedName("conversion_rates")
    private Map<String, Double> conversionRates;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7472995153194965018L;
}