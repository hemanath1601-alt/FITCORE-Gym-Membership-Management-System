
package com.gym.ui;

import com.gym.model.MembershipPlan;
import com.gym.service.MembershipPlanService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MembershipPlanForm extends JFrame {

    private JTextField txtPlanName;
    private JTextField txtDuration;
    private JTextField txtFees;
    private JTextField txtDescription;

    private JTable table;
    private DefaultTableModel tableModel;

    private MembershipPlanService planService;

    public MembershipPlanForm() {

        planService =
                new MembershipPlanService();

        setTitle("Membership Plans");

        setSize(850, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        createGUI();

        loadPlans();
    }

    private void createGUI() {

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
                        "MEMBERSHIP PLANS",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                4, 2, 10, 10
                        )
                );

        formPanel.add(
                new JLabel("Plan Name:")
        );

        txtPlanName =
                new JTextField();

        formPanel.add(txtPlanName);

        formPanel.add(
                new JLabel("Duration (Months):")
        );

        txtDuration =
                new JTextField();

        formPanel.add(txtDuration);

        formPanel.add(
                new JLabel("Fees:")
        );

        txtFees =
                new JTextField();

        formPanel.add(txtFees);

        formPanel.add(
                new JLabel("Description:")
        );

        txtDescription =
                new JTextField();

        formPanel.add(txtDescription);

        mainPanel.add(
                formPanel,
                BorderLayout.WEST
        );

        // =========================
        // TABLE
        // =========================

        tableModel =
                new DefaultTableModel();

        tableModel.addColumn("ID");
        tableModel.addColumn("Plan Name");
        tableModel.addColumn("Duration");
        tableModel.addColumn("Fees");
        tableModel.addColumn("Description");

        table =
                new JTable(tableModel);

        table.setRowHeight(25);

        JScrollPane scrollPane =
                new JScrollPane(table);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel();

        JButton btnAdd =
                new JButton("ADD PLAN");

        JButton btnUpdate =
                new JButton("UPDATE");

        JButton btnDelete =
                new JButton("DELETE");

        JButton btnClear =
                new JButton("CLEAR");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        btnAdd.addActionListener(e ->
                addPlan()
        );

        btnUpdate.addActionListener(e ->
                updatePlan()
        );

        btnDelete.addActionListener(e ->
                deletePlan()
        );

        btnClear.addActionListener(e ->
                clearFields()
        );

        // =========================
        // TABLE CLICK
        // =========================

        table.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        int row =
                                table.getSelectedRow();

                        if (row >= 0) {

                            txtPlanName.setText(
                                    tableModel
                                            .getValueAt(
                                                    row, 1
                                            )
                                            .toString()
                            );

                            txtDuration.setText(
                                    tableModel
                                            .getValueAt(
                                                    row, 2
                                            )
                                            .toString()
                            );

                            txtFees.setText(
                                    tableModel
                                            .getValueAt(
                                                    row, 3
                                            )
                                            .toString()
                            );

                            txtDescription.setText(
                                    tableModel
                                            .getValueAt(
                                                    row, 4
                                            )
                                            .toString()
                            );
                        }
                    }
                }
        );

        add(mainPanel);
    }

    // =========================
    // ADD PLAN
    // =========================

    private void addPlan() {

        try {

            String planName =
                    txtPlanName
                            .getText()
                            .trim();

            int duration =
                    Integer.parseInt(
                            txtDuration
                                    .getText()
                                    .trim()
                    );

            double fees =
                    Double.parseDouble(
                            txtFees
                                    .getText()
                                    .trim()
                    );

            String description =
                    txtDescription
                            .getText()
                            .trim();

            if (planName.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Plan Name!"
                );

                return;
            }

            if (duration <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Duration must be greater than 0!"
                );

                return;
            }

            if (fees < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Fees cannot be negative!"
                );

                return;
            }

            MembershipPlan plan =
                    new MembershipPlan();

            plan.setPlanName(planName);

            plan.setDurationMonths(
                    duration
            );

            plan.setFees(fees);

            plan.setDescription(
                    description
            );

            boolean success =
                    planService.addPlan(plan);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Membership Plan Added Successfully!"
                );

                clearFields();

                loadPlans();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to Add Plan!"
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid Duration and Fees!"
            );
        }
    }

    // =========================
    // UPDATE PLAN
    // =========================

    private void updatePlan() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a plan first!"
            );

            return;
        }

        try {

            int planId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            row, 0
                                    )
                                    .toString()
                    );

            String planName =
                    txtPlanName
                            .getText()
                            .trim();

            int duration =
                    Integer.parseInt(
                            txtDuration
                                    .getText()
                                    .trim()
                    );

            double fees =
                    Double.parseDouble(
                            txtFees
                                    .getText()
                                    .trim()
                    );

            String description =
                    txtDescription
                            .getText()
                            .trim();

            if (planName.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Plan Name!"
                );

                return;
            }

            MembershipPlan plan =
                    new MembershipPlan();

            plan.setPlanId(planId);

            plan.setPlanName(planName);

            plan.setDurationMonths(
                    duration
            );

            plan.setFees(fees);

            plan.setDescription(
                    description
            );

            boolean success =
                    planService.updatePlan(plan);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Membership Plan Updated Successfully!"
                );

                clearFields();

                loadPlans();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Update Failed!"
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid Duration and Fees!"
            );
        }
    }

    // =========================
    // DELETE PLAN
    // =========================

    private void deletePlan() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a plan first!"
            );

            return;
        }

        int planId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row, 0
                                )
                                .toString()
                );

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this plan?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm ==
                JOptionPane.YES_OPTION) {

            boolean success =
                    planService.deletePlan(
                            planId
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Membership Plan Deleted Successfully!"
                );

                clearFields();

                loadPlans();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Delete Failed!"
                );
            }
        }
    }

    // =========================
    // LOAD PLANS
    // =========================

    private void loadPlans() {

        tableModel.setRowCount(0);

        List<MembershipPlan> plans =
                planService.getAllPlans();

        for (MembershipPlan plan : plans) {

            tableModel.addRow(
                    new Object[]{
                            plan.getPlanId(),
                            plan.getPlanName(),
                            plan.getDurationMonths()
                                    + " Months",
                            plan.getFees(),
                            plan.getDescription()
                    }
            );
        }
    }

    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        txtPlanName.setText("");

        txtDuration.setText("");

        txtFees.setText("");

        txtDescription.setText("");

        table.clearSelection();
    }
}
