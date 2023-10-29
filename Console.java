
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
     * @throws InterruptedException if a thread is interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        readFromWatcher(); // Reads data from watcher.txt
        readFromEarthquake(); // Reads data from earthquake.txt

        // Current fictional time initialization
        int j = 0;
        int i = 0;
        int k = 0;

        int eTime = eTemp.get(j).getTime();
        int wTime = wTemp.get(i).getTime();

        // Loop until all data from Watchers or Earthquakes is processed
        while (i < wTemp.size() || j < eTemp.size()) {
            System.out.println("+=========== " + k + " ===========+");

            // Process actions for Watchers
            while (k == wTime && i < wTemp.size()) {
                if (wTemp.get(i).getCommand().equals("add")) {
                    // Add watcher to the list
                    watcherList.add(wTemp.get(i));
                    System.out.println(wTemp.get(i).getName() + " is added to watcher List\n");

                } else if (wTemp.get(i).getCommand().equals("delete")) {
                    // Remove watcher from the list
                    String name = wTemp.get(i).getName();
                    watcherList.remove(name);
                    System.out.println(name + " is deleted from list\n");

                } else {
                    // Process action when the command is neither 'add' nor 'delete'
                    Earthquake largest = earthquakeList.getLargestEarthquake(); // Retrieve the largest earthquake

                    if (largest != null) {
                        // Display information about the largest earthquake in the past 6 hours
                        System.out.println("Largest earthquake in the past 6 hours:");
                        System.out.println("Magnitude " + largest.getMagnitude() + " at " + largest.getPlace() + "\n");
                    } else {
                        System.out.println("No record found \n"); // Display if no earthquake record is found
                    }
                }
                i++;
                if (i < wTemp.size()) {
                    wTime = wTemp.get(i).getTime(); // Update wTime with the new value
                }
            }

            // Process Earthquake actions
            while (k == eTime && j < eTemp.size()) {
                // Add earthquake to the list
                earthquakeList.add(eTemp.get(j));
                System.out.println("Earthquake " + eTemp.get(j).getPlace() + " is inserted to earthquake-list\n");

                // Notify watchers close to the earthquake
                earthquakeList.notifyWatcherCloseToEarthquake(earthquakeList, watcherList);
                j++;
                if (j < eTemp.size()) {
                    eTime = eTemp.get(j).getTime(); // Update eTime with the new value
                }
            }

            if (eTime - k >= 6) {
                earthquakeList.remove(); // Remove the earthquake if it's been more than 6 hours
            }

            // Potential thread sleep
            // Thread.sleep(1000); // Not currently active, potential thread sleep
            k++; // Increment time
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

                if (wLine.contains("add")) {
                    // Add new watcher data
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];
                    Double latitude = Double.parseDouble(order[2]);
                    Double longitude = Double.parseDouble(order[3]);
                    String name = order[4];

                    wTemp.add(new Watcher(time, command, latitude, longitude, name));

                } else if (wLine.contains("delete")) {
                    // Delete watcher data
                    String name = order[2];
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];
                    wTemp.add(new Watcher(time, command, name));

                } else {
                    // Other watcher action
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];

                    wTemp.add(new Watcher(time, command));
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
