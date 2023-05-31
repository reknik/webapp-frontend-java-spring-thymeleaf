package com.reknik.webAppDemoFrontEnd.entity.dto;

import java.io.Serializable;

public class JobDTO implements Serializable {

    private long id;

    private String title;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
