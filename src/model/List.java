/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author MHL1
 */
public class List {
    private String app;
    private String sale;
    private String tIncome;
    
    //private String 

    

    public List(String appIncome, String salseIncome, String totalIncomee) {
        this.app = appIncome;
        this.sale = salseIncome;
        this.tIncome = totalIncomee;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String gettIncome() {
        return tIncome;
    }

    public void settIncome(String tIncome) {
        this.tIncome = tIncome;
    }
    
    
}
