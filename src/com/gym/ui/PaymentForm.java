package com.gym.ui;

import com.gym.model.Payment;
import com.gym.service.PaymentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class PaymentForm extends JFrame {

    private JTextField txtMemberId;
    private JTextField txtAmount;
    private JTextField txtDate;

    private JComboBox<String> cmbMethod;
    private JComboBox<String> cmbStatus;

    private JTable table;
    private DefaultTableModel tableModel;

    private PaymentService paymentService;

    public PaymentForm() {

        paymentService = new PaymentService();

        setTitle("Gym Management - Payment Management");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createGUI();
        loadPayments();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // ================= TITLE =================

        JLabel title = new JLabel(
                "PAYMENT MANAGEMENT",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        mainPanel.add(title, BorderLayout.NORTH);

        // ================= FORM =================

        JPanel formPanel = new JPanel(
                new GridLayout(3, 4, 10, 10)
        );

        formPanel.add(new JLabel("Member ID:"));

        txtMemberId = new JTextField();
        formPanel.add(txtMemberId);

        formPanel.add(new JLabel("Amount:"));

        txtAmount = new JTextField();
        formPanel.add(txtAmount);

        formPanel.add(new JLabel("Payment Date:"));

        txtDate = new JTextField(
                new Date(System.currentTimeMillis()).toString()
        );

        formPanel.add(txtDate);

        formPanel.add(new JLabel("Payment Method:"));

        cmbMethod = new JComboBox<>(
                new String[]{
                        "Cash",
                        "UPI",
                        "Card",
                        "Bank Transfer"
                }
        );

        formPanel.add(cmbMethod);

        formPanel.add(new JLabel("Status:"));

        cmbStatus = new JComboBox<>(
                new String[]{
                        "Paid",
                        "Pending"
                }
        );

        formPanel.add(cmbStatus);

        JButton btnAdd = new JButton("ADD PAYMENT");

        formPanel.add(btnAdd);

        JButton btnClear = new JButton("CLEAR");

        formPanel.add(btnClear);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // ================= TABLE =================

        tableModel = new DefaultTableModel();

        tableModel.addColumn("Payment ID");
        tableModel.addColumn("Member ID");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Date");
        tableModel.addColumn("Method");
        tableModel.addColumn("Status");

        table = new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(table);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ================= BUTTON ACTION =================

        btnAdd.addActionListener(e -> addPayment());

        btnClear.addActionListener(e -> clearFields());

        add(mainPanel);
    }

    // ================= ADD PAYMENT =================

    private void addPayment() {

        try {

            int memberId =
                    Integer.parseInt(
                            txtMemberId.getText()
                    );

            double amount =
                    Double.parseDouble(
                            txtAmount.getText()
                    );

            Date paymentDate =
                    Date.valueOf(
                            txtDate.getText()
                    );

            String method =
                    cmbMethod.getSelectedItem().toString();

            String status =
                    cmbStatus.getSelectedItem().toString();

            Payment payment = new Payment();

            payment.setMemberId(memberId);
            payment.setAmount(amount);
            payment.setPaymentDate(paymentDate);
            payment.setPaymentMethod(method);
            payment.setStatus(status);

            boolean success =
                    paymentService.addPayment(payment);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment Added Successfully!"
                );

                clearFields();
                loadPayments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to Add Payment!"
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Member ID and Amount must be valid numbers!"
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Date format must be YYYY-MM-DD!"
            );
        }
    }

    // ================= LOAD PAYMENTS =================

    private void loadPayments() {

        tableModel.setRowCount(0);

        List<Payment> payments =
                paymentService.getAllPayments();

        for (Payment payment : payments) {

            tableModel.addRow(
                    new Object[]{
                            payment.getPaymentId(),
                            payment.getMemberId(),
                            payment.getAmount(),
                            payment.getPaymentDate(),
                            payment.getPaymentMethod(),
                            payment.getStatus()
                    }
            );
        }
    }

    // ================= CLEAR =================

    private void clearFields() {

        txtMemberId.setText("");
        txtAmount.setText("");

        txtDate.setText(
                new Date(
                        System.currentTimeMillis()
                ).toString()
        );

        cmbMethod.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
    }
}