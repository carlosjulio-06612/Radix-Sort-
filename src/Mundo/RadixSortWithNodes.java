package Mundo;

public class RadixSortWithNodes {
    public RxNodos Radix(RxNodos head) {
        if (head == null) return null;

        for (int x = 0; x < Integer.SIZE; x++) {
            RxNodos zeroBucketHead = new RxNodos(0), zeroBucket = zeroBucketHead;
            RxNodos oneBucketHead = new RxNodos(0), oneBucket = oneBucketHead;

            RxNodos current = head;
            while (current != null) {
                RxNodos next = current.getNext();
                // Separar nodos en dos buckets
                if ((current.value & (1 << x)) == 0) {
                    zeroBucket.next = current;
                    zeroBucket = zeroBucket.next;
                } else {
                    oneBucket.next = current;
                    oneBucket = oneBucket.next;
                }
                current = next;
            }

            // Terminar los buckets
            zeroBucket.next = oneBucketHead.next;
            oneBucket.next = null;

            // Reensamblar la lista desde los buckets
            head = zeroBucketHead.next;
        }
        return head;
    }

}

