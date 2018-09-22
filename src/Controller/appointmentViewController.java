/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Services.AppointmentServices;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
import model.Appointment;
import Utils.DBConnection;

/**
 *
 * @author Ushadi
 */
public class appointmentViewController implements Initializable {
//    private Stage primaryStage = SalonManagementSystem.primaryStage;
    public static String phasen;
    
    @FXML
    private TableView<Appointment> AppointView;

    @FXML
    private TableColumn<Appointment, Integer> apID;

    @FXML
    private TableColumn<Appointment, String> date2;

    @FXML
    private TableColumn<Appointment, String> time2;

    @FXML
    private TableColumn<Appointment, String> emp2;

    @FXML
    private TableColumn<Appointment, Integer> cid2;

    @FXML
    private TableColumn<Appointment, String> cName2s;

    @FXML
    private TableColumn<Appointment, String> cAddress2;

    @FXML
    private TableColumn<Appointment, String> Tel2;

    @FXML
    private TableColumn<Appointment, String> ser2;

    @FXML
    private TableColumn<Appointment, String> pack2;

    @FXML
    private TableColumn<Appointment, Double> tot2;

    @FXML
    private Label TableLbel;

    @FXML
    private Button AddApoint;

    @FXML
    private TextField txtSearch;

    @FXML
    private Text search2;
    
    @FXML
    private Button exit;
    
    @FXML
    private Button report;
    
    //@FXML
    //private ComboBox<Integer> searchCombo;


    
    

    ObservableList<Appointment> appointlist = FXCollections.observableArrayList();
    FilteredList<Appointment> filtlist = new FilteredList(appointlist, e -> true);

    Connection connection;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    @FXML
    private AnchorPane anchor;
    @FXML
    private ComboBox<?> searchCombo;
    @FXML
    private Button del_col;

    //sorte details in search bar
   @FXML
   public void txtSearch(KeyEvent event) {
       
        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            filtlist.setPredicate((Predicate<? super Appointment>) (Appointment appoint) -> {
                String customerName = appoint.getCustomerName().toLowerCase();
                int appID = appoint.getAppointmentId();
                //int cusID = appoint.getCustomerID();
                
                if (newValue == null || newValue.isEmpty()) {

                    return true;
                }
                if (String.valueOf(appID).contains(newValue)) {

                    return true;
               }
//                if (String.valueOf(cusID).contains(newValue)) {
//
//                    return true;
//               }
                if (customerName.contains(newValue)) {

                    return true;
                }

                return false;

            });

           

        }));
         SortedList<Appointment> sort = new SortedList(filtlist);
            sort.comparatorProperty().bind(AppointView.comparatorProperty());
            AppointView.setItems(sort);

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }
    private void loadData(){
                try {
            connection = DBConnection.getDBConnection();

            try {
                String query = "select * from Appointment";
                resultSet = connection.createStatement().executeQuery(query);

                while (resultSet.next()) {
                    appointlist.add(new Appointment(resultSet.getInt("AppointID"), resultSet.getString("Date"), resultSet.getString("Time"), resultSet.getString("employee"), resultSet.getInt("custID"), resultSet.getString("custName"), resultSet.getString("custAddress"), resultSet.getString("TeleNum"), resultSet.getString("services"), resultSet.getString("packages"), resultSet.getDouble("total")));
                }
            } catch (Exception e) {

            }

            apID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            date2.setCellValueFactory(new PropertyValueFactory<>("Date"));
            time2.setCellValueFactory(new PropertyValueFactory<>("Time"));
            emp2.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
            cid2.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            cName2s.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            cAddress2.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            Tel2.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            ser2.setCellValueFactory(new PropertyValueFactory<>("service"));
            pack2.setCellValueFactory(new PropertyValueFactory<>("packages"));
            tot2.setCellValueFactory(new PropertyValueFactory<>("total"));

            AppointView.setItems(appointlist);

            connection.close();
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(appointmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(appointmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    //refresh the table
    /* private void refreshTableValues(){
        appointlist.clear();
        
        try{
            connection = DBConnect.getDBConnection();
            
            String query = "select * from Appointment";
            
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                appointlist.add(new Appointment(resultSet.getInt("AppointID"),resultSet.getString("Date"),resultSet.getString("Time"),resultSet.getString("employee"),resultSet.getInt("custID"),resultSet.getString("custName"),resultSet.getString("custAddress"),resultSet.getString("TeleNum"),resultSet.getString("services"),resultSet.getString("packages"),resultSet.getDouble("total"))); 
                
                AppointView.setItems(appointlist);
            }
            
            preparedStatement.close();
            resultSet.close();
            connection.close();
        
        }catch(Exception e){
        System.out.println(e);
        }
    }*/
     
    //button click to enter the appointment page
    @FXML
    void AddApoint(ActionEvent event) throws IOException {
            AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/addAppointment.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/salonmanagementsystem/FXMLDocument.fxml"));
//        Parent root1 = (Parent) fxmlLoader.load();
//        //Stage stage = new Stage();
//        primaryStage.setScene(new Scene(root1));
//        primaryStage.show();

    }
    
    //exit button 
    @FXML
    void exitBtn(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
        
    }
      /* @FXML
    void searchCombo(ActionEvent event) {
         phasen = String.valueOf(searchCombo.getValue());
        Parent root1;
        try {
            root1 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

            Scene scene = new Scene(root1);

            primaryStage.setTitle("");
            primaryStage.setScene(scene);
            primaryStage.show();
       
        
    }   catch (IOException ex) {
            Logger.getLogger(appointmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    @FXML
    private void del_col(ActionEvent event) {
        int appid = AppointView.getSelectionModel().getSelectedItem().getAppointmentId();
        AppointmentServices appointmentServices = new AppointmentServices();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to delete this appointment");

        Optional<ButtonType> res = alert.showAndWait();

        if (res.orElse(ButtonType.OK) == ButtonType.OK) {
            int result = appointmentServices.delete(appid);
            if (result > 0) {
                appointlist.clear();
                loadData();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success Dialog");
                alert1.setHeaderText(null);
                alert1.setContentText("Successfully deleted");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success Dialog");
                alert1.setHeaderText(null);
                alert1.setContentText("Cannot be delete");
                alert1.showAndWait();
            }
        }
    }
    
     //report generation
       @FXML
    void report(ActionEvent event) throws SQLException, ClassNotFoundException, JRException, FileNotFoundException {
        
        
            BasicConfigurator.configure();
            
            String path = "src\\iReportGenerate\\Appointment.jrxml";
            InputStream input = new FileInputStream(new File(path));
            
            String query = "select * from Appointment";
            
            JRDesignQuery jrquery = new JRDesignQuery();
            jrquery.setText(query);
            
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            
            jasperDesign.setQuery(jrquery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getDBConnection());
            
             //File pdf = File.createTempFile("D\\aa", ".pdf");
             //JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
             
             JasperViewer viewer = new JasperViewer(jasperPrint, false);
             ((Node) (event.getSource())).getScene().getWindow().hide();
             viewer.setVisible(true);
            
            
        
    }
}