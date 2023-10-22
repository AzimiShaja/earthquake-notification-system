/**
 * Earthquake represents information about an earthquake event.
 */
public class Earthquake {

    private int id;
    private int time;
    private String place;
    private String coordinates;
    private double magnitude;

    /**
     * Constructor to initialize an Earthquake object with the provided data.
     *
     * @param id          The unique identifier for the earthquake.
     * @param time        The time of the earthquake event.
     * @param place       The location where the earthquake occurred.
     * @param coordinates The geographical coordinates of the earthquake's
     *                    epicenter.
     * @param magnitude   The magnitude of the earthquake.
     */
    public Earthquake(int id, int time, String place, String coordinates, double magnitude) {
        this.id = id;
        this.time = time;
        this.place = place;
        this.coordinates = coordinates;
        this.magnitude = magnitude;
    }

    /**
     * Get the unique identifier of the earthquake.
     *
     * @return The earthquake's identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the time of the earthquake event.
     *
     * @return The time of the earthquake event.
     */
    public int getTime() {
        return time;
    }

    /**
     * Get the location where the earthquake occurred.
     *
     * @return The location of the earthquake.
     */
    public String getPlace() {
        return place;
    }

    /**
     * Get the geographical coordinates of the earthquake's epicenter.
     *
     * @return The coordinates of the earthquake's epicenter.
     */
    public String getCoordinates() {
        return coordinates;
    }

    /**
     * Get the magnitude of the earthquake.
     *
     * @return The magnitude of the earthquake.
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Create a string representation of the Earthquake object.
     *
     * @return A string containing earthquake information in a human-readable
     *         format.
     */
    public String toString() {
        return "id: " + id + "\n"
                + "time: " + time + "\n"
                + "place: " + place + "\n"
                + "Coordinates: " + coordinates + "\n"
                + "Magnitude: " + magnitude + "\n";
    }
}
