public class EarthquakeList {

    private class Node {
        private Earthquake data;
        private Node next;

        public Node(Earthquake e) {
            this.data = e;
        }
    }

    private Node head;
    private Node tail;

    public EarthquakeList() {
        this.head = this.tail = null;
    }

    public void add(Earthquake e) {
        Node newest = new Node(e);

        if (head == null) {
            this.head = newest;
            this.tail = newest;
            return;
        }
        this.tail.next = newest;
        this.tail = newest;
    }

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
        this.head = this.head.next;
    }

    public void print() {
        Node pointer = head;

        while (pointer != null) {
            System.out.println(pointer.data);
            pointer = pointer.next;
        }
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
