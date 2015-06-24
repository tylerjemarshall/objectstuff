package objectstuff;


import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
//import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;






import javax.swing.*;
import javax.swing.event.*;
//import javax.swing.text.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;


public class NodeManager extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String endl = "\n";
	
	//North
	private JLabel lblTitle = new JLabel("Node Manager", JLabel.CENTER);
	private JLabel lblName = new JLabel("Name", JLabel.CENTER);
	//Center
	private JLabel lblChild = new JLabel("Children: ", JLabel.LEFT);
	private JList<String> childList = new JList<String>();
	JScrollPane sclChild = new JScrollPane (childList);
	private JLabel lblParent = new JLabel("Parent: ", JLabel.LEFT);
	private JList<String> parentList = new JList<String>();
	JScrollPane sclParent = new JScrollPane (parentList);
	private JLabel lblDesc = new JLabel("Description: ", JLabel.LEFT);
	private JTextPane txtDesc = new JTextPane();
	JScrollPane sclDesc = new JScrollPane (txtDesc);
	//South
	private JButton btnSearch = new JButton("Search: ");
	private JTextField txtSearch = new JTextField("");
	private JButton btnRefresh = new JButton("Refresh");
	private JButton btnExit = new JButton("Exit");
	//Local Variables
	Node selectedNode;

	public NodeManager() {
		super("Node Manager");
		
		//Settings
		setSize(500, 700);
		setLocationRelativeTo(getParent());

		Font bigFont = new Font("Serif",Font.BOLD,26);
		Font mediumFont = new Font("Serif",Font.BOLD,18);
//		Font smallFont = new Font("Serif",Font.BOLD,12);
		
		lblTitle.setFont(bigFont);
		lblName.setFont(mediumFont);
		lblParent.setFont(mediumFont);
		lblChild.setFont(mediumFont);
		lblDesc.setFont(bigFont);
		
		
		childList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		childList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		childList.setVisibleRowCount(-1);
		childList.setFont(bigFont);

		
		parentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		parentList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		parentList.setVisibleRowCount(-1);
		parentList.setFont(bigFont);
		
		sclDesc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    sclDesc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		txtDesc.setEditable(false);
		
		//Layout
		setLayout(new BorderLayout(15, 15));
		

		final Panel north = new Panel();
		final Panel center = new Panel();
		final Panel south = new Panel();
		
		//final Panel test = new Panel();
		
		final Panel listBox = new Panel();
		listBox.setLayout(new GridLayout(2, 2, 0, 0)) ;
		
		final Panel descBox = new Panel();
		descBox.setLayout(new GridLayout(2, 1, 0, 0));
		
		add("North", north);
		add("Center", center);
		add("South", south);

		north.setLayout(new GridLayout(2, 1, 5, 5));
		center.setLayout(new GridLayout(2, 1, 5, 5));
		south.setLayout(new GridLayout(2, 2, 5, 5));
		
		
		
		north.add(lblTitle);		
		north.add(lblName);
		
		
		listBox.add(lblChild);
		listBox.add(sclChild);
		
		listBox.add(lblParent);
		listBox.add(sclParent);
		
		center.add(listBox);
		
//		center.add(lblChild);
//		center.add(sclChild);
		
		descBox.add(lblDesc);
		descBox.add(sclDesc);
		center.add(descBox);
//		center.add(sclDesc);
		
		south.add(btnSearch);
		south.add(txtSearch);	
		south.add(btnRefresh);
		south.add(btnExit);
	
		setVisible(true);

		//Handles navigation for both lists.
		ListSelectionListener selectionListener = new ListSelectionListener() {
			@SuppressWarnings("rawtypes")
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JList jl = (JList) e.getSource();
					String name = (String) jl.getSelectedValue();				
					select(selectedNode.findNodeR(name, 1));
			}
		}};
		
		
		childList.addListSelectionListener(selectionListener);
		parentList.addListSelectionListener(selectionListener);

		
		//Default Close
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (promptUser(center, "Close the window?", "Exit") == 1)
					dispose();
			}
		});
		
		//Refresh Button
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
				
			}
		});

		//Exit Button
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
		//Search Button
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select(selectedNode.findNodeR(txtSearch.getText()));
				
			}
		});
		
		//Search Field
		txtSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Node temp = null;
				temp = selectedNode.findNodeR(txtSearch.getText(), 5, true);
				select(temp);
				txtSearch.setText("");
			}
		});	
	}

	public void append(String s) {
		
		append(s, Color.red);

	}

	public void append(String s, Color c) {
		try {
			String newString = "\n";
			int count = 0;

			Style style = txtDesc.addStyle("Message", null);
			StyleConstants.setForeground(style, c);

			for (int x = 0; x < s.length(); x++) {
				if (s.charAt(x) == ' ') {
					count = 0;
				} else {
					if (count > 15) {
						newString += ' ';
						count = 0;
					}
					count++;
				}
				newString += s.charAt(x);
			}
			Document doc = txtDesc.getDocument();
			doc.insertString(doc.getLength(), newString, style);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}
	
	//Simple Popup Box
	private int promptUser(Panel panel, String message, String title) {
		if (JOptionPane.showConfirmDialog(panel, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return 1;
		}
		return 0;

	}

	//Recreates master node, and reselects.
	public void refresh()
	{
		Node master = new Node("Tyler");
		master.setParent(new Node("Clinton"));
		
		
		master.add(new Node("Tyler 2.0"));
		master.add(new Node("Tyler 3.0"));
		master.add(new Node("Tyler Jr"));
		
		master.findNodeR("Tyler 2.0", 1).add(new Node("Tyler Jr Jr"));
		master.findNodeR("Tyler 3.0", 1).add(new Node("Tyler Jr 2.0"));
		master.getParent().setParent(new Node("Eric"));
		master.getParent().getParent().setParent(new Node("Eric's Dad"));
		
		
		master.findNodeR("Tyler Jr Jr", 5).add(new Node("Mini Tyler"));
		master.findNodeR("Clinton", 2).add(new Node("Justin"));
		
		master.getParent().findNodeR("Justin", 3).add(new Node("Mini Justin"));
		
		master.add(new Node("Laptop"));
		master.findNodeR("Laptop").add(new Node("Keyboard"));
		master.findNodeR("Laptop").add(new Node("Mouse"));
		
		master.add("Touchpad", "Laptop");
		master.add("Justin", "Eric");
		
		master.add("Hunger", "Tyler");
		
		selectedNode = master;
		select(selectedNode);
	}
	
	/**
	 * Focuses the NodeManager on the selected node.
	 * @param n Node being selected
	 */
	@SuppressWarnings("deprecation")
	public void select(Node n)
	{
		if (n == null) 
			{
				append("Node not found: " + txtSearch.getText(), Color.RED);
				return;
			}
		
		String[] listData = new String[n.size()];
		int x = 0;
		
		Iterator<Node> i = n.getSubNodeList().iterator();
		for(Iterator<Node> z = i; z.hasNext();) {
		    Node item = z.next();
		    
		    listData[x]=item.toString();
		    x++; 
		}
	    childList.setListData(listData);
	    try
	    {
	    	parentList.show();
	    	parentList.setListData(new String[]{n.getParent().getName()});
	    }
	    catch(Exception npe)
	    {
	    	parentList.hide();
	    }
		
		lblName.setText(n.getName());	
		
		append("Node Found: " + n.getName(), Color.BLUE);
    	selectedNode=n;
		
	}
	
	
	
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		NodeManager n = new NodeManager();
	}
	
	
}
