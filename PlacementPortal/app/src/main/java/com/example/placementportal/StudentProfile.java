package com.example.placementportal;

public class StudentProfile {
    public String name,bio,branch;
    public Double CPI;

    public StudentProfile() {}
    public StudentProfile(String name, String branch, Double CPI) {
        this.branch = branch;
        this.name = name;
        this.CPI = CPI;
    }
}
