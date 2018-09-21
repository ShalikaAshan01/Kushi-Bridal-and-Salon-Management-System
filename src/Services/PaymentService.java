/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Utils.CommonUtils;
import Utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import model.Appointment;
import model.Customer;
import model.PaymentModel;
import model.ServiceModel;

/**
 *
 * @author Shalika Ashan
 */
public class PaymentService implements PaymentServiceInterface {

    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    /**
     * insert payment into database
     *
     * @param payment
     * @return
     */
    @Override
    public int addPayment(PaymentModel payment) {
        int affectedRow = 0;
        String query = "INSERT INTO "
                + CommonUtils.TABLE_PAYMENT
                + "(" + CommonUtils.COLUMN_CUSTOMERID + ","
                + CommonUtils.COLUMN_APPOINTMENTID + ","
                + CommonUtils.COLUMN_PAYMENTDATE + ","
                + CommonUtils.COLUMN_PAYMENTTIME + ","
                + CommonUtils.COLUMN_TOTAL + ")"
                + "VALUES (?,?,?,?,?)";

        try {
            connection = DBConnection.getDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, payment.getCid());
            preparedStatement.setInt(2, payment.getAid());
            preparedStatement.setString(3, payment.getPayDate());
            preparedStatement.setString(4, payment.getPayTime());
            preparedStatement.setDouble(5, payment.getTotal());
            affectedRow = preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }

