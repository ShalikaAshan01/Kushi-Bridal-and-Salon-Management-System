/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Shalika Ashan
 */
public class PaymentModelforTable extends RecursiveTreeObject<PaymentModelforTable>{
    public SimpleStringProperty payid;
    public SimpleStringProperty cid;
    public SimpleStringProperty aid;
    public SimpleStringProperty payDate;
    public SimpleStringProperty payTime;
    public SimpleStringProperty total;
    public SimpleStringProperty fname;
    public SimpleStringProperty lname;
    
    public PaymentModelforTable(PaymentModel paymentModel){
        payid = new SimpleStringProperty(String.valueOf(paymentModel.getPayid()));
        cid = new SimpleStringProperty(String.valueOf(paymentModel.getCid()));
        aid = new SimpleStringProperty(String.valueOf(paymentModel.getAid()));
        payDate = new SimpleStringProperty(paymentModel.getPayDate());
        payTime = new SimpleStringProperty(paymentModel.getPayTime());
        total = new SimpleStringProperty(String.valueOf(paymentModel.getTotal()));
        fname = new SimpleStringProperty(paymentModel.getFname());
        lname = new SimpleStringProperty(paymentModel.getLname());
    }

    public String getPayid() {
        return payid.get();
    }

    public String getCid() {
        return cid.get();
    }

    public String getAid() {
        return aid.get();
    }

    public String getPayDate() {
        return payDate.get();
    }

    public String getPayTime() {
        return payTime.get();
    }

    public String getTotal() {
        return total.get();
    }

    public String getFname() {
        return fname.get();
    }

    public String getLname() {
        return lname.get();
    }
}
