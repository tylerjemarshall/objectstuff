package objectstuff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class Node implements NodeListInterface, Comparable<Node> {

	protected ArrayList<Node> childList;
	protected ArrayList<Node> parentList;

	private static final long serialVersionUID = 1L;
	private String name;

	private String desc = "Description";
	

	/**
	 * Uses findParentNodeR first, then tries findChildNodeR
	 * Defaults to 5 depth search
	 * Defaults to exact search
	 * @param node
	 * @return
	 * @throws NullPointerException
	 */
	public Node findNode(String node) throws NullPointerException {
		return findNode(node, 5, false);
	}	
	/**
	 * Uses findParentNodeR first, then tries findChildNodeR
	 * Defaults to 5 depth search
	 * Defaults to exact search
	 * @param node
	 * @return
	 * @throws NullPointerException
	 */
	public Node findNode(String node, int level) throws NullPointerException {
		return findNode(node, level, false);
	}
	/**
	 * Uses findParentNodeR first, then tries findChildNodeR
	 * @param node name of node
	 * @param level depth of search
	 * @return Node
	 * @throws NullPointerException
	 */
	public Node findNode(String node, int level, Boolean blnLike) throws NullPointerException {

		
		if (name.equals(node)) return this;
	
		Node temp = findParentNode(node, level, blnLike);
		
		return (temp!=null) ? temp : 
			(temp!=null) ? temp : findChildNode(node, level, blnLike);

	}

	
	/**
	 * Searches for nearest parent node by name, to specified depth
	 * Defaults to 5 depth search.
	 * Defaults to exact match
	 * @param node name of node
	 * @return Node
	 */
	public Node findParentNode(String node) {
		return findChildNode(node, 5, false);
	}
	
	
	/**
	 * Searches for nearest parent node by name, to specified depth
	 * Defaults to 5 depth search.
	 * Defaults to exact match
	 * @param node name of node
	 * @return Node
	 */
	public Node findParentNode(String node, int level) {
		return findChildNode(node, level, false);
	}
	
	
	
	/**
	 * Searches for nearest child node by name, to specified depth
	 * @param node name of node
	 * @param level depth of search
	 * @return Node
	 */
	public Node findParentNode(String node, int level, Boolean blnLike) {

		Node temp = null;
		Node found = null;
		Node like = null;
		String compare = "";
		String compare2 = "";
		
		
		
		for (Iterator<Node> z = parentList.iterator(); z.hasNext();) {
			Node item = z.next();
			if (item.getName().equals(node) && level > 0) {
				found = item;
			} 
			
			if (level == 0 || level < 0)
			{
				return null;
			}
			
			try
			{
				compare = item.getName().toLowerCase();
				compare2 = node.toLowerCase();
//				System.out.println("Comparing: " + compare + ", " + compare2);
				
			}
			catch(NullPointerException npe)
			{
				compare = "";
			}
			
			if (compare.contains(compare2) && blnLike)	
			{
				return item;
			}
			
			else
			{
				Node temp1 = item.findParentNode(node, level - 1, false);
				Node temp2 = item.findChildNode(node, level - 1, false);
				if (temp1 != null) return temp1;
				if (temp2 != null) return temp2;
			}
			
			
		}
		return (found != null) ? found : (like != null) ? like : temp;
	}
	
	

	/**
	 * Searches for nearest child node by name, to specified depth
	 * Defaults to 5 depth search.
	 * Defaults to exact match
	 * @param node name of node
	 * @return Node
	 */
	public Node findChildNode(String node) {
		return findChildNode(node, 5, false);
	}
	/**
	 * Searches for nearest child node by name, to specified depth
	 * Defaults to 5 depth search.
	 * Defaults to exact match
	 * @param node name of node
	 * @param level depth of search
	 * @return Node
	 */
	public Node findChildNode(String node, int level) {
		return findChildNode(node, level, false);
	}

	/**
	 * Searches for nearest child node by name, to specified depth
	 * @param node name of node
	 * @param level depth of search
	 * @return Node
	 */
	public Node findChildNode(String node, int level, Boolean blnLike) {

		Node temp = null;
		Node found = null;
		Node like = null;


		
		for (Iterator<Node> z = iterator(); z.hasNext();) {
			Node item = z.next();
			if (item.getName().equals(node) && level > 0) {
				found = item;
			} 
			
			else if (level > 0) 
			{
				try {
					
					if (blnLike && item != null)
					{
						if (item.getName().toLowerCase().contains(node.toLowerCase()))
							like=item;
					}
					
					
					Node temp1 = item.findParentNode(node, level - 1);
					Node temp2 = item.findChildNode(node, level - 1);
					if (temp1 != null) temp = temp1;
					if (temp2 != null) temp = temp2;
					
					if (temp != null)
						found = temp;
				} catch (NullPointerException npe) {
				}
			}
			
		}
		return (found != null) ? found : (like != null) ? like : temp;
	}

	
	
	public String[] getSiblings()
	{
		String[][] s = new String[getParent().size()][];
		
		
		int y = 0;
		int x = 0;
		Iterator<Node> u = getParent().iterator();;
		for(Iterator<Node> z = u; z.hasNext();) {
		    Node item = z.next();
		    
		    s[y]=item.toStringArray();
		    x=+s[y].length;
		    y++; 
		    
		}
	    
		
		String[] ss = new String[y];
		for(int z = 0; z < x; z++)
		{
			for(int q = 0; q < y; q++)
			{
				ss[z]+= s[q];
				
			}
		}
		
		return ss;
		
		
		
	}
	
	public ArrayList<Node>getSiblingsList()
	{
//need to make a search.
		ArrayList<Node> n = new ArrayList<Node>();
		

		Iterator<Node> u = getParent().iterator();;
		for(Iterator<Node> z = u; z.hasNext();) {
		    Node item = z.next();
		    n.add(item);
		    
		}
		return n;
		
		
		
		
	}
	
	
	public String[] toStringArray(ArrayList<Node> n)
	{
		String[] s = new String[n.size()];
		int y = 0;
		Iterator<Node> u = n.iterator();;
		for(Iterator<Node> z = u; z.hasNext();) {
		    Node item = z.next();
		    
		    s[y]=item.getName();
		    y++; 
		}
	    return s;
		
	}
	
	public String[] toStringArray(Node n)
	{
		String[] s = new String[n.size()];
		int y = 0;
		Iterator<Node> u = n.getSubNodeList().iterator();;
		for(Iterator<Node> z = u; z.hasNext();) {
		    Node item = z.next();
		    
		    s[y]=item.getName();
		    y++; 
		}
	    return s;
		
	}
	
	public String[] toStringArray()
	{
		String[] s = new String[size()];
		int y = 0;
		Iterator<Node> u = getSubNodeList().iterator();;
		for(Iterator<Node> z = u; z.hasNext();) {
		    Node item = z.next();
		    
		    s[y]=item.getName();
		    y++; 
		}
	    return s;
		
	}
	
		
	public Node(String name) {
		childList = new ArrayList<Node>();
		parentList = new ArrayList<Node>();
		this.name = name;
	}

	public String toString() {
		return this.name; 
	}

	public ArrayList<Node> getSubNodeList()
	{
		return childList;
		
	}
	
	public ArrayList<Node> getParent() {
		return parentList;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	public void addParent(Node parent) {
		
		if (!parentList.contains(parent))
		parentList.add(parent);

		if (!parent.contains(this))
			parent.add(this);
	}

	
	public boolean add(Node arg0) {
		if (!arg0.getParent().contains(this))
		arg0.addParent(this);
		
		if (!childList.contains(arg0))
		return childList.add(arg0);
		else return false;
	}
	
	

	public boolean add(String node, String parent)
	{
		Node temp = findNode(parent, 1);
		if (temp == null) return false;
		return add(node, temp);
	}

	public boolean add(String node, Node parent)
	{
		if (parent != null)
			return parent.add(new Node(node));
		else
			 return false;
	}
	
	

	public void add(int arg0, Node arg1) {
		arg1.addParent(this);
		childList.add(arg0, arg1);

	}

	public boolean addAll(Collection<? extends Node> arg0) {
		return childList.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection<? extends Node> arg1) {
		return childList.addAll(arg0, arg1);
	}

	public void clear() {
		childList.clear();
	}

	
	public boolean contains(Object arg0) {
		return childList.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return childList.containsAll(arg0);
	}

	public Node get(int arg0) {
		return childList.get(arg0);
	}

	public int indexOf(Object arg0) {
		return childList.indexOf(arg0);
	}

	public boolean isEmpty() {
		return childList.isEmpty();
	}

	public Iterator<Node> iterator() {
		return childList.iterator();
	}

	public int lastIndexOf(Object arg0) {
		return childList.lastIndexOf(arg0);
	}

	public ListIterator<Node> listIterator() {
		return childList.listIterator();
	}

	public ListIterator<Node> listIterator(int arg0) {
		return childList.listIterator(arg0);
	}

	public boolean remove(Object arg0) {
		return childList.remove(arg0);
	}

	public Node remove(int arg0) {
		return childList.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0) {
		return childList.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return childList.retainAll(arg0);
	}

	public Node set(int arg0, Node arg1) {
		return childList.set(arg0, arg1);
	}

	public int size() {
		return childList.size();
	}

	public java.util.List<Node> subList(int arg0, int arg1) {
		return childList.subList(arg0, arg1);
	}

	public Object[] toArray() {
		return childList.toArray();
	}

	public <T> T[] toArray(T[] arg0) {
		return childList.toArray(arg0);
	}

	public int compareTo(Node arg0) {
		return this.compareTo(arg0);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
