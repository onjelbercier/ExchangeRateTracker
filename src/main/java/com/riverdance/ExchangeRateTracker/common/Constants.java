package com.riverdance.ExchangeRateTracker.common;

import com.riverdance.ExchangeRateTracker.enumeration.CurrencyAbbreviation;

import java.math.BigDecimal;

public class Constants
{
    public static final String HYPHEN = "-";

    public static final String PAIR_WITH_HYPHEN_TEMPLATE = "%s"+HYPHEN+"%s";
    public static class Coinbase {
        public static final BigDecimal PIP_VALUE = new BigDecimal("0.0001");
        public  static final BigDecimal PIP_THRESHOLD = new BigDecimal(".5");
        private static final String COINBASE_BASE_URL = "https://api.coinbase.com/v2";

        private static final String CURRENCYPAIR_SPOT_PATH_SEGMENT = "/prices/" + PAIR_WITH_HYPHEN_TEMPLATE + "/spot";

        private static final String CURRENCYPAIR_SPOT_URI = COINBASE_BASE_URL + CURRENCYPAIR_SPOT_PATH_SEGMENT;

        public static String currencyPairSpotEndpointUri(CurrencyAbbreviation base, CurrencyAbbreviation target)
        {
            return String.format(CURRENCYPAIR_SPOT_URI, base.name(), target.name());
        }
    }
}
