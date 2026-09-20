
package com.gym.service;

import com.gym.database.DBConnection;
import com.gym.model.Attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceService {

    // =========================
    // CHECK IF MEMBER IS ALREADY
    // CHECKED IN
    // =========================

    public boolean isAlreadyCheckedIn(int memberId) {

        String sql =
                "SELECT attendance_id " +
                "FROM attendance " +
                "WHERE member_id = ? " +
                "AND attendance_date = CURDATE() " +
                "AND check_out IS NULL " +
                "LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {

                return rs.next();
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================
    // CHECK IN
    // =========================

    public String checkIn(int memberId) {

        // First check
        if (isAlreadyCheckedIn(memberId)) {

            return "ALREADY_CHECKED_IN";
        }

        String sql =
                "INSERT INTO attendance " +
                "(member_id, check_in, attendance_date) " +
                "VALUES (?, NOW(), CURDATE())";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);

            int result = ps.executeUpdate();

            if (result > 0) {

                return "SUCCESS";
            }

        } catch (SQLException e) {

            /*
             * MySQL duplicate-key error.
             * This protects against duplicate check-in
             * even if two requests happen at almost
             * the same time.
             */

            if (e.getErrorCode() == 1062) {

                return "ALREADY_CHECKED_IN";
            }

            e.printStackTrace();

            return "ERROR";
        }

        return "ERROR";
    }

    // =========================
    // CHECK OUT
    // =========================

    public boolean checkOut(int attendanceId) {

        String sql =
                "UPDATE attendance " +
                "SET check_out = NOW() " +
                "WHERE attendance_id = ? " +
                "AND check_out IS NULL";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, attendanceId);

            int result = ps.executeUpdate();

            return result > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================
    // GET ALL ATTENDANCE
    // =========================

    public List<Attendance> getAllAttendance() {

        List<Attendance> list =
                new ArrayList<>();

        String sql =
                "SELECT attendance_id, member_id, " +
                "check_in, check_out, attendance_date " +
                "FROM attendance " +
                "ORDER BY attendance_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Attendance attendance =
                        new Attendance();

                attendance.setAttendanceId(
                        rs.getInt("attendance_id")
                );

                attendance.setMemberId(
                        rs.getInt("member_id")
                );

                attendance.setCheckIn(
                        rs.getTimestamp("check_in")
                );

                attendance.setCheckOut(
                        rs.getTimestamp("check_out")
                );

                attendance.setAttendanceDate(
                        rs.getDate("attendance_date")
                );

                list.add(attendance);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }
}