package objectstuff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class Node implements NodeListInterface, Comparable<Node> {

	protected ArrayList<Node> n;

	private static final long serialVersionUID = 1L;
	private String name;

	Node parent = null;
	Node child = null;

	public Node(String name) {
		n = new ArrayList<Node>();
		this.name = name;
	}

	public String toString() {
		String parent = "null";
		try {
			parent = this.getParent().getName();
		} catch (Exception npe) {}
		

		String child = "null";
		try {
			child = this.getChild().getName();
		} catch (Exception npe) {}
		return this.name + "(" + parent + ", " + child + ") " + n;
	}

	public ArrayList<Node> getSubNodeList()
	{
		return n;
		
	}
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
		this.parent.child = this;
	}

	public Node getChild() {
		return child;
	}

	public void setChild(Node child) {
		this.child = child;
		this.child.parent = this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean add(Node arg0) {
		arg0.setParent(this);
		return n.add(arg0);
	}

	public void add(int arg0, Node arg1) {
		arg1.setParent(this);
		n.add(arg0, arg1);

	}

	public boolean addAll(Collection<? extends Node> arg0) {
		return n.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection<? extends Node> arg1) {
		return n.addAll(arg0, arg1);
	}

	public void clear() {
		n.clear();
	}

	public boolean contains(Object arg0) {
		return n.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return n.containsAll(arg0);
	}

	public Node get(int arg0) {
		return n.get(arg0);
	}

	public int indexOf(Object arg0) {
		return n.indexOf(arg0);
	}

	public boolean isEmpty() {
		return n.isEmpty();
	}

	public Iterator<Node> iterator() {
		return n.iterator();
	}

	public int lastIndexOf(Object arg0) {
		return n.lastIndexOf(arg0);
	}

	public ListIterator<Node> listIterator() {
		return n.listIterator();
	}

	public ListIterator<Node> listIterator(int arg0) {
		return n.listIterator(arg0);
	}

	public boolean remove(Object arg0) {
		return n.remove(arg0);
	}

	public Node remove(int arg0) {
		return n.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0) {
		return n.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return n.retainAll(arg0);
	}

	public Node set(int arg0, Node arg1) {
		return n.set(arg0, arg1);
	}

	public int size() {
		return n.size();
	}

	public java.util.List<Node> subList(int arg0, int arg1) {
		return n.subList(arg0, arg1);
	}

	public Object[] toArray() {
		return n.toArray();
	}

	public <T> T[] toArray(T[] arg0) {
		return n.toArray(arg0);
	}

	public int compareTo(Node arg0) {
		return this.compareTo(arg0);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
