package com.example.placementportal;

public class Opening {
    public String Company;
    public String JobTitle;
    public int [] years;
    public Double cutoff_cpi = 0.0;
    public String job_profile;
    public String Description;
    public String brochureURL;

    public Opening() {}

    public Opening(String Company, String JobTitle, String JobDesc, Double CPI) {
        this.Company = Company;
        this.JobTitle = JobTitle;
        this.Description = JobDesc;
        this.cutoff_cpi = CPI;
    }
}
