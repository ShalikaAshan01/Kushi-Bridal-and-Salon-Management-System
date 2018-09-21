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
public class AppointmentforTable extends RecursiveTreeObject<AppointmentforTable>{
    public final SimpleStringProperty aid;
    public final SimpleStringProperty fname;
    public final SimpleStringProperty lname;
    public  SimpleStringProperty aDate;
    public  SimpleStringProperty aTime;
    public final SimpleStringProperty packageName;
    
    public AppointmentforTable(Appointment appointment){
        aid = new SimpleStringProperty(String.valueOf(appointment.getAid()));
        fname = new SimpleStringProperty(appointment.getFname());
        lname = new SimpleStringProperty(appointment.getLname());
        aDate = new SimpleStringProperty(String.valueOf(appointment.getaDate()));
        aTime = new SimpleStringProperty(appointment.getaTime());
        packageName = new SimpleStringProperty(appointment.getPackageName());
    }

    public String getAid() {
        return aid.get();
    }

    public String getFname() {
        return fname.get();
    }

    public String getLname() {
        return lname.get();
    }

    public String getaDate() {
        return aDate.get();
    }

    public String getaTime() {
        return aTime.get();
    }

    public String getPackageName() {
        return packageName.get();
    }
    
    
}
