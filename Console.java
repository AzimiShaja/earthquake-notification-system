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
        // System.out.print("Enter the file path for earthquake records: ");
        // String eFile = sc.nextLine();
        // System.out.print("Enter the file path for watcher records:");
        // String wFile = sc.nextLine();
        System.out.println("\n");

        try {
            File watcherFile = new File("watcher.txt");
            Scanner inputStream1 = new Scanner(watcherFile);
            File earthquakeFile = new File("earthquake.txt");
            Scanner inputStream2 = new Scanner(earthquakeFile);

            int myTime = 0;
            int eTime = 0;
            int wTime = 0;

            while (inputStream1.hasNextLine() || inputStream2.hasNextLine()) {

                processWatcherLine(wTime, myTime, inputStream1);
                processEarthquakeLine(eTime, myTime, inputStream2);

                if (earthquakeList.length() == 7) {
                    earthquakeList.remove();
                }
                if (earthquakeList != null && watcherList != null) {
                    earthquakeList.notifyWatcherCloseToEarthquake(earthquakeList, watcherList);

                }

            }

            inputStream1.close();
            inputStream2.close();
            System.out.println("\n\n");

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());

        }

        sc.close(); // Close scanner
        System.out.println("\n");
    }

    public static void processWatcherLine(int wTime, int myTime, Scanner inputStream1) {
        while (wTime == myTime && inputStream1.hasNextLine()) {
            String wLine = inputStream1.nextLine();
            String[] order = wLine.split(" ");

            if (wLine.contains("add")) {
                wTime = Integer.parseInt(order[0]);
                String command = order[1];
                Double latitude = Double.parseDouble(order[2]);
                Double longitude = Double.parseDouble(order[3]);
                String name = order[4];

                watcherList.add(new Watcher(wTime, command, latitude, longitude, name));
                System.out.println(name + " is added to the watcher list \n");
            } else if (wLine.contains("delete")) {
                String name = order[2];
                wTime = Integer.parseInt(order[0]);
                watcherList.remove(name);
                System.out.println(name + " is removed from the watcher list \n");
            } else {
                wTime = Integer.parseInt(order[0]);
                Earthquake largest = earthquakeList.getLargestEarthquake();

                if (largest != null) {
                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    System.out.println("Largest earthquake in the past 6 hours:");
                    System.out.println(
                            "Magnitude " + largest.getMagnitude() + " at " + largest.getPlace() + "");
                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
                } else {
                    System.out.println("No record found \n");
                }
            }
            myTime++;
        }
    }

    public static void processEarthquakeLine(int eTime, int myTime, Scanner inputStream2) {
        String place = "";
        String coordinates = "";
        String magnitude = "";
        String id = " ";

        while (inputStream2.hasNextLine()) {
            String eLine = inputStream2.nextLine();

            if (eLine.contains("<id>")) {
                id = eLine.replaceAll("<id>|</id>", "").trim();
                // Process the other fields related to an earthquake within this block
            }
            if (eLine.contains("<time>")) {
                eTime = Integer.parseInt(eLine.replaceAll("<time>|</time>", "").trim());
            } else if (eLine.contains("<place>")) {
                place = eLine.replaceAll("<place>|</place>", "").trim();
            } else if (eLine.contains("<coordinates>")) {
                coordinates = eLine.replaceAll("<coordinates>|</coordinates>", "").trim();
            } else if (eLine.contains("<magnitude>")) {
                magnitude = eLine.replaceAll("<magnitude>|</magnitude>", "").trim();
            }

            if (eLine.contains("</earthquake>")) { // end of a tag
                // Create a new Earthquake object and add it to the earthquakeList
                earthquakeList.add(new Earthquake(Integer.parseInt(id), eTime, place,
                        coordinates,
                        Double.parseDouble(magnitude)));
                System.out.println("Earthquake " + place + " is inserted into the earthquake list\n");
                myTime++;

                // Clear the variables for the next entry.
                id = "";
                place = "";
                coordinates = "";
                magnitude = "";

                if (myTime != eTime) {
                    break;
                }
            }

        }
    }
}