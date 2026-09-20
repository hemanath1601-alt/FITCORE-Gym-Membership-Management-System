package com.gym.model;

import java.sql.Date;

public class Member {

    private int memberId;

    private String name;

    private int age;

    private String gender;

    private String phone;

    private String email;

    private String plan;

    private double fees;

    private Date startDate;

    private Date expiryDate;

    // =========================
    // DEFAULT CONSTRUCTOR
    // =========================

    public Member() {

    }

    // =========================
    // EXISTING PARAMETERIZED CONSTRUCTOR
    // =========================

    public Member(
            int memberId,
            String name,
            int age,
            String gender,
            String phone,
            String email,
            String plan,
            double fees) {

        this.memberId = memberId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.plan = plan;
        this.fees = fees;
    }

    // =========================
    // NEW PARAMETERIZED CONSTRUCTOR
    // =========================

    public Member(
            int memberId,
            String name,
            int age,
            String gender,
            String phone,
            String email,
            String plan,
            double fees,
            Date startDate,
            Date expiryDate) {

        this.memberId = memberId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.plan = plan;
        this.fees = fees;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    // =========================
    // MEMBER ID
    // =========================

    public int getMemberId() {

        return memberId;
    }

    public void setMemberId(int memberId) {

        this.memberId = memberId;
    }

    // =========================
    // NAME
    // =========================

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    // =========================
    // AGE
    // =========================

    public int getAge() {

        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }

    // =========================
    // GENDER
    // =========================

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }

    // =========================
    // PHONE
    // =========================

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    // =========================
    // EMAIL
    // =========================

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    // =========================
    // PLAN
    // =========================

    public String getPlan() {

        return plan;
    }

    public void setPlan(String plan) {

        this.plan = plan;
    }

    // =========================
    // FEES
    // =========================

    public double getFees() {

        return fees;
    }

    public void setFees(double fees) {

        this.fees = fees;
    }

    // =========================
    // START DATE
    // =========================

    public Date getStartDate() {

        return startDate;
    }

    public void setStartDate(Date startDate) {

        this.startDate = startDate;
    }

    // =========================
    // EXPIRY DATE
    // =========================

    public Date getExpiryDate() {

        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {

        this.expiryDate = expiryDate;
    }

    // =========================
    // TO STRING
    // =========================

    @Override
    public String toString() {

        return "Member ID: " + memberId
                + ", Name: " + name
                + ", Age: " + age
                + ", Gender: " + gender
                + ", Phone: " + phone
                + ", Email: " + email
                + ", Plan: " + plan
                + ", Fees: " + fees
                + ", Start Date: " + startDate
                + ", Expiry Date: " + expiryDate;
    }
}
