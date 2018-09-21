/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Shalika Ashan
 */
public class PopUPSummaryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private PieChart pie;
    @FXML
    private Label lblinvoice;
    @FXML
    private Label lblIncome;
    ObservableList<PieChart.Data> pielist = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblinvoice.setText("No.of Invoice: " + ViewInvoicesController.no);
        lblIncome.setText("Total Income: " + ViewInvoicesController.total);
                   //pie chart
            pielist = FXCollections.observableArrayList(
                    new PieChart.Data("Sunday", ViewInvoicesController.sun),
                    new PieChart.Data("Monday", ViewInvoicesController.mon),
                    new PieChart.Data("Tuesday", ViewInvoicesController.tue),
                    new PieChart.Data("Wednesday", ViewInvoicesController.wen),
                    new PieChart.Data("Thursday", ViewInvoicesController.thu),
                    new PieChart.Data("Friday", ViewInvoicesController.fri),
                    new PieChart.Data("Saturday", ViewInvoicesController.sat)
            );
            for (PieChart.Data data : pie.getData()) {

            }
            pie.getData().clear();
            pie.setData(pielist);
            pie.setLegendSide(Side.LEFT);
    }    
    
}
