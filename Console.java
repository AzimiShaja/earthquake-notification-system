import java.util.Scanner;
import java.io.File;

/*
*  @author Ahmad Shaja AZIMI - 64220003 
*/
public class Console {

    private static EarthquakeList earthquakeList = new EarthquakeList(); // Create an instance of EarthquakeList.
    private static WatcherList watcherList = new WatcherList(); // Create an instance of WatcherList.
    private static Scanner sc = new Scanner(System.in); // Create a Scanner to read input from the console.

    public static void main(String[] args) {

        System.out.print("Enter the file path for earthquake records: ");

        // Read the user's input as the file path for earthquake records.
        String eFile = sc.nextLine();

        System.out.print("Enter the file path for watcher records:");

        String wFile = sc.nextLine(); // Read the user's input as the file path for watcher records.
        System.out.println("\n");

        readFromEarthquakeFile(eFile); // Call a method to read and process earthquake records from the provided file.

        readFromWatcherFile(wFile); // Call a method to read and process watcher

        // records from the provided file.

        // check if there is a earthquake close to watcher
        earthquakeList.notifyWatcherCloseToEarthquake(earthquakeList, watcherList);

    }

    /**
     * Reads data from a file, parses the content, and performs actions based on the
     * data.
     * The file is expected to have lines in a specific format, with commands like
     * 'add', 'delete',
     * or other actions, and this method processes these commands accordingly.
     *
     * @param wFile The file path to read data from.
     */
    public static void readFromWatcherFile(String wFile) {
        Scanner inputStream = null; // Declare a Scanner to read from the input file.
        try {
            File file = new File(wFile); // Create a File object from the provided file path.

            if (file.exists()) {
                inputStream = new Scanner(file); // If the file exists, initialize the Scanner to read from it.
            } else {
                System.out.println("File not found;"); // Print an error message if the file doesn't exist.
            }

        } catch (Exception e) {
            System.err.println("Error While reading file " + wFile);
        }

        try {
            while (inputStream.hasNextLine()) {
                // Read the next line from the file.
                String line = inputStream.nextLine();

                // Split the line into an array based on space (' ') as a delimiter.
                String[] order = line.split(" ");

                if (order[1].equals("add")) {
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];
                    double latitude = Double.parseDouble(order[2]);
                    double longitude = Double.parseDouble(order[3]);
                    String name = order[4];

                    // Create a new Watcher object with the parsed data and add it to a list.
                    watcherList.add(new Watcher(time, command, latitude, longitude, name));
                    System.out.println(name + " is added to the watcher-list \n");

                } else if (order[1].equals("delete")) {

                    String name = order[2];

                    // Remove a Watcher object with the specified data from the list.w
                    watcherList.remove(name);
                    System.out.println(name + " is deleted from the watcher-list \n");

                } else if (order[1].equals("query-largest")) {
                    // Create a new Watcher object with the parsed data and add it to a list.
                    Earthquake largest = earthquakeList.getLargestEarthquake();
                    if (largest != null) {
                        System.out.println("Largest earthquake in the past 6 hours:\n");

                        // Get the largest earthquake from an earthquake list and display its
                        // information.

                        System.out.println("Magnitude " + largest.getMagnitude() + " at " +
                                largest.getPlace() + "\n");
                    } else {
                        System.out.println("No record found");
                    }

                }
            }
            inputStream.close();
        } catch (Exception e) {
            // Handle any exceptions that may occur during
            // data parsing.
            System.err.println("Error while parsing data from file");
        }
    }

    /**
     * Reads earthquake data from an XML file, parses the content, and adds it to an
     * EarthquakeList.
     *
     * @param eFile The file path to read earthquake data from.
     */
    public static void readFromEarthquakeFile(String eFile) {
        try {
            File xmlFile = new File(eFile); // Create a File object for the earthquake file
            Scanner scanner = new Scanner(xmlFile); // Create a scanner to read the file

            String id = "";
            String time = "";
            String place = "";
            String coordinates = "";
            String magnitude = "";

            while (scanner.hasNextLine()) { // Read the file line by line
                String line = scanner.nextLine();

                // Extract data from earthquake file using replaceAll method + trim
                if (line.contains("<id>")) {
                    id = line.replaceAll("<id>|</id>", "").trim();

                } else if (line.contains("<time>")) {
                    time = line.replaceAll("<time>|</time>", "").trim();

                } else if (line.contains("<place>")) {
                    place = line.replaceAll("<place>|</place>", "").trim();

                } else if (line.contains("<coordinates>")) {
                    coordinates = line.replaceAll("<coordinates>|</coordinates>", "").trim();

                } else if (line.contains("<magnitude>")) {
                    magnitude = line.replaceAll("<magnitude>|</magnitude>", "").trim();

                }

                if (line.contains("</earthquake>")) { // end of a tag
                    // Create a new Earthquake object and add it to the earthquakeList
                    earthquakeList.add(new Earthquake(Integer.parseInt(id), Integer.parseInt(time), place, coordinates,
                            Double.parseDouble(magnitude)));

                    System.out.println("Earthquake " + place + " is inserted into the earthquake-list \n");

                    // Clear the variables for the next entry.
                    id = "";
                    time = "";
                    place = "";
                    coordinates = "";
                    magnitude = "";
                }

                // Check if there are more than 5 elements and remove the earthquake with
                // oldesttime
                if (earthquakeList.length() == 7) {
                    earthquakeList.remove();
                }
            }
            scanner.close(); // Close the scanner when done
        } catch (Exception e) {
            System.out.println("Error while storing data");
        }
    }
}
