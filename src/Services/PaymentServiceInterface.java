/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.ArrayList;
import java.util.logging.Logger;
import model.Appointment;
import model.Customer;
import model.PaymentModel;
import model.ServiceModel;

/**
 *
 * @author Shalika Ashan
 */
public interface PaymentServiceInterface {

    Logger log = Logger.getLogger(PaymentService.class.getName());

    /**
     * insert payment into database
     *
     * @param payment
     * @return
     */
    int addPayment(PaymentModel payment);

    ArrayList<Appointment> getAllAppointments();

    /**
     * get appointment info
     *
     * @param id
     * @return
     */
    Appointment getAppoimentByID(int id);

    /**
     * get appointment's services
     *
     * @param id
     * @return
     */
    /**
     * get customer info
     *
     * @param nic
     * @return
     */
    Customer getCustomerInfoByCID(int nic);

    /**
     * get next invoice number
     *
     * @return
     */
    int getInvoiceNumber();

    /**
     * get all payment info
     *
     * @return
     */
    ArrayList<PaymentModel> getPaymentInfo();

    /**
     *
     * @param id
     * @return
     */
    PaymentModel getPaymentInfoByAppointmentID(int id);

    /**
     * get payment info by payid
     *
     * @param id
     * @return
     */
    PaymentModel getPaymentInfoByID(int id);

    /**
     *
     * @param aid
     * @return
     */
    //    public int selectPaymentInstallmentbyAppointmentID(int aid) {
    //        int result = 0;
    //        try {
    //            connection = DBConnection.getDBConnection();
    //            String query = "insert into payment_installment values (?,?)";
    //            preparedStatement = connection.prepareStatement(query);
    //            preparedStatement.setInt(1, aid);
    //            result = preparedStatement.executeUpdate();
    //        } catch (SQLException | ClassNotFoundException e) {
    //            e.printStackTrace();
    //        } finally {
    //            try {
    //                if (!preparedStatement.isClosed()) {
    //                    preparedStatement.close();
    //                }
    //                if (!connection.isClosed()) {
    //                    connection.close();
    //                }
    //
    //                if (!resultSet.isClosed()) {
    //                    resultSet.close();
    //                }
    //            } catch (SQLException e) {
    //                log.log(Level.SEVERE, e.getMessage());
    //            }
    //        }
    //        return result;
    //    }
    /**
     * get payment information list
     *
     * @param id
     * @return
     */
    ArrayList<PaymentModel> getPaymentInfoListByAppointmentID(int id);

    /**
     * get all appointment from database
     *
     * @return
     */
    ArrayList<Appointment> getappointments();

    /**
     * save installment payment in the database
     *
     * @param aid
     * @param remaining
     * @return
     */
    int savePaymentWithInstallment(int aid, double remaining);

    /**
     * update installment payment table
     *
     * @param aid
     * @param remaining
     * @return
     */
    int updatePaymentWithInstallment(int aid, double remaining);
    public String getCustomerEmail(String nic);
    public int deletePaymentByID(int payid);
}
