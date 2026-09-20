
package com.gym.service;

import com.gym.database.DBConnection;
import com.gym.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberService {

    // =========================
    // ADD MEMBER
    // =========================

    public boolean addMember(Member member) {

        String sql = "INSERT INTO members "
                + "(name, age, gender, phone, email, plan, fees, start_date, expiry_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, member.getName());
            ps.setInt(2, member.getAge());
            ps.setString(3, member.getGender());
            ps.setString(4, member.getPhone());
            ps.setString(5, member.getEmail());
            ps.setString(6, member.getPlan());
            ps.setDouble(7, member.getFees());
            ps.setDate(8, member.getStartDate());
            ps.setDate(9, member.getExpiryDate());

            int result = ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================
    // VIEW ALL MEMBERS
    // =========================

    public List<Member> getAllMembers() {

        List<Member> members = new ArrayList<>();

        String sql = "SELECT * FROM members";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Member member = new Member();

                member.setMemberId(
                        rs.getInt("member_id")
                );

                member.setName(
                        rs.getString("name")
                );

                member.setAge(
                        rs.getInt("age")
                );

                member.setGender(
                        rs.getString("gender")
                );

                member.setPhone(
                        rs.getString("phone")
                );

                member.setEmail(
                        rs.getString("email")
                );

                member.setPlan(
                        rs.getString("plan")
                );

                member.setFees(
                        rs.getDouble("fees")
                );

                member.setStartDate(
                        rs.getDate("start_date")
                );

                member.setExpiryDate(
                        rs.getDate("expiry_date")
                );

                members.add(member);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return members;
    }

    // =========================
    // GET MEMBER NAME BY ID
    // =========================

    public String getMemberNameById(int memberId) {

        String sql =
                "SELECT name FROM members WHERE member_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getString("name");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // SEARCH MEMBERS
    // =========================

    public List<Member> searchMembers(String keyword) {

        List<Member> members =
                new ArrayList<>();

        String sql =
                "SELECT * FROM members "
                + "WHERE name LIKE ? "
                + "OR phone LIKE ? "
                + "OR email LIKE ? "
                + "ORDER BY member_id DESC";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            String searchKeyword =
                    "%" + keyword + "%";

            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);
            ps.setString(3, searchKeyword);

            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    Member member =
                            new Member();

                    member.setMemberId(
                            rs.getInt("member_id")
                    );

                    member.setName(
                            rs.getString("name")
                    );

                    member.setAge(
                            rs.getInt("age")
                    );

                    member.setGender(
                            rs.getString("gender")
                    );

                    member.setPhone(
                            rs.getString("phone")
                    );

                    member.setEmail(
                            rs.getString("email")
                    );

                    member.setPlan(
                            rs.getString("plan")
                    );

                    member.setFees(
                            rs.getDouble("fees")
                    );

                    member.setStartDate(
                            rs.getDate("start_date")
                    );

                    member.setExpiryDate(
                            rs.getDate("expiry_date")
                    );

                    members.add(member);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return members;
    }

    // =========================
    // UPDATE MEMBER
    // =========================

    public boolean updateMember(Member member) {

        String sql = "UPDATE members SET "
                + "name=?, age=?, gender=?, phone=?, "
                + "email=?, plan=?, fees=?, "
                + "start_date=?, expiry_date=? "
                + "WHERE member_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, member.getName());
            ps.setInt(2, member.getAge());
            ps.setString(3, member.getGender());
            ps.setString(4, member.getPhone());
            ps.setString(5, member.getEmail());
            ps.setString(6, member.getPlan());
            ps.setDouble(7, member.getFees());
            ps.setDate(8, member.getStartDate());
            ps.setDate(9, member.getExpiryDate());
            ps.setInt(10, member.getMemberId());

            int result = ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
 // =========================
 // GET EXPIRING / EXPIRED MEMBERS
 // =========================

 public List<Member> getExpiringMembers() {

     List<Member> members = new ArrayList<>();

     String sql =
             "SELECT * FROM members "
             + "WHERE expiry_date IS NOT NULL "
             + "AND expiry_date <= CURDATE() + INTERVAL 7 DAY "
             + "ORDER BY expiry_date ASC";

     try (Connection con = DBConnection.getConnection();
          PreparedStatement ps = con.prepareStatement(sql);
          ResultSet rs = ps.executeQuery()) {

         while (rs.next()) {

             Member member = new Member();

             member.setMemberId(
                     rs.getInt("member_id")
             );

             member.setName(
                     rs.getString("name")
             );

             member.setAge(
                     rs.getInt("age")
             );

             member.setGender(
                     rs.getString("gender")
             );

             member.setPhone(
                     rs.getString("phone")
             );

             member.setEmail(
                     rs.getString("email")
             );

             member.setPlan(
                     rs.getString("plan")
             );

             member.setFees(
                     rs.getDouble("fees")
             );

             member.setStartDate(
                     rs.getDate("start_date")
             );

             member.setExpiryDate(
                     rs.getDate("expiry_date")
             );

             members.add(member);
         }

     } catch (Exception e) {

         e.printStackTrace();
     }

     return members;
 }

    // =========================
    // DELETE MEMBER
    // =========================

    public boolean deleteMember(int memberId) {

        String sql =
                "DELETE FROM members WHERE member_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);

            int result = ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}