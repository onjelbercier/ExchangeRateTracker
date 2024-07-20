package com.riverdance.ExchangeRateTracker.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.riverdance.ExchangeRateTracker.dto.CurrencyPairRateDto;
import com.riverdance.ExchangeRateTracker.enumeration.CurrencyAbbreviation;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyPairRateDtoDeserializer extends JsonDeserializer<CurrencyPairRateDto> {

    @Override
    public CurrencyPairRateDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode dataNode = node.get("data");

        BigDecimal amount = new BigDecimal(dataNode.get("amount").asText());
        CurrencyAbbreviation base = CurrencyAbbreviation.valueOf(dataNode.get("base").asText());
        CurrencyAbbreviation currency = CurrencyAbbreviation.valueOf(dataNode.get("currency").asText());

        return CurrencyPairRateDto.builder()
                .amount(amount)
                .base(base)
                .currency(currency)
                .build();
    }
}
