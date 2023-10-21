/**
 * Earthquake
 */
public class Earthquake {

    private int id;
    private int time;
    private String place;
    private String coordinates;
    private double magnitude;

    public Earthquake(int id, int time, String place, String coordinates, double magnitude) {
        this.id = id;
        this.time = time;
        this.place = place;
        this.coordinates = coordinates;
        this.magnitude = magnitude;
    }

    public int getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String toString() {
        return "id: " + id + "\n"
                + "time: " + time + "\n"
                + "place: " + place + "\n"
                + "Coordinates: " + coordinates + "\n"
                + "Magnitude: " + magnitude + "\n";
    }
}