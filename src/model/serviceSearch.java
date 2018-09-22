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
public class serviceSearch {
     private int serviceId;
     private String serviceName;

     
      public serviceSearch(int serviceId, String serviceName) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    @Override
    public String toString() {
        return getServiceName();
    }
      
      
 
     
}
