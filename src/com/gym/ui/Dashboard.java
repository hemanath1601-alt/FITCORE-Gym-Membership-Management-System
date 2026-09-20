package com.gym.ui;

import com.gym.model.Member;
import com.gym.service.MemberService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("FITCORE - Smart Gym Management");

        setSize(1000, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createDashboard();
    }

    private void createDashboard() {

        // =========================
        // BACKGROUND
        // =========================

        BackgroundPanel mainPanel = new BackgroundPanel();

        mainPanel.setLayout(new BorderLayout(10, 10));

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel logo = new JLabel("FITCORE");

        logo.setFont(
                new Font("Arial", Font.BOLD, 32)
        );

        logo.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "SMART GYM MANAGEMENT"
        );

        subtitle.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        subtitle.setForeground(Color.LIGHT_GRAY);

        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS)
        );

        titlePanel.setOpaque(false);

        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(logo);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        headerPanel.add(
                titlePanel,
                BorderLayout.CENTER
        );

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 5, 20
                )
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // SMALL BUTTON PANEL
        // =========================

        JPanel cardPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                10
                        )
                );

        cardPanel.setOpaque(false);

        // More space on left and right
        // makes buttons smaller and centered

        cardPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        300,
                        15,
                        300
                )
        );

        // =========================
        // BUTTONS
        // =========================

        RoundedButton btnMembers =
                new RoundedButton("MEMBER MANAGEMENT");

        RoundedButton btnPayments =
                new RoundedButton("PAYMENTS");

        RoundedButton btnTrainers =
                new RoundedButton("TRAINERS");

        RoundedButton btnPlans =
                new RoundedButton("MEMBERSHIP PLANS");

        RoundedButton btnAttendance =
                new RoundedButton("ATTENDANCE");

        RoundedButton btnSearch =
                new RoundedButton("SEARCH MEMBERS");

        RoundedButton btnExpiry =
                new RoundedButton("EXPIRING MEMBERS");

        RoundedButton btnLogout =
                new RoundedButton("LOGOUT");

        RoundedButton btnExit =
                new RoundedButton("EXIT");

        RoundedButton btnClose =
                new RoundedButton("CLOSE DASHBOARD");

        // =========================
        // ADD BUTTONS
        // =========================

        cardPanel.add(btnMembers);
        cardPanel.add(btnPayments);

        cardPanel.add(btnTrainers);
        cardPanel.add(btnPlans);

        cardPanel.add(btnAttendance);
        cardPanel.add(btnSearch);

        cardPanel.add(btnExpiry);
        cardPanel.add(btnLogout);

        cardPanel.add(btnExit);
        cardPanel.add(btnClose);

        mainPanel.add(
                cardPanel,
                BorderLayout.CENTER
        );

        // =========================
        // MEMBER
        // =========================

        btnMembers.addActionListener(e -> {

            new MemberForm()
                    .setVisible(true);

        });

        // =========================
        // PAYMENT
        // =========================

        btnPayments.addActionListener(e -> {

            new PaymentForm()
                    .setVisible(true);

        });

        // =========================
        // TRAINER
        // =========================

        btnTrainers.addActionListener(e -> {

            new TrainerForm()
                    .setVisible(true);

        });

        // =========================
        // MEMBERSHIP PLANS
        // =========================

        btnPlans.addActionListener(e -> {

            new MembershipPlanForm()
                    .setVisible(true);

        });

        // =========================
        // ATTENDANCE
        // =========================

        btnAttendance.addActionListener(e -> {

            new AttendanceForm()
                    .setVisible(true);

        });

        // =========================
        // SEARCH MEMBERS
        // =========================

        btnSearch.addActionListener(e -> {

            new MemberSearchForm()
                    .setVisible(true);

        });

        // =========================
        // EXPIRING MEMBERS
        // =========================

        btnExpiry.addActionListener(e -> {

            showExpiringMembers();

        });

        // =========================
        // LOGOUT
        // =========================

        btnLogout.addActionListener(e -> {

            dispose();

            new LoginForm()
                    .setVisible(true);

        });

        // =========================
        // EXIT
        // =========================

        btnExit.addActionListener(e -> {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to exit?",
                            "Exit",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice ==
                    JOptionPane.YES_OPTION) {

                System.exit(0);
            }
        });

        // =========================
        // CLOSE
        // =========================

        btnClose.addActionListener(e -> {

            dispose();

        });

        add(mainPanel);
    }

    // ==================================================
    // EXPIRING MEMBERS
    // ==================================================

    private void showExpiringMembers() {

        MemberService memberService =
                new MemberService();

        List<Member> members =
                memberService.getExpiringMembers();

        String[] columns = {

                "ID",
                "Name",
                "Phone",
                "Plan",
                "Start Date",
                "Expiry Date"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0
                );

        for (Member member : members) {

            model.addRow(
                    new Object[]{

                            member.getMemberId(),

                            member.getName(),

                            member.getPhone(),

                            member.getPlan(),

                            member.getStartDate(),

                            member.getExpiryDate()
                    }
            );
        }

        JTable table =
                new JTable(model);

        table.setRowHeight(25);

        JScrollPane scrollPane =
                new JScrollPane(table);

        if (members.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No expired or expiring members found!"
            );

            return;
        }

        JDialog dialog =
                new JDialog(
                        this,
                        "Expiring / Expired Members",
                        true
                );

        dialog.setSize(750, 400);

        dialog.setLocationRelativeTo(this);

        dialog.add(scrollPane);

        dialog.setVisible(true);
    }

    // ==================================================
    // ROUNDED BUTTON
    // ==================================================

    private static class RoundedButton
            extends JButton {

        public RoundedButton(String text) {

            super(text);

            setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            12
                    )
            );

            setForeground(Color.WHITE);

            setFocusPainted(false);

            setContentAreaFilled(false);

            setBorderPainted(false);

            setOpaque(false);

            // Small button size

            setPreferredSize(
                    new Dimension(
                            160,
                            40
                    )
            );

            setMinimumSize(
                    new Dimension(
                            160,
                            40
                    )
            );

            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Button background

            g2.setColor(
                    new Color(
                            15,
                            75,
                            110,
                            225
                    )
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    20,
                    20
            );

            // Border

            g2.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            150
                    )
            );

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    20,
                    20
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // ==================================================
    // BACKGROUND IMAGE
    // ==================================================

    private static class BackgroundPanel
            extends JPanel {

        private Image backgroundImage;

        public BackgroundPanel() {

            backgroundImage =
                    new ImageIcon(
                            getClass()
                                    .getResource(
                                            "/images/gym_background.jpg"
                                    )
                    ).getImage();
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );

            // Background image

            g2.drawImage(
                    backgroundImage,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );

            // Dark overlay

            g2.setColor(
                    new Color(
                            0,
                            0,
                            0,
                            120
                    )
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

            g2.dispose();
        }
    }
}