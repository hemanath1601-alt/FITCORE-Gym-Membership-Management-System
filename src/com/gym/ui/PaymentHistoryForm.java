
package com.gym.ui;

import com.gym.database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentHistoryForm extends JFrame {

    private JTable paymentTable;
    private DefaultTableModel tableModel;

    private JButton btnRefresh;
    private JButton btnClose;

    public PaymentHistoryForm() {

        setTitle("Payment History");

        setSize(850, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createForm();
    }

    // =========================
    // CREATE FORM
    // =========================

    private void createForm() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "PAYMENT HISTORY",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 15, 10
                )
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // =========================
        // TABLE
        // =========================

        String[] columns = {

                "Payment ID",
                "Member ID",
                "Amount",
                "Payment Date",
                "Payment Method",
                "Status"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        paymentTable =
                new JTable(tableModel);

        paymentTable.setRowHeight(25);

        paymentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(paymentTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                10
                        )
                );

        btnRefresh =
                new JButton("REFRESH");

        btnClose =
                new JButton("CLOSE");

        buttonPanel.add(btnRefresh);

        buttonPanel.add(btnClose);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // REFRESH BUTTON
        // =========================

        btnRefresh.addActionListener(e -> {

            loadPayments();

        });

        // =========================
        // CLOSE BUTTON
        // =========================

        btnClose.addActionListener(e -> {

            dispose();

        });

        add(mainPanel);

        // Load payments when window opens
        loadPayments();
    }

    // =========================
    // LOAD PAYMENTS
    // =========================

    private void loadPayments() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT payment_id, member_id, amount, "
                + "payment_date, payment_method, status "
                + "FROM payments "
                + "ORDER BY payment_id DESC";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql);

             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                Object[] row = {

                        rs.getInt("payment_id"),

                        rs.getInt("member_id"),

                        rs.getDouble("amount"),

                        rs.getDate("payment_date"),

                        rs.getString("payment_method"),

                        rs.getString("status")
                };

                tableModel.addRow(row);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load payment history!"
            );
        }
    }
}
