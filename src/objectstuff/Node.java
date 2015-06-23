package objectstuff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class Node implements NodeListInterface, Comparable<Node> {

	protected ArrayList<Node> n;

	private static final long serialVersionUID = 1L;
	private String name;

	private Node parent = null;
	//Node child = null;

	
	public Node findNode(String node) throws NullPointerException
	{
		for(Iterator<Node> z = iterator(); z.hasNext();) {
		    Node item = z.next();
		    if (item.getName().equals(node)) return item;   
		}
		
		try
		{
			if(parent.getName().equals(node)) 
				{			
					return parent;
				}
		}
		catch (NullPointerException npe)
		{
			throw npe;
		}
		return null;
	}
	
	public Node findNodeR(String node) throws NullPointerException
	{
		return findNodeR(node, 5);
	}
	
	public Node findNodeR(String node, int level) throws NullPointerException
	{
		System.out.println("Level: " + level);
		if (name.equals(node))return this;
		
		
		
		Node temp = findParentNodeR(node, level);
		
		//if no parent found, find child
		
		
		
		if (temp == null)
		{
			temp = findChildNodeR(node, level);
		}
		else
			return temp;
		
		return temp;

	}
	
	
	public Node findChildNodeR(String node)
	{
		
		return findChildNodeR(node, 5);
	}
	
	public Node findChildNodeR(String node, int level)
	{
		System.out.println("LevelC: " + level);
		
		Node temp = null;
		for(Iterator<Node> z = iterator(); z.hasNext();) {
		    Node item = z.next();
		    if (item.getName().equals(node) && level > 0) 
		    	{
		    		System.out.println("Found C at level " + level);
		    		return item;   
		    	}
		    else if (level > 0) //this should go into each childs child 5 generations down
		    {
		    	try
		    	{
		    		temp= item.findChildNodeR(node, level-1);
		    	}
		    	catch (NullPointerException npe)
		    	{
		    		
		    	}
		    	
		    }
		    
		    
		}
		return temp;
		
		
		
		
	}
	
	
	public Node findParentNodeR(String node)
	{
		return findParentNodeR(node, 5);
		
	}
	
	public Node findParentNodeR(String node, int level)
	{
		System.out.println("LevelP: " + level);
		try
		{
			if (parent.getName().equals(node) && level > 0) 
				return parent;
			else if (level == 0 || level < 0)
				return null;
			else	
				return parent.findParentNodeR(node, level-1);
		}
		catch (NullPointerException npe)
		{
			return null;
		}
		
		
	}
	
	public Node(String name) {
		n = new ArrayList<Node>();
		this.name = name;
	}

	public String toString() {
		return this.name; 
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
		
		parent.add(this, true);

	}
	public void setParent(Node parent, Boolean recursive) {

		if (!recursive)
		{
			parent.add(this, true);
		}
		this.parent = parent;
		

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean add(Node arg0) {
		arg0.setParent(this, true);
		return n.add(arg0);
	}
	
	public boolean add(Node arg0, Boolean recursive)
	{
		if (!recursive)
		{
			arg0.setParent(this, true);
		}
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
