package com.example.placementportal;

public class Opening {
    public String Company;
    public String JobTitle;
    public Double cutoff_cpi = 0.0;
    public String Description;

    public Opening() {}

    public Opening(String Company, String JobTitle, String JobDesc, Double CPI) {
        this.Company = Company;
        this.JobTitle = JobTitle;
        this.Description = JobDesc;
        this.cutoff_cpi = CPI;
    }
}
