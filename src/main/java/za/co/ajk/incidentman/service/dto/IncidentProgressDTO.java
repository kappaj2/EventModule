package za.co.ajk.incidentman.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import za.co.ajk.incidentman.domain.enumeration.Priority;

/**
 * A DTO for the IncidentProgress entity.
 */
public class IncidentProgressDTO implements Serializable {

    private String id;

    @NotNull
    private Integer incidentNumber;

    @NotNull
    private Integer progressNumber;

    @NotNull
    private String updatedBy;

    @NotNull
    private ZonedDateTime dateUpdated;

    private Priority updatedPriority;

    @NotNull
    private String progressDescription;

    private String loanEquipment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIncidentNumber() {
        return incidentNumber;
    }

    public void setIncidentNumber(Integer incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    public Integer getProgressNumber() {
        return progressNumber;
    }

    public void setProgressNumber(Integer progressNumber) {
        this.progressNumber = progressNumber;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(ZonedDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Priority getUpdatedPriority() {
        return updatedPriority;
    }

    public void setUpdatedPriority(Priority updatedPriority) {
        this.updatedPriority = updatedPriority;
    }

    public String getProgressDescription() {
        return progressDescription;
    }

    public void setProgressDescription(String progressDescription) {
        this.progressDescription = progressDescription;
    }

    public String getLoanEquipment() {
        return loanEquipment;
    }

    public void setLoanEquipment(String loanEquipment) {
        this.loanEquipment = loanEquipment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidentProgressDTO incidentProgressDTO = (IncidentProgressDTO) o;
        if(incidentProgressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentProgressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentProgressDTO{" +
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
