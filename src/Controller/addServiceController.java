/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.createInvoiceController.TITLE;
import Utils.DBConnection;
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
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.serviceModel;
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
 *
 * @author pramulitha
 */
public class addServiceController implements Initializable {
    Connection connection;
    ObservableList<serviceModel> data = FXCollections.observableArrayList();
    FilteredList<serviceModel> filtlist = new FilteredList(data,e->true);
     
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;      

    @FXML
    private TextField colservice;

    @FXML
    private TextField colname;

    @FXML
    private TextField colsprice;

    @FXML
    private Button submit;
     @FXML
    private Button loadbtn;
    
     @FXML
    private TableView<serviceModel> serviceTable;

    @FXML
    private TableColumn<?, ?> sid;

    @FXML
    private TableColumn<?, ?> sname;

    @FXML
    private TableColumn<?, ?> sprice;
    @FXML
    private Button btn_update;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField searchService;
      @FXML
    private Button reset;
    @FXML
    private Label label;
    
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

    public addServiceController() throws ClassNotFoundException, SQLException {
        this.connection = DBConnection.getDBConnection();
    }


    
    public void cleanField(){
        colname.clear();
        colservice.clear();
        colsprice.clear();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
   
//To change body of generated methods, choose Tools | Templates.

        sid.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        sname.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        sprice.setCellValueFactory(new PropertyValueFactory< >("servicePrice"));
        
        serviceTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                colname.setText(serviceTable.selectionModelProperty().getValue().getSelectedItem().getServiceName());
                colservice.setText(serviceTable.selectionModelProperty().getValue().getSelectedItem().getServiceId());
                colsprice.setText(String.valueOf(serviceTable.selectionModelProperty().getValue().getSelectedItem().getServicePrice()));
                btn_update.setVisible(true);
            }
        });
        
        pane.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                btn_update.setVisible(false);
            }
            
        }
        
        );
        
        
        btn_update.setVisible(false);
    }
    
    @FXML
     public void loadData(){
            String query = "SELECT *FROM service_table";
            data.clear();
            try{
                preparedStatement = connection.prepareStatement(query);
                rs = preparedStatement.executeQuery();
                while(rs.next()){
                    data.add(new serviceModel(rs.getString("ServiceId"), rs.getString("ServiceName"), rs.getInt("ServicePrice")));
              
                   
                    serviceTable.setItems(data);
     
                }
                preparedStatement.close();
                rs.close();
                
             }catch(SQLException e){
                System.out.println(e);
            }
     

    /**
     *
     * @param event
     */
    
     }
    @FXML
     public void updateService(ActionEvent event){
                 int sID = Integer.parseInt(colservice.getText());
        String name = colname.getText();
        double price = Double.parseDouble(colsprice.getText());
        
        String query = "update service_table set"
                + " ServiceName=?,ServicePrice=? where ServiceId = ?";
        preparedStatement = null;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, sID);
            preparedStatement.executeUpdate();
            cleanField();
          
        } catch (SQLException ex) {
            
            System.out.println("ex: " + ex.toString());
        }
        data.clear();
        btn_update.setVisible(false);
     }
     
     
    @FXML
    public void addService(ActionEvent event)throws SQLException, ClassNotFoundException {
       
        if(emptyValidation() && validateServiceId() && validateServicePrice() && PackageNameValidation()  ){
            
        
        
       
        String query = "INSERT into service_table(ServiceId,ServiceName,ServicePrice) VALUES(?,?,?)";
        preparedStatement = null;
        try{
        
            preparedStatement = connection.prepareStatement(query);
            
            int sID = Integer.parseInt(colservice.getText());
            String name = colname.getText();
            double price = Double.parseDouble(colsprice.getText());
            
            preparedStatement.setInt(1, sID);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, price);
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
    
    public void cleanfield() {
        colservice.clear();
        colname.clear();
        colsprice.clear();
    }
    @FXML
    public void delete(){
        
        String ID = null;
        
        try{
            serviceModel ser = (serviceModel)serviceTable.getSelectionModel().getSelectedItem();
            String query = "DELETE FROM service_table WHERE ServiceId = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,ser.getServiceId());
            ID = ser.getServiceId();
            preparedStatement.executeUpdate();
            preparedStatement.close();
            rs.close();
        }catch(SQLException e){
            System.out.println(e);
            
        }
        cleanField();
        data.clear();
        loadData();
}
 


    
   
                
            

    
       /*
    *
    *VALIDATIONS
    *
     */
    //validate Empty
    private boolean validationEmpty() {

        if (colservice.getText().isEmpty() |  colname.getText().isEmpty() |  colsprice.getText().isEmpty() ) {

            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText(null);
            alert2.setContentText("Please fill all the details");

            alert2.showAndWait();

            return false;

        }
        return true;
        
        
}
         public static void showInformationAlertBox(String message){
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Information Dialog");
         alert.setHeaderText(null);
         alert.setContentText(message);
         alert.showAndWait();
     }
    public boolean emptyValidation(){
        if(colservice.getText().isEmpty() || colname.getText().isEmpty()||colsprice.getText().isEmpty()){
            showInformationAlertBox("These fields can't be empty");
            return false;
        }
        return true;
    }
    
    
    private boolean validateServiceId() {

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(colservice.getText());
        

        if (m.find() && m.group().equals(colservice.getText()) ) {

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
    
    
    
    private boolean validateServicePrice() {

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(colsprice.getText());
        

        if (m.find() && m.group().equals(colsprice.getText()) ) {

            return true;

        } else {

            Alert alert4 = new Alert(Alert.AlertType.WARNING);
            alert4.setTitle("Warning Dialog");
            alert4.setHeaderText(null);
            alert4.setContentText("Please enter valid number for Price !");

            alert4.showAndWait();

            return false;

        }

   
    
    

    }
    
    
    private boolean PackageNameValidation() {

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(colname.getText());

        if (m.find() && m.group().equals(colname.getText())) {
           
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
    
    
    private void showAlert1() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Service is added!!!");

        alert.showAndWait();

    }

    //alert is not sucess
    private void showAlert2() {

        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Information Dialog");
        alert1.setHeaderText(null);
        alert1.setContentText("Service is  Not Added!!");

        alert1.showAndWait();

    }
    public void report(){
        BasicConfigurator.configure();
    }
    
 @FXML
    public void searchService(KeyEvent event) {
        
        searchService.textProperty().addListener(((observable,oldValue,newValue)->{
        filtlist.setPredicate((Predicate<? super serviceModel>)(serviceModel st)->{
            String serviceId = st.getServiceId();
            String serviceName = st.getServiceName().toLowerCase();
            
            if(newValue == null || newValue.isEmpty()){
                return true;
            }
            if(String.valueOf(serviceId).contains(newValue)){
                return true;
            }
            if(String.valueOf(serviceName).contains(newValue)){
                return true;
            }
            return false;
        });
        
        }));
        
        SortedList<serviceModel> sort = new SortedList(filtlist);
        sort.comparatorProperty().bind(serviceTable.comparatorProperty());
        serviceTable.setItems(sort);
            
        }

    @FXML
    private void Reset(ActionEvent event) {
         cleanfield();
    }
    @FXML
    public void genarateReport(ActionEvent event) throws JRException, FileNotFoundException, ClassNotFoundException, SQLException{
                    BasicConfigurator.configure();
            
            String path = "src\\views\\Reports\\Service.jrxml";
            InputStream input = new FileInputStream(new File(path));
            
            String query = "select * from service_table";
            
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
    
    
   /*  private boolean validateupdatdeServiceId() {
         
         String x = colservice.getText();
         String y = colservice.getText();

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(colsprice.getText());
        

        if (m.find() && m.group().equals(colsprice.getText()) ) {

            return true;

        } else {

            Alert alert4 = new Alert(Alert.AlertType.WARNING);
            alert4.setTitle("Warning Dialog");
            alert4.setHeaderText(null);
            alert4.setContentText("Please enter valid number for Price !");

            alert4.showAndWait();

            return false;

        }

   
    
    

    }*/
    @FXML
    public void viewPackage(ActionEvent actionEvent){
                        try {
            AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/addPackage.fxml"));
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
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
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
  

    

