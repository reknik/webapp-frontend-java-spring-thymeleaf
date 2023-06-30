package com.reknik.webAppDemoFrontEnd.entity;

import com.reknik.webAppDemoFrontEnd.entity.dto.CompanyDTO;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Employee {

    private int id;

    private String firstName;

    private String lastName;

    private boolean drivingLicense;

    private List<Address> addresses = new ArrayList<>(List.of(new Address()));

    private List<Contact> contacts = new ArrayList<>(List.of(new Contact()));

    private Set<CompanyDTO> companies = new HashSet<>(Set.of(new CompanyDTO()));

    private Set<Job> jobs = new HashSet<>(Set.of(new Job()));

    private int birthYear;

    public int getAge() {
        return Year.now().getValue() - birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(boolean drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<CompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<CompanyDTO> companies) {
        this.companies = companies;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String printCompanies() {
        StringBuilder builder = new StringBuilder();
        this.companies.forEach(company -> builder.append(company.getName()).append(" ; "));
        return builder.toString();
    }

    public String printJobs() {
        StringBuilder builder = new StringBuilder();
        this.jobs.forEach(job -> builder.append(job.getTitle()).append(" ; "));
        return builder.toString();
    }

    public String printContacts() {
        StringBuilder builder = new StringBuilder();
        this.contacts.forEach(
                contact -> builder.append(contact.getEmail()).append('\n').append(contact.getPhone()).append('\n'));
        return builder.toString();
    }

    public String printAddresses() {
        StringBuilder builder = new StringBuilder();
        this.addresses.forEach(
                address -> builder.append(address.getAddressDetails()).append(" ").append(address.getCity()).append(" ")
                        .append(address.getCountry()).append(" ; "));
        return builder.toString();
    }


}
