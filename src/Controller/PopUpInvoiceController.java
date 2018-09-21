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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import model.PaymentModel;
import model.PaymentModelforTable;

/**
 * FXML Controller class
 *
 * @author Shalika Ashan
 */
public class PopUpInvoiceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTreeTableView<PaymentModelforTable> paymentTable;
    @FXML
    private TreeTableColumn<PaymentModelforTable, String> colfname;
    @FXML
    private TreeTableColumn colpayID;
    @FXML
    private TreeTableColumn<PaymentModelforTable, String> collname;
    @FXML
    private TreeTableColumn colappointmentID;
    @FXML
    private TreeTableColumn coldate;
    @FXML
    private TreeTableColumn coltime;
    @FXML
    private TreeTableColumn coltotal;
    @FXML
    private JFXTextField txtKey;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //add values to table
        PaymentService paymentService = new PaymentService();
        ArrayList<PaymentModel> payments = paymentService.getPaymentInfo();
        ArrayList<PaymentModelforTable> paymentsforTable = new ArrayList<>();
        for (PaymentModel temp : payments) {
            PaymentModelforTable temp1 = new PaymentModelforTable(temp);
            paymentsforTable.add(temp1);
        }

        ObservableList<PaymentModelforTable> paymentlist = FXCollections.observableArrayList(paymentsforTable);
        colpayID.setCellValueFactory(new TreeItemPropertyValueFactory<PaymentModelforTable, String>("payid"));
        collname.setCellValueFactory(new TreeItemPropertyValueFactory<PaymentModelforTable, String>("lname"));
        colfname.setCellValueFactory(new TreeItemPropertyValueFactory<PaymentModelforTable, String>("fname"));
        colappointmentID.setCellValueFactory(new TreeItemPropertyValueFactory<PaymentModelforTable, String>("aid"));
        coldate.setCellValueFactory(new TreeItemPropertyValueFactory<PaymentModelforTable, String>("payDate"));
        coltime.setCellValueFactory(new TreeItemPropertyValueFactory<PaymentModelforTable, String>("payTime"));
        coltotal.setCellValueFactory(new TreeItemPropertyValueFactory<PaymentModelforTable, String>("total"));
//        collname.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PaymentModelforTable, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PaymentModelforTable, String> param) {
//                return param.getValue().getValue().lname;
//            }
//        });

        TreeItem<PaymentModelforTable> root = new RecursiveTreeItem<>(paymentlist, RecursiveTreeObject::getChildren);
        paymentTable.setRoot(root);
        paymentTable.setShowRoot(false);

        colpayID.setStyle("-fx-alignment: CENTER;");
        collname.setStyle("-fx-alignment: CENTER;");
        colfname.setStyle("-fx-alignment: CENTER;");
        colappointmentID.setStyle("-fx-alignment: CENTER;");
        coldate.setStyle("-fx-alignment: CENTER;");
        coltime.setStyle("-fx-alignment: CENTER;");
        coltotal.setStyle("-fx-alignment: CENTER;");
        searchPayment();
                
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
        paymentTable.requestFocus();
        paymentTable.getSelectionModel().select(0);
        paymentTable.getFocusModel().focus(0);
            }
        });
    }
    
        public void searchPayment() {
        txtKey.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> ov, String s, String t1) {
                paymentTable.setPredicate(new Predicate<TreeItem<PaymentModelforTable>>(){
                    @Override
                    public boolean test(TreeItem<PaymentModelforTable> payment) {
                        String fname = payment.getValue().getFname().toLowerCase();
                        String lname = payment.getValue().getLname().toLowerCase();
                        String name = fname + " " + lname;
                           Boolean found = payment.getValue().getLname().toLowerCase().contains(t1.toLowerCase())
                                   || payment.getValue().getFname().toLowerCase().contains(t1.toLowerCase())
                                   || name.contains(t1.toLowerCase())
                                   ||payment.getValue().getPayTime().contains(t1)
                                   ||payment.getValue().getPayid().contains(t1)
                                   ||payment.getValue().getPayDate().contains(t1)
                                   ||payment.getValue().getTotal().contains(t1);
                        return found;
                    }
                });
            }
        });
    }

}
