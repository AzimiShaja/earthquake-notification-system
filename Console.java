import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver
 */
public class Console {

    private static ArrayList<Earthquake> eTemp = new ArrayList<>();
    private static ArrayList<Watcher> wTemp = new ArrayList<>();

    private static EarthquakeList earthquakeList = new EarthquakeList();
    private static WatcherList watcherList = new WatcherList();

    public static void main(String[] args) throws InterruptedException {
        readFromWatcher();
        readFromEarthquake();

        System.out.println("\n");

        int i = 0;
        int j = 0;

        int myTime = 0;

        while ((i < wTemp.size() && j < eTemp.size())) {
            System.out.println("=====  Time " + myTime + " ============");

            int wTime = wTemp.get(i).getTime();
            int eTime = eTemp.get(j).getTime();

            while (myTime == wTime && i < wTemp.size()) {

                if (wTemp.get(i).getCommand().equals("add")) {
                    watcherList.add(wTemp.get(i));
                    System.out.println(wTemp.get(i).getName() + " is added to watcher List\n");

                } else if (wTemp.get(i).getCommand().equals("delete")) {

                    String name = wTemp.get(i).getName();
                    watcherList.remove(name);
                    System.out.println(name + " is deleted from list\n");

                } else {
                    Earthquake largest = earthquakeList.getLargestEarthquake();

                    if (largest != null) {

                        System.out.println("Largest earthquake in the past 6 hours:");
                        System.out.println(
                                "Magnitude " + largest.getMagnitude() + " at " + largest.getPlace() + "\n");
                    } else {
                        System.out.println("No record found \n");
                    }
                }
                i++;
                if (i < wTemp.size()) {
                    wTime = wTemp.get(i).getTime(); // Update wTime with the new value
                }
            }

            while (myTime == eTime && j < eTemp.size()) {
                earthquakeList.add(eTemp.get(j));
                System.out.println(eTemp.get(j).getPlace() + " is inserted to earthquake-list\n");

                earthquakeList.notifyWatcherCloseToEarthquake(earthquakeList, watcherList);

                if (myTime - eTemp.get(j + 1).getTime() >= 6) {
                    earthquakeList.remove(); // Remove the earthquake
                    System.out.println("Earthquake removed after 6 hours");
                }

                j++;
                if (j < eTemp.size()) {
                    eTime = eTemp.get(j).getTime(); // Update eTime with the new value
                }
            }

            // Thread.sleep(1000);
            myTime++;
        }

        wTemp.clear();
        eTemp.clear();

    }

    private static void readFromWatcher() {
        try {
            File file1 = new File("watcher.txt");
            Scanner sc1 = new Scanner(file1);

            while (sc1.hasNextLine()) {
                String wLine = sc1.nextLine();
                String[] order = wLine.split(" ");

                if (wLine.contains("add")) {
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];
                    Double latitude = Double.parseDouble(order[2]);
                    Double longitude = Double.parseDouble(order[3]);
                    String name = order[4];

                    wTemp.add(new Watcher(time, command, latitude, longitude, name));

                } else if (wLine.contains("delete")) {
                    String name = order[2];
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];
                    wTemp.add(new Watcher(time, command, name));

                } else {
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

    private static void readFromEarthquake() {

        try {
            File file2 = new File("earthquake.txt");

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

                if (eLine.contains("</earthquake>")) { // end of a tag
                    // Create a new Earthquake object and add it to the earthquakeList
                    eTemp.add(new Earthquake(Integer.parseInt(id), time, place,
                            coordinates,
                            Double.parseDouble(magnitude)));
                    // Clear the variables for the next entry.
                    id = "";
                    place = "";
                    coordinates = "";
                    magnitude = "";
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}