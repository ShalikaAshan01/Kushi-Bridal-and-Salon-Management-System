/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Services.PaymentService;
import Services.PaymentServiceInterface;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Appointment;
import model.AppointmentforTable;
import model.Customer;
import model.PaymentModel;
import model.ServiceModel;

/**
 *
 * @author Shalika Ashan
 */
public class createInvoiceController implements Initializable {

    PaymentServiceInterface paymentService = new PaymentService();
    PaymentModel payment = new PaymentModel();
    @FXML
    private JFXComboBox cbPayment;
    @FXML
    private Label invoiceNo;

    @FXML
    private Label grossTotal;

    @FXML
    private Label netAmount;

    @FXML
    private Label change;

    @FXML
    private JFXTextField cash;

    @FXML
    private Button resetButton;

    @FXML
    private Button saveButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTreeTableView<AppointmentforTable> tableAppointment;

    @FXML
    private TreeTableColumn columnAID;

    @FXML
    private TreeTableColumn columnname;

    @FXML
    private TreeTableColumn columnPackage;

    @FXML
    private TreeTableColumn columnDate;

    @FXML
    private TreeTableColumn columnTime;

    @FXML
    private Label customerName;

    @FXML
    private Label customerAddress;

    @FXML
    private Label customerPhone;

    @FXML
    private JFXRadioButton without;

    @FXML
    private ToggleGroup installment;

    @FXML
    private Label remPay;

    @FXML
    private ListView listService;

    @FXML
    private JFXRadioButton with;
    @FXML
    private Button btnView;
    @FXML
    private JFXTextField txtSearch;

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
    private Button btnViewPay;

    @FXML
    private Button btnHomeUI;

    private int nic = 0;
    private double appointmentTotal = 0;
    private double total = 0;
    private boolean valid = true;
    private final String txtcolor = "yellow";
    public static int appointmentNumber = 0;
    public static int curInvoiceNo = 0;
    public static final String TITLE = "Kushi Bridal and Beauty Salon";

    public int getInvoiceNo() {
        return Integer.parseInt(invoiceNo.getText().substring(11));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cash.setStyle("-fx-text-inner-color: " + txtcolor + ";");
        setInvoiceNumber();
        setAppointmentTable();
        cleanText();
        //set combobox values
        cbPayment.setStyle("-fx-text-fill: white;");
        cbPayment.setStyle("-fx-background-color:  WHITE;");

        ObservableList<String> value = FXCollections.observableArrayList(
                "All Appointment", "Payment Incompleted", "Payments Completed"
        );
        cbPayment.setItems(value);

        resetButton.setTooltip(new Tooltip("Reset all fields"));
        saveButton.setTooltip(new Tooltip("Add payment into Database"));
        cbPayment.setTooltip(new Tooltip("Filter table by categories"));
        txtSearch.setTooltip(new Tooltip("Filter table"));
        cash.setTooltip(new Tooltip("Enter cash amount"));
        with.setTooltip(new Tooltip("Select Installment plan"));
        without.setTooltip(new Tooltip("Select Installment plan"));
        btnView.setTooltip(new Tooltip("View Installments"));
        listService.setTooltip(new Tooltip("Customer's Services"));
        tableAppointment.setTooltip(new Tooltip("Appointments"));
        btnViewPay.setTooltip(new Tooltip("View Payments"));
        btnView.setCursor(Cursor.HAND);
        setNavigationbarToolTip();
    }

