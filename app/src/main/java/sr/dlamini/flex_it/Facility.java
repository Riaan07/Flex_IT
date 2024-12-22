package sr.dlamini.flex_it;

import androidx.annotation.NonNull;

public class Facility {
    private int Id;
    private String FacilityName;
    private int Capacity;
    private int Price;

    public Facility(int id, String facilityName, int capacity, int price) {
        Id = id;
        FacilityName = facilityName;
        Capacity = capacity;
        Price = price;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFacilityName() {
        return FacilityName;
    }

    public void setFacilityName(String facilityName) {
        FacilityName = facilityName;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return getFacilityName() + "\n" +
                "Facility Price:    R" + getPrice() + ".00\n" +
                "Facility Capacity: " + getCapacity() + "\n";
    }
}
