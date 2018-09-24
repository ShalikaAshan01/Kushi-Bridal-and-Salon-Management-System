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
public class List1 {
    private String TI;
    private String TE;
    private String PT;

    
    
    public List1(String totincome, String totalexpanse, String profitloset) {
        this.TI = totincome;
        this.TE = totalexpanse;
        this.PT = profitloset;
    }
    
    
    public String getTI() {
        return TI;
    }

    public void setTI(String TI) {
        this.TI = TI;
    }

    public String getTE() {
        return TE;
    }

    public void setTE(String TE) {
        this.TE = TE;
    }

    public String getPT() {
        return PT;
    }

    public void setPT(String PT) {
        this.PT = PT;
    }
  
}
