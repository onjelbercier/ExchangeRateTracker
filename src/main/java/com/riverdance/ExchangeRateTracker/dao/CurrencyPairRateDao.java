package com.riverdance.ExchangeRateTracker.dao;

import com.riverdance.ExchangeRateTracker.common.Constants;
import com.riverdance.ExchangeRateTracker.dto.CurrencyPairRateDto;
import com.riverdance.ExchangeRateTracker.enumeration.CurrencyAbbreviation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@AllArgsConstructor
@Component
public class CurrencyPairRateDao {

    private Function<String, WebClient> webClientFunction;

    public Mono<CurrencyPairRateDto> currencyPairSpot(CurrencyAbbreviation base, CurrencyAbbreviation target) {

        //derive the realized value once and make if final so it cannot be changed by another thread
        //so now, it's thread safe assignment and can me use anywhere in this method below
        //it follows the whole "don't repeat yourself" or DRY principle because we need to use this value
        //more than one time below so I am calculationg it once and then making it a constant so it can't
        //be mutated after that
        final String coinbaseSpotPriceUriForPair = Constants.Coinbase.currencyPairSpotEndpointUri(base, target);

        log.info("calling Coinbase.currencyPairSpot for uri = {}", coinbaseSpotPriceUriForPair);

        try {
            return webClientFunction.apply(coinbaseSpotPriceUriForPair).get()
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve() // using retrieve instead of exchange
                    .bodyToMono(CurrencyPairRateDto.class) // Directly mapping to CurrencyPairRateDTO
                    .single();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}