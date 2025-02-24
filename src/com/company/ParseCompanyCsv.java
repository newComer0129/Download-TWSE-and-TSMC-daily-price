package com.company;

import com.company.entity.Stock;
import java.io.*;
import java.util.ArrayList;

public class ParseCompanyCsv {
    private ArrayList<Stock> stocks = new ArrayList<Stock>();
    private String insertSql="";

    public String parse(String filepath){
        BufferedReader bufferedReader = null;
        String line = "";
        String splitBy = ",";

        try {
            bufferedReader = new BufferedReader(new FileReader(filepath));
            while ((line = bufferedReader.readLine()) != null){
                String[] s = line.split(splitBy);
                stocks.add(new Stock(s[1], s[3]));
            }
            insertSql = createSQLQuery(stocks);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            return insertSql;
        }
    }


    public String createSQLQuery(ArrayList<Stock> stocks){
        System.out.println("createSQLQuery(){}  stocks.size   " + stocks.size());
        String sql = "";
        for(int i=1; i<stocks.size(); i++){
            Stock s = stocks.get(i);
            /*
            INSERT INTO table_name
            VALUES (value1, value2, value3, ...);
            */
            sql = sql +
            "INSERT INTO twfinancial.company (code, name) VALUES ("
                    + "'" + s.getCode() + "'" + ", '"+ s.getCompanyName() + "'); ";
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
