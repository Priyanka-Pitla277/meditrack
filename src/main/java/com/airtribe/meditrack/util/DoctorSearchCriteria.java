package com.airtribe.meditrack.util;

public class DoctorSearchCriteria {
    private String name;
    private String specialization;
    private Integer minExperience;
    private Double maxConsultationFee;

    // Fluent Builder for clean criteria assignment
    public static class Builder {
        private final DoctorSearchCriteria criteria = new DoctorSearchCriteria();
        public Builder name(String name) { criteria.name = name; return this; }
        public Builder specialization(String specialization) { criteria.specialization = specialization; return this; }
        public Builder minExperience(Integer exp) { criteria.minExperience = exp; return this; }
        public Builder maxConsultationFee(Double fee) { criteria.maxConsultationFee = fee; return this; }
        public DoctorSearchCriteria build() { return criteria; }
    }

    // Getters
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public Integer getMinExperience() { return minExperience; }
    public Double getMaxConsultationFee() { return maxConsultationFee; }
}
