package com.company;

import com.company.entity.StockPrice;

import java.io.*;
import java.util.ArrayList;

public class ParseStockPriceCsv {


    public ArrayList<StockPrice> parse(String filepath){
        BufferedReader bufferedReader = null;
        String line = "";
        String splitBy = ",";
        ArrayList<StockPrice> stockPrices = new ArrayList<StockPrice>();

        try {
            bufferedReader = new BufferedReader(new FileReader(filepath));
            while ((line = bufferedReader.readLine()) != null){
                String[] s = line.split(splitBy);
                System.out.println("========");
                System.out.println("s.length = " + s.length + " ");
                System.out.println("line  = " + line);
                System.out.println("========");
                System.out.print("\n");
                //stockPrices.add(new StockPrice(s[1], s[3]));
            }
            System.out.println("try{} stockPrices.size   " + stockPrices.size());

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            return stockPrices;
        }
    }

    public String createSQLQuery(ArrayList<StockPrice> stockPrices){
        System.out.println("createSQLQuery(){}  stockPrices.size   " + stockPrices.size());
        String sql = "";
        for(int i=1; i<stockPrices.size(); i++){
            StockPrice s = stockPrices.get(i);
            /*
            INSERT INTO table_name
            VALUES (value1, value2, value3, ...);
            */
            sql = sql +
                    "INSERT INTO twfinancial.stock (code, date, open, hige, low, close) VALUES ("
                    + "'" + s.getCode() + "'"
                    + ","
                    + "'" + s.getDate() + "'"
                    +","
                    +"'" + s.getOpen() + "'"
                    +","
                    +"'" + s.getHigh() + "'"
                    +","
                    +"'" + s.getLow() + "'"
                    +","
                    +"'" + s.getClose() + "'"
                    + "'); ";
        }
        String s = sql.replace("\"","");
        System.out.println("createSQLQuery" + s);
        return s;
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
