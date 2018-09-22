/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.createInvoiceController.TITLE;
import Utils.DBConnection;
import com.sun.javaws.exceptions.JreExecException;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import model.packageModel;


/**
 *
 * @author pramulitha
 */
public class addPackageController implements Initializable{

    Connection connection;
    ObservableList<packageModel> data = FXCollections.observableArrayList();

    ObservableList<String> options = FXCollections.observableArrayList();
    FilteredList<packageModel> filtlist = new FilteredList(data,e->true);

    //fill the supplier combo box in
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    @FXML
    private TextField colpid;

    @FXML
    private TextField colpname;

    @FXML
    private TextField colPrice;

    @FXML
    private Button submit;
    
    @FXML
    private TextField searchPackage;

    @FXML
    private TableView<packageModel> packagetable;
    @FXML
    private TableColumn<?, ?> packageid;

    @FXML
    private TableColumn<?, ?> packagename;

    @FXML
    private TableColumn<?, ?> packageprice;

    @FXML
    private TableColumn<?, ?> colservice1;

    @FXML
    private TableColumn<?, ?> colservice2;

    @FXML
    private TableColumn<?, ?> colservice3;

    @FXML
    private Button btn_update;

  
    @FXML
    private Button load;
    @FXML
    private Button delete;
    @FXML
    private Button report;

    @FXML
    private ComboBox<String> combo1;

    @FXML
    private ComboBox<String> combo2;

    @FXML
    private ComboBox<String> combo3;
    @FXML
    private Label label;
    @FXML
    private Button reset;
    
        //for navigation bar

    @FXML
    private Button btnStockUI;

    @FXML
    private Button btnAppointmentUI;

    @FXML
    private Button btnCustomerUI;

    @FXML
    private Button btnPackageUI;

    @FXML
    private Button btnPaymentUI;

    @FXML
    private Button btnEmployeeUI;

    @FXML
    private Button btnSuppllierUI;

    @FXML
    private Button btnHomeUI;

