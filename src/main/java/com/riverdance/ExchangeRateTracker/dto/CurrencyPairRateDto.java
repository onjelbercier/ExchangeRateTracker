package com.riverdance.ExchangeRateTracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.riverdance.ExchangeRateTracker.enumeration.CurrencyAbbreviation;
import com.riverdance.ExchangeRateTracker.rest.CurrencyPairRateDtoDeserializer;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = CurrencyPairRateDtoDeserializer.class)
public class CurrencyPairRateDto {

    @JsonProperty("amount")
    private BigDecimal amount = BigDecimal.ZERO;
    private CurrencyAbbreviation base;
    private CurrencyAbbreviation currency;

}

