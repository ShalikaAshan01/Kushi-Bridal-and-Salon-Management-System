/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ushadi
 */
public class packageSearch {
    private int packageId;
    private String packageName;
   /* private String serviceName;
    private double price;*/
    private double price;

    public packageSearch(int packageId , String packageName,double price) {
   this.packageId = packageId;
   this.packageName = packageName;
   this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /*public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }*/

   @Override
    public String toString() {
        return getPackageName();
    }
}
    
    
    
    

