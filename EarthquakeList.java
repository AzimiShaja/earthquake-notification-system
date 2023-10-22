public class EarthquakeList {

    // Inner class representing a linked list node
    private class Node {
        private Earthquake data; // Data stored in this node (Earthquake object)
        private Node prev; // Reference to the previous node in the list
        private Node next; // Reference to the next node in the list

        public Node(Earthquake e) {
            this.data = e; // Constructor to create a new node with Earthquake data
        }

        public Node getNext() {
            return next; // Get the next node in the list
        }

        public Earthquake getData() {
            return data; // Get the Earthquake data stored in this node
        }
    }

    private Node head; // Reference to the first node in the list
    private Node tail; // Reference to the last node in the list

    public Node getHead() {
        return head; // Get the reference to the first node in the list
    }

    public Node getTail() {
        return tail; // Get the reference to the last node in the list
    }

    public EarthquakeList() {
        this.head = this.tail = null; // Initialize the list with both head and tail set to null.
    }

    public void add(Earthquake e) {
        Node newest = new Node(e); // Create a new node with Earthquake data

        if (head == null) {
            this.head = newest;
            this.tail = newest;
            return;
        }
        head.prev = null;
        newest.prev = tail;
        tail.next = newest;
        tail = newest;
    }

    /**
     * Get the length of the linked list.
     *
     * @return The number of elements in the linked list.
     */
    public int length() {
        Node pointer = this.head;
        int index = 0;
        while (pointer != null) {
            index++;
            pointer = pointer.next;
        }
        return index;
    }

    public void remove() {
        Node prev = null;
        Node current = this.head;
        int oldestTime = Integer.MAX_VALUE; // Start with a high value as a placeholder

        while (current != null) {
            if (current.data.getTime() <= oldestTime) {
                // If the current earthquake has the same or older time, update the oldestTime
                oldestTime = current.data.getTime();
            }
            current = current.next;
        }

        current = this.head; // Reset the current pointer to the head of the list

        while (current != null) {
            if (current.data.getTime() == oldestTime) {
                if (prev == null) {
                    // If the oldest earthquake is at the head of the list
                    this.head = this.head.next;
                } else {
                    prev.next = current.next;
                }
            } else {
                prev = current;
            }
            current = current.next;
        }
    }

    public void notifyWatcherCloseToEarthquake(EarthquakeList earthquakes, WatcherList watchers) {
        Node ePointer = earthquakes.getHead();
        WatcherList.Node wPointer = watchers.getHead();

        while (ePointer != null && wPointer != null) {
            double watcherLatitude = wPointer.getData().getLatitude();
            double watcherLongitude = wPointer.getData().getLongitude();

            String[] coordinatesOrder = ePointer.getData().getCoordinates().split(",");
            double latitude = Double.parseDouble(coordinatesOrder[0]);
            double longitude = Double.parseDouble(coordinatesOrder[1]);

            double distance = calculateHaversineDistance(watcherLatitude, watcherLongitude, latitude, longitude);

            // Define a threshold distance, e.g., 100 kilometers
            double thresholdDistance = 100.0;

            if (distance <= thresholdDistance) {

                System.out.println("Earthquake " + ePointer.getData().getPlace() + " is close to "
                        + wPointer.getClass().getName());
            }

            ePointer = ePointer.getNext();
            wPointer = wPointer.getNext();
        }
    }

    // Haversine formula to calculate the distance between two points on the Earth's
    // surface
    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Radius of the Earth in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c; // Distance in kilometers
    }

    public Earthquake getLargestEarthquake() {
        if (head == null) {
            return null; // Handle the case where the list is empty.
        }

        Node pointer = head;
        Earthquake largestEarthquake = pointer.data; // Initialize with the first earthquake data.

        while (pointer != null) {
            if (pointer.data.getMagnitude() > largestEarthquake.getMagnitude()) {
                largestEarthquake = pointer.data;
            }
            pointer = pointer.next;
        }

        return largestEarthquake;
    }
}
