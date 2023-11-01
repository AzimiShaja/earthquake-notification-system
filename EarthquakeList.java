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

    public Earthquake getFirstElement() {
        if (head == null) {
            return null;
        }
        return head.getData();
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
        if (head == null) {
            // Handle the case where the list is empty
            return;
        }

        head = head.next;
        if (head != null) {
            head.prev = null;

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