package Mundo;

import java.util.Random;

public class RxNodos {
	int value;
	RxNodos next; 
	public RxNodos(int val){
		this.value=val;
		this.next=null;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public RxNodos getNext() {
		return next;
	}
	public void setNext(RxNodos next) {
		this.next = next;
	}
	public RxNodos insert(RxNodos n, int z){
		RxNodos copy=new RxNodos(z);
		n.setNext(copy);
		return n;
	}
	public void print(RxNodos n) {
		RxNodos copy=n;
		while (copy!=null) {
			System.out.print(copy.getValue()+"->");
			copy=copy.getNext();
		}
	}
	public RxNodos fillOut(RxNodos n, int y){
		if (n == null) {
            n = new RxNodos(0); // Iniciar con el primer valor aleatorio si n es null
        }
		RxNodos copy=n;
		Random r=new Random();
		for(int i=1;i<y;i++){
			RxNodos nuevo=new RxNodos(r.nextInt(y));
			copy.setNext(nuevo);
			copy=copy.getNext();
		}
		return n;
	}
}


