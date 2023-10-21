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

    private static EarthquakeList earthquakeList = new EarthquakeList();
    private static WatcherList watcherList = new WatcherList();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Enter the file path for earthquake records: ");
        String eFile = sc.nextLine();
        System.out.print("Enter the fill path for watcher records: ");
        String wFile = sc.nextLine();

        readFromEarthquakeFile(eFile);
        readFromWatcherFile(wFile);

    }

    public static void readFromWatcherFile(String wFile) {
        Scanner inputStream = null;
        try {
            File file = new File(wFile);

            if (file.exists()) {
                inputStream = new Scanner(file);

            } else {
                System.out.println("File not found;");
            }

        } catch (Exception e) {
            System.err.println("Error While reading file " + wFile);
        }

        try {
            while (inputStream.hasNextLine()) {

                String line = inputStream.nextLine();

                String[] order = line.split(" ");

                if (order[1].equals("add")) {
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];
                    double latitude = Double.parseDouble(order[2]);
                    double longitude = Double.parseDouble(order[3]);
                    String name = order[4];

                    watcherList.add(new Watcher(time, command, latitude, longitude, name));
                    System.out.println(name + " is added to the watcher-list \n");

                } else if (order[1].equals("delete")) {
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];
                    String name = order[2];

                    watcherList.remove(new Watcher(time, command, name));
                    System.out.println(name + " is deleted from the watcher-list \n");
                } else {
                    int time = Integer.parseInt(order[0]);
                    String command = order[1];
                    watcherList.add(new Watcher(time, command));
                    System.out.println("Largest earthquake in the past 6 hours:");
                    Earthquake largest = earthquakeList.getLargestEarthquake();
                    System.out.println("Magnitude " + largest.getMagnitude() + " at " + largest.getPlace() + "\n");
                }

            }
        } catch (Exception e) {
            System.err.println("Error while parsing data from file");
        }
    }

    public static void readFromEarthquakeFile(String eFile) {
        try {
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Create an InputSource from the XML data in the 'xmlData' variable
            String xmlData = new String(Files.readAllBytes(Paths.get(eFile)));
            InputSource inputSource = new InputSource(new StringReader(xmlData));

            // Parse the InputSource
            Document document = builder.parse(inputSource);

            // Get a NodeList of 'earthquake' elements
            NodeList earthquakeNodes = document.getElementsByTagName("earthquake");

            // Iterate through the 'earthquake' elements
            for (int i = 0; i < earthquakeNodes.getLength(); i++) {
                Node earthquakeNode = earthquakeNodes.item(i);
                if (earthquakeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element earthquakeElement = (Element) earthquakeNode;

                    // Extract data from 'earthquake' element
                    String id = earthquakeElement.getElementsByTagName("id").item(0).getTextContent().trim();
                    String time = earthquakeElement.getElementsByTagName("time").item(0).getTextContent().trim();
                    String place = earthquakeElement.getElementsByTagName("place").item(0).getTextContent().trim();
                    String coordinates = earthquakeElement.getElementsByTagName("coordinates").item(0).getTextContent()
                            .trim();
                    String magnitude = earthquakeElement.getElementsByTagName("magnitude").item(0).getTextContent()
                            .trim();

                    earthquakeList.add(new Earthquake(Integer.parseInt(id), Integer.parseInt(time), place,
                            coordinates, Double.parseDouble(magnitude)));

                    if (earthquakeList.length() - 1 == 6) {
                        earthquakeList.remove();
                    }

                    System.out.println("Earthquake " + place + " is inserted to the the earthquake-list \n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
