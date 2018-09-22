/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import Utils.DBConnection;

/**
 *
 * @author Ushadi
 */
public class AppointmentServices {

    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;
    public static final Logger log = Logger.getLogger(AppointmentServices.class.getName());

    public int insertQuery(Appointment appointment) {
        int result = 0;
        try {
            connection = DBConnection.getDBConnection();
            String query = "insert into Appointment(Date,Time,employee,custID,services,packages,total,custName,custAddress,TeleNum) values(?,?,?,?,?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, appointment.getDate());
            preparedStatement.setString(2, appointment.getTime());
            preparedStatement.setString(3, appointment.getEmployeeName());
            preparedStatement.setInt(4, appointment.getCustomerID());
            preparedStatement.setString(5, appointment.getService());
            preparedStatement.setString(6, appointment.getPackages());
            preparedStatement.setDouble(7, appointment.getTotal());
            preparedStatement.setString(8, appointment.getCustomerName());
            preparedStatement.setString(9, appointment.getCustomerAddress());
            preparedStatement.setString(10, appointment.getTelephone());

            result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        return result;
    }
    
    public Appointment getAppointmentByID(int id){
        Appointment appointment = new Appointment();
        
        try{
            connection = DBConnection.getDBConnection();
            String query = "select * from Appointment where AppointID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                appointment.setDate(resultSet.getString("Date"));
                appointment.setTime(resultSet.getString("Time"));
                appointment.setEmployeeName(resultSet.getString("employee"));
                appointment.setCustomerID(resultSet.getInt("custID"));
                appointment.setCustomerName(resultSet.getString("custName"));
                appointment.setCustomerAddress(resultSet.getString("custAddress"));
                appointment.setTelephone(resultSet.getString("TeleNum"));
                appointment.setService(resultSet.getString("services"));
                appointment.setPackages(resultSet.getString("packages"));
                appointment.setTotal(resultSet.getDouble("total"));
                return appointment;
            }
            

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        
        return appointment;
    }

     public int updateQuery(Appointment appointment) {
        int result = 0;
        try {
            connection = DBConnection.getDBConnection();
            String query = "update Appointment set Date = ?,Time = ?,employee = ?,custID = ?,services = ?,packages = ?,total=?,custName = ?,custAddress=?,TeleNum=? where AppointID = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, appointment.getDate());
            preparedStatement.setString(2, appointment.getTime());
            preparedStatement.setString(3, appointment.getEmployeeName());
            preparedStatement.setInt(4, appointment.getCustomerID());
            preparedStatement.setString(5, appointment.getService());
            preparedStatement.setString(6, appointment.getPackages());
            preparedStatement.setDouble(7, appointment.getTotal());
            preparedStatement.setString(8, appointment.getCustomerName());
            preparedStatement.setString(9, appointment.getCustomerAddress());
            preparedStatement.setString(10, appointment.getTelephone());
            preparedStatement.setInt(11, appointment.getAppointmentId());

            result = preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        return result;
    }
     
     public int delete(int id){
         int result = 0;
         try{
             connection = DBConnection.getDBConnection();
             String query = "delete from Appointment where AppointID = ?";
             preparedStatement = connection.prepareStatement(query);
             preparedStatement.setInt(1, id);
             result = preparedStatement.executeUpdate();
             return result;
         } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        return result;
     }
     
        public int getAppointmentNumber() {
        int max = 0;
        String query = "select max(AppointID) as curAppID from Appointment";
        try {
            connection = DBConnection.getDBConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                max = resultSet.getInt("curAppID");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
                if (!connection.isClosed()) {
                    connection.close();
                }

                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        return max + 1;
    }
      
}
        
        
                
    
