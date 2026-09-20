
package com.gym.service;

import com.gym.database.DBConnection;
import com.gym.model.MembershipPlan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MembershipPlanService {

    // =========================
    // ADD PLAN
    // =========================

    public boolean addPlan(MembershipPlan plan) {

        String sql =
                "INSERT INTO membership_plans "
                + "(plan_name, duration_months, fees, description) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    plan.getPlanName()
            );

            ps.setInt(
                    2,
                    plan.getDurationMonths()
            );

            ps.setDouble(
                    3,
                    plan.getFees()
            );

            ps.setString(
                    4,
                    plan.getDescription()
            );

            int result =
                    ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================
    // GET ALL PLANS
    // =========================

    public List<MembershipPlan> getAllPlans() {

        List<MembershipPlan> plans =
                new ArrayList<>();

        String sql =
                "SELECT * FROM membership_plans "
                + "ORDER BY plan_id DESC";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql);

             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                MembershipPlan plan =
                        new MembershipPlan();

                plan.setPlanId(
                        rs.getInt("plan_id")
                );

                plan.setPlanName(
                        rs.getString("plan_name")
                );

                plan.setDurationMonths(
                        rs.getInt("duration_months")
                );

                plan.setFees(
                        rs.getDouble("fees")
                );

                plan.setDescription(
                        rs.getString("description")
                );

                plans.add(plan);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return plans;
    }

    // =========================
    // UPDATE PLAN
    // =========================

    public boolean updatePlan(
            MembershipPlan plan) {

        String sql =
                "UPDATE membership_plans SET "
                + "plan_name=?, "
                + "duration_months=?, "
                + "fees=?, "
                + "description=? "
                + "WHERE plan_id=?";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    plan.getPlanName()
            );

            ps.setInt(
                    2,
                    plan.getDurationMonths()
            );

            ps.setDouble(
                    3,
                    plan.getFees()
            );

            ps.setString(
                    4,
                    plan.getDescription()
            );

            ps.setInt(
                    5,
                    plan.getPlanId()
            );

            int result =
                    ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================
    // DELETE PLAN
    // =========================

    public boolean deletePlan(int planId) {

        String sql =
                "DELETE FROM membership_plans "
                + "WHERE plan_id=?";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    planId
            );

            int result =
                    ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}
