package r.tree.data;

import java.util.LinkedList;

public class my_Queue<E> {
	
	private LinkedList<E> ll;
	public my_Queue() {
		ll = new LinkedList<E>();
	}
	public boolean add(E arg0) {return ll.offer(arg0);}
	public E remove() {return ll.poll();}
	public boolean isEmpty () {return ll.isEmpty();}
	public E[] toArray (E[] e) {return ll.toArray(e);}
	public E get_First () {return ll.element();}
	public int size() {return ll.size();}
	public static void main(String[] args) {
		my_Queue<Integer> a = new my_Queue<Integer>();
		
		a.add(1);
		a.add(2);
		a.add(3);

		System.out.println(a.get_First());
		System.out.println(a.get_First());
		System.out.println(a.get_First());
		System.out.println(a.get_First());
		System.out.println(a.get_First());
		System.out.println(a.get_First());
		System.out.println(a.get_First());
	}
}