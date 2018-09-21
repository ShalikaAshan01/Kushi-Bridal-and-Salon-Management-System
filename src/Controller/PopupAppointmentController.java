/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Services.PaymentService;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.Appointment;
import model.AppointmentforTable;

/**
 * FXML Controller class
 *
 * @author Shalika Ashan
 */
public class PopupAppointmentController implements Initializable {

    @FXML
    private JFXTreeTableView<AppointmentforTable> tableAppointment;

    @FXML
    private TreeTableColumn columnAID;

    @FXML
    private TreeTableColumn columnFname;

    @FXML
    private TreeTableColumn columnPackage;

    @FXML
    private TreeTableColumn columnDate;

    @FXML
    private TreeTableColumn columnTime;
    @FXML
    private JFXTextField txtSearch;
    
    public static int SELECTED_APPOINTMENT_ID = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        PaymentService paymentService = new PaymentService();
        ArrayList<Appointment> temparrayList = paymentService.getAllAppointments();
        ArrayList<AppointmentforTable> arrayList = new ArrayList<>();

        for (Appointment tempAppointment : temparrayList) {
            AppointmentforTable appointmentforTable = new AppointmentforTable(tempAppointment);
            arrayList.add(appointmentforTable);
        }
        ObservableList<AppointmentforTable> observableList = FXCollections.observableArrayList(arrayList);
        columnAID.setCellValueFactory(new TreeItemPropertyValueFactory<AppointmentforTable, String>("aid"));
 //       columnFname.setCellValueFactory(new TreeItemPropertyValueFactory<AppointmentforTable, String>("fname"));
 //       columnLname.setCellValueFactory(new TreeItemPropertyValueFactory<AppointmentforTable, String>("lname"));
        columnPackage.setCellValueFactory(new TreeItemPropertyValueFactory<AppointmentforTable, String>("packageName"));
        columnDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AppointmentforTable, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AppointmentforTable, String> param) {
                return param.getValue().getValue().aDate;
            }
        });
        columnTime.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AppointmentforTable, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AppointmentforTable, String> param) {
                return param.getValue().getValue().aDate;
            }
        });
        columnFname.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AppointmentforTable, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AppointmentforTable, String> param) {
                SimpleStringProperty value = new SimpleStringProperty((param.getValue().getValue().getFname() + " " + param.getValue().getValue().getLname() ));
                return value;
            }
        });

        TreeItem<AppointmentforTable> root = new RecursiveTreeItem<>(observableList, RecursiveTreeObject::getChildren);
        tableAppointment.setRoot(root);
        tableAppointment.setShowRoot(false);
        searchAppointment();
        
        
        tableAppointment.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                if(t.isPrimaryButtonDown()){
                    System.out.println("kk");
                }else{
                    System.out.println("hi");
                }
            }
            
        });
    }
    
        private void searchAppointment() {
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> ov, String s, String t1) {
                tableAppointment.setPredicate(new Predicate<TreeItem<AppointmentforTable>>(){
                    @Override
                    public boolean test(TreeItem<AppointmentforTable> appointment) {
                        String fname = appointment.getValue().getFname().toLowerCase();
                        String lname = appointment.getValue().getLname().toLowerCase();
                        String name = fname + " " + lname;
                           Boolean found = appointment.getValue().getLname().toLowerCase().contains(t1.toLowerCase())
                                   || appointment.getValue().getFname().toLowerCase().contains(t1.toLowerCase())
                                   || name.contains(t1.toLowerCase())
                                   ||appointment.getValue().getAid().contains(t1)
                                   ||appointment.getValue().getaDate().contains(t1);
                        return found;                    }
                });
            }
        });
    }
        
            @FXML
    public void sumbitAppointmentID(ActionEvent event) {
        SELECTED_APPOINTMENT_ID = Integer.parseInt(tableAppointment.getSelectionModel().getSelectedItem().getValue().getAid());
    }
}
