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
//import java.util.Iterator;






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
	private JList<String> lstChild = new JList<String>();
	private JScrollPane sclChild = new JScrollPane (lstChild);
	
	private JLabel lblParent = new JLabel("Parent: ", JLabel.LEFT);
	private JList<String> lstParent = new JList<String>();
	private JScrollPane sclParent = new JScrollPane (lstParent);
	
	private JLabel lblSib = new JLabel("Siblings: ", JLabel.LEFT);
	private JList<String> lstSib = new JList<String>();
	private JScrollPane sclSib = new JScrollPane (lstSib);
	
	private JLabel lblError = new JLabel("Errors: ", JLabel.LEFT);
	
	private JTextPane txtDesc = new JTextPane();
	private JScrollPane sclDesc = new JScrollPane (txtDesc);
	//South
	private JButton btnSearch = new JButton("Search: ");
	private JTextField txtSearch = new JTextField("");
	private JButton btnAdd = new JButton("Add: ");
	private JTextField txtAdd = new JTextField("");
	
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
		Font mediumFont = new Font("Serif",Font.BOLD,16);
		Font smallFont = new Font("Serif",Font.BOLD,12);
		
		lblTitle.setFont(bigFont);
		lblName.setFont(mediumFont);
		lblParent.setFont(mediumFont);
		lblChild.setFont(mediumFont);
		lblSib.setFont(mediumFont);
		lblError.setFont(smallFont);
		
		
		lstChild.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstChild.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lstChild.setVisibleRowCount(-1);
		lstChild.setFont(bigFont);

		
		lstParent.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstParent.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lstParent.setVisibleRowCount(-1);
		lstParent.setFont(bigFont);
		
		lstSib.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstSib.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lstSib.setVisibleRowCount(-1);
		lstSib.setFont(bigFont);
		
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
		listBox.setLayout(new GridLayout(3, 2, 0, 0)) ;
		
		final Panel descBox = new Panel();
		descBox.setLayout(new GridLayout(2, 1, 0, 0));
		
		add("North", north);
		add("Center", center);
		add("South", south);

		north.setLayout(new GridLayout(2, 1, 5, 5));
		center.setLayout(new GridLayout(2, 1, 5, 5));
		south.setLayout(new GridLayout(3, 2, 5, 5));
		
		
		
		north.add(lblTitle);		
		north.add(lblName);
		
		
		listBox.add(lblChild);
		listBox.add(sclChild);
		
		listBox.add(lblParent);
		listBox.add(sclParent);
		
		listBox.add(lblSib);
		listBox.add(sclSib);
		
		center.add(listBox);
		
		descBox.add(lblError);
		descBox.add(sclDesc);
		center.add(descBox);

		
		
		south.add(btnAdd);
		south.add(txtAdd);
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
					select(selectedNode.findNode(name, 1));
			}
		}};
		
		
		lstChild.addListSelectionListener(selectionListener);
		lstParent.addListSelectionListener(selectionListener);
		lstSib.addListSelectionListener(selectionListener);

		
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

		
		
		ActionListener addListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					selectedNode.add(txtAdd.getText(), selectedNode);
				}
				catch(NullPointerException npe)
				{
					append(npe.getMessage(), Color.RED);
				}
				
				select(selectedNode);
				
				txtAdd.setText("");
				
			}
		};
			
		//Add Button
		btnAdd.addActionListener(addListener);

		//Add TextField
		txtAdd.addActionListener(addListener);
				
		//Exit Button
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
		//Search Button
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select(selectedNode.findNode(txtSearch.getText()));
				
			}
		});
		
		//Search Field
		txtSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Node temp = null;
				temp = selectedNode.findNode(txtSearch.getText(), 5, true);
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
		//master.add(new Node("Test"));
		master.addParent(new Node("Clinton"));
		master.addParent(new Node("Terry"));
		
		master.add("Justin", "Terry");
		
		master.findNode("Terry").addParent(new Node("Diana"));
		master.findNode("Terry").addParent(new Node("George"));
		
		master.add(new Node("Tyler 2.0"));
		master.add(new Node("Tyler 3.0"));
		master.add(new Node("Tyler Jr"));
		

		master.add("Tyler Jr Jr", "Tyler 2.0");
		
		master.add("Tyler 3.0", "Tyler Jr 2.0");
		
		master.findNode("Clinton").addParent(new Node("Eric"));
		master.findNode("Eric").addParent(new Node("Eric's Dad"));
		
		
		master.findNode("Tyler Jr Jr", 6).add(new Node("Mini Tyler"));
		master.findNode("Clinton", 2).add(new Node("Justin"));
		
		
		master.getParent().get(0).findNode("Justin", 3).add(new Node("Mini Justin"));
		
		master.add(new Node("Laptop"));
		master.findNode("Laptop").add(new Node("Keyboard"));
		master.findNode("Laptop").add(new Node("Mouse"));
		
		master.add("Touchpad", "Laptop");
		//master.add("Justin", "Eric");
		
		master.add("Hunger", "Tyler");
		
		selectedNode = master;
		select(selectedNode);
	}
	

	
	/**
	 * Focuses the NodeManager on the selected node.
	 * @param n Node being selected
	 */
	public void select(Node n)
	{
		if (n == null) 
			{
				append("Node not found: " + txtSearch.getText(), Color.RED);
				return;
			}
		
		String[] child = n.toStringArray(n);
		String[] parent = n.toStringArray(n.getParent());
		String[] sibling = n.toStringArray(n.getSiblings());

	    lstChild.setListData(child);

	    lstParent.setListData(parent);
	    
	    //lstSib.setListData(n.getSiblingsList());
	    lstSib.setListData(sibling);
	    
		lblName.setText(n.getName());	
		
		append("Node Found: " + n.getName(), Color.BLUE);
    	selectedNode=n;
		
	}
	
//	public String[] getThing(String[] s)
//	{		
//		return s;
//	}
	
	
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		NodeManager n = new NodeManager();
	}
	
	
}
