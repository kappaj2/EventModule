package za.co.ajk.incidentman.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import za.co.ajk.incidentman.domain.enumeration.Priority;
import za.co.ajk.incidentman.domain.enumeration.IncidentStatus;

/**
 * A DTO for the Incident entity.
 */
public class IncidentDTO implements Serializable {

    private String id;

    @NotNull
    private String companyKey;

    @NotNull
    private Integer incidentNumber;

    @NotNull
    private String loggedBy;

    @NotNull
    private ZonedDateTime dateLogged;

    @NotNull
    private Priority priority;

    @NotNull
    private IncidentStatus incidentStatus;

    @NotNull
    private String incidentDescription;

    private byte[] screenCapture;
    private String screenCaptureContentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }

    public Integer getIncidentNumber() {
        return incidentNumber;
    }

    public void setIncidentNumber(Integer incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    public String getLoggedBy() {
        return loggedBy;
    }

    public void setLoggedBy(String loggedBy) {
        this.loggedBy = loggedBy;
    }

    public ZonedDateTime getDateLogged() {
        return dateLogged;
    }

    public void setDateLogged(ZonedDateTime dateLogged) {
        this.dateLogged = dateLogged;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public byte[] getScreenCapture() {
        return screenCapture;
    }

    public void setScreenCapture(byte[] screenCapture) {
        this.screenCapture = screenCapture;
    }

    public String getScreenCaptureContentType() {
        return screenCaptureContentType;
    }

    public void setScreenCaptureContentType(String screenCaptureContentType) {
        this.screenCaptureContentType = screenCaptureContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidentDTO incidentDTO = (IncidentDTO) o;
        if(incidentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentDTO{" +
            "id=" + getId() +
            ", companyKey='" + getCompanyKey() + "'" +
            ", incidentNumber='" + getIncidentNumber() + "'" +
            ", loggedBy='" + getLoggedBy() + "'" +
            ", dateLogged='" + getDateLogged() + "'" +
            ", priority='" + getPriority() + "'" +
            ", incidentStatus='" + getIncidentStatus() + "'" +
            ", incidentDescription='" + getIncidentDescription() + "'" +
            ", screenCapture='" + getScreenCapture() + "'" +
            "}";
    }
}
