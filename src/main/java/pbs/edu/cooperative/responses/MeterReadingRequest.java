// src/main/java/pbs/edu/cooperative/requests/MeterReadingRequest.java
package pbs.edu.cooperative.responses;

import java.time.LocalDate;

public class MeterReadingRequest {
    private double reading;
    private LocalDate readingDate;
    private String tenantName;
    private String tenantSurname;

    // Getters and setters
    public double getReading() {
        return reading;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }

    public LocalDate getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(LocalDate readingDate) {
        this.readingDate = readingDate;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantSurname() {
        return tenantSurname;
    }

    public void setTenantSurname(String tenantSurname) {
        this.tenantSurname = tenantSurname;
    }
}