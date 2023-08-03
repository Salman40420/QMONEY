
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Task:
  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Go through all of the trades in the given file,
  //       - Prepare the list of all symbols a portfolio has.
  //       - if "trades.json" has trades like
  //         [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  //         Then you should return ["MSFT", "AAPL", "GOOGL"]
  //  Hints:
  //    1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  //       Check if they are of any help to you.
  //    2. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {

    String jsonFileName = args[0];
    File tradesFile = resolveFileFromResources(jsonFileName);
    ObjectMapper mapper = getObjectMapper();

    PortfolioTrade[] allTrades = mapper.readValue(tradesFile, PortfolioTrade[].class);
    List<String> tradeSymbolList = new ArrayList<>();

    for(PortfolioTrade trade : allTrades) {
      tradeSymbolList.add(trade.getSymbol());
    }


     return tradeSymbolList;
  }




  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  //  for the stocks provided in the Json.
  //  Use the function you just wrote #calculateAnnualizedReturns.
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.





  // TODO: CRIO_TASK_MODULE_REST_API
  //  Find out the closing price of each stock on the end_date and return the list
  //  of all symbols in ascending order by its close value on end date.

  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

     String valueOfArgument0 = "trades.json";
     String resultOfResolveFilePathArgs0 = "/home/crio-user/workspace/salman-pathan0420-ME_QMONEY_V2/qmoney/bin/main/trades.json";
     String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@1573f9fc";
     String functionNameFromTestFileInStackTrace = "PortfolioManagerApplicationTest.mainReadFile()";
     String lineNumberFromTestFileInStackTrace = "29";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }


  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {

    List<PortfolioTrade> tradeList = readTradesFromJson(args[0]);
    LocalDate endDate = LocalDate.parse(args[1]);
    List<TotalReturnsDto> res = new ArrayList<>();

    for(PortfolioTrade trade : tradeList) {
      res.add(doTingoHttpRequest(trade, endDate));
    }

    List<String> result = sortDto(res);

     return result;
  }

  public static List<String> sortDto(List<TotalReturnsDto> dto) {

    Collections.sort(dto, new TotalReturnsDto());
    List<String> resultSymbol = new ArrayList<>();

    for(TotalReturnsDto element : dto) {
      resultSymbol.add(element.getSymbol());
    }

    return resultSymbol;

  } 

  public static TotalReturnsDto doTingoHttpRequest(PortfolioTrade trade, LocalDate endDate) throws URISyntaxException, StreamWriteException, DatabindException, IOException {

    String url = prepareUrl(trade, endDate, PortfolioManagerApplication.getToken());

    RestTemplate template = new RestTemplate();
    ResponseEntity<TiingoCandle[]> response = template.getForEntity(url, TiingoCandle[].class);
      
    TiingoCandle[] result = response.getBody();

    TotalReturnsDto dto;

    if(result.length == 0) {
      return null;

    }

    dto = new TotalReturnsDto(trade.getSymbol(), result[result.length-1].getClose());
    return dto;
  }

  // TODO:
  //  After refactor, make sure that the tests pass by using these two commands
  //  ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  //  ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
  public static List<PortfolioTrade> readTradesFromJson(String filename) throws IOException, URISyntaxException {

    File tradesFile = resolveFileFromResources(filename);
    ObjectMapper mapper = getObjectMapper();

    List<PortfolioTrade> allTrades = mapper.readValue(tradesFile, new TypeReference<List<PortfolioTrade>>(){});
    return allTrades;
  }

  private static String readFileAsString(String fileName) throws IOException, URISyntaxException {
    return new String(Files.readAllBytes(resolveFileFromResources(fileName).toPath()), "UTF-8");
  }


  // https://api.tiingo.com/tiingo/daily/AAPL/prices?startDate=2019-01-02&endDate=2022-1-1&token=89f1e94a31ab7fae25d4bde0a597d9a57f4b60ed

  // https://api.tiingo.com/tiingo/daily/AAPL/prices?startDate=2019-01-02&endDate=2022-01-02&token=89f1e94a31ab7fae25d4bde0a597d9a57f4b60ed
  // https://api.tiingo.com/tiingo/daily/MSFT/prices?startDate=2019-01-02&endDate=2022-01-02&token=89f1e94a31ab7fae25d4bde0a597d9a57f4b60ed
  // https://api.tiingo.com/tiingo/daily/GOOGL/prices?startDate=2019-01-02&endDate=2022-01-02&token=89f1e94a31ab7fae25d4bde0a597d9a57f4b60ed

  // TODO:
  //  Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {

    LocalDate startDate = trade.getPurchaseDate();
    String symbol = trade.getSymbol();

    String url = "https://api.tiingo.com/tiingo/daily/" + symbol + "/prices?startDate=" + startDate + "&endDate=" + endDate.toString() + "&token=" + token;
     return url;
  }
  
  // TODO:
  //  Ensure all tests are passing using below command
  //  ./gradlew test --tests ModuleThreeRefactorTest
  static Double getOpeningPriceOnStartDate(List<Candle> candles) {

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


  public static Double getClosingPriceOnEndDate(List<Candle> candles) {
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

  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) {

    String url = prepareUrl(trade, endDate, PortfolioManagerApplication.getToken());

    RestTemplate template = new RestTemplate();
    Candle[] response = template.getForObject(url, TiingoCandle[].class);
    List<Candle> candles = Arrays.asList(response);
    
    return candles;
  }

  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {

        List<PortfolioTrade> tradeList = readTradesFromJson(args[0]);
        LocalDate endDate = LocalDate.parse(args[1]);

        List<AnnualizedReturn> res = new ArrayList<>();

        for(PortfolioTrade trade : tradeList) {
          res.add(getAnnualizedReturn(trade, endDate));
        }

        Collections.sort(res, new AnnualizedReturn());

        return res;
  }

  public static AnnualizedReturn getAnnualizedReturn(PortfolioTrade trade, LocalDate endDate) {

    List<Candle> candles = fetchCandles(trade, endDate, PortfolioManagerApplication.getToken());
    Double buyPrice = getOpeningPriceOnStartDate(candles);
    Double sellPrice = getClosingPriceOnEndDate(candles);

    AnnualizedReturn annualizedReturn = calculateAnnualizedReturns(endDate, trade, buyPrice, sellPrice);

    return annualizedReturn;
  }

  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Return the populated list of AnnualizedReturn for all stocks.
  //  Annualized returns should be calculated in two steps:
  //   1. Calculate totalReturn = (sell_value - buy_value) / buy_value.
  //      1.1 Store the same as totalReturns
  //   2. Calculate extrapolated annualized returns by scaling the same in years span.
  //      The formula is:
  //      annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //      2.1 Store the same as annualized_returns
  //  Test the same using below specified command. The build should be successful.
  //     ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice, Double sellPrice) {

      Double totolReturns = (sellPrice - buyPrice) / buyPrice;
      Double noOfDays = (double) ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate);

      Double totalNumYears = noOfDays/365;
      Double annualizedReturn = Math.pow((1 + totolReturns), (1 / totalNumYears)) - 1;

      return new AnnualizedReturn(trade.getSymbol(), annualizedReturn, totolReturns);
  }


