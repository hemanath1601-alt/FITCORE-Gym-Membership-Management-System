package com.gym.model;

import java.sql.Timestamp;
import java.sql.Date;

public class Attendance {

    private int attendanceId;
    private int memberId;
    private Timestamp checkIn;
    private Timestamp checkOut;
    private Date attendanceDate;

    // Default Constructor
    public Attendance() {
    }

    // Constructor
    public Attendance(int attendanceId, int memberId,
                      Timestamp checkIn, Timestamp checkOut,
                      Date attendanceDate) {

        this.attendanceId = attendanceId;
        this.memberId = memberId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.attendanceDate = attendanceDate;
    }

    // Getters and Setters

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Timestamp getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Timestamp checkIn) {
        this.checkIn = checkIn;
    }

    public Timestamp getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Timestamp checkOut) {
        this.checkOut = checkOut;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
}