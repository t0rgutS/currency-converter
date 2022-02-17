package com.example.currconverter.repository.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCodesResponse implements Serializable  {
    private String result;
    private String documentation;
    @SerializedName("terms_of_use")
    private String termsOfUse;
    @SerializedName("supported_codes")
    private List<List<String>> supportedCodes = new ArrayList<List<String>>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 780753646117378306L;

}
