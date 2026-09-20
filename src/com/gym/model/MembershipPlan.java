
package com.gym.model;

public class MembershipPlan {

    private int planId;

    private String planName;

    private int durationMonths;

    private double fees;

    private String description;

    // =========================
    // DEFAULT CONSTRUCTOR
    // =========================

    public MembershipPlan() {

    }

    // =========================
    // PARAMETERIZED CONSTRUCTOR
    // =========================

    public MembershipPlan(
            int planId,
            String planName,
            int durationMonths,
            double fees,
            String description) {

        this.planId = planId;
        this.planName = planName;
        this.durationMonths = durationMonths;
        this.fees = fees;
        this.description = description;
    }

    // =========================
    // PLAN ID
    // =========================

    public int getPlanId() {

        return planId;
    }

    public void setPlanId(int planId) {

        this.planId = planId;
    }

    // =========================
    // PLAN NAME
    // =========================

    public String getPlanName() {

        return planName;
    }

    public void setPlanName(String planName) {

        this.planName = planName;
    }

    // =========================
    // DURATION
    // =========================

    public int getDurationMonths() {

        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {

        this.durationMonths = durationMonths;
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
    // DESCRIPTION
    // =========================

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    // =========================
    // TO STRING
    // =========================

    @Override
    public String toString() {

        return "MembershipPlan{" +
                "planId=" + planId +
                ", planName='" + planName + '\'' +
                ", durationMonths=" + durationMonths +
                ", fees=" + fees +
                ", description='" + description + '\'' +
                '}';
    }
}
