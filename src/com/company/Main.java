package com.company;

import java.io.IOException;

public class Main {
    static String filePath = "/Users/david/Downloads/GetTwseStockID/t187ap03_L.csv";
    static String sqlQueryPath ="/Users/david/Downloads/GetTwseStockID/company.sql";
    static String TSMC2023PricePath ="/Users/david/Downloads/GetTwseStockID/2330_2023_price.sql";
    static String TSMC2024PricePath ="/Users/david/Downloads/GetTwseStockID/2330_2024_price.sql";


    /*
    https://www.twse.com.tw/rwd/zh/afterTrading/STOCK_DAY?date=20230101&stockNo=2330&response=html";
    https://www.twse.com.tw/rwd/zh/afterTrading/STOCK_DAY?date=20241201&stockNo=2330&response=html";
    */

    public static void main(String[] args) throws IOException {
        //create company table data
        ParseCompanyCsv parseCsv = new ParseCompanyCsv();
        String sql  = parseCsv.parse(filePath);
        parseCsv.saveSqlQueryFile(sqlQueryPath, sql);

        //create 2330 stock table data
        DownloadStockPrice downloadStockPrice = new DownloadStockPrice();

        //fetch 2023 TSMC daily price
        String insertTSMCPricSql = downloadStockPrice.fetchTSMCPrice(2023);
        downloadStockPrice.saveSqlQueryFile(TSMC2023PricePath , insertTSMCPricSql);

        //fetch 2024 TSMC daily price
        insertTSMCPricSql = downloadStockPrice.fetchTSMCPrice(2024);
        downloadStockPrice.saveSqlQueryFile(TSMC2024PricePath , insertTSMCPricSql);
    }

}
