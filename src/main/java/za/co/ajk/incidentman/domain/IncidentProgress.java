package za.co.ajk.incidentman.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import za.co.ajk.incidentman.domain.enumeration.Priority;

/**
 * A IncidentProgress.
 */
@Document(collection = "incident_progress")
public class IncidentProgress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("incident_number")
    private Integer incidentNumber;

    @NotNull
    @Field("progress_number")
    private Integer progressNumber;

    @NotNull
    @Field("updated_by")
    private String updatedBy;

    @NotNull
    @Field("date_updated")
    private ZonedDateTime dateUpdated;

    @Field("updated_priority")
    private Priority updatedPriority;

    @NotNull
    @Field("progress_description")
    private String progressDescription;

    @Field("loan_equipment")
    private String loanEquipment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIncidentNumber() {
        return incidentNumber;
    }

    public IncidentProgress incidentNumber(Integer incidentNumber) {
        this.incidentNumber = incidentNumber;
        return this;
    }

    public void setIncidentNumber(Integer incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    public Integer getProgressNumber() {
        return progressNumber;
    }

    public IncidentProgress progressNumber(Integer progressNumber) {
        this.progressNumber = progressNumber;
        return this;
    }

    public void setProgressNumber(Integer progressNumber) {
        this.progressNumber = progressNumber;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public IncidentProgress updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getDateUpdated() {
        return dateUpdated;
    }

    public IncidentProgress dateUpdated(ZonedDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(ZonedDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Priority getUpdatedPriority() {
        return updatedPriority;
    }

    public IncidentProgress updatedPriority(Priority updatedPriority) {
        this.updatedPriority = updatedPriority;
        return this;
    }

    public void setUpdatedPriority(Priority updatedPriority) {
        this.updatedPriority = updatedPriority;
    }

    public String getProgressDescription() {
        return progressDescription;
    }

    public IncidentProgress progressDescription(String progressDescription) {
        this.progressDescription = progressDescription;
        return this;
    }

    public void setProgressDescription(String progressDescription) {
        this.progressDescription = progressDescription;
    }

    public String getLoanEquipment() {
        return loanEquipment;
    }

    public IncidentProgress loanEquipment(String loanEquipment) {
        this.loanEquipment = loanEquipment;
        return this;
    }

    public void setLoanEquipment(String loanEquipment) {
        this.loanEquipment = loanEquipment;
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
        IncidentProgress incidentProgress = (IncidentProgress) o;
        if (incidentProgress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentProgress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentProgress{" +
            "id=" + getId() +
            ", incidentNumber='" + getIncidentNumber() + "'" +
            ", progressNumber='" + getProgressNumber() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", updatedPriority='" + getUpdatedPriority() + "'" +
            ", progressDescription='" + getProgressDescription() + "'" +
            ", loanEquipment='" + getLoanEquipment() + "'" +
            "}";
    }
}
