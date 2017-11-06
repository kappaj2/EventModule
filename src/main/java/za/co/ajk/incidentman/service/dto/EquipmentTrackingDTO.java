package za.co.ajk.incidentman.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EquipmentTracking entity.
 */
public class EquipmentTrackingDTO implements Serializable {

    private String id;

    @NotNull
    private String equipmentId;

    @NotNull
    private Integer trackingId;

    @NotNull
    private ZonedDateTime dateOnLoan;

    @NotNull
    private String bookedOutBy;

    private ZonedDateTime dateBookedBack;

    private String bookedInBy;

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

    public Integer getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Integer trackingId) {
        this.trackingId = trackingId;
    }

    public ZonedDateTime getDateOnLoan() {
        return dateOnLoan;
    }

    public void setDateOnLoan(ZonedDateTime dateOnLoan) {
        this.dateOnLoan = dateOnLoan;
    }

    public String getBookedOutBy() {
        return bookedOutBy;
    }

    public void setBookedOutBy(String bookedOutBy) {
        this.bookedOutBy = bookedOutBy;
    }

    public ZonedDateTime getDateBookedBack() {
        return dateBookedBack;
    }

    public void setDateBookedBack(ZonedDateTime dateBookedBack) {
        this.dateBookedBack = dateBookedBack;
    }

    public String getBookedInBy() {
        return bookedInBy;
    }

    public void setBookedInBy(String bookedInBy) {
        this.bookedInBy = bookedInBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquipmentTrackingDTO equipmentTrackingDTO = (EquipmentTrackingDTO) o;
        if(equipmentTrackingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipmentTrackingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipmentTrackingDTO{" +
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
