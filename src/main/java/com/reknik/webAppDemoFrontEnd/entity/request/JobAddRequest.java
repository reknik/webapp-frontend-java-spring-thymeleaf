package com.reknik.webAppDemoFrontEnd.entity.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class JobAddRequest implements Serializable {

    private long employeeId;

    private String title;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(final long employeeId) {
        this.employeeId = employeeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
