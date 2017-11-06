package za.co.ajk.incidentman.web.rest.testrest;

public class Reservation {
    private Integer id;
    private String reservationName;
    
    public Reservation(Integer id, String reservationName) {
        this.id = id;
        this.reservationName = reservationName;
    }
    
    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }
    public String getReservationName() {
        return reservationName;
    }
    
}


