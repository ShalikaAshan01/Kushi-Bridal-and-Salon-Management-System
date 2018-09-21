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
public class Appointment {
    private int appointmentId;
    private String date;
    private String time;
    private String employeeName;
    private int CustomerID;
    private String customerName;
    private String customerAddress;
    private String telephone;
    private String service;
    private String packages;
    private double total;
    

    public Appointment(int appointmentId, String date, String time, String employeeName, int CustomerID, String customerName, String customerAddress, String telephone, String service, String packages, double total) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.time = time;
        this.employeeName = employeeName;
        this.CustomerID = CustomerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.telephone = telephone;
        this.service = service;
        this.packages = packages;
        this.total = total;
    }

    public Appointment() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Appointment(String string, String string0, String string1, int aInt, String string2, String string3, String string4, String string5, String string6, double aDouble) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Appointment{" + "appointmentId=" + appointmentId + ", date=" + date + ", time=" + time + ", employeeName=" + employeeName + ", CustomerID=" + CustomerID + ", customerName=" + customerName + ", customerAddress=" + customerAddress + ", telephone=" + telephone + ", service=" + service + ", packages=" + packages + ", total=" + total + '}';
    }
    
}
