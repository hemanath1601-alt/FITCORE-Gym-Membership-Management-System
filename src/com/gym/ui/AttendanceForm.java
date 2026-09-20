
package com.gym.ui;

import com.gym.model.Attendance;
import com.gym.service.AttendanceService;
import com.gym.service.MemberService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AttendanceForm extends JFrame {

    private JTextField txtMemberId;
    private JTextField txtMemberName;

    private JButton btnCheckIn;
    private JButton btnCheckOut;
    private JButton btnClear;

    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    private AttendanceService attendanceService;
    private MemberService memberService;

    public AttendanceForm() {

        attendanceService = new AttendanceService();
        memberService = new MemberService();

        setTitle("Attendance Management");

        setSize(950, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createForm();
    }

    private void createForm() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        // =========================
        // INPUT PANEL
        // =========================

        JPanel inputPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                10
                        )
                );

        JLabel lblMemberId =
                new JLabel("Member ID:");

        txtMemberId =
                new JTextField(8);

        JLabel lblMemberName =
                new JLabel("Member Name:");

        txtMemberName =
                new JTextField(15);

        txtMemberName.setEditable(false);

        btnCheckIn =
                new JButton("CHECK IN");

        btnCheckOut =
                new JButton("CHECK OUT");

        btnClear =
                new JButton("CLEAR");

        inputPanel.add(lblMemberId);
        inputPanel.add(txtMemberId);

        inputPanel.add(lblMemberName);
        inputPanel.add(txtMemberName);

        inputPanel.add(btnCheckIn);
        inputPanel.add(btnCheckOut);
        inputPanel.add(btnClear);

        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "Attendance ID",
                "Member ID",
                "Check In",
                "Check Out",
                "Date"
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

        attendanceTable =
                new JTable(tableModel);

        attendanceTable.setRowHeight(25);

        attendanceTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(attendanceTable);

        // =========================
        // ADD COMPONENTS
        // =========================

        mainPanel.add(
                inputPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =========================
        // MEMBER LOOKUP
        // =========================

        txtMemberId.addActionListener(e -> {
            findMember();
        });

        // =========================
        // CHECK IN
        // =========================

        btnCheckIn.addActionListener(e -> {
            checkIn();
        });

        // =========================
        // CHECK OUT
        // =========================

        btnCheckOut.addActionListener(e -> {
            checkOut();
        });

        // =========================
        // CLEAR
        // =========================

        btnClear.addActionListener(e -> {
            clearFields();
        });

        // =========================
        // LOAD ATTENDANCE
        // =========================

        loadAttendance();
    }

    // ==================================================
    // FIND MEMBER
    // ==================================================

    private void findMember() {

        String memberIdText =
                txtMemberId.getText().trim();

        if (memberIdText.isEmpty()) {

            txtMemberName.setText("");

            return;
        }

        try {

            int memberId =
                    Integer.parseInt(memberIdText);

            String memberName =
                    memberService.getMemberNameById(
                            memberId
                    );

            if (memberName != null) {

                txtMemberName.setText(
                        memberName
                );

            } else {

                txtMemberName.setText("");

                JOptionPane.showMessageDialog(
                        this,
                        "Member ID not found!"
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Member ID must be a number!"
            );

            txtMemberName.setText("");
        }
    }

    // ==================================================
    // CHECK IN
    // ==================================================

    private void checkIn() {

        String memberIdText =
                txtMemberId.getText().trim();

        if (memberIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Member ID!"
            );

            return;
        }

        try {

            int memberId =
                    Integer.parseInt(memberIdText);

            // Check member exists
            String memberName =
                    memberService.getMemberNameById(
                            memberId
                    );

            if (memberName == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Member ID not found!"
                );

                return;
            }

            // Check-in
            String result =
                    attendanceService.checkIn(
                            memberId
                    );

            // =========================
            // SUCCESS
            // =========================

            if (result.equals("SUCCESS")) {

                JOptionPane.showMessageDialog(
                        this,
                        memberName
                        + " Checked In Successfully!"
                );

                clearFields();

                loadAttendance();

            }

            // =========================
            // ALREADY CHECKED IN
            // =========================

            else if (
                    result.equals(
                            "ALREADY_CHECKED_IN"
                    )
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        memberName
                        + " is already checked in!"
                );

            }

            // =========================
            // ERROR
            // =========================

            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Check In Failed!"
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Member ID must be a number!"
            );
        }
    }

    // ==================================================
    // CHECK OUT
    // ==================================================

    private void checkOut() {

        int selectedRow =
                attendanceTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an attendance record!"
            );

            return;
        }

        int attendanceId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        Object checkOutValue =
                tableModel.getValueAt(
                        selectedRow,
                        3
                );

        // Already checked out
        if (checkOutValue != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "This member is already checked out!"
            );

            return;
        }

        boolean success =
                attendanceService.checkOut(
                        attendanceId
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Member Checked Out Successfully!"
            );

            loadAttendance();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Check Out Failed!"
            );
        }
    }

    // ==================================================
    // LOAD ATTENDANCE
    // ==================================================

    private void loadAttendance() {

        tableModel.setRowCount(0);

        List<Attendance> attendanceList =
                attendanceService.getAllAttendance();

        for (Attendance attendance :
                attendanceList) {

            Object[] row = {

                    attendance.getAttendanceId(),

                    attendance.getMemberId(),

                    attendance.getCheckIn(),

                    attendance.getCheckOut(),

                    attendance.getAttendanceDate()
            };

            tableModel.addRow(row);
        }
    }

    // ==================================================
    // CLEAR
    // ==================================================

    private void clearFields() {

        txtMemberId.setText("");

        txtMemberName.setText("");

        attendanceTable.clearSelection();

        txtMemberId.requestFocus();
    }
}