    /**
     * insert payment into database
     *
     * @param event
     */
    @FXML
    public void addPayment(ActionEvent event) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
//        PaymentService paymentService = new PaymentService();
//        PaymentModel payment = new PaymentModel();
//        payment.setAid(Integer.parseInt(appointmentNo.getText()));
        payment.setCid(nic);
        payment.setPayDate(dateFormat.format(date));
        payment.setPayTime(timeFormat.format(date));
        payment.setAid(appointmentNumber);
        payment.setTotal(total);
        if (nic > 0) {
            int result = paymentService.addPayment(payment);
            int result1 = 1;
            if (with.isSelected()) {
                if (with.isDisable()) {
                    result1 = paymentService.updatePaymentWithInstallment(payment.getAid(), 0);
                } else {
                    result1 = paymentService.savePaymentWithInstallment(payment.getAid(), total);
                }

            }
            if (result > 0 && result1 > 0) {
                int temp = nic;
                setInvoiceNumber();
                cleanText();
//send email

                try {
                    String host = "smtp.gmail.com";
                    String user = "noreplykushisalon@gmail.com";
                    String pass = "JOa0eO4Gtqn0";
                    System.out.println("nic: " + temp);
                    String to = paymentService.getCustomerEmail(String.valueOf(temp));
                    String from = "noreply@kushisalon";
                    String subject = "Your Payment has Succeessfully paid";
                    String messageText
                            = "<h2>Kushi Bridal and Beauty Salon</h2><br/>"
                            + "Hi,"
                            + tableAppointment.getSelectionModel().getSelectedItem().getValue().getFname()
                            + "<br/>Your Payment has Successfully paid<br/>"
                            + "<strong>Invoice Number: "
                            + curInvoiceNo
                            + "</strong><br />"
                            + " <br />"
                            + "Appointment Date: "
                            + tableAppointment.getSelectionModel().getSelectedItem().getValue().getaDate()
                            + "<br/>"
                            + "Appointment Time: "
                            + tableAppointment.getSelectionModel().getSelectedItem().getValue().getaTime()
                            + "<br/>"
                            + "Payment Date: "
                            + payment.getPayDate()
                            + " <br />"
                            + "Payment Time: "
                            + payment.getPayTime()
                            + "<br />"
                            + "<strong>Total Amount: "
                            + payment.getTotal()
                            + "</strong>";
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

                    // Create a default MimeMessage object.
                    Message msg = new MimeMessage(mailSession);
                    // Set From: header field of the header.
                    msg.setFrom(new InternetAddress(from));
                    InternetAddress[] address = {new InternetAddress(to)};
                    // Set To: header field of the header.
                    msg.setRecipients(Message.RecipientType.TO, address);
                    // Set Subject: header field
                    msg.setSubject(subject);
                    msg.setSentDate(new Date());
                    // set the actual message
                    msg.setContent(messageText, "text/html; charset=utf-8");

                    Transport transport = mailSession.getTransport("smtp");
                    transport.connect(host, user, pass);
                    // Send message
                    transport.sendMessage(msg, msg.getAllRecipients());
                    transport.close();
                    showSucessMessage("Kushi Bridal and Salon", "Sucessfully added", "Payment was added into the datadabe");
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Cannot send email");
                    alert.setHeaderText("");
                    alert.setContentText("Email not sended");
                    alert.showAndWait();
                    System.out.println(ex);

                }
                changeTable(event);
            } else {
                ButtonType retry = new ButtonType("Retry", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.WARNING, "", retry, cancel);
                alert.setTitle("Error");
                alert.setHeaderText("Something was going wrong");
                alert.setContentText("Cannot added into database" + getInvoiceNo());
                Optional<ButtonType> res = alert.showAndWait();
                if (res.orElse(cancel) == retry) {
                    addPayment(event);
                }
            }

        } else {
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setHeading(new Text("Appointment number is null"));
            layout.setBody(new Text("Please enter appointmnent number"));
            Button button = new Button("Okay");
            button.setCursor(Cursor.HAND);
            button.setStyle("-fx-background-color: green;");
            button.setStyle("-fx-background-color: #40c4ff;");
            JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    dialog.close();
                }
            });
            layout.setActions(button);
            dialog.show();
        }

    }

    public void showSucessMessage(String title, String headerText, String content) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