        return affectedRow;
    }

    /**
     * get all appointment from database
     *
     * @return
     */
    @Override
    public ArrayList<Appointment> getappointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            connection = DBConnection.getDBConnection();
            String query = "SELECT * FROM "
                    + CommonUtils.TABLE_APPOINTMENT;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAid(resultSet.getInt(CommonUtils.COLUMN_APPOINTMENTID));
                appointment.setaDate(resultSet.getString(CommonUtils.COLUMN_APPOINTMENTDATE));
                appointment.setaTime(resultSet.getString(CommonUtils.COLUMN_APPOINTMENTTIME));
                appointment.setCid(resultSet.getInt(CommonUtils.COLUMN_CUSTOMERID));
                appointment.setApackage(resultSet.getInt(CommonUtils.COLUMN_APPOINTMENTPACKAGE));
                appointments.add(appointment);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (connection != null) {
                    connection.close();
                }

                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        return appointments;

    }

    /**
     * get appointment info
     *
     * @param id
     * @return
     */
    @Override
    public Appointment getAppoimentByID(int id) {
        Appointment appointment = new Appointment();
        try {
            connection = DBConnection.getDBConnection();
            String query = "SELECT * FROM "
                    + CommonUtils.TABLE_APPOINTMENT + " a,"
                    + CommonUtils.TABLE_PACKAGE + " p "
                    + "where a." + CommonUtils.COLUMN_APPOINTMENTID + "=? AND"
                    + " p." + CommonUtils.COLUMN_PACKAGEID
                    + "=a." + CommonUtils.COLUMN_APPOINTMENTPACKAGE;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                appointment.setAid(resultSet.getInt(CommonUtils.COLUMN_APPOINTMENTID));
                appointment.setaDate(resultSet.getString(CommonUtils.COLUMN_APPOINTMENTDATE));
                appointment.setaTime(resultSet.getString(CommonUtils.COLUMN_APPOINTMENTTIME));
                appointment.setCid(resultSet.getInt(CommonUtils.COLUMN_CUSTOMERID));
                appointment.setApackage(resultSet.getInt(CommonUtils.COLUMN_APPOINTMENTPACKAGE));
                appointment.setPackageName(resultSet.getString(CommonUtils.COLUMN_PACKAGENAME));
                appointment.total = resultSet.getInt(CommonUtils.COLUMN_PRODUCTPRICE);
                return appointment;
            } else {
                return null;
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
        return appointment;
    }

    /**
     * get customer info
     *
     * @param nic
     * @return
     */
    @Override
    public Customer getCustomerInfoByNIC(int nic) {
        Customer customer = new Customer();
        try {
            connection = DBConnection.getDBConnection();
            String query = "Select * from "
                    + CommonUtils.TABLE_CUSTOMER + " c,"
                    + CommonUtils.TABLE_CusPhoneNo + " cp "
                    + "where "
                    + "c." + CommonUtils.COLUMN_CUSTOMERNIC
                    + " =? AND cp."
                    + CommonUtils.COLUMN_CUSTOMERNIC
                    + " =c."
                    + CommonUtils.COLUMN_CUSTOMERNIC;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, nic);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                customer.setFname(resultSet.getString(CommonUtils.COLUMN_CUSTOMERFNAME));
                customer.setLname(resultSet.getString(CommonUtils.COLUMN_CUSTOMERLNAME));
                customer.setAddress(resultSet.getString(CommonUtils.COLUMN_CUSTOMERADDRESS));
                customer.setNic(resultSet.getString(CommonUtils.COLUMN_CUSTOMERNIC));
                customer.setPhone(resultSet.getString(CommonUtils.COLUMN_CUSTOMERPHONENUMBER));
                return customer;
            } else {
                return null;
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
        return customer;
    }

    /**
     * get appointment's services
     *
     * @param id
     * @return
     */
    @Override
    public ArrayList<ServiceModel> getAppointmentServiceByAppointmentID(int id) {
        ArrayList<ServiceModel> serviceList = new ArrayList<>();
        try {
            connection = DBConnection.getDBConnection();
            String query = "select s."
                    + CommonUtils.COLUMN_SERVICENAME
                    + ","
                    + "s."
                    + CommonUtils.COLUMN_SERVICEPRICE
                    + " from "
                    + CommonUtils.TABLE_SERVICE
                    + " s, "
                    + CommonUtils.TABLE_APPOINTMENTSERVICE
                    + " a where a."
                    + CommonUtils.COLUMN_APPOINTMENTID
                    + " = ? and a."
                    + CommonUtils.COLUMN_SERVICEID
                    + "=s."
                    + CommonUtils.COLUMN_SERVICEID;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ServiceModel services = new ServiceModel();
                services.setsName(resultSet.getString(CommonUtils.COLUMN_SERVICENAME));
                services.setsPrice(resultSet.getDouble(CommonUtils.COLUMN_SERVICEPRICE));
                serviceList.add(services);
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
        return serviceList;
    }

    /**
     * get next invoice number
     *
     * @return
     */
    @Override
    public int getInvoiceNumber() {
        int max = 0;
        String query = "select max("
                + CommonUtils.COLUMN_PAYMENTID
                + ") as curPayID from "
                + CommonUtils.TABLE_PAYMENT;
        try {
            connection = DBConnection.getDBConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                max = resultSet.getInt("curPayID");
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

    /**
     * get all payment info
     *
     * @return
     */
    @Override
    public ArrayList<PaymentModel> getPaymentInfo() {
        ArrayList<PaymentModel> paymentlist = new ArrayList<>();
        String query = "select "
                + CommonUtils.COLUMN_PAYMENTID + ","
                + CommonUtils.COLUMN_CUSTOMERFNAME + ","
                + CommonUtils.COLUMN_CUSTOMERLNAME + ","
                + CommonUtils.COLUMN_APPOINTMENTID + ","
                + CommonUtils.COLUMN_PAYMENTDATE + ","
                + CommonUtils.COLUMN_PAYMENTTIME + ","
                + CommonUtils.COLUMN_APPOINTMENTTOTAL
                + " from "
                + CommonUtils.TABLE_PAYMENT
                + " p,"
                + CommonUtils.TABLE_CUSTOMER
                + " c where p."
                + CommonUtils.COLUMN_CUSTOMERID
                + "= c."
                + CommonUtils.COLUMN_CUSTOMERNIC
                + " order by "
                + CommonUtils.COLUMN_PAYMENTID
                + " desc";
        try {
            connection = DBConnection.getDBConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PaymentModel payment = new PaymentModel();
                payment.setPayid(resultSet.getInt(CommonUtils.COLUMN_PAYMENTID));
                payment.setFname(resultSet.getString(CommonUtils.COLUMN_CUSTOMERFNAME));
                payment.setLname(resultSet.getString(CommonUtils.COLUMN_CUSTOMERLNAME));
                payment.setAid(resultSet.getInt(CommonUtils.COLUMN_APPOINTMENTID));
                payment.setPayDate(resultSet.getString(CommonUtils.COLUMN_PAYMENTDATE));
                payment.setPayTime(resultSet.getString(CommonUtils.COLUMN_PAYMENTTIME));
                payment.setTotal(resultSet.getDouble(CommonUtils.COLUMN_APPOINTMENTTOTAL));
                paymentlist.add(payment);
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
        return paymentlist;
    }

    /**
     * get payment info by payid
     *
     * @param id
     * @return
     */
    @Override
    public PaymentModel getPaymentInfoByID(int id) {
        PaymentModel payment = new PaymentModel();
//        String query = "select payid,fname,lname,aid,payDate,payTime,total "
//                + "from payment p,Customer c where payid=? and p.cid= c.nic";

        String query = "select "
                + CommonUtils.COLUMN_PAYMENTID + ","
                + CommonUtils.COLUMN_CUSTOMERFNAME + ","
                + CommonUtils.COLUMN_CUSTOMERLNAME + ","
                + CommonUtils.COLUMN_APPOINTMENTID + ","
                + CommonUtils.COLUMN_PAYMENTDATE + ","
                + CommonUtils.COLUMN_PAYMENTTIME + ","
                + CommonUtils.COLUMN_APPOINTMENTTOTAL
                + " from "
                + CommonUtils.TABLE_PAYMENT
                + " p,"
                + CommonUtils.TABLE_CUSTOMER
                + " c where p."
                + CommonUtils.COLUMN_CUSTOMERID
                + "= c."
                + CommonUtils.COLUMN_CUSTOMERNIC
                + " and p."
                + CommonUtils.COLUMN_CUSTOMERID + " = ?";
        try {
            connection = DBConnection.getDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                payment.setPayid(resultSet.getInt(CommonUtils.COLUMN_PAYMENTID));
                payment.setFname(resultSet.getString(CommonUtils.COLUMN_CUSTOMERFNAME));
                payment.setLname(resultSet.getString(CommonUtils.COLUMN_CUSTOMERLNAME));
                payment.setAid(resultSet.getInt(CommonUtils.COLUMN_APPOINTMENTID));
                payment.setPayDate(resultSet.getString(CommonUtils.COLUMN_PAYMENTDATE));
                payment.setPayTime(resultSet.getString(CommonUtils.COLUMN_PAYMENTTIME));
                payment.setTotal(resultSet.getDouble(CommonUtils.COLUMN_APPOINTMENTTOTAL));
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
        return payment;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public PaymentModel getPaymentInfoByAppointmentID(int id) {
        PaymentModel payment = new PaymentModel();
//        String query = "select aid,payDate,payTime,total,payid"
//                + " from payment where aid=?";

        String query = "select "
                + CommonUtils.COLUMN_PAYMENTID + ","
                + CommonUtils.COLUMN_APPOINTMENTID + ","
                + CommonUtils.COLUMN_PAYMENTDATE + ","
                + CommonUtils.COLUMN_PAYMENTTIME + ","
                + CommonUtils.COLUMN_APPOINTMENTTOTAL
                + " from "
                + CommonUtils.TABLE_PAYMENT
                + " where " + CommonUtils.COLUMN_APPOINTMENTID + " = ?";
        try {
            connection = DBConnection.getDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            String date = null, time = null;
            double total = 0;
            int payid = 0;
            while (resultSet.next()) {
                date = resultSet.getString(CommonUtils.COLUMN_PAYMENTDATE);
                time = resultSet.getString(CommonUtils.COLUMN_PAYMENTTIME);
                total += resultSet.getDouble(CommonUtils.COLUMN_APPOINTMENTTOTAL);
                payid = resultSet.getInt(CommonUtils.COLUMN_PAYMENTID);
            }
            payment.setPayid(payid);
            payment.setPayDate(date);
            payment.setPayTime(time);
            payment.setTotal(total);
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
        return payment;
    }

    /**
     * save installment payment in the database
     *
     * @param aid
     * @param remaining
     * @return
     */
    @Override
    public int savePaymentWithInstallment(int aid, double remaining) {
        int result = 0;
        try {
            connection = DBConnection.getDBConnection();
            String query = "insert into "
                    + CommonUtils.TABLE_PAYMENTINSTALLMENT
                    + " values (?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, aid);
            preparedStatement.setDouble(2, remaining);
            result = preparedStatement.executeUpdate();
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
        return result;
    }

    /**
     * update installment payment table
     *
     * @param aid
     * @param remaining
     * @return
     */
    @Override
    public int updatePaymentWithInstallment(int aid, double remaining) {
        int result = 0;
        try {
            connection = DBConnection.getDBConnection();
            String query = "update "
                    + CommonUtils.TABLE_PAYMENTINSTALLMENT
                    + " set "
                    + CommonUtils.COLUMN_REMAINGPAYMENT
                    + "= ? where "
                    + CommonUtils.COLUMN_APPOINTMENTID
                    + "=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, remaining);
            preparedStatement.setInt(2, aid);
            result = preparedStatement.executeUpdate();
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
        return result;
    }

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
    @Override
    public ArrayList<PaymentModel> getPaymentInfoListByAppointmentID(int id) {
        ArrayList<PaymentModel> list = new ArrayList<>();
//        String query = "select aid,payDate,payTime,total,payid"
//                + " from payment where aid=?";

        String query = "select "
                + CommonUtils.COLUMN_PAYMENTID + ","
                + CommonUtils.COLUMN_APPOINTMENTID + ","
                + CommonUtils.COLUMN_PAYMENTDATE + ","
                + CommonUtils.COLUMN_PAYMENTTIME + ","
                + CommonUtils.COLUMN_APPOINTMENTTOTAL
                + " from "
                + CommonUtils.TABLE_PAYMENT
                + " where " + CommonUtils.COLUMN_APPOINTMENTID + " = ?";
        try {
            connection = DBConnection.getDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PaymentModel payment = new PaymentModel();
                payment.setPayDate(resultSet.getString(CommonUtils.COLUMN_PAYMENTDATE));
                payment.setPayTime(resultSet.getString(CommonUtils.COLUMN_PAYMENTTIME));
                payment.setTotal(resultSet.getDouble(CommonUtils.COLUMN_APPOINTMENTTOTAL));
                payment.setPayid(resultSet.getInt(CommonUtils.COLUMN_PAYMENTID));
                list.add(payment);
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
        return list;
    }

    @Override
    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        Connection nconnection = null;
        try {
            String query = "select a."
                    + CommonUtils.COLUMN_APPOINTMENTID
                    + ",c."
                    + CommonUtils.COLUMN_CUSTOMERFNAME
                    + ",c."
                    + CommonUtils.COLUMN_CUSTOMERLNAME
                    + ",p."
                    + CommonUtils.COLUMN_PACKAGENAME
                    + ",a."
                    + CommonUtils.COLUMN_APPOINTMENTDATE
                    + ",a."
                    + CommonUtils.COLUMN_APPOINTMENTTIME
                    + " from "
                    + CommonUtils.TABLE_APPOINTMENT
                    + " a,"
                    + CommonUtils.TABLE_CUSTOMER
                    + " c,"
                    + CommonUtils.TABLE_PACKAGE
                    + " p "
                    + "where c."
                    + CommonUtils.COLUMN_CUSTOMERNIC
                    + " = a."
                    + CommonUtils.COLUMN_CUSTOMERID
                    + " and p."
                    + CommonUtils.COLUMN_PACKAGEID
                    + "=a."
                    + CommonUtils.COLUMN_APPOINTMENTPACKAGE
                    + " order by a."
                    + CommonUtils.COLUMN_APPOINTMENTID
                    + " desc";

            nconnection = DBConnection.getDBConnection();
            preparedStatement = nconnection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAid(resultSet.getInt(CommonUtils.COLUMN_APPOINTMENTID));
                appointment.setFname(resultSet.getString(CommonUtils.COLUMN_CUSTOMERFNAME));
                appointment.setLname(resultSet.getString(CommonUtils.COLUMN_CUSTOMERLNAME));
                appointment.setPackageName(resultSet.getString(CommonUtils.COLUMN_PACKAGENAME));
                appointment.setaDate(resultSet.getString(CommonUtils.COLUMN_APPOINTMENTDATE));
                appointment.setaTime(resultSet.getString(CommonUtils.COLUMN_APPOINTMENTTIME));
                appointmentList.add(appointment);

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
                if (!nconnection.isClosed()) {
                    nconnection.close();
                }

                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        return appointmentList;
    }

    @Override
    public String getCustomerEmail(String nic) {
        String email = null;
        try {
            connection = DBConnection.getDBConnection();
            String query = "select "
                    + CommonUtils.COLUMN_CUSTOMEREMAIL
                    + " from "
                    + CommonUtils.TABLE_CUSTOMER
                    + " where "
                    + CommonUtils.COLUMN_CUSTOMERNIC
                    + " = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nic);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString(CommonUtils.COLUMN_CUSTOMEREMAIL);
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
        return email;
    }

    @Override
    public int deletePaymentByID(int payid) {
        int result = 0;
        try {
            connection = DBConnection.getDBConnection();
            String query = "delete "
                    + " from "
                    + CommonUtils.TABLE_PAYMENT
                    + " where "
                    + CommonUtils.COLUMN_PAYMENTID
                    + " = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, payid);
            return preparedStatement.executeUpdate();
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
        return result;    
    }

}
