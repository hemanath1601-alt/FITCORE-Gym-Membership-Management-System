package com.gym.ui;

import com.gym.model.Trainer;
import com.gym.service.TrainerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TrainerForm extends JFrame {

    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtSpecialization;
    private JTextField txtSalary;

    private JComboBox<String> cmbGender;

    private JTable table;
    private DefaultTableModel tableModel;

    private TrainerService trainerService;

    public TrainerForm() {

        trainerService = new TrainerService();

        setTitle("Gym Management - Trainer Management");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createGUI();
        loadTrainers();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // ================= TITLE =================

        JLabel title = new JLabel(
                "TRAINER MANAGEMENT",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        // ================= FORM =================

        JPanel formPanel =
                new JPanel(new GridLayout(4, 4, 10, 10));

        formPanel.add(new JLabel("Name:"));

        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Age:"));

        txtAge = new JTextField();
        formPanel.add(txtAge);

        formPanel.add(new JLabel("Gender:"));

        cmbGender = new JComboBox<>(
                new String[]{
                        "Male",
                        "Female",
                        "Other"
                }
        );

        formPanel.add(cmbGender);

        formPanel.add(new JLabel("Phone:"));

        txtPhone = new JTextField();
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Email:"));

        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Specialization:"));

        txtSpecialization = new JTextField();
        formPanel.add(txtSpecialization);

        formPanel.add(new JLabel("Salary:"));

        txtSalary = new JTextField();
        formPanel.add(txtSalary);

        JButton btnAdd =
                new JButton("ADD TRAINER");

        formPanel.add(btnAdd);

        JButton btnDelete =
                new JButton("DELETE SELECTED");

        formPanel.add(btnDelete);

        JButton btnClear =
                new JButton("CLEAR");

        formPanel.add(btnClear);

        // ================= TOP PANEL =================

        JPanel topPanel =
                new JPanel(new BorderLayout(10, 10));

        topPanel.add(
                title,
                BorderLayout.NORTH
        );

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // ================= TABLE =================

        tableModel = new DefaultTableModel();

        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Email");
        tableModel.addColumn("Specialization");
        tableModel.addColumn("Salary");

        table = new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(table);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ================= BUTTON ACTIONS =================

        btnAdd.addActionListener(
                e -> addTrainer()
        );

        btnDelete.addActionListener(
                e -> deleteTrainer()
        );

        btnClear.addActionListener(
                e -> clearFields()
        );

        add(mainPanel);
    }

    // ================= ADD TRAINER =================

    private void addTrainer() {

        try {

            String name =
                    txtName.getText().trim();

            if (name.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter trainer name!"
                );

                return;
            }

            int age =
                    Integer.parseInt(
                            txtAge.getText().trim()
                    );

            double salary =
                    Double.parseDouble(
                            txtSalary.getText().trim()
                    );

            String gender =
                    cmbGender.getSelectedItem()
                            .toString();

            String phone =
                    txtPhone.getText().trim();

            String email =
                    txtEmail.getText().trim();

            String specialization =
                    txtSpecialization.getText().trim();

            Trainer trainer =
                    new Trainer();

            trainer.setName(name);
            trainer.setAge(age);
            trainer.setGender(gender);
            trainer.setPhone(phone);
            trainer.setEmail(email);
            trainer.setSpecialization(
                    specialization
            );
            trainer.setSalary(salary);

            boolean success =
                    trainerService.addTrainer(
                            trainer
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Trainer Added Successfully!"
                );

                clearFields();
                loadTrainers();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to Add Trainer!"
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age and Salary must be valid numbers!"
            );
        }
    }

    // ================= LOAD TRAINERS =================

    private void loadTrainers() {

        tableModel.setRowCount(0);

        List<Trainer> trainers =
                trainerService.getAllTrainers();

        for (Trainer trainer : trainers) {

            tableModel.addRow(
                    new Object[]{
                            trainer.getTrainerId(),
                            trainer.getName(),
                            trainer.getAge(),
                            trainer.getGender(),
                            trainer.getPhone(),
                            trainer.getEmail(),
                            trainer.getSpecialization(),
                            trainer.getSalary()
                    }
            );
        }
    }

    // ================= DELETE TRAINER =================

    private void deleteTrainer() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a trainer!"
            );

            return;
        }

        int trainerId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete this trainer?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm == JOptionPane.YES_OPTION) {

            boolean success =
                    trainerService.deleteTrainer(
                            trainerId
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Trainer Deleted Successfully!"
                );

                loadTrainers();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to Delete Trainer!"
                );
            }
        }
    }

    // ================= CLEAR =================

    private void clearFields() {

        txtName.setText("");
        txtAge.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtSpecialization.setText("");
        txtSalary.setText("");

        cmbGender.setSelectedIndex(0);
    }
}