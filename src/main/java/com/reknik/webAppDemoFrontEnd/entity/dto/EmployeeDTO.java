package com.reknik.webAppDemoFrontEnd.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class EmployeeDTO {
    private long id;
    private String firstName;
    private String lastName;
    private boolean drivingLicense;
    private List<Long> addresses;
    private List<Long> contacts;
    private List<Long> companies;
    private List<Long> jobs;
    private Date birthDate;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public boolean isDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(final boolean drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public List<Long> getAddresses() {
        return addresses;
    }

    public void setAddresses(final List<Long> addresses) {
        this.addresses = addresses;
    }

    public List<Long> getContacts() {
        return contacts;
    }

    public void setContacts(final List<Long> contacts) {
        this.contacts = contacts;
    }

    public List<Long> getCompanies() {
        return companies;
    }

    public void setCompanies(final List<Long> companies) {
        this.companies = companies;
    }

    public List<Long> getJobs() {
        return jobs;
    }

    public void setJobs(final List<Long> jobs) {
        this.jobs = jobs;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }
}
