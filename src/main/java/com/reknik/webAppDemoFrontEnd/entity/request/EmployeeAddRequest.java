package com.reknik.webAppDemoFrontEnd.entity.request;

import com.reknik.webAppDemoFrontEnd.entity.Address;
import com.reknik.webAppDemoFrontEnd.entity.Contact;
import com.reknik.webAppDemoFrontEnd.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAddRequest implements Serializable {

    private long id;

    private String firstName;

    private String lastName;

    private boolean drivingLicense;

    private Address address = new Address();

    private Contact contact = new Contact();

    private Long companyId;

    private Job job = new Job();

    private int birthYear;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(final Contact contact) {
        this.contact = contact;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(final Job job) {
        this.job = job;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(final int birthYear) {
        this.birthYear = birthYear;
    }
}
