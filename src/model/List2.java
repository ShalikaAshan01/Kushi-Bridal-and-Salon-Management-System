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
public class List2 {
    private String del;
    private String sal;
    private String tEx;
    private String main;
    
    public List2(String deEX, String maEx, String Salary, String totalEX) {
        this.del = deEX;
        this.main = maEx;
        this.sal = Salary;
        this.tEx = totalEX;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }

    public String gettEx() {
        return tEx;
    }

    public void settEx(String tEx) {
        this.tEx = tEx;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
