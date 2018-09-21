/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.createInvoiceController.appointmentNumber;
import Services.PaymentService;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import model.PaymentModel;
import model.PaymentModelforTable;

/**
 * FXML Controller class
 *
 * @author Shalika Ashan
 */
public class PopUpInstallmentPaymentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML // fx:id="tableInstallment"
    private JFXTreeTableView<PaymentModelforTable> tableInstallment; // Value injected by FXMLLoader

    @FXML // fx:id="columnID"
    private TreeTableColumn columnID; // Value injected by FXMLLoader

    @FXML // fx:id="columnDate"
    private TreeTableColumn columnDate; // Value injected by FXMLLoader

    @FXML // fx:id="columnAmount"
    private TreeTableColumn columnAmount; // Value injected by FXMLLoader

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createInvoiceController controller = new createInvoiceController();
        PaymentService paymentService = new PaymentService();
        ArrayList<PaymentModel> installmentList = paymentService.getPaymentInfoListByAppointmentID(appointmentNumber);
        ArrayList<PaymentModelforTable> tempInstallment = new ArrayList<>();
        for (PaymentModel temp : installmentList) {
            PaymentModelforTable tempTable = new PaymentModelforTable(temp);
            tempInstallment.add(tempTable);
        }

        ObservableList<PaymentModelforTable> installment = FXCollections.observableArrayList(tempInstallment);

        columnID.setCellValueFactory(new TreeItemPropertyValueFactory<PaymentModelforTable, String>("payid"));
        columnAmount.setCellValueFactory(new TreeItemPropertyValueFactory<PaymentModelforTable, String>("total"));
        columnDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PaymentModelforTable, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PaymentModelforTable, String> param) {
                SimpleStringProperty value = new SimpleStringProperty(param.getValue().getValue().getPayDate() + " " + param.getValue().getValue().getPayTime());
                return value;
            }
        });
        TreeItem<PaymentModelforTable> root = new RecursiveTreeItem<>(installment, RecursiveTreeObject::getChildren);
        tableInstallment.setRoot(root);
        tableInstallment.setShowRoot(false);
    }
}
