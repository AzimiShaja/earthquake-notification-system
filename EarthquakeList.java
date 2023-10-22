public class EarthquakeList {

    // Inner class representing a linked list node
    private class Node {
        private Earthquake data;
        private Node prev;
        private Node next;

        public Node(Earthquake e) {
            this.data = e;
        }
    }

    private Node head;
    private Node tail;

    public EarthquakeList() {
        this.head = this.tail = null; // Initialize the list with both head and tail set to null.
    }

    public void add(Earthquake e) {
        Node newest = new Node(e);

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
