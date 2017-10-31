package za.co.ajk.incidentman.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A EquipmentTracking.
 */
@Document(collection = "equipment_tracking")
public class EquipmentTracking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("equipment_id")
    private String equipmentId;

    @NotNull
    @Field("tracking_id")
    private Integer trackingId;

    @NotNull
    @Field("date_on_loan")
    private ZonedDateTime dateOnLoan;

    @NotNull
    @Field("booked_out_by")
    private String bookedOutBy;

    @Field("date_booked_back")
    private ZonedDateTime dateBookedBack;

    @Field("booked_in_by")
    private String bookedInBy;

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

    public EquipmentTracking equipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
        return this;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getTrackingId() {
        return trackingId;
    }

    public EquipmentTracking trackingId(Integer trackingId) {
        this.trackingId = trackingId;
        return this;
    }

    public void setTrackingId(Integer trackingId) {
        this.trackingId = trackingId;
    }

    public ZonedDateTime getDateOnLoan() {
        return dateOnLoan;
    }

    public EquipmentTracking dateOnLoan(ZonedDateTime dateOnLoan) {
        this.dateOnLoan = dateOnLoan;
        return this;
    }

    public void setDateOnLoan(ZonedDateTime dateOnLoan) {
        this.dateOnLoan = dateOnLoan;
    }

    public String getBookedOutBy() {
        return bookedOutBy;
    }

    public EquipmentTracking bookedOutBy(String bookedOutBy) {
        this.bookedOutBy = bookedOutBy;
        return this;
    }

    public void setBookedOutBy(String bookedOutBy) {
        this.bookedOutBy = bookedOutBy;
    }

    public ZonedDateTime getDateBookedBack() {
        return dateBookedBack;
    }

    public EquipmentTracking dateBookedBack(ZonedDateTime dateBookedBack) {
        this.dateBookedBack = dateBookedBack;
        return this;
    }

    public void setDateBookedBack(ZonedDateTime dateBookedBack) {
        this.dateBookedBack = dateBookedBack;
    }

    public String getBookedInBy() {
        return bookedInBy;
    }

    public EquipmentTracking bookedInBy(String bookedInBy) {
        this.bookedInBy = bookedInBy;
        return this;
    }

    public void setBookedInBy(String bookedInBy) {
        this.bookedInBy = bookedInBy;
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
        EquipmentTracking equipmentTracking = (EquipmentTracking) o;
        if (equipmentTracking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipmentTracking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipmentTracking{" +
            "id=" + getId() +
            ", equipmentId='" + getEquipmentId() + "'" +
            ", trackingId='" + getTrackingId() + "'" +
            ", dateOnLoan='" + getDateOnLoan() + "'" +
            ", bookedOutBy='" + getBookedOutBy() + "'" +
            ", dateBookedBack='" + getDateBookedBack() + "'" +
            ", bookedInBy='" + getBookedInBy() + "'" +
            "}";
    }
}
