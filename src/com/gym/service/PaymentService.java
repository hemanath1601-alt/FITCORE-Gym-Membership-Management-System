package com.gym.service;

import com.gym.database.DBConnection;
import com.gym.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    public boolean addPayment(Payment payment) {

        String sql = "INSERT INTO payments " +
                "(member_id, amount, payment_date, payment_method, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, payment.getMemberId());
            ps.setDouble(2, payment.getAmount());
            ps.setDate(3, payment.getPaymentDate());
            ps.setString(4, payment.getPaymentMethod());
            ps.setString(5, payment.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public List<Payment> getAllPayments() {

        List<Payment> payments = new ArrayList<>();

        String sql = "SELECT * FROM payments ORDER BY payment_id DESC";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Payment payment = new Payment();

                payment.setPaymentId(
                        rs.getInt("payment_id")
                );

                payment.setMemberId(
                        rs.getInt("member_id")
                );

                payment.setAmount(
                        rs.getDouble("amount")
                );

                payment.setPaymentDate(
                        rs.getDate("payment_date")
                );

                payment.setPaymentMethod(
                        rs.getString("payment_method")
                );

                payment.setStatus(
                        rs.getString("status")
                );

                payments.add(payment);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return payments;
    }
}