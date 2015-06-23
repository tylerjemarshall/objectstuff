package objectstuff;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;



import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;


public class NodeManager extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String endl = "\n";
	
	
	private JLabel lblTitle = new JLabel("Node Manager", JLabel.LEFT);
	
	private JLabel lblParent = new JLabel("Parent: ", JLabel.LEFT);
	private JLabel lblChild = new JLabel("Children: ", JLabel.LEFT);
//	private Panel pnlParent = new Panel();
	private JTextPane jpane = new JTextPane();
	
	private JLabel lblName = new JLabel("Name: ", JLabel.LEFT);

	private JButton btnSearch = new JButton("Search: ");
	private JTextField txtSearch = new JTextField("Search");
	
	private JLabel lblDesc = new JLabel("Description: ", JLabel.LEFT);
	
	private JList<String> childList = new JList<String>();
	private JList<String> parentList = new JList<String>();
	
	Node selectedNode;
	
	

	private JButton btnRefresh = new JButton("Refresh");
	private JButton btnExit = new JButton("Exit");

	public NodeManager() {
		super("Node Manager");
		
		setSize(500, 700);
		setLocationRelativeTo(getParent());

		Font bigFont = new Font("Serif",Font.BOLD,26);
		Font mediumFont = new Font("Serif",Font.BOLD,18);
		Font smallFont = new Font("Serif",Font.BOLD,12);
		
		lblTitle.setFont(bigFont);
		lblName.setFont(mediumFont);
		lblParent.setFont(mediumFont);
		lblChild.setFont(mediumFont);
		lblDesc.setFont(smallFont);
		
		
		// Draw JFrame

		setLayout(new BorderLayout(15, 15));

		
		
		final Panel north = new Panel();
		final Panel center = new Panel();
		final Panel south = new Panel();
		
		add("North", north);
		add("Center", center);
		add("South", south);

		north.setLayout(new GridLayout(2, 1, 5, 5));
		center.setLayout(new GridLayout(6, 1, 5, 5));
		south.setLayout(new GridLayout(2, 2, 5, 5));

		north.add(lblTitle);
		
		
		north.add(lblName);
		
		
		
		childList = new JList<String>(new String[]{"Child List"});
		childList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		childList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		childList.setVisibleRowCount(-1);
		JScrollPane childScroll = new JScrollPane(childList);
		childScroll.setPreferredSize(new Dimension(250, 80));
		
		
		
		parentList = new JList<String>(new String[]{"Parent List"});
		parentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		parentList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		parentList.setVisibleRowCount(-1);
		JScrollPane parentScroll = new JScrollPane(parentList);
		parentScroll.setPreferredSize(new Dimension(250, 80));
		
		//add(childScroll);
		//add(parentScroll);
		
		center.add(lblChild);
		center.add(childList);
		
		center.add(lblParent);
		center.add(parentList);
		
		
		center.add(lblDesc);
		center.add(jpane);
		
		south.add(btnSearch);
		south.add(txtSearch);
		
		
		south.add(btnRefresh);
		south.add(btnExit);
		
		


		setVisible(true);

		ListSelectionListener selectionListener = new ListSelectionListener() {
			@SuppressWarnings("rawtypes")
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JList jl = (JList) e.getSource();

					String name = (String) jl.getSelectedValue();
					try {
						System.out.println("Looking for... " + name);
						if(selectedNode.findNode(name) != null)
						{
							System.out.println("Found Node, going to select");
							selectedNode=selectedNode.findNode(name);
							select(selectedNode);	
						}		
						else
						{
							System.out.println("Could not find node.");
						}
					} catch (NullPointerException npe) {
						System.out.println("Node has no parents!" + npe.toString());
					}
					;	
				}
			}
		};
		
		childList.addListSelectionListener(selectionListener);
		parentList.addListSelectionListener(selectionListener);

		
		
		
//		 This handles closing the client with the X Button
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (promptUser(center, "Close the window?", "Exit") == 1)
					dispose();
			}
		});
		
		
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
				
			}
		});

		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select(selectedNode.findNodeR(txtSearch.getText()));
				
			}
		});


		
	}
	
	
	
	
	// ///////////////////////////////////////////////////////////////////
	// promptUser
	// return 1 if yes, 0 if no	
	// ///////////////////////////////////////////////////////////////////

	private int promptUser(Panel panel, String message, String title) {
		if (JOptionPane.showConfirmDialog(panel, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return 1;
		}
		return 0;

	}
	
	public void append(String s) {
		append(s, Color.black);
	}

	public void append(String s, Color c) {
		try {
			String newString = "";
			int count = 0;

			Style style = jpane.addStyle("Message", null);
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
			Document doc = jpane.getDocument();
			doc.insertString(doc.getLength(), newString, style);

		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		NodeManager n = new NodeManager();
	}
	
	
	public void refresh()
	{
		
		
		Node master = new Node("Tyler");
		master.setParent(new Node("Clinton"));
		
		
		master.add(new Node("Tyler 2.0"));
		master.add(new Node("Tyler 3.0"));
		master.add(new Node("Tyler Jr"));
		
		master.findNode("Tyler 2.0").add(new Node("Tyler Jr Jr"));
		master.findNode("Tyler 3.0").add(new Node("Tyler Jr 2.0"));
		master.getParent().setParent(new Node("Eric"));
		master.getParent().getParent().setParent(new Node("Eric's Dad"));
		
		
		selectedNode = master;
		select(selectedNode);
	}
	
	
	@SuppressWarnings("deprecation")
	public void select(Node n)
	{
		if (n == null) return;
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
	}
}
