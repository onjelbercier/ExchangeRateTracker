package com.riverdance.ExchangeRateTracker.service;

import com.riverdance.ExchangeRateTracker.common.CoinbaseMemento;
import com.riverdance.ExchangeRateTracker.dao.CurrencyPairRateDao;
import com.riverdance.ExchangeRateTracker.dto.CurrencyPairRateDto;
import com.riverdance.ExchangeRateTracker.enumeration.CurrencyAbbreviation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Optional;

import static com.riverdance.ExchangeRateTracker.common.Constants.Coinbase.PIP_THRESHOLD;
import static com.riverdance.ExchangeRateTracker.common.Constants.Coinbase.PIP_VALUE;

@Slf4j
@Service
@AllArgsConstructor
public class ExchangeRateTrackerService {

    private CurrencyPairRateDao currencyPairRateDao;
    private static long iteration=0;

    @Scheduled(fixedRate = 2000, initialDelay = 2000)
    private void checkRatesForEurUsdPairOnSchedule() {
        CurrencyPairRateDto currentRateDto = currencyPairRateDao.currencyPairSpot(CurrencyAbbreviation.EUR, CurrencyAbbreviation.USD)
                .blockOptional(Duration.ofMillis(20000))
                .orElseThrow(() -> new RuntimeException(String.format("No spot price found; iteration = %s", iteration)));

        orchestrateResponse(currentRateDto);
    }

    private void orchestrateResponse(CurrencyPairRateDto currentRateDto) {
        iteration++;

        final BigDecimal pipDifference = Optional.ofNullable(CoinbaseMemento.previousCurrencyPairRateDtoRef.get())
                .map(previousCurrencyPairRateDto -> {
                    log.info("iteration ={}; previousCurrencyPairRate = {}; currentRate = {}", iteration, previousCurrencyPairRateDto.getAmount(), currentRateDto.getAmount());
                    BigDecimal difference = currentRateDto.getAmount().subtract(previousCurrencyPairRateDto.getAmount()).abs();
                    return difference.divide(PIP_VALUE, 2, RoundingMode.HALF_UP);
                })
                .orElse(BigDecimal.ZERO);

        log.info("iteration = {}; pipDifference = {}", iteration, pipDifference);

        if (pipDifference.compareTo(PIP_THRESHOLD) >= 0) {
            CoinbaseMemento.previousCurrencyPairRateDtoRef.set(currentRateDto);
            // Send email logic or other actions
            throw new UnsupportedOperationException(String.format("Email not yet implemented; iteration = %s", iteration));
        }

        // Update the previous rate reference for next comparison
        CoinbaseMemento.previousCurrencyPairRateDtoRef.set(currentRateDto);
    }
}

