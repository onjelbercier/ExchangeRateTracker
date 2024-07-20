package com.riverdance.ExchangeRateTracker.common;

import com.riverdance.ExchangeRateTracker.dto.CurrencyPairRateDto;

import java.util.concurrent.atomic.AtomicReference;

public class CoinbaseMemento {
    public static AtomicReference<CurrencyPairRateDto> previousCurrencyPairRateDtoRef = new AtomicReference<>();
}
