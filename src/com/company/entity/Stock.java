package com.company.entity;

public class Stock {
    private String code;
    private String companyName;

    public Stock(String code,String companyName){
        this.code = code;
        this.companyName = companyName;
    }

    public String getCode(){return code;}
    public String getCompanyName(){return companyName;}
}
