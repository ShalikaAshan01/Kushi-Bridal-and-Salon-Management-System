/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author pramulitha
 */

public class packageModel {
    private final StringProperty packageId;
    private final StringProperty packageName;
    private final StringProperty service1;
    private final StringProperty service2;
    private final StringProperty service3;
    private final DoubleProperty packagePrice;

    /**
     *
     * @param packageId
     * @param packageName
     * @param service1
     * @param service2
     * @param service3
     * @param packagePrice
     */
    public packageModel(String packageId, String packageName, String service1,String service2,String service3, Integer packagePrice) {
        this.packageId =  new SimpleStringProperty(packageId);
        this.packageName = new SimpleStringProperty(packageName);
        this.service1 = new SimpleStringProperty(service1);
        this.service2 = new SimpleStringProperty(service2);
        this.service3 = new SimpleStringProperty(service3);
        this.packagePrice = new SimpleDoubleProperty(packagePrice);
    }
    
// 
//    public String getPackageId() {
//        return packageId.get();
//    }
//
//    public void setPackageId(String packageId) {
//        this.packageId.set(packageId);
//    }
//    public StringProperty packageIdProperty(){
//        return packageId;
//    }
//    public String getPackageName() {
//         return packageName.get();
//    }
//
//    public void setPackageName(String packageName) {
//        this.packageName.set(packageName);
//    }
//    public StringProperty packageNameProperty(){
//        return packageName;
//    }
//    public String getservice1() {
//        return service1.get();
//    }
//
//    public void setservice1(String serviceName) {
//        this.service1.set(serviceName);
//    }
//    public StringProperty service1Property(){
//        return service1;
//    }
//     public String getservice2() {
//        return service2.get();
//    }
//
//    public void setservice2(String serviceName) {
//        this.service2.set(serviceName);
//    }
//    public StringProperty service2Property(){
//        return service2;
//    }
//    
//     public String getservice3() {
//        return service3.get();
//    }
//
//    public void setservice3(String serviceName) {
//        this.service3.set(serviceName);
//    }
//    public StringProperty service3Property(){
//        return service3;
//    }
//
//
//
//    public int getpackagePrice() {
//        return packagePrice.get();
//    }
//    
//    public void setpackagePrice(int packagePrice){
//        this.packagePrice.set(packagePrice);
//    }
//    public IntegerProperty packagePriceProperty(){
//        return packagePrice;
//    }
/*
    public String getPackageId() {
        return packageId.get();
    }

    public String getPackageName() {
        return packageName.get();
    }

    public String getServiceName() {
        return serviceName.get();
    }

    public int getPackagePrice() {
        return packagePrice.get();
    }
  */     

    public String getPackageId() {
        return packageId.get();
    }

    public String getPackageName() {
        return packageName.get();
    }

    public String getService1() {
        return service1.get();
    }

    public String getService2() {
        return service2.get();
    }

    public String getService3() {
        return service3.get();
    }

    public Double getPackagePrice() {
        return packagePrice.get();
    }
    
}

