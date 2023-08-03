
package com.crio.warmup.stock.dto;

import java.util.Comparator;

public class AnnualizedReturn implements Comparator<AnnualizedReturn> {

  private String symbol;
  private Double annualizedReturn;
  private Double totalReturns;

  public AnnualizedReturn() {
  }

  public AnnualizedReturn(String symbol, Double annualizedReturn, Double totalReturns) {
    this.symbol = symbol;
    this.annualizedReturn = annualizedReturn;
    this.totalReturns = totalReturns;
  }

  public String getSymbol() {
    return symbol;
  }

  public Double getAnnualizedReturn() {
    return annualizedReturn;
  }

  public Double getTotalReturns() {
    return totalReturns;
  }

  @Override
  public int compare(AnnualizedReturn arg0, AnnualizedReturn arg1) {
    
    if(arg0.getAnnualizedReturn() > arg1.getAnnualizedReturn()) {
      return -1;
    }

    if(arg0.getAnnualizedReturn() < arg1.getAnnualizedReturn()) {
      return 1;
    }

    return 0;
  }
}
