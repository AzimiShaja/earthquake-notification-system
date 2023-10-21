public class WatcherList {

    private class Node {
        private Node next;
        private Node prev;
        private Watcher data;

        public Node(Watcher data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;

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

    public int length() {
        Node pointer = this.head;
        int index = 0;
        while (pointer != null) {
            index++;
            pointer = pointer.next;
        }
        return index;
    }

    public void remove(Watcher w) {
        if (head == null) {
            // Handle the case where the list is empty.
            return;
        }

        if (head.data.equals(w)) {
            // If the target is the head, update the head to the next node.
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            return;
        }

        Node current = head;

        while (current.next != null) {
            if (current.next.data.equals(w)) {
                current.next = current.next.next;
                if (current.next != null) {
                    current.next.prev = current;
                }
                return; // Exit the loop after removing the element.
            }
            current = current.next;
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
