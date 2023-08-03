
package com.crio.warmup.stock.dto;

import java.util.Comparator;

public class TotalReturnsDto implements Comparator<TotalReturnsDto>{

  private String symbol;
  private Double closingPrice;

  public TotalReturnsDto() {
  }


  public TotalReturnsDto(String symbol, Double closingPrice) {
    this.symbol = symbol;
    this.closingPrice = closingPrice;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getClosingPrice() {
    return closingPrice;
  }

  public void setClosingPrice(Double closingPrice) {
    this.closingPrice = closingPrice;
  }

  @Override
  public int compare(TotalReturnsDto arg0, TotalReturnsDto arg1) {
    
    if(arg0.getClosingPrice() > arg1.getClosingPrice()) {
      return 1;
    } 

    if(arg0.getClosingPrice() < arg1.getClosingPrice()) {
      return -1;
    } 

    return 0;
  }
}
