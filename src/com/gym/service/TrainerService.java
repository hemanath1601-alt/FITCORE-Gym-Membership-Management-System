package com.gym.service;

import com.gym.database.DBConnection;
import com.gym.model.Trainer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerService {

    public boolean addTrainer(Trainer trainer) {

        String sql = "INSERT INTO trainers " +
                "(name, age, gender, phone, email, specialization, salary) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, trainer.getName());
            ps.setInt(2, trainer.getAge());
            ps.setString(3, trainer.getGender());
            ps.setString(4, trainer.getPhone());
            ps.setString(5, trainer.getEmail());
            ps.setString(6, trainer.getSpecialization());
            ps.setDouble(7, trainer.getSalary());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public List<Trainer> getAllTrainers() {

        List<Trainer> trainers = new ArrayList<>();

        String sql = "SELECT * FROM trainers ORDER BY trainer_id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Trainer trainer = new Trainer();

                trainer.setTrainerId(
                        rs.getInt("trainer_id")
                );

                trainer.setName(
                        rs.getString("name")
                );

                trainer.setAge(
                        rs.getInt("age")
                );

                trainer.setGender(
                        rs.getString("gender")
                );

                trainer.setPhone(
                        rs.getString("phone")
                );

                trainer.setEmail(
                        rs.getString("email")
                );

                trainer.setSpecialization(
                        rs.getString("specialization")
                );

                trainer.setSalary(
                        rs.getDouble("salary")
                );

                trainers.add(trainer);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return trainers;
    }

    public boolean deleteTrainer(int trainerId) {

        String sql =
                "DELETE FROM trainers WHERE trainer_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, trainerId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}