//        alert.setTitle(title);
//        alert.setHeaderText(headerText);
//        alert.setContentText(content);
//        alert.showAndWait();
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text(headerText));
        layout.setBody(new Text(content));
        Button button = new Button("Okay");
        button.setCursor(Cursor.HAND);
        button.setStyle("-fx-background-color: green;");
        button.setStyle("-fx-background-color: #40c4ff;");
        JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                dialog.close();
                askForPopUp();
            }
        });
        layout.setActions(button);
        dialog.show();

    }

    public ArrayList<Appointment> getIncompletedAppointment() {
        PaymentServiceInterface paymentService = new PaymentService();
        ArrayList<Appointment> list = paymentService.getAllAppointments();
        ArrayList<Appointment> totable = new ArrayList<>();
        for (Appointment temp : list) {
            double tempTotal = 0;
            Appointment AppointmentTemp = paymentService.getAppoimentByID(temp.getAid());
            ArrayList<ServiceModel> servicesWithPrice = new ArrayList<>();
            servicesWithPrice = paymentService.getAppointmentServiceByAppointmentID(temp.getAid());
            tempTotal = AppointmentTemp.total;
            for (ServiceModel tempService : servicesWithPrice) {
                tempTotal += tempService.getsPrice();
            }
            PaymentModel payInfo = paymentService.getPaymentInfoByAppointmentID(temp.getAid());
            if (payInfo.getTotal() >= tempTotal) {
                continue;
            }

            totable.add(temp);
        }
        return totable;
    }

    public ArrayList<Appointment> getCompletedAppointment() {
        PaymentServiceInterface paymentService = new PaymentService();
        ArrayList<Appointment> list = paymentService.getAllAppointments();
        ArrayList<Appointment> totable = new ArrayList<>();
        for (Appointment temp : list) {
            double tempTotal = 0;
            Appointment AppointmentTemp = paymentService.getAppoimentByID(temp.getAid());
            ArrayList<ServiceModel> servicesWithPrice = new ArrayList<>();
            servicesWithPrice = paymentService.getAppointmentServiceByAppointmentID(temp.getAid());
            tempTotal = AppointmentTemp.total;
            for (ServiceModel tempService : servicesWithPrice) {
                tempTotal += tempService.getsPrice();
            }
            PaymentModel payInfo = paymentService.getPaymentInfoByAppointmentID(temp.getAid());
            if (payInfo.getTotal() >= tempTotal) {
                totable.add(temp);
            }
        }
        return totable;
    }

    /**
     * search appointment details
     *
     *
     */
    public void getAppointment() {
        PaymentServiceInterface paymentService = new PaymentService();
        Appointment appointment = new Appointment();
        appointment = paymentService.getAppoimentByID(appointmentNumber);
        if (appointment != null) {
            cleanText();

            //set text values, based on appointment details
            //get custometr details
            Customer customer = paymentService.getCustomerInfoByNIC(appointment.getCid());
            ArrayList<ServiceModel> servicesWithPrice = new ArrayList<>();
            servicesWithPrice = paymentService.getAppointmentServiceByAppointmentID(appointment.getAid());
            customerName.setText(customer.getFname() + " " + customer.getLname());
            customerPhone.setText(customer.getPhone());
            customerAddress.setText(customer.getAddress());
            nic = Integer.parseInt(customer.getNic());
            appointmentNumber = appointment.getAid();
            total = appointment.total;
            ArrayList<String> services = new ArrayList<>();/*for list view*/
            for (ServiceModel tempService : servicesWithPrice) {
                total += tempService.getsPrice();
                services.add(tempService.getsName());
            }
            listService.getItems().addAll(services);
            appointmentTotal = total;
            grossTotal.setText(String.valueOf(total));
            netAmount.setText(String.valueOf(total));

            /*Set save button disabled if payment complete*/
            PaymentModel payInfo = paymentService.getPaymentInfoByAppointmentID(appointmentNumber);

            if (payInfo != null) {
                if (payInfo.getTotal() >= total) {
                    saveButton.setDisable(true);
                    valid = false;
                    total = appointmentTotal - payInfo.getTotal();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("All payment has completed for given appointment");
                    alert.showAndWait();
                    ArrayList<PaymentModel> installmentList = paymentService.getPaymentInfoListByAppointmentID(appointmentNumber);

                    if (installmentList.size() > 1) {

                        without.setDisable(true);
                        with.setSelected(true);
                        with.setDisable(true);
                        btnView.setVisible(true);
//                        tableInstallment.setVisible(true);

                        //set button on click action
                        btnView.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                showPopUpTable();
                            }
                        });

                        total = appointmentTotal - payInfo.getTotal();
                        remPay.setText("Remaining Payment: " + total);
                        remPay.setVisible(true);
                    }
                    without.setDisable(true);
                    with.setDisable(true);
                } else if (payInfo.getPayid() != 0) {
                    without.setDisable(true);
                    with.setDisable(true);
                    with.setSelected(true);
                    //add values into table
                    btnView.setVisible(true);
                    //set button on click action
                    btnView.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent t) {
                            showPopUpTable();
                        }
                    });

                    total = appointmentTotal - payInfo.getTotal();
                    remPay.setText("Remaining Payment: " + total);
                    remPay.setVisible(true);
                }
                grossTotal.setText(String.valueOf(total));
                netAmount.setText(String.valueOf(total));
            } else {
                saveButton.setDisable(false);
            }
            if (appointment.getApackage() == 2) {
                with.setVisible(true);
                without.setVisible(true);
            }
        } else {
            cleanText();
            ButtonType retry = new ButtonType("Retry", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING, "", retry, cancel);
            alert.setTitle("Error");
            alert.setHeaderText("No appointment based on given id: " + appointmentNumber);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(cancel) == retry) {
                getAppointment();
            }

        }
    }

    public void cleanText() {
        btnView.setVisible(false);
        remPay.setVisible(false);
        remPay.setText("");
        without.setDisable(false);
        with.setDisable(false);
        valid = true;
        with.setVisible(false);
        without.setVisible(false);
        without.setSelected(true);
        saveButton.setDisable(true);
        nic = 0;
        appointmentTotal = 0;
        total = 0;
        netAmount.setText("");
        cash.setText("");
        change.setText("");
        listService.getItems().clear();
        listService.setPlaceholder(new Label("No Content In List"));
        customerName.setText("");
        customerPhone.setText("");
        customerAddress.setText("");
        grossTotal.setText("");
    }

    /**
     * set invoice number
     */
    public void setInvoiceNumber() {
        PaymentServiceInterface ps = new PaymentService();
        int id = ps.getInvoiceNumber();
        curInvoiceNo = id;
        invoiceNo.setText("Invoice No:" + id);
    }

    @FXML
    public void setChange(ActionEvent actionEvent) {
        double cash, netAmount;
        if (this.cash.getText() != null && !this.cash.getText().isEmpty()
                && this.netAmount.getText() != null && !this.netAmount.getText().isEmpty()) {
            netAmount = Double.parseDouble(this.netAmount.getText());
            try {
                cash = Double.parseDouble(this.cash.getText());
                if (cash < netAmount) {
                    saveButton.setDisable(true);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Net amount is greater than entired cash amount");
                    alert.setContentText("Please enter cash amount greater than " + netAmount);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(ButtonType.OK) == ButtonType.OK || result.orElse(ButtonType.CLOSE) == ButtonType.CLOSE) {
                        this.cash.setText("");
                    }
                } else {
                    if (!valid) {
                        saveButton.setDisable(true);
                    } else {
                        saveButton.setDisable(false);
                    }

                }
                change.setText(Double.toString(cash - netAmount));
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Enter valid amount");
                alert.setContentText("Amount cannot contain text");
                Optional<ButtonType> result = alert.showAndWait();
            }

        }
    }

    @FXML
    public void onClickinstallment(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Installment");
        alert.setHeaderText("Customer needs to pay 1st installment");
        alert.setContentText("Customer needs to pay 1st installment as " + total / 2);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(ButtonType.OK) == ButtonType.OK) {
            total = total / 2;
            grossTotal.setText(String.valueOf(total));
            netAmount.setText(String.valueOf(total));
        } else {
            without.setSelected(true);
        }

    }

    @FXML
    public void onClickWithoutInstallment(ActionEvent event) {
        total = total * 2;
        grossTotal.setText(String.valueOf(total));
        netAmount.setText(String.valueOf(total));
    }

    @FXML
    public void reset(ActionEvent event) {
        cleanText();
        tableAppointment.getSelectionModel().clearSelection();
        appointmentNumber = 0;
    }

    ObservableList<AppointmentforTable> observableListForTAppointment = FXCollections.observableArrayList();

    public void setAppointmentTable() {
        PaymentServiceInterface paymentService = new PaymentService();
        ArrayList<Appointment> temparrayList = paymentService.getAllAppointments();
        ArrayList<AppointmentforTable> arrayList = new ArrayList<>();

        for (Appointment tempAppointment : temparrayList) {
            AppointmentforTable appointmentforTable = new AppointmentforTable(tempAppointment);
            arrayList.add(appointmentforTable);
        }
        observableListForTAppointment.addAll(arrayList);
        columnAID.setCellValueFactory(new TreeItemPropertyValueFactory<AppointmentforTable, String>("aid"));
        //       columnFname.setCellValueFactory(new TreeItemPropertyValueFactory<AppointmentforTable, String>("fname"));
        //       columnLname.setCellValueFactory(new TreeItemPropertyValueFactory<AppointmentforTable, String>("lname"));
        columnPackage.setCellValueFactory(new TreeItemPropertyValueFactory<AppointmentforTable, String>("packageName"));
        columnDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AppointmentforTable, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AppointmentforTable, String> param) {
                return param.getValue().getValue().aDate;
            }
        });
        columnTime.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AppointmentforTable, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AppointmentforTable, String> param) {
                return param.getValue().getValue().aTime;
            }
        });
        columnname.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AppointmentforTable, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AppointmentforTable, String> param) {
                SimpleStringProperty value = new SimpleStringProperty((param.getValue().getValue().getFname() + " " + param.getValue().getValue().getLname()));
                return value;

            }
        });

        columnDate.setStyle("-fx-alignment: CENTER;");
        columnTime.setStyle("-fx-alignment: CENTER;");
        columnAID.setStyle("-fx-alignment: CENTER;");

        TreeItem<AppointmentforTable> root = new RecursiveTreeItem<>(observableListForTAppointment, RecursiveTreeObject::getChildren);
        tableAppointment.setRoot(root);
        tableAppointment.setShowRoot(false);

        //set mouse press event
        //in this method get selected row appointment id
        tableOnClick();

        //filter appointment table
        FilterAppointmentTable();
    }

    public void showPopUpTable() {

        try {

            AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/popUpInstallmentPayment.fxml"));
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            Scene scene = new Scene(root);
            //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
            stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
            //stage.initStyle(StageStyle.UNDECORATED);//hide all button
            stage.setScene(scene);
            stage.setTitle(TITLE);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnView.getScene().getWindow());
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void FilterAppointmentTable() {
        txtSearch.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String s, String t1) {
                tableAppointment.setPredicate(new Predicate<TreeItem<AppointmentforTable>>() {
                    @Override
                    public boolean test(TreeItem<AppointmentforTable> appointment) {
                        String fname = appointment.getValue().getFname().toLowerCase();
                        String lname = appointment.getValue().getLname().toLowerCase();
                        String name = fname + " " + lname;
                        Boolean found = appointment.getValue().getLname().toLowerCase().contains(t1.toLowerCase())
                                || name.contains(t1.toLowerCase())
                                || appointment.getValue().getAid().contains(t1)
                                || appointment.getValue().getaDate().contains(t1);
                        return found;
                    }
                });
            }
        });
    }

    public void tableOnClick() {
        //set mouse press event
        //in this method get selected row appointment id
        tableAppointment.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                appointmentNumber = Integer.parseInt(tableAppointment.selectionModelProperty().getValue().getSelectedItem().getValue().getAid());
                getAppointment();

            }

        });

        //set arrow key event
        tableAppointment.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    appointmentNumber = Integer.parseInt(tableAppointment.selectionModelProperty().getValue().getSelectedItem().getValue().getAid());
                    getAppointment();
                }
            }

        });

    }

    public void askForPopUp() {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yes, no);
        alert.setTitle("Kushi Bridal and Beauty Salon");
        alert.setContentText("Do you want to view Table.?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.orElse(ButtonType.OK) == yes) {
            try {
                AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/popUpInvoice.fxml"));
                Stage stage = new Stage();
                stage.setAlwaysOnTop(true);
                Scene scene = new Scene(root);
                //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
                //stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
                //stage.initStyle(StageStyle.UNDECORATED);//hide all button
                stage.setScene(scene);
                stage.setTitle("Kushi Bridal and Beauty Salon");
                stage.showAndWait();
            } catch (IOException e) {
                Logger.getLogger(createInvoiceController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    public void changeTable(ActionEvent event) {
        if (cbPayment.getSelectionModel().getSelectedIndex() == 0) {
            setAppointmentTable();
        }
        if (cbPayment.getSelectionModel().getSelectedIndex() == 1) {
            observableListForTAppointment.clear();
            ArrayList<Appointment> temp = getIncompletedAppointment();
            ArrayList<AppointmentforTable> tempTableList = new ArrayList<>();
            for (Appointment tempAppointment : temp) {
                AppointmentforTable appointmentforTable = new AppointmentforTable(tempAppointment);
                tempTableList.add(appointmentforTable);
            }
            observableListForTAppointment.addAll(tempTableList);
        }
        if (cbPayment.getSelectionModel().getSelectedIndex() == 2) {
            observableListForTAppointment.clear();
            ArrayList<Appointment> temp = getCompletedAppointment();
            ArrayList<AppointmentforTable> tempTableList = new ArrayList<>();
            for (Appointment tempAppointment : temp) {
                AppointmentforTable appointmentforTable = new AppointmentforTable(tempAppointment);
                tempTableList.add(appointmentforTable);
            }
            observableListForTAppointment.addAll(tempTableList);
        }
    }

    @FXML
    public void viewPaymentUI(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/viewInvoices.fxml"));
            Stage stage = new Stage();
            //    stage.setAlwaysOnTop(true);
            Scene scene = new Scene(root);
            //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
            //stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
            //stage.initStyle(StageStyle.UNDECORATED);//hide all button
            stage.setScene(scene);
            stage.setTitle(TITLE);
            stage.setMaximized(true);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

            //handle close button(X)
//            stage.setOnHiding(new EventHandler<WindowEvent>() {
//                @Override
//                public void handle(WindowEvent event) {
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("/views/Menu.fxml"));
//                                Stage stage = new Stage();
//                                //    stage.setAlwaysOnTop(true);
//                                Scene scene = new Scene(root);
//                                //        stage.resizableProperty().setValue(Boolean.FALSE);//disable maximize btn
//                                //stage.initStyle(StageStyle.UTILITY);//disable mini,max,e
//                                //stage.initStyle(StageStyle.UNDECORATED);//hide all button
//                                stage.setScene(scene);
//                                stage.setTitle(TITLE);
//                                stage.show();
//                            } catch (IOException ex) {
//                                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//
//                        }
//                    }
//                    );
//
//                }
//            }
//            );
//
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void viewAppointmentUIb(ActionEvent event) {

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
