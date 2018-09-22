/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author pramulitha
 */
public class serviceModel {
    
    private final StringProperty serviceId;
    private final StringProperty serviceName;
    public final DoubleProperty servicePrice;

    public serviceModel(String serviceId, String serviceName,int servicePrice) {
        this.serviceId = new SimpleStringProperty(serviceId);
        this.serviceName = new SimpleStringProperty(serviceName);
        this.servicePrice = new SimpleDoubleProperty(servicePrice); 
        
      
    }

    public String getServiceId() {
        return serviceId.get();
    }
    
    public void setServiceId(String serviceId) {
        this.serviceId.set(serviceId);
    }
    
    public StringProperty ServiceIdProperty(){
        return serviceId;
    }
    
     public String getServiceName() {
        return serviceName.get();
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName.set(serviceName);
    }
    
    public StringProperty ServiceNameProperty(){
        return serviceName;
    }
    
    public double getServicePrice() {
        return servicePrice.get();
    }
    
    public void setServicePrice(double servicePrice) {
        this.servicePrice.set(servicePrice);
    }
    
    public DoubleProperty ServicePrice(){
        return servicePrice;
    }
    
    
    
    

    
    
    
    
    
}
