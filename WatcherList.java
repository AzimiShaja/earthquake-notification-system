public class WatcherList {

    // Inner class representing a linked list node for Watcher objects
    class Node {
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

    public void print() {
        Node pointer = head;

        while (pointer != null) {
            System.out.print(pointer.data);
            pointer = pointer.next;
        }
    }
}
