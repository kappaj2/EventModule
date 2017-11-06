package za.co.ajk.incidentman.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import za.co.ajk.incidentman.domain.enumeration.EquipmentStatus;

/**
 * A Equipment.
 */
@Document(collection = "equipment")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("equipment_id")
    private String equipmentId;

    @NotNull
    @Field("equipment_name")
    private String equipmentName;

    @NotNull
    @Field("date_loaded_on_system")
    private ZonedDateTime dateLoadedOnSystem;

    @NotNull
    @Field("uploaded_by")
    private String uploadedBy;

    @NotNull
    @Field("current_status")
    private EquipmentStatus currentStatus;

    @NotNull
    @Field("date_created")
    private ZonedDateTime dateCreated;

    @NotNull
    @Field("date_modified")
    private ZonedDateTime dateModified;

    @Field("updated_by")
    private String updatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public Equipment equipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
        return this;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public Equipment equipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
        return this;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public ZonedDateTime getDateLoadedOnSystem() {
        return dateLoadedOnSystem;
    }

    public Equipment dateLoadedOnSystem(ZonedDateTime dateLoadedOnSystem) {
        this.dateLoadedOnSystem = dateLoadedOnSystem;
        return this;
    }

    public void setDateLoadedOnSystem(ZonedDateTime dateLoadedOnSystem) {
        this.dateLoadedOnSystem = dateLoadedOnSystem;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public Equipment uploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
        return this;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public EquipmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public Equipment currentStatus(EquipmentStatus currentStatus) {
        this.currentStatus = currentStatus;
        return this;
    }

    public void setCurrentStatus(EquipmentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Equipment dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public Equipment dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Equipment updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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
        Equipment equipment = (Equipment) o;
        if (equipment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Equipment{" +
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
