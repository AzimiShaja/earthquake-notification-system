
/**
 * The Console class manages Earthquake and Watcher data from input files.
 *
 * It reads data from 'watcher.txt' and 'earthquake.txt' files, processes the information, and performs actions based on specific commands from the input.
 * This class uses EarthquakeList and WatcherList to maintain the respective data.
 * 
 * @author Ahmad Shaja AZIMI - 64220003
 */
import java.io.File; // Import File class to handle file operations
import java.util.ArrayList; // Import ArrayList for dynamic array handling
import java.util.Scanner; // Import Scanner to read input

public class Console {

    // Temporary lists for Earthquakes and Watchers
    private static ArrayList<Earthquake> eTemp = new ArrayList<>();
    private static ArrayList<Watcher> wTemp = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in); // Scanner to read input

    // Lists to hold Earthquake and Watcher data
    private static EarthquakeList earthquakeList = new EarthquakeList();
    private static WatcherList watcherList = new WatcherList();

    /**
     * The main method processes Watcher and Earthquake data, performs actions based
     * on input, and manages temporal time.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        readFromWatcher(); // Reads data from watcher.txt
        readFromEarthquake(); // Reads data from earthquake.txt

        int wIth = 0; // the ith element in the watcher list
        int eIth = 0; // the jth element in the Earthquake list

        // to make sure returns the most correct earthquake.
        boolean isQl = false;
        // Current fictional time initialization
        int now = 0;

        // earthquake and watcher timestamp
        int eTime = eTemp.get(eIth).getTime();
        int wTime = wTemp.get(wIth).getTime();

        // Loop until all data from Watchers or Earthquakes is processed
        while (wIth < wTemp.size() || eIth < eTemp.size()) {
            // Process actions for Watchers
            while (now == wTime && wIth < wTemp.size()) {
                switch (wTemp.get(wIth).getCommand()) {
                    case "add":
                        // Add watcher to the list
                        watcherList.add(wTemp.get(wIth));
                        System.out.println("\n" + wTemp.get(wIth).getName() + " is added to watcher-list\n");
                        break;

                    case "delete":
                        // Remove watcher from the list
                        String name = wTemp.get(wIth).getName();
                        watcherList.remove(name);
                        System.out.println("\n" + name + " is deleted from watcher-list\n");
                        break;

                    default:
                        // Process query-largest when the command is neither 'add' nor 'delete'
                        isQl = true;
                        break;
                }
                wIth++;
                if (wIth < wTemp.size()) {
                    wTime = wTemp.get(wIth).getTime(); // Update wTime with the new value
                }

            }

            // Process Earthquake actions
            while (now == eTime && eIth < eTemp.size()) {
                // Add earthquake to the list
                earthquakeList.add(eTemp.get(eIth));
                System.out.println("Earthquake " + eTemp.get(eIth).getPlace() + " is inserted to earthquake-list");

                // Notify watchers close to the earthquake
                earthquakeList.notifyWatcherCloseToEarthquake(eTemp.get(eIth), watcherList);

                eIth++;
                if (eIth < eTemp.size()) {
                    eTime = eTemp.get(eIth).getTime(); // Update eTime with the new value
                }
            }

            if (earthquakeList.getFirstElement() != null) {
                while (now - earthquakeList.getFirstElement().getTime() > 6) {
                    earthquakeList.remove(); // Remove the earthquake if it's been more than 6 hours
                    if (earthquakeList.length() == 0) {
                        break;
                    }
                }
            }

            if (isQl) {
                Earthquake largest = earthquakeList.getLargestEarthquake(); // Retrieve the largest earthquake
                if (largest != null) {
                    // Display information about the largest earthquake in the past 6 hours
                    System.out.println("\nLargest earthquake in the past 6 hours:");
                    System.out.println("Magnitude " + largest.getMagnitude() + " at " + largest.getPlace() + "\n");
                } else {
                    System.out.println("No record found \n"); // Display if no earthquake record is found
                }
                isQl = false;
            }

            now++; // Increment current time
        }

        // Clear the temporary lists
        eTemp.clear();
        wTemp.clear();
    }

    /**
     * Method to read data from the watcher file and populate the temporary list.
     */
    private static void readFromWatcher() {
        System.out.print("Please Enter the filepath for watcher: ");
        String filepath = sc.nextLine();
        try {
            File file1 = new File(filepath);

            Scanner sc1 = new Scanner(file1);

            while (sc1.hasNextLine()) {
                String wLine = sc1.nextLine();
                String[] order = wLine.split(" ");

                switch (order[1]) {
                    case "add":
                        // Add new watcher data
                        int time = Integer.parseInt(order[0]);
                        String command = order[1];
                        Double latitude = Double.parseDouble(order[2]);
                        Double longitude = Double.parseDouble(order[3]);
                        String name = order[4];

                        wTemp.add(new Watcher(time, command, latitude, longitude, name));
                        break;

                    case "delete":
                        name = order[2];
                        time = Integer.parseInt(order[0]);
                        command = order[1];
                        wTemp.add(new Watcher(time, command, name));
                        break;

                    default:
                        // add query-largest to temp arrayList
                        time = Integer.parseInt(order[0]);
                        command = order[1];
                        wTemp.add(new Watcher(time, command));
                        break;
                }
            }
            sc1.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to read data from the earthquake file and populate the temporary list.
     */
    private static void readFromEarthquake() {
        System.out.print("Please enter the filepath for earthquake: ");
        String filepath = sc.nextLine();
        try {
            File file2 = new File(filepath);
            Scanner sc1 = new Scanner(file2);

            int time = 0;
            String place = "";
            String coordinates = "";
            String magnitude = "";
            String id = " ";

            while (sc1.hasNextLine()) {
                String eLine = sc1.nextLine();

                if (eLine.contains("<id>")) {
                    id = eLine.replaceAll("<id>|</id>", "").trim();
                    // Process the other fields related to an earthquake within this block
                }
                if (eLine.contains("<time>")) {
                    time = Integer.parseInt(eLine.replaceAll("<time>|</time>", "").trim());
                } else if (eLine.contains("<place>")) {
                    place = eLine.replaceAll("<place>|</place>", "").trim();
                } else if (eLine.contains("<coordinates>")) {
                    coordinates = eLine.replaceAll("<coordinates>|</coordinates>", "").trim();
                } else if (eLine.contains("<magnitude>")) {
                    magnitude = eLine.replaceAll("<magnitude>|</magnitude>", "").trim();
                }

                if (eLine.contains("</earthquake>")) { // End of a tag
                    // Create a new Earthquake object and add it to the earthquakeList
                    eTemp.add(new Earthquake(Integer.parseInt(id), time, place, coordinates,
                            Double.parseDouble(magnitude)));
                    // Clear the variables for the next entry.
                    id = "";
                    place = "";
                    coordinates = "";
                    magnitude = "";
                }
            }
            sc1.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}