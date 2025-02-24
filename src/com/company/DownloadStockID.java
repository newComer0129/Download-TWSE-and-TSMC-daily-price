package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DownloadStockID {

    String content = "";
    URLConnection connection = null;
    String TwseStockIDWebage = "https://isin.twse.com.tw/isin/C_public.jsp?strMode=2";

    public void fetchHtmlBody(String url){
        try {
            System.out.println("fetchHtmlBody");
            connection =  new URL(url).openConnection();
            //InputStream responce = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String htmlbody = "";

            while ( (htmlbody=bufferedReader.readLine()) != null){
                System.out.print(bufferedReader.readLine());
                htmlbody += htmlbody;
            }
            System.out.println(htmlbody);

        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

}
