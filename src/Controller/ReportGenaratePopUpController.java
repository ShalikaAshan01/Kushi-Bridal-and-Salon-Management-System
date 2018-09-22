/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Services.PaymentService;
import Utils.DBConnection;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import model.PaymentModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.BasicConfigurator;

/**
 * FXML Controller class
 *
 * @author Shalika Ashan
 */
public class ReportGenaratePopUpController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbMonth.setVisible(false);
        cbYear.setVisible(false);
        dateTime.setVisible(false);
        txtCustom.setVisible(false);

        setComboBox();

        txtCustom.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblHeader.setText(title + " " + newValue + " ?");
            }

        });
    }
    private String query = "select payid,cid,firstname,lastname,aid,payDate,payTime,total  from payment p,Customer c where p.cid= c.id";

    private String title = "";
    private String alertHeader = "";
    private String contextHeader = "";
    @FXML // fx:id="cbReport"
    private JFXComboBox cbReport; // Value injected by FXMLLoader

    @FXML // fx:id="rbYear"
    private JFXRadioButton rbYear; // Value injected by FXMLLoader

    @FXML // fx:id="rbMonth"
    private JFXRadioButton rbMonth; // Value injected by FXMLLoader

    @FXML // fx:id="rbDaily"
    private JFXRadioButton rbDaily; // Value injected by FXMLLoader

    @FXML // fx:id="rbCustom"
    private JFXRadioButton rbCustom; // Value injected by FXMLLoader

    @FXML // fx:id="cbYear"
    private JFXComboBox cbYear; // Value injected by FXMLLoader

    @FXML // fx:id="cbMonth"
    private JFXComboBox cbMonth; // Value injected by FXMLLoader

    @FXML // fx:id="lblHeader"
    private Label lblHeader; // Value injected by FXMLLoader
    @FXML
    private Label lblExport;
    @FXML // fx:id="txtCustom"
    private JFXTextField txtCustom; // Value injected by FXMLLoader

    @FXML // fx:id="dateTime"
    private JFXDatePicker dateTime; // Value injected by FXMLLoader

    @FXML
    public void onPicker(ActionEvent event) {
        title = "Do you want Genarate Daily Report for";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localdate = LocalDate.parse(dateTime.getValue().toString(), dtf);

        title = title + " " + localdate.getDayOfWeek().toString().charAt(0)
                + localdate.getDayOfWeek().toString().substring(1, 3).toLowerCase()
                + ", " + localdate.getMonth().toString().charAt(0)
                + localdate.getMonth().toString().substring(1, 3).toLowerCase()
                + " " + localdate.getDayOfMonth() + ", "
                + localdate.getYear();
        setTitle();

    }

    @FXML
    public void onRBCustom(ActionEvent event) {
        if (rbCustom.isSelected()) {
            txtCustom.setVisible(true);
            title = "Do you want Genarate Custom Report for";
            cbMonth.setVisible(false);
            cbYear.setVisible(false);
            dateTime.setVisible(false);
            setTitle();
        }
    }

    @FXML
    public void onRBDaily(ActionEvent event) {
        if (rbDaily.isSelected()) {
            title = "Do you want Genarate Daily Report for";
            setTitle();
            cbMonth.setVisible(false);
            cbYear.setVisible(false);
            dateTime.setVisible(true);
            txtCustom.setVisible(false);
        }
    }

    @FXML
    public void onRBMonth(ActionEvent event) {
        if (rbMonth.isSelected()) {
            cbYear.getSelectionModel().clearSelection();
            cbMonth.getSelectionModel().clearSelection();
            title = "Do you want Genarate Monthly Report for";
            setTitle();
            cbMonth.setVisible(true);
            cbYear.setVisible(true);
            dateTime.setVisible(false);
            txtCustom.setVisible(false);
        }
    }

    @FXML
    public void onRByear(ActionEvent event) {
        if (rbYear.isSelected()) {
            cbYear.getSelectionModel().clearSelection();
            title = "Do you want Genarate Yearly Report for";
            setTitle();
            cbMonth.setVisible(false);
            cbYear.setVisible(true);
            dateTime.setVisible(false);
            txtCustom.setVisible(false);
        }
    }

    @FXML
    public void oncbMonth(ActionEvent event) {
        if (!cbYear.getSelectionModel().isEmpty() && !cbMonth.getSelectionModel().isEmpty()) {
            title = "Do you want Genarate Monthly Report for "
                    + cbYear.getSelectionModel().getSelectedItem() + "/"
                    + cbMonth.getSelectionModel().getSelectedItem();
            setTitle();
        }
    }

    @FXML
    public void onTxtCustom(ActionEvent event) {

    }

    @FXML
    public void oncbYear(ActionEvent event) {
        title = title + " Year: " + cbYear.getSelectionModel().getSelectedItem();
        setTitle();
    }

    @FXML
    public void close(ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void changeDate(InputMethodEvent event) {
        title = title + " Year: " + dateTime.toString();
        setTitle();
    }

    public void setTitle() {
        lblHeader.setText(title + " ?");
    }

    private void setComboBox() {
        //set year combo box
        PaymentService ps = new PaymentService();
        ArrayList<PaymentModel> payment = ps.getPaymentInfo();
        ObservableList<String> years = FXCollections.observableArrayList();

        for (PaymentModel temp : payment) {
            String Payyear = temp.getPayDate().substring(0, 4);
            if (!years.contains(Payyear)) {
                years.add(Payyear);
            }
        }
        cbYear.setItems(years);
        //Set Month combobox
        ObservableList<String> months = FXCollections.observableArrayList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec");

        cbMonth.setItems(months);

    }

    public boolean validation() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cannot genarate report");
        if (txtCustom.isVisible() && txtCustom.getText().isEmpty()) {
            alert.setHeaderText("");
            alert.setContentText("Please enter Customer Name");
            alert.showAndWait();
            return false;

        }
        if (txtCustom.isVisible() && !txtCustom.getText().isEmpty()) {
            try {
                String name[] = txtCustom.getText().split(" ");
                String fname = name[0];
                String lname = name[1];
            } catch (Exception e) {
                alert.setHeaderText("");
                alert.setContentText("Please enter Customer's full name");
                alert.showAndWait();
                return false;
            }
        }

        if (cbYear.isVisible() && cbYear.getSelectionModel().isEmpty()) {
            alert.setHeaderText("");
            alert.setContentText("Please enter Valid Year");
            alert.showAndWait();
            return false;
        }
        if (cbMonth.isVisible() && cbMonth.getSelectionModel().isEmpty()) {
            alert.setHeaderText("");
            alert.setContentText("Please enter Valid Month");
            alert.showAndWait();
            return false;
        }
        if (dateTime.isVisible() && dateTime.getValue() == null) {
            alert.setHeaderText("");
            alert.setContentText("Please enter Valid Date");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    @FXML
    public void genarate(ActionEvent event) throws ClassNotFoundException, SQLException, FileNotFoundException, JRException, IOException {
        if (validation()) {
            if (!queryValidation()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Cannot Genarate Report");
                alert.setHeaderText(alertHeader);
                alert.setContentText(contextHeader);
                alert.showAndWait();
            } else {
                BasicConfigurator.configure();

                String path = "src\\views\\Reports\\AllPayments.jrxml";
                InputStream input = new FileInputStream(new File(path));

                JRDesignQuery jrquery = new JRDesignQuery();
                jrquery.setText(query);

                JasperDesign jasperDesign = JRXmlLoader.load(input);

                jasperDesign.setQuery(jrquery);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getDBConnection());
//                File pdf = File.createTempFile("D\\aa", ".pdf");
//                JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
                JasperViewer viewer = new JasperViewer(jasperPrint, false);
                ((Node) (event.getSource())).getScene().getWindow().hide();
                viewer.setVisible(true);

            }
        }

    }

    public boolean queryValidation() throws ClassNotFoundException, SQLException {
        query = "select payid,cid,firstname,lastname,aid,payDate,payTime,total  from payment p,Customer c where p.cid= c.id";
        Connection conn = DBConnection.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String month, year, date, name[];
        if (rbMonth.isSelected()) {
            month = cbMonth.getSelectionModel().selectedItemProperty().getValue().toString();
            year = cbYear.getSelectionModel().selectedItemProperty().getValue().toString();
            alertHeader = "No enties found for " + month + "/" + year;
            contextHeader = "Please enter valid year and month";
            int tempMonth = 0;
            switch (month) {
                case "Jan":
                    tempMonth = 1;
                    break;
                case "Feb":
                    tempMonth = 2;
                    break;
                case "Mar":
                    tempMonth = 3;
                    break;
                case "Apr":
                    tempMonth = 4;
                    break;
                case "May":
                    tempMonth = 5;
                    break;
                case "Jun":
                    tempMonth = 6;
                    break;
                case "Jul":
                    tempMonth = 7;
                    break;
                case "Aug":
                    tempMonth = 8;
                    break;
                case "Sept":
                    tempMonth = 9;
                    break;
                case "Oct":
                    tempMonth = 10;
                    break;
                case "Nov":
                    tempMonth = 11;
                    break;
                case "Dec":
                    tempMonth = 12;
                    break;
            }
            if (tempMonth < 10) {
                query = query + " and p.payDate like '%"
                        + year + "/0" + tempMonth
                        + "%'";
            } else {
                query = query + " and p.payDate like '%"
                        + year + "/" + tempMonth
                        + "%'";

            }
        }
        if (rbYear.isSelected()) {
            year = cbYear.getSelectionModel().selectedItemProperty().getValue().toString();
            query = query + " and p.payDate like '%"
                    + year
                    + "%'";
        }
        if (rbDaily.isSelected()) {
            date = dateTime.getValue().getYear() + "/" + dateTime.getValue().getMonthValue() + "/" + dateTime.getValue().getDayOfMonth();
            alertHeader = "No enties found for " + dateTime.getValue();
            contextHeader = "Please enter valid date";
            if (dateTime.getValue().getMonthValue() < 10) {
                date = dateTime.getValue().getYear() + "/0" + dateTime.getValue().getMonthValue() + "/" + dateTime.getValue().getDayOfMonth();
            }
            if (dateTime.getValue().getDayOfMonth() < 10) {
                date = dateTime.getValue().getYear() + "/" + dateTime.getValue().getMonthValue() + "/0" + dateTime.getValue().getDayOfMonth();
            }
            if (dateTime.getValue().getMonthValue() < 10 && dateTime.getValue().getDayOfMonth() < 10) {
                date = dateTime.getValue().getYear() + "/0" + dateTime.getValue().getMonthValue() + "/0" + dateTime.getValue().getDayOfMonth();

            }
            query = query + " and p.payDate like '%" + date + "%'";
        }
        if (rbCustom.isSelected()) {
            name = txtCustom.getText().split(" ");
            String fname = name[0];
            String lname = name[1];
            alertHeader = "No enties found for " + txtCustom.getText();
            contextHeader = "Please enter valid Customer name";
            query = query + " and c.firstname like '%" + fname + "%'"
                    + "and c.lastname like '%" + lname + "%'";

        }

        preparedStatement = conn.prepareStatement(query);
        
        System.out.println("query: " + query);
        resultSet = preparedStatement.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        //close db connection,resultset,prepared statements
        if (!resultSet.isClosed()) {
            resultSet.close();
        }
        if (!preparedStatement.isClosed()) {
            preparedStatement.close();
        }
        if (!conn.isClosed()) {
            conn.close();
        }
        return count > 0;
    }
}
