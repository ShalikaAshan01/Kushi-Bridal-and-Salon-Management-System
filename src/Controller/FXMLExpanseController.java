/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Utils.DBConnection;
import model.List2;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author MHL1
 */
public class FXMLExpanseController implements Initializable {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    ObservableList<List2> data;
    
    @FXML
    private Button Button1;
    @FXML
    private Button Button2;
    @FXML
    private Button Button3;
    @FXML
    private Button Button4;
    @FXML
    private Button Button5;
    @FXML
    private Button Button6;
    @FXML
    private Button Button7;
    @FXML
    private Button Button8;
    @FXML
    private Button Button9;
    @FXML
    private TextField del;
    @FXML
    private TextField sal;
    @FXML
    private TextField tEx;
    @FXML
    private TextField main;
    @FXML
    private TableView<List2> table;
    @FXML
    private TableColumn<List2, String> DE;
    @FXML
    private TableColumn<List2, String> SA;
    @FXML
    private TableColumn<List2, String> MA;
    @FXML
    private TableColumn<List2, String> TE;
    @FXML
    private Button Button91;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void incomeAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/FXMLIncome.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1)); 
        stage.setMaximized(true);
        stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void PTAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/FXMLPT.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));  
                stage.setMaximized(true);
        stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void bankAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/FXMLbankAccounts.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
                stage.setMaximized(true);

        stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void expanseAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/FXMLExpanse.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1)); 
                stage.setMaximized(true);

        stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void reportAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/FXMLreport.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
                stage.setMaximized(true);
        stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void dealersAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        con = DBConnection.getDBConnection();
             if(con != null){
              String sql ="Select sum(deexpance) from deExpan";
              try{
              ps = con.prepareStatement(sql);
              rs = ps.executeQuery();
              if(rs.next()){
                  String sum = rs.getString("sum(deexpance)");
                  del.setText(sum);
              }

            }catch(Exception e){     
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e);

            }finally{
             ps.close();
            rs.close();
            con.close();
            }
              
         }
    }

    @FXML
    private void salaryAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        con = DBConnection.getDBConnection();
             if(con != null){
              String sql ="Select sum(salary) from Esalary";
              try{
              ps = con.prepareStatement(sql);
              rs = ps.executeQuery();
              if(rs.next()){
                  String sum = rs.getString("sum(salary)");
                  sal.setText(sum);
              }

            }catch(Exception e){     

                JOptionPane.showMessageDialog(null, e);

            }finally{
             ps.close();
            rs.close();
            con.close();
            }
         }
    }

    @FXML
    private void mainAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        con = DBConnection.getDBConnection();
             if(con != null){
              String sql ="Select sum(maExpance) from maExpan";
              try{
              ps = con.prepareStatement(sql);
              rs = ps.executeQuery();
              if(rs.next()){
                  String sum = rs.getString("sum(maExpance)");
                  main.setText(sum);
              }

            }catch(Exception e){     

                JOptionPane.showMessageDialog(null, e);

            }finally{
             ps.close();
            rs.close();
            con.close();
            }
         }
    }

    @FXML
    private void totalAction(ActionEvent event) {
        
         int one = Integer.parseInt(del.getText());
         int two = Integer.parseInt(main.getText());
         int three = Integer.parseInt(sal.getText());
         
         String answer = String.valueOf(one+two+three);
         tEx.setText(answer);
    }
    
    private void setTableValue(){
        
        DE.setCellValueFactory(new PropertyValueFactory<>("del"));
        MA.setCellValueFactory(new PropertyValueFactory<>("main"));
        SA.setCellValueFactory(new PropertyValueFactory<>("sal"));
        TE.setCellValueFactory(new PropertyValueFactory<>("tEx"));
        
    }
    @FXML
    private void loadT(ActionEvent event) throws SQLException, ClassNotFoundException {
        data = FXCollections.observableArrayList();
        data.clear();
        try {
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement("Select * from totalExpanse");
            rs = ps.executeQuery();
            while(rs.next()){
             data.add(new List2(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLIncomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
           ps.close();
            rs.close();
            con.close();
       }
        setTableValue();
        table.setItems(data);   
    }
    
}
