/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jxl.write.WritableImage;

/**
 * FXML Controller class
 *
 * @author Shalika Ashan
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    final String title = "Kushi Bridal and Beauty Salon";

    @FXML
    public void addPayment(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/createInvoice.fxml"));
            Stage stage = new Stage();
            //    stage.setAlwaysOnTop(true);
            Scene scene = new Scene(root);
            //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
            //stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
            //stage.initStyle(StageStyle.UNDECORATED);//hide all button
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setMaximized(true);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

            //handle close button(X)
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/Menu.fxml"));
                                Stage stage = new Stage();
                                //    stage.setAlwaysOnTop(true);
                                Scene scene = new Scene(root);
                                //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
                                //stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
                                //stage.initStyle(StageStyle.UNDECORATED);//hide all button
                                stage.setScene(scene);
                                stage.setTitle(title);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                    );

                }
            }
            );
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void viewInvoice(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/viewInvoices.fxml"));
            Stage stage = new Stage();
            //    stage.setAlwaysOnTop(true);
            Scene scene = new Scene(root);
            //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
            //stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
            //stage.initStyle(StageStyle.UNDECORATED);//hide all button
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

            
            //handle close button(X)
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/Menu.fxml"));
                                Stage stage = new Stage();
                                //    stage.setAlwaysOnTop(true);
                                Scene scene = new Scene(root);
                                //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
                                //stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
                                //stage.initStyle(StageStyle.UNDECORATED);//hide all button
                                stage.setScene(scene);
                                stage.setTitle(title);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                    );

                }
            }
            );

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private Button btn;

    @FXML
    public void popupAppointment(ActionEvent event) {
        try{
            String host ="smtp.gmail.com" ;
            String user = "noreplykushisalon@gmail.com";
            String pass = "JOa0eO4Gtqn0";
            String to = "shalikaashan01@gmail.com";
            String from = "noreply@kushisalon";
            String subject = "Your Payment has Succeessfully paid";
            String messageText = 
                    "<strong>Invoice Number: 123</strong><br />"
                    + "Appintment Date: 2017/02/15 <br />"
                    + "Appointment Time: 08:18 <br />"
                    + "Payment Date: 2017/02/15 <br />"
                    + "Payment Time: 08:18 <br />"
                    + "<strong>Total Amount: 123</strong>"
                    ;
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setContent(messageText,"text/html; charset=utf-8");

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
           System.out.println("message send successfully");
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
    @FXML
    private StackPane spane;

}