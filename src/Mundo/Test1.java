package Mundo;

public class Test1 {
	public static void main(String[] args) {
    
    RxNodos nuevo=new RxNodos(2);
    nuevo=nuevo.fillOut(nuevo,100);
    nuevo.print(nuevo);
	}
}
