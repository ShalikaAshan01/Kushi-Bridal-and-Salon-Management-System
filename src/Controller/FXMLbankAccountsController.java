/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import model.List3;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author MHL1
 */
public class FXMLbankAccountsController implements Initializable {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    ObservableList<List3> data;
    
    
    @FXML
    private Button home;
    @FXML
    private Button appointment;
    @FXML
    private Button customer;
    @FXML
    private Button packages;
    @FXML
    private Button payment;
    @FXML
    private Button employee;
    @FXML
    private Button suplier;
    @FXML
    private Button finance;
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
    private TextField BN;
    @FXML
    private TableView<List3> table;
    @FXML
    private TableColumn<List3, String> ba;
    @FXML
    private TableColumn<List3, String> br;
    @FXML
    private TableColumn<List3, String> acn;
    @FXML
    private TableColumn<List3, String> ach;
    @FXML
    private TextField BR;
    @FXML
    private TextField ACN;
    @FXML
    private TextField ACH;

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
    private void addAccAction(ActionEvent event) throws SQLException {
        String BankName = BN.getText();
        String Branch = BR.getText();
        String AccountNu = ACN.getText();
        String AccountHol = ACH.getText();
        
        String query = "Insert into BankAcc(BankName, Branch, AccountNu, AccountHol) values(?,?,?,?)";
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(query);
            
            ps.setString(1, BankName);
            ps.setString(2, Branch);
            ps.setString(3, AccountNu);
            ps.setString(4, AccountHol);
            
            ps.execute(query);
        }catch(SQLException e){
            System.out.println(e);
            
        }
        

       }

    @FXML
    private void tranAction(ActionEvent event) {
    }

    @FXML
    private void loadTable(ActionEvent event) {
        data = FXCollections.observableArrayList();
        data.clear();
        String sql = "SELECT * from BankAcc";
        
        try{
            ps = con.prepareStatement(sql);
            while(rs.next()){
                data.add(new List3(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                table.setItems(data);
            }
            ps.close();
            rs.close();
            con.close();
        }catch(Exception e){
            
        }
    }

    
}