public static String getToken() {
  return "89f1e94a31ab7fae25d4bde0a597d9a57f4b60ed";
}








  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Once you are done with the implementation inside PortfolioManagerImpl and
  //  PortfolioManagerFactory, create PortfolioManager using PortfolioManagerFactory.
  //  Refer to the code from previous modules to get the List<PortfolioTrades> and endDate, and
  //  call the newly implemented method in PortfolioManager to calculate the annualized returns.

  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.

  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args)
      throws Exception {
       String file = args[0];
       LocalDate endDate = LocalDate.parse(args[1]);
       String contents = readFileAsString(file);
       RestTemplate restTemplate = new RestTemplate();

       ObjectMapper objectMapper = getObjectMapper();
       PortfolioTrade[] portfolioTrades = objectMapper.readValue(contents, PortfolioTrade[].class);

       PortfolioManager portfolioManager = PortfolioManagerFactory.getPortfolioManager(restTemplate);
       return portfolioManager.calculateAnnualizedReturn(Arrays.asList(portfolioTrades), endDate);
  }


  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

    args = new String[2];
    args[0] = "trades.json";
    args[1] = "2022-01-02";

    printJsonObject(mainReadFile(args));
    printJsonObject(mainReadQuotes(args));
    printJsonObject(mainCalculateSingleReturn(args));




    printJsonObject(mainCalculateReturnsAfterRefactor(args));
  }
}

