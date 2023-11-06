public class WatcherList {

    // Inner class representing a linked list node for Watcher objects
    private class Node {
        private Node next;
        private Node prev;
        private Watcher data;

        public Node(Watcher data) {
            this.data = data;
        }

        public Watcher getData() {
            return data;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    private Node head;
    private Node tail;

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public void add(Watcher w) {
        Node newest = new Node(w);

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

    public void remove(String name) {

        if (head.data.getName().equals(name)) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            return;
        }

        Node pointer = this.head.next;

        while (pointer != null && pointer.data != null) {

            if (pointer.data.getName().equals(name)) {
                pointer.prev.next = pointer.next;
                pointer.next.prev = pointer.prev;
                return;
            }
            pointer = pointer.next;
        }
    }

    public void notifyWatcherCloseToEarthquake(Earthquake earthquake) {

        Node wPointer = this.head; // Get the first watcher node

        if (earthquake != null) { // Iterate through earthquakes and watchers
            while (wPointer != null) {
                double watcherLatitude = wPointer.getData().getLatitude(); // Get watcher's latitude
                double watcherLongitude = wPointer.getData().getLongitude(); // Get watcher's longitude

                String[] coordinatesOrder = earthquake.getCoordinates().split(","); // Split earthquake
                                                                                    // coordinates
                double latitude = Double.parseDouble(coordinatesOrder[0]); // Get earthquake's latitude
                double longitude = Double.parseDouble(coordinatesOrder[1]); // Get earthquake's longitude

                // Calculate the distance between watcher and earthquake using the distance
                // formula
                double distance = Math
                        .sqrt(Math.pow(latitude - watcherLatitude, 2) + Math.pow(longitude - watcherLongitude, 2));

                // Check if the distance is less than 2 times the cube of earthquake magnitude
                if (distance < 2 * (Math.pow(earthquake.getMagnitude(), 3))) {

                    System.out.println(
                            "Earthquake " + earthquake.getPlace() + " is close to " +
                                    wPointer.getData().getName());

                }
                wPointer = wPointer.next; // Move to the next watcher node
            }
        }
    }
}