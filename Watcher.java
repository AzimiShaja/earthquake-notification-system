public class Watcher {

    private int time;
    private String command;
    private double latitude;
    private double longitude;
    private String name;

    public Watcher(int time, String command, double latitude, double longitude, String name) {
        this.time = time;
        this.command = command;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public Watcher(int time, String command, String name) {
        this.time = time;
        this.command = command;
        this.name = name;
    }

    public Watcher(int time, String command) {
        this.time = time;
        this.command = command;

    }

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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return time + " " + command + " " + latitude + " " + longitude + " " + name + "\n";
    }
}
