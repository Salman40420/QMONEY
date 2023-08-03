
package com.crio.warmup.stock.portfolio;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {


  private RestTemplate restTemplate;

  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  //TODO: CRIO_TASK_MODULE_REFACTOR
  // 1. Now we want to convert our code into a module, so we will not call it from main anymore.
  //    Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  //    into #calculateAnnualizedReturn function here and ensure it follows the method signature.
  // 2. Logic to read Json file and convert them into Objects will not be required further as our
  //    clients will take care of it, going forward.

  // Note:
  // Make sure to exercise the tests inside PortfolioManagerTest using command below:
  // ./gradlew test --tests PortfolioManagerTest

  //CHECKSTYLE:OFF




  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.


  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {
    String url = buildUri(symbol, from, to);

    Candle[] response = restTemplate.getForObject(url, TiingoCandle[].class);
    List<Candle> candles = Arrays.asList(response);
    
    return candles;
  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
  
      String url = "https://api.tiingo.com/tiingo/daily/" + symbol + "/prices?startDate=" + startDate + "&endDate=" + endDate.toString() + "&token=" + getToken();

      return url;
  }

  public static String getToken() {
    final String token = "89f1e94a31ab7fae25d4bde0a597d9a57f4b60ed";
    return token;
  }

  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
      LocalDate endDate) {

        List<AnnualizedReturn> annualizedReturns = new ArrayList<>();

        for(PortfolioTrade trade : portfolioTrades) {
          try {
            String symbol = trade.getSymbol();
            LocalDate buyDate = trade.getPurchaseDate();
            LocalDate sellDate = endDate;

            List<Candle> candles = getStockQuote(symbol, buyDate, sellDate);

            double buyPrice = getBuyPrice(candles);
            double sellPrice = geSellPrice(candles);

            AnnualizedReturn annualizedReturn = calculateAnnualizedReturns(trade, sellDate, buyPrice, sellPrice);

            annualizedReturns.add(annualizedReturn);

          } catch (JsonProcessingException e) {
            e.printStackTrace();
          }
        }

        Collections.sort(annualizedReturns ,getComparator());

    return annualizedReturns;
  }


  private Double getBuyPrice(List<Candle> candles) {

    double openingPrice = 0.0;

    if(candles.isEmpty() || candles.size() == 0)
        return openingPrice;

    for(Candle candle : candles) {
      if(candle.getOpen() == null || candle.getOpen() <= 0.0)
        continue;

      openingPrice = candle.getOpen();
      break;
    }

    return openingPrice;
  }

  private static Double geSellPrice(List<Candle> candles) {
    double closingPrice = 0.0;

    if(candles.isEmpty() || candles.size() == 0)
        return closingPrice;

    for(int i = candles.size() - 1; i >= 0; i--) {
      Candle candle = candles.get(i);

      if(candle.getClose() == null || candle.getClose() <= 0.0)
        continue;

      closingPrice = candle.getClose();
      break;
    }

    return closingPrice;
  }

  public static AnnualizedReturn calculateAnnualizedReturns(PortfolioTrade trade, LocalDate endDate,
    Double buyPrice, Double sellPrice) {

    LocalDate startDate = trade.getPurchaseDate();

    Double totolReturns = (sellPrice - buyPrice) / buyPrice;
    Double noOfDays = (double) ChronoUnit.DAYS.between(startDate, endDate);

    Double totalNumYears = noOfDays/365.24;
    Double annualizedReturn = Math.pow((1 + totolReturns), (1 / totalNumYears)) - 1;

    return new AnnualizedReturn(trade.getSymbol(), annualizedReturn, totolReturns);
}
}
