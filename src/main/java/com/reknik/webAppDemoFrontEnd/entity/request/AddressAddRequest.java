package com.reknik.webAppDemoFrontEnd.entity.request;

import java.io.Serializable;

public class AddressAddRequest implements Serializable {

    private long employeeId;

    private String city;

    private String addressDetails;

    private String postalCode;

    private String country;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(final long employeeId) {
        this.employeeId = employeeId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(final String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }
}
