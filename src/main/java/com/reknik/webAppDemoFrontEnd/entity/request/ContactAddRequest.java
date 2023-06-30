package com.reknik.webAppDemoFrontEnd.entity.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactAddRequest implements Serializable {

    private long employeeId;

    private String phone;

    private String email;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
