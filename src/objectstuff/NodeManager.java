package objectstuff;

import java.util.Iterator;

public class NodeManager {

	
	public final static String endl = "\n";
	
	public static void main(String[] args)
	{
		
		
		Node master = new Node("Tyler");
		
		master.add(new Node("Tyler 2.0"));
		master.add(new Node("Tyler 3.0"));
		
		master.setChild(new Node("Tyler Jr"));
		master.setParent(new Node("Clinton"));
		
		
		master.child.setChild(new Node("Tyler Jr Jr"));
		
		
		master.child.add(new Node("Tyler Jr 2.0"));
		
		
		master.parent.setParent(new Node("Eric"));
		
		master.parent.parent.setParent(new Node("Eric's Dad"));
		
		print(master);
		
		print(master.child);
		
		print(master.parent);
		
		print(master.parent.child);
		
		print(master.get(0));
		
		print(master.child.get(0));
		
		print(master);
		
		
		
	}
	
	
	public static void print(Node n)
	{
		
		String parent = "null";
		try {
			parent = n.getParent().getName();
		} catch (Exception npe) {}
		

		String child = "null";
		try {
			child = n.getChild().getName();
		} catch (Exception npe) {}
		
		
		
		
		System.out.println("Name: " + n.getName() + endl
				+ "Parent:" + parent + endl
				+ "Child:" + child + endl
				+ "Sub-Children:"
				);
		
		
		Iterator<Node> i = n.getSubNodeList().iterator();
		for(Iterator<Node> z = i; z.hasNext(); ) {
		    Node item = z.next();
		    System.out.println(item);
		}
		
		System.out.println(endl);
		
		
	}
	
	
	/*
	 * 
	 * Currently can only have a single child or parent.
	 * Can have any number of SubChildren.
	 * Every node is and will be recursive.
	 * 
	 * What I want to do, have MANY relationship for parent and child, no subs.
	 * 
	 * How?
	 * 
	 * Will replace parent and child each with a list.
	 * 
	 * Need to make a visual to easily see connections, and make a lookup table.
	 * 
	 * Not sure. 
	 * 
	 * Food.
	 * 
	 * 
	 */
	 
	
	
	
}
