package com.gym.ui;

import com.gym.model.Member;
import com.gym.service.MemberService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MemberSearchForm extends JFrame {

    private JTextField txtSearch;

    private JTable table;

    private DefaultTableModel tableModel;

    private MemberService memberService;

    public MemberSearchForm() {

        memberService = new MemberService();

        setTitle("Search Members");

        setSize(950, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        createForm();

        loadMembers();
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
                        "SEARCH MEMBERS",
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
        // SEARCH PANEL
        // =========================

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                10
                        )
                );

        JLabel lblSearch =
                new JLabel(
                        "Search Name / Phone:"
                );

        txtSearch =
                new JTextField(25);

        JButton btnSearch =
                new JButton("SEARCH");

        JButton btnClear =
                new JButton("CLEAR");

        searchPanel.add(lblSearch);

        searchPanel.add(txtSearch);

        searchPanel.add(btnSearch);

        searchPanel.add(btnClear);

        mainPanel.add(
                searchPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // TABLE
        // =========================

        String[] columns = {

                "Member ID",
                "Name",
                "Age",
                "Gender",
                "Phone",
                "Email",
                "Plan",
                "Fees"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        table =
                new JTable(tableModel);

        table.setRowHeight(25);

        JScrollPane scrollPane =
                new JScrollPane(table);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =========================
        // SEARCH BUTTON
        // =========================

        btnSearch.addActionListener(e -> {

            searchMembers();

        });

        // =========================
        // ENTER KEY SEARCH
        // =========================

        txtSearch.addActionListener(e -> {

            searchMembers();

        });

        // =========================
        // CLEAR BUTTON
        // =========================

        btnClear.addActionListener(e -> {

            txtSearch.setText("");

            loadMembers();

        });
    }

    // =========================
    // LOAD ALL MEMBERS
    // =========================

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

                            member.getFees()
                    }
            );
        }
    }

    // =========================
    // SEARCH MEMBERS
    // =========================

    private void searchMembers() {

        String searchText =
                txtSearch
                        .getText()
                        .trim()
                        .toLowerCase();

        if (searchText.isEmpty()) {

            loadMembers();

            return;
        }

        tableModel.setRowCount(0);

        List<Member> members =
                memberService.getAllMembers();

        for (Member member : members) {

            String name =
                    member.getName()
                            .toLowerCase();

            String phone =
                    member.getPhone()
                            .toLowerCase();

            if (name.contains(searchText)
                    || phone.contains(searchText)) {

                tableModel.addRow(
                        new Object[]{

                                member.getMemberId(),

                                member.getName(),

                                member.getAge(),

                                member.getGender(),

                                member.getPhone(),

                                member.getEmail(),

                                member.getPlan(),

                                member.getFees()
                        }
                );
            }
        }

        if (tableModel.getRowCount() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No members found!"
            );
        }
    }
}