package za.co.ajk.incidentman.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import za.co.ajk.incidentman.domain.enumeration.EquipmentStatus;

/**
 * A DTO for the Equipment entity.
 */
public class EquipmentDTO implements Serializable {

    private String id;

    @NotNull
    private String equipmentId;

    @NotNull
    private String equipmentName;

    @NotNull
    private ZonedDateTime dateLoadedOnSystem;

    @NotNull
    private String uploadedBy;

    @NotNull
    private EquipmentStatus currentStatus;

    @NotNull
    private ZonedDateTime dateCreated;

    @NotNull
    private ZonedDateTime dateModified;

    private String updatedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public ZonedDateTime getDateLoadedOnSystem() {
        return dateLoadedOnSystem;
    }

    public void setDateLoadedOnSystem(ZonedDateTime dateLoadedOnSystem) {
        this.dateLoadedOnSystem = dateLoadedOnSystem;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public EquipmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(EquipmentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquipmentDTO equipmentDTO = (EquipmentDTO) o;
        if(equipmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipmentDTO{" +
            "id=" + getId() +
            ", equipmentId='" + getEquipmentId() + "'" +
            ", equipmentName='" + getEquipmentName() + "'" +
            ", dateLoadedOnSystem='" + getDateLoadedOnSystem() + "'" +
            ", uploadedBy='" + getUploadedBy() + "'" +
            ", currentStatus='" + getCurrentStatus() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
