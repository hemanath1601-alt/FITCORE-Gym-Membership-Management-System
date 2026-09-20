
package com.gym.ui;

import com.gym.model.Member;
import com.gym.service.MemberService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class MemberForm extends JFrame {

    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtFees;

    private JTextField txtStartDate;
    private JTextField txtExpiryDate;

    private JTextField txtSearch;

    private JComboBox<String> cmbGender;
    private JComboBox<String> cmbPlan;

    private JTable table;
    private DefaultTableModel tableModel;

    private MemberService memberService;

    public MemberForm() {

        memberService = new MemberService();

        setTitle("Gym Membership Management System");

        setSize(1100, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createGUI();

        loadMembers();
    }

    private void createGUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        // ================= TITLE =================

        JLabel title =
                new JLabel(
                        "GYM MEMBERSHIP MANAGEMENT",
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

        // ================= FORM =================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                5, 4, 10, 10
                        )
                );

        formPanel.add(new JLabel("Name:"));

        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Age:"));

        txtAge = new JTextField();
        formPanel.add(txtAge);

        formPanel.add(new JLabel("Gender:"));

        cmbGender =
                new JComboBox<>(
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

        formPanel.add(new JLabel("Plan:"));

        cmbPlan =
                new JComboBox<>(
                        new String[]{
                                "Monthly",
                                "Quarterly",
                                "Yearly"
                        }
                );

        formPanel.add(cmbPlan);

        formPanel.add(new JLabel("Fees:"));

        txtFees = new JTextField();
        formPanel.add(txtFees);

        formPanel.add(new JLabel("Start Date:"));

        txtStartDate =
                new JTextField();

        txtStartDate.setToolTipText(
                "Format: YYYY-MM-DD"
        );

        formPanel.add(txtStartDate);

        formPanel.add(new JLabel("Expiry Date:"));

        txtExpiryDate =
                new JTextField();

        txtExpiryDate.setToolTipText(
                "Format: YYYY-MM-DD"
        );

        formPanel.add(txtExpiryDate);

        // ================= SEARCH PANEL =================

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                5
                        )
                );

        JLabel searchLabel =
                new JLabel("Search Member:");

        txtSearch =
                new JTextField(20);

        JButton btnSearch =
                new JButton("SEARCH");

        JButton btnShowAll =
                new JButton("SHOW ALL");

        searchPanel.add(searchLabel);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnShowAll);

        // ================= TOP PANEL =================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                searchPanel,
                BorderLayout.SOUTH
        );

        // ================= TABLE =================

        tableModel =
                new DefaultTableModel();

        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Email");
        tableModel.addColumn("Plan");
        tableModel.addColumn("Fees");
        tableModel.addColumn("Start Date");
        tableModel.addColumn("Expiry Date");

        table =
                new JTable(tableModel);

        table.setRowHeight(25);

        JScrollPane scrollPane =
                new JScrollPane(table);

        // ================= CENTER PANEL =================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        centerPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // ================= BUTTON PANEL =================

        JPanel buttonPanel =
                new JPanel();

        JButton btnAdd =
                new JButton("ADD MEMBER");

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

        // ================= ACTIONS =================

        btnAdd.addActionListener(e ->
                addMember()
        );

        btnUpdate.addActionListener(e ->
                updateMember()
        );

        btnDelete.addActionListener(e ->
                deleteMember()
        );

        btnClear.addActionListener(e ->
                clearFields()
        );

        btnSearch.addActionListener(e ->
                searchMembers()
        );

        btnShowAll.addActionListener(e -> {

            txtSearch.setText("");

            loadMembers();
        });

        txtSearch.addActionListener(e ->
                searchMembers()
        );

        // ================= TABLE CLICK =================

        table.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        int row =
                                table.getSelectedRow();

                        if (row >= 0) {

                            txtName.setText(
                                    tableModel
                                            .getValueAt(
                                                    row, 1
                                            )
                                            .toString()
                            );

                            txtAge.setText(
                                    tableModel
                                            .getValueAt(
                                                    row, 2
                                            )
                                            .toString()
                            );

                            cmbGender.setSelectedItem(
                                    tableModel
                                            .getValueAt(
                                                    row, 3
                                            )
                                            .toString()
                            );

                            txtPhone.setText(
                                    tableModel
                                            .getValueAt(
                                                    row, 4
                                            )
                                            .toString()
                            );

                            txtEmail.setText(
                                    tableModel
                                            .getValueAt(
                                                    row, 5
                                            )
                                            .toString()
                            );

                            cmbPlan.setSelectedItem(
                                    tableModel
                                            .getValueAt(
                                                    row, 6
                                            )
                                            .toString()
                            );

                            txtFees.setText(
                                    tableModel
                                            .getValueAt(
                                                    row, 7
                                            )
                                            .toString()
                            );

                            Object startDate =
                                    tableModel.getValueAt(
                                            row, 8
                                    );

                            Object expiryDate =
                                    tableModel.getValueAt(
                                            row, 9
                                    );

                            txtStartDate.setText(
                                    startDate == null
                                            ? ""
                                            : startDate.toString()
                            );

                            txtExpiryDate.setText(
                                    expiryDate == null
                                            ? ""
                                            : expiryDate.toString()
                            );
                        }
                    }
                }
        );

        add(mainPanel);
    }

    // ================= ADD MEMBER =================

    private void addMember() {

        try {

            Member member =
                    new Member();

            member.setName(
                    txtName.getText()
            );

            member.setAge(
                    Integer.parseInt(
                            txtAge.getText()
                    )
            );

            member.setGender(
                    cmbGender
                            .getSelectedItem()
                            .toString()
            );

            member.setPhone(
                    txtPhone.getText()
            );

            member.setEmail(
                    txtEmail.getText()
            );

            member.setPlan(
                    cmbPlan
                            .getSelectedItem()
                            .toString()
            );

            member.setFees(
                    Double.parseDouble(
                            txtFees.getText()
                    )
            );

            // Convert text to SQL Date
            member.setStartDate(
                    Date.valueOf(
                            txtStartDate.getText().trim()
                    )
            );

            member.setExpiryDate(
                    Date.valueOf(
                            txtExpiryDate.getText().trim()
                    )
            );

            boolean success =
                    memberService.addMember(
                            member
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Member Added Successfully!"
                );

                clearFields();

                loadMembers();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to Add Member"
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid Age and Fees!"
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter dates in YYYY-MM-DD format!"
            );
        }
    }

    // ================= UPDATE MEMBER =================

    private void updateMember() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a member first!"
            );

            return;
        }

        try {

            int memberId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            row, 0
                                    )
                                    .toString()
                    );

            Member member =
                    new Member();

            member.setMemberId(
                    memberId
            );

            member.setName(
                    txtName.getText()
            );

            member.setAge(
                    Integer.parseInt(
                            txtAge.getText()
                    )
            );

            member.setGender(
                    cmbGender
                            .getSelectedItem()
                            .toString()
            );

            member.setPhone(
                    txtPhone.getText()
            );

            member.setEmail(
                    txtEmail.getText()
            );

            member.setPlan(
                    cmbPlan
                            .getSelectedItem()
                            .toString()
            );

            member.setFees(
                    Double.parseDouble(
                            txtFees.getText()
                    )
            );

            member.setStartDate(
                    Date.valueOf(
                            txtStartDate.getText().trim()
                    )
            );

            member.setExpiryDate(
                    Date.valueOf(
                            txtExpiryDate.getText().trim()
                    )
            );

            boolean success =
                    memberService.updateMember(
                            member
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Member Updated Successfully!"
                );

                clearFields();

                loadMembers();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Update Failed!"
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid Age and Fees!"
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter dates in YYYY-MM-DD format!"
            );
        }
    }

    // ================= DELETE MEMBER =================

    private void deleteMember() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a member first!"
            );

            return;
        }

        int memberId =
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
                        "Are you sure you want to delete this member?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm ==
                JOptionPane.YES_OPTION) {

            boolean success =
                    memberService.deleteMember(
                            memberId
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Member Deleted Successfully!"
                );

                clearFields();

                loadMembers();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Delete Failed!"
                );
            }
        }
    }

    // ================= SEARCH MEMBERS =================

    private void searchMembers() {

        String keyword =
                txtSearch.getText().trim();

        if (keyword.isEmpty()) {

            loadMembers();

            return;
        }

        tableModel.setRowCount(0);

        List<Member> members =
                memberService.searchMembers(
                        keyword
                );

        for (Member member : members) {

            tableModel.addRow(
                    new Object[]{
                            member.getMemberId(),
                            member.getName(),
                            member.getAge(),
                            member.getGender(),
                            member.getPhone(),
                            member.getEmail(),
                            member.getPlan(),
                            member.getFees(),
                            member.getStartDate(),
                            member.getExpiryDate()
                    }
            );
        }

        if (members.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No members found!"
            );
        }
    }

    // ================= LOAD MEMBERS =================

    private void loadMembers() {

        tableModel.setRowCount(0);

        List<Member> members =
                memberService.getAllMembers();

        for (Member member : members) {

            tableModel.addRow(
                    new Object[]{
                            member.getMemberId(),
                            member.getName(),
                            member.getAge(),
                            member.getGender(),
                            member.getPhone(),
                            member.getEmail(),
                            member.getPlan(),
                            member.getFees(),
                            member.getStartDate(),
                            member.getExpiryDate()
                    }
            );
        }
    }

    // ================= CLEAR =================

    private void clearFields() {

        txtName.setText("");

        txtAge.setText("");

        txtPhone.setText("");

        txtEmail.setText("");

        txtFees.setText("");

        txtSearch.setText("");

        txtStartDate.setText(
                LocalDate.now().toString()
        );

        txtExpiryDate.setText("");

        cmbGender.setSelectedIndex(0);

        cmbPlan.setSelectedIndex(0);

        table.clearSelection();

        loadMembers();
    }
}
