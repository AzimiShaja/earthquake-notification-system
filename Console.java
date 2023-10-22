import java.util.Scanner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

public class Console {

    private static EarthquakeList earthquakeList = new EarthquakeList(); // Create an instance of EarthquakeList.
    private static WatcherList watcherList = new WatcherList(); // Create an instance of WatcherList.
    private static Scanner sc = new Scanner(System.in); // Create a Scanner to read input from the console.

    public static void main(String[] args) {

        System.out.print("Enter the file path for earthquake records: "); // Prompt the user to enter a file path for
                                                                          // earthquake records.
        String eFile = sc.nextLine(); // Read the user's input as the file path for earthquake records.

        System.out.print("Enter the file path for watcher records:"); // Prompt the user to enter a file path for
                                                                      // watcher records.
        String wFile = sc.nextLine(); // Read the user's input as the file path for watcher records.
        System.out.println("\n");
        readFromEarthquakeFile(eFile); // Call a method to read and process earthquake records from the provided file.
        readFromWatcherFile(wFile); // Call a method to read and process watcher records from the provided file.
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
            // Handle any exceptions that may occur during file
            // handling.
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
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];
                    String name = order[2];

                    // Remove a Watcher object with the specified data from the list.
                    watcherList.remove(new Watcher(time, command, name));
                    System.out.println(name + " is deleted from the watcher-list \n");

                } else {
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];

                    // Create a new Watcher object with the parsed data and add it to a list.
                    watcherList.add(new Watcher(time, command));

                    System.out.println("Largest earthquake in the past 6 hours:");

                    // Get the largest earthquake from an earthquake list and display its
                    // information.
                    Earthquake largest = earthquakeList.getLargestEarthquake();
                    System.out.println("Magnitude " + largest.getMagnitude() + " at " + largest.getPlace() + "\n");

                }
            }
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
            // Create a DocumentBuilderFactory to handle XML parsing
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Read the XML data from the specified file into a String
            String xmlData = new String(Files.readAllBytes(Paths.get(eFile)));

            // Create an InputSource from the XML data
            InputSource inputSource = new InputSource(new StringReader(xmlData));

            // Parse the XML data to create a Document
            Document document = builder.parse(inputSource);

            // Get a NodeList of 'earthquake' elements from the parsed document
            NodeList earthquakeNodes = document.getElementsByTagName("earthquake");

            // Iterate through the 'earthquake' elements
            for (int i = 0; i < earthquakeNodes.getLength(); i++) {
                Node earthquakeNode = earthquakeNodes.item(i);
                if (earthquakeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element earthquakeElement = (Element) earthquakeNode;

                    // Extract data from the 'earthquake' element
                    String id = earthquakeElement.getElementsByTagName("id").item(0).getTextContent().trim();
                    String time = earthquakeElement.getElementsByTagName("time").item(0).getTextContent().trim();
                    String place = earthquakeElement.getElementsByTagName("place").item(0).getTextContent().trim();
                    String coordinates = earthquakeElement.getElementsByTagName("coordinates").item(0).getTextContent()
                            .trim();
                    String magnitude = earthquakeElement.getElementsByTagName("magnitude").item(0).getTextContent()
                            .trim();

                    // Create an Earthquake object and add it to the EarthquakeList
                    earthquakeList.add(new Earthquake(Integer.parseInt(id), Integer.parseInt(time), place,
                            coordinates, Double.parseDouble(magnitude)));

                    System.out.println("Earthquake " + place + " is inserted into the earthquake-list \n");

                    // Ensure that the EarthquakeList does not exceed a certain length (e.g., 5)
                    if (earthquakeList.length() > 5) {
                        earthquakeList.remove();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error while loading earthquake data from file");
        }
    }
}
