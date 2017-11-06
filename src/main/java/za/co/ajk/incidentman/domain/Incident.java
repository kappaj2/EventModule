package za.co.ajk.incidentman.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import io.undertow.security.idm.Account;
import za.co.ajk.incidentman.domain.enumeration.Priority;

import za.co.ajk.incidentman.domain.enumeration.IncidentStatus;

/**
 * A Incident.
 */
@Document(collection = "incident")
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("company_key")
    private String companyKey;

    @NotNull
    @Field("incident_number")
    private Integer incidentNumber;

    @NotNull
    @Field("logged_by")
    private String loggedBy;

    @NotNull
    @Field("date_logged")
    private ZonedDateTime dateLogged;

    @NotNull
    @Field("priority")
    private Priority priority;

    @NotNull
    @Field("incident_status")
    private IncidentStatus incidentStatus;

    @NotNull
    @Field("incident_description")
    private String incidentDescription;

    @Field("screen_capture")
    private byte[] screenCapture;

    @Field("screen_capture_content_type")
    private String screenCaptureContentType;
    
    @DBRef
    private List<IncidentProgress> indcidentProgress;
    
    
    public List<IncidentProgress> getIndcidentProgress() {
        return indcidentProgress;
    }
    
    public void setIndcidentProgress(List<IncidentProgress> indcidentProgress) {
        this.indcidentProgress = indcidentProgress;
    }
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyKey() {
        return companyKey;
    }

    public Incident companyKey(String companyKey) {
        this.companyKey = companyKey;
        return this;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }

    public Integer getIncidentNumber() {
        return incidentNumber;
    }

    public Incident incidentNumber(Integer incidentNumber) {
        this.incidentNumber = incidentNumber;
        return this;
    }

    public void setIncidentNumber(Integer incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    public String getLoggedBy() {
        return loggedBy;
    }

    public Incident loggedBy(String loggedBy) {
        this.loggedBy = loggedBy;
        return this;
    }

    public void setLoggedBy(String loggedBy) {
        this.loggedBy = loggedBy;
    }

    public ZonedDateTime getDateLogged() {
        return dateLogged;
    }

    public Incident dateLogged(ZonedDateTime dateLogged) {
        this.dateLogged = dateLogged;
        return this;
    }

    public void setDateLogged(ZonedDateTime dateLogged) {
        this.dateLogged = dateLogged;
    }

    public Priority getPriority() {
        return priority;
    }

    public Incident priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public Incident incidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
        return this;
    }

    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public Incident incidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
        return this;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public byte[] getScreenCapture() {
        return screenCapture;
    }

    public Incident screenCapture(byte[] screenCapture) {
        this.screenCapture = screenCapture;
        return this;
    }

    public void setScreenCapture(byte[] screenCapture) {
        this.screenCapture = screenCapture;
    }

    public String getScreenCaptureContentType() {
        return screenCaptureContentType;
    }

    public Incident screenCaptureContentType(String screenCaptureContentType) {
        this.screenCaptureContentType = screenCaptureContentType;
        return this;
    }

    public void setScreenCaptureContentType(String screenCaptureContentType) {
        this.screenCaptureContentType = screenCaptureContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Incident incident = (Incident) o;
        if (incident.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incident.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Incident{" +
            "id=" + getId() +
            ", companyKey='" + getCompanyKey() + "'" +
            ", incidentNumber='" + getIncidentNumber() + "'" +
            ", loggedBy='" + getLoggedBy() + "'" +
            ", dateLogged='" + getDateLogged() + "'" +
            ", priority='" + getPriority() + "'" +
            ", incidentStatus='" + getIncidentStatus() + "'" +
            ", incidentDescription='" + getIncidentDescription() + "'" +
            ", screenCapture='" + getScreenCapture() + "'" +
            ", screenCaptureContentType='" + screenCaptureContentType + "'" +
            "}";
    }
}
