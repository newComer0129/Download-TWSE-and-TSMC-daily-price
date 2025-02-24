package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.util.ArrayList;
import java.util.Locale;

import com.company.entity.StockPrice;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownloadStockPrice {

    static String TSMCPriceUrl ="https://www.twse.com.tw/rwd/zh/afterTrading/STOCK_DAY";
    static String TSMCPriceUrlDateQuery ="?date=";
    static String TSMCPriceUrlStockCodeQuery ="&stockNo=2330";
    static String TSMCPriceUrlResponseQuery ="&response=html";

    //get TSMC price html content
    public ArrayList<StockPrice> parseHtmlForPrice(String html){
        ArrayList<StockPrice> stockprices = new ArrayList<>();
        try {
            Document document = Jsoup.connect(html).get();
            Element table = document.select("table").first();
            Elements rows = table.select("tr");
            //Elements e = rows.select("tr:eq(0) td:eq(0)");
            //get table value by rows.select("tr:eq(0) td:eq(0)");
            for(int row = 0; row<rows.size()-2; row++){
                StockPrice stockPrice = new StockPrice(
                        "2330",
                        parseStringToDate(rows.select("tr:eq(" + row+ ")" + " td:eq(0)").first().text()),
                        new BigDecimal( rows.select("tr:eq(" + row + ")" + " td:eq(3)").first().text().replace(",","")),
                        new BigDecimal( rows.select("tr:eq(" + row + ")" + " td:eq(4)").first().text().replace(",","")),
                        new BigDecimal( rows.select("tr:eq(" + row + ")" + " td:eq(5)").first().text().replace(",","")),
                        new BigDecimal( rows.select("tr:eq(" + row + ")" + " td:eq(6)").first().text().replace(",","")));
                stockprices .add(stockPrice);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return stockprices;
    }

    public LocalDate parseStringToDate(String date){
        Chronology chrono = MinguoChronology.INSTANCE;
        DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient()
                .appendPattern("yyy/MM/dd").toFormatter().withChronology(chrono)
                .withDecimalStyle(DecimalStyle.of(Locale.getDefault()));

        ChronoLocalDate d1 = chrono.date(df.parse(date));
        LocalDate ld1 = LocalDate.from(d1);
        return ld1;
    }

    public String createSQLQuery(ArrayList<StockPrice> stockPrices){
        /*
            INSERT INTO table_name
            VALUES (value1_1, value2_2, value3_3,···), (value2_1, value2_2, value2_3,···),
            (value3_1, value3_2, value3_3,···),······;
        */
        String sql = "";
        for(int i=1; i<stockPrices.size(); i++){
            StockPrice s = stockPrices.get(i);
            sql = sql
                    + "("
                    + "'" + s.getCode() + "'"
                    + "," + "'" + s.getDate() + "'"
                    + "," + "'" + s.getOpen() + "'"
                    + "," + "'" + s.getHigh() + "'"
                    + "," + "'" + s.getLow() + "'"
                    + "," + "'" + s.getClose() + "'"
                    +"),";
        }
        //sql = sql+ ";";
        return sql;
    }

    public String fetchTSMCPrice(int year){
        ArrayList<StockPrice> stockPrices = new ArrayList<StockPrice>();
        DownloadStockPrice downloadStockPrice = new DownloadStockPrice();
        String sql = "INSERT INTO twfinancial.stock (code, date, open, high, low, close) VALUES ";
        for(int month=1; month<=9;month++){
            stockPrices = downloadStockPrice.parseHtmlForPrice(TSMCPriceUrl
                    + TSMCPriceUrlDateQuery+ year + "0" +month + "01"
                    +TSMCPriceUrlStockCodeQuery+TSMCPriceUrlResponseQuery);

            //create insert daily price sql query
            sql = sql + downloadStockPrice.createSQLQuery(stockPrices);
        }
        for(int month=10; month<=12;month++){
            if(month==12){System.out.println("month == 12");}
            stockPrices = downloadStockPrice.parseHtmlForPrice(TSMCPriceUrl
                    + TSMCPriceUrlDateQuery+ year + month + "01"
                    +TSMCPriceUrlStockCodeQuery+TSMCPriceUrlResponseQuery);

            //create insert daily price sql query
            sql = sql + downloadStockPrice.createSQLQuery(stockPrices);
        }
        sql = sql.substring(0, sql.length()-1);
        sql = sql+";";
        System.out.println("sql: " + sql);
        return sql;
    }

    public void saveSqlQueryFile(String filePath, String  content) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
        //Add this to write a string to a file
        try {
            out.write(content);  //Replace with the string
            //you are trying to write
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            out.close();
        }
    }
}


