/**
 * Watcher represents information about a watcher and their actions.
 */
public class Watcher {

    private int time;
    private String command;
    private double latitude;
    private double longitude;
    private String name;

    /**
     * Constructor for Watcher with full information, including latitude and
     * longitude.
     *
     * @param time      The time of the watcher's action.
     * @param command   The action command (e.g., 'add', 'delete').
     * @param latitude  The latitude associated with the action.
     * @param longitude The longitude associated with the action.
     * @param name      The name associated with the action (e.g., earthquake
     *                  place).
     */
    public Watcher(int time, String command, double latitude, double longitude, String name) {
        this.time = time;
        this.command = command;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    /**
     * Constructor for Watcher with action and name information, without latitude
     * and longitude.
     *
     * @param time    The time of the watcher's action.
     * @param command The action command (e.g., 'add', 'delete').
     * @param name    The name associated with the action (e.g., earthquake place).
     */
    public Watcher(int time, String command, String name) {
        this.time = time;
        this.command = command;
        this.name = name;
    }

    /**
     * Constructor for Watcher with minimal information, only including time and
     * command.
     *
     * @param time    The time of the watcher's action.
     * @param command The action command (e.g., 'add', 'delete').
     */
    public Watcher(int time, String command) {
        this.time = time;
        this.command = command;
    }

    // Setter methods for various properties
    public void setTime(int time) {
        this.time = time;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter methods for various properties
    public int getTime() {
        return time;
    }

    public String getCommand() {
        return command;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getName() {
        return name;
    }

    /**
     * Create a string representation of the Watcher object.
     *
     * @return A string containing watcher information in a human-readable format.
     */
    @Override
    public String toString() {
        return time + " " + command + " " + latitude + " " + longitude + " " + name + "\n";
    }
}
