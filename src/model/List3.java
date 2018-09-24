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
public class List3 {
    private String BN;
    private String BR;
    private String ACN;
    private String ACH;

    public List3(String BankName, String Branch, String AccountNu, String AccountHol){
            this.BN = BankName;
            this.BR = Branch;
            this.ACN = AccountNu;
            this.ACH = AccountHol;
    }
    public String getBN() {
        return BN;
    }

    public void setBN(String BN) {
        this.BN = BN;
    }

    public String getBR() {
        return BR;
    }

    public void setBR(String BR) {
        this.BR = BR;
    }

    public String getACN() {
        return ACN;
    }

    public void setACN(String ACN) {
        this.ACN = ACN;
    }

    public String getACH() {
        return ACH;
    }

    public void setACH(String ACH) {
        this.ACH = ACH;
    }
}
