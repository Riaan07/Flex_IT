package sr.dlamini.flex_it;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {
    private int Id;
    private String Email;
    private String FacilityId;
    private String DateIn;
    private String TimeIn;
    private String TimeOut;

    public Booking(int id, String email, String facilityId, String dateIn, String timeIn, String timeOut) {
        Id = id;
        Email = email;
        FacilityId = facilityId;
        DateIn = dateIn;
        TimeIn = timeIn;
        TimeOut = timeOut;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFacilityId() {
        return FacilityId;
    }

    public void setFacilityId(String facilityId) {
        FacilityId = facilityId;
    }

    public String getDateIn() {
        return DateIn;
    }

    public void setDateIn(String dateIn) {
        DateIn = dateIn;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public void setTimeIn(String timeIn) {
        TimeIn = timeIn;
    }

    public String getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(String timeOut) {
        TimeOut = timeOut;
    }
}