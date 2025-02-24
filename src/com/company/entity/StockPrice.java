package com.company.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class StockPrice {
    private String code;
    private LocalDate date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;


    public StockPrice(String code, LocalDate date, BigDecimal open, BigDecimal high,
                      BigDecimal low, BigDecimal close){
        this.code = code;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public String getCode(){return code;}
    public LocalDate getDate(){return date;}
    public BigDecimal getOpen(){return open;}
    public BigDecimal getHigh(){return high;}
    public BigDecimal getLow(){return low;}
    public BigDecimal getClose(){return close;}

}