    public addPackageController() throws ClassNotFoundException, SQLException {
        this.connection = DBConnection.getDBConnection();
    }

    


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillComboBox1();
        fillComboBox2();
        fillComboBox3();
        setNavigationbarToolTip();
    }

    @FXML
    public void loadData() {
        String query = "SELECT *FROM package_table";
        data.clear();
        try {
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                data.add(new packageModel(rs.getString("PackageId"), rs.getString("PackageName"), rs.getString("Service1"), rs.getString("Service2"), rs.getString("Service3"), rs.getInt("PackagePrice")));

                packageid.setCellValueFactory(new PropertyValueFactory<>("packageId"));
                packagename.setCellValueFactory(new PropertyValueFactory<>("packageName"));
                colservice1.setCellValueFactory(new PropertyValueFactory<>("service1"));
                colservice2.setCellValueFactory(new PropertyValueFactory<>("service2"));
                colservice3.setCellValueFactory(new PropertyValueFactory<>("service3"));
                packageprice.setCellValueFactory(new PropertyValueFactory<>("packagePrice"));

                packagetable.setItems(data);

            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void updatePackage(ActionEvent event) {
        int sID = Integer.parseInt(colpid.getText());
        String name = colpname.getText();
        double price = Double.parseDouble(colPrice.getText());
        String Service1 = combo1.getSelectionModel().getSelectedItem();
        String Service2 = combo2.getSelectionModel().getSelectedItem();
        String Service3 = combo3.getSelectionModel().getSelectedItem();

        String query = "update package_table set"
                + " PackageName=?,PackagePrice=?, Service1=?,Service2= ?,Service3 = ? where PackageId = ?";
        preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, Service1);
            preparedStatement.setString(4, Service2);
            preparedStatement.setString(5, Service3);
            preparedStatement.setInt(6, sID);
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
        }
        data.clear();
        btn_update.setVisible(false);
        cleanfield();
       
    }

    @FXML
    private void insertPackage(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (validationEmpty() &&  validateNumberPrice() && validateNumberId() && combo1ValidationEmpty() && combo2ValidationEmpty() && combo3ValidationEmpty() && comboValidationDuplicate() && PackageNameValidation() ) {

            String PackageId = colpid.getText();
            String PackageName = colpname.getText();
            String Service1 = combo1.getSelectionModel().getSelectedItem();
            String Service2 = combo2.getSelectionModel().getSelectedItem();
            String Service3 = combo3.getSelectionModel().getSelectedItem();
            String price = colPrice.getText();

            String query = "INSERT into package_table(PackageId,PackageName,Service1,Service2,Service3,PackagePrice) VALUES(?,?,?,?,?,?)";
            preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(query);

                int pID = Integer.parseInt(PackageId);
                double prce = Double.parseDouble(price);

                preparedStatement.setInt(1, pID);
                preparedStatement.setString(2, PackageName);
                preparedStatement.setString(3, Service1);
                preparedStatement.setString(4, Service2);
                preparedStatement.setString(5, Service3);
                preparedStatement.setDouble(6, prce);
                preparedStatement.execute();
                
                if(query!=null){
                    showAlert1();
                }else{
                    showAlert2();
                    
                    
                 
                }

            } catch (NumberFormatException | SQLException e) {
                System.out.println(e);
               
            } finally {

                preparedStatement.close();
            }
            data.clear();
            cleanfield();
        }

    }

    @FXML
    public void delete() {

        String ID = packagetable.getSelectionModel().getSelectedItem().getPackageId();

        try {
            String query = "DELETE FROM package_table WHERE PackageId = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);

        }
        data.clear();
        loadData();
        cleanfield();
    }

    
    public void fillComboBox1() {

        try {
            connection = DBConnection.getDBConnection();

            String query = "SELECT ServiceName FROM service_table";

            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                options.add(rs.getString("serviceName"));
            }
            combo1.setItems(options);
            preparedStatement.close();
            rs.close();

        } catch (SQLException|ClassNotFoundException e) {
            Logger.getLogger(addPackageController.class.getName()).log(Level.SEVERE, null, e);

        }
        
    }

    
    public void fillComboBox2() {
        try {
            options.clear();
            connection = DBConnection.getDBConnection();

            String query = "SELECT ServiceName FROM service_table";

            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                options.add(rs.getString("serviceName"));
            }
            combo2.setItems(options);
            preparedStatement.close();
            rs.close();

        } catch (SQLException|ClassNotFoundException e) {
            Logger.getLogger(addPackageController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    
    public void fillComboBox3() {

        try {
            options.clear();
            connection = DBConnection.getDBConnection();

            String query = "SELECT ServiceName FROM service_table";

            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                options.add(rs.getString("serviceName"));
            }
            combo3.setItems(options);
            preparedStatement.close();
            rs.close();

        } catch (SQLException|ClassNotFoundException e) {
            Logger.getLogger(addPackageController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    private void fill(MouseEvent event) {
        colpid.setText(packagetable.selectionModelProperty().getValue().getSelectedItem().getPackageId());
        colPrice.setText(packagetable.selectionModelProperty().getValue().getSelectedItem().getPackagePrice().toString());
        colpname.setText(packagetable.selectionModelProperty().getValue().getSelectedItem().getPackageName());
        combo1.getSelectionModel().select(packagetable.selectionModelProperty().getValue().getSelectedItem().getService1());
        combo2.getSelectionModel().select(packagetable.selectionModelProperty().getValue().getSelectedItem().getService2());
        combo3.getSelectionModel().select(packagetable.selectionModelProperty().getValue().getSelectedItem().getService3());
    }

    public void cleanfield() {
        colPrice.clear();
        colpid.clear();
        colpname.clear();
        combo1.getSelectionModel().clearSelection();
        combo2.getSelectionModel().clearSelection();
        combo3.getSelectionModel().clearSelection();
    }

    //validate Empty
    private boolean validationEmpty() {

        if (colPrice.getText().isEmpty() | colpid.getText().isEmpty() | colpname.getText().isEmpty()) {

            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText(null);
            alert2.setContentText("Please fill all the details");

            alert2.showAndWait();

            return false;

        }
        return true;
    }
    
    
    
    
    
    private boolean validateNumberPrice() {

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(colPrice.getText());
        

        if (m.find() && m.group().equals(colPrice.getText()) ) {

            return true;

        } else {

            Alert alert4 = new Alert(Alert.AlertType.WARNING);
            alert4.setTitle("Warning Dialog");
            alert4.setHeaderText(null);
            alert4.setContentText("Please enter valid number for price!");

            alert4.showAndWait();

            return false;

        }

   
    
    

    }
    
    
    private boolean validateNumberId() {

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(colpid.getText());
        

        if (m.find() && m.group().equals(colpid.getText()) ) {

            return true;

        } else {

            Alert alert4 = new Alert(Alert.AlertType.WARNING);
            alert4.setTitle("Warning Dialog");
            alert4.setHeaderText(null);
            alert4.setContentText("Please enter valid number for Id !");

            alert4.showAndWait();

            return false;

        }

   
    
    

    }
     

   
    
    

    
    private void showAlert1() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Package is added!!!");

        alert.showAndWait();

    }

    //alert is not sucess
    private void showAlert2() {

        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Information Dialog");
        alert1.setHeaderText(null);
        alert1.setContentText("Package is  Not Added!!");

        alert1.showAndWait();

    }
    
    
    private boolean combo1ValidationEmpty() {

        if (combo1.getSelectionModel().isEmpty() ) {

            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText(null);
            alert2.setContentText("Please select the service1 ");

            alert2.showAndWait();

            return false;

        }
        return true;
}
     private boolean combo2ValidationEmpty() {

        if (combo2.getSelectionModel().isEmpty() ) {

            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText(null);
            alert2.setContentText("Please select the service2 ");

            alert2.showAndWait();

            return false;

        }
        return true;
}
     
     
     private boolean combo3ValidationEmpty() {

        if (combo3.getSelectionModel().isEmpty() ) {

            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText(null);
            alert2.setContentText("Please select the service3 ");

            alert2.showAndWait();

            return false;

        }
        return true;
}
     
       private boolean comboValidationDuplicate() {
                String Service1 = combo1.getSelectionModel().getSelectedItem();
                String Service2 = combo2.getSelectionModel().getSelectedItem();
                String Service3 = combo3.getSelectionModel().getSelectedItem();

        if (Service1.equals(Service2)  || Service2.equals(Service3 )|| Service1.equals(Service3)  ) {

            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText(null);
            alert2.setContentText("choose  different Services ! ");

            alert2.showAndWait();

            return false;

        }
        return true;
}
       
       
        //Name Validation
    private boolean PackageNameValidation() {

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(colpname.getText());

        if (m.find() && m.group().equals(colpname.getText())) {
           
            Alert alert5 = new Alert(Alert.AlertType.WARNING);
            alert5.setTitle("Warning Dialog");
            alert5.setHeaderText(null);
            alert5.setContentText("Please enter valid Name!");

            alert5.showAndWait();
            return false;
        } else {

            return true;

        }
    }
       
   @FXML
    public void searchPackage(KeyEvent event) {
        
            searchPackage.textProperty().addListener(((observable,oldValue,newValue)->{
            filtlist.setPredicate((Predicate<? super packageModel>)(packageModel st)->{
            String packageId = st.getPackageId();
            String serviceName = st.getPackageName().toLowerCase();
            
            if(newValue == null || newValue.isEmpty()){
                return true;
            }
            if(String.valueOf(packageId).contains(newValue)){
                return true;
            }
            return false;
        });
        
        }));
        
        SortedList<packageModel> sort = new SortedList(filtlist);
        sort.comparatorProperty().bind(packagetable.comparatorProperty());
        packagetable.setItems(sort);
            
    }

    @FXML
    public void reset(ActionEvent event) {
        
         cleanfield();
        
    }
    @FXML
    public void viewService(ActionEvent event){
                try {
            AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/addService.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //    stage.setAlwaysOnTop(true);
            //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
            //stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
            //stage.initStyle(StageStyle.UNDECORATED);//hide all button
            stage.setTitle(TITLE);
            stage.setMaximized(true);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        //for navigation bar
    @FXML
    public void viewAppointmentUIb(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/addAppointment.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //    stage.setAlwaysOnTop(true);
            //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
            //stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
            //stage.initStyle(StageStyle.UNDECORATED);//hide all button
            stage.setTitle(TITLE);
            stage.setMaximized(true);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void viewCustomerUIb(ActionEvent event) {

    }

    @FXML
    public void viewEmployeeUIb(ActionEvent event) {

    }

    @FXML
    public void viewHomeUIb(ActionEvent event) {

    }

    @FXML
    public void viewPackageUIb(ActionEvent event) {
        
    }

    @FXML
    public void viewPaymentUIb(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/createInvoice.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //    stage.setAlwaysOnTop(true);
            //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
            //stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
            //stage.initStyle(StageStyle.UNDECORATED);//hide all button
            stage.setTitle(TITLE);
            stage.setMaximized(true);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void viewStockUIb(ActionEvent event) {

    }

    @FXML
    public void viewSupplierUIb(ActionEvent event) {

    }
    public void setNavigationbarToolTip(){
        btnAppointmentUI.setTooltip(new Tooltip("Appointment Management System"));
        btnCustomerUI.setTooltip(new Tooltip("Customer Management System"));
        btnEmployeeUI.setTooltip(new Tooltip("Employee Management System"));
        btnStockUI.setTooltip(new Tooltip("Stock Management System"));
        btnPackageUI.setTooltip(new Tooltip("Package Management System"));
        btnPaymentUI.setTooltip(new Tooltip("Payment Management System"));
        btnSuppllierUI.setTooltip(new Tooltip("Supplier Management System"));
        btnHomeUI.setTooltip(new Tooltip("Home"));

                
    }
}
