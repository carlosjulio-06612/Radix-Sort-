package Mundo;

public class Test1 {
	public static void main(String[] args) {
    RxNodos head = new RxNodos(30);
    head.setNext(new RxNodos(1));
    head.getNext().setNext(new RxNodos(23));
    head.getNext().getNext().setNext(new RxNodos(4));
    head.print(head);
    System.out.println();
    RadixSortWithNodes sorter = new RadixSortWithNodes();
    RxNodos sortedHead = sorter.Radix(head);

    // Print the sorted list
    RxNodos current = sortedHead;
    current.print(current);
	}
}
