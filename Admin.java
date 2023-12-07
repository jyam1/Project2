package CS2450;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class Admin{
	private static Admin instance;
	
	private DefaultMutableTreeNode treeRoot;
	
	private JTextArea userId;
	private JTextArea groupId;
	private JButton addUser;
	private JButton addGroup;
	
	private JButton openUserView;
	private JButton findLastUpdated;
	
	private JButton showUserTotal;
	private JButton showMessagesTotal;
	private JButton showGroupTotal;
	private JButton showPositivePercentage;
	private JButton IdVerification;
	private JTree tree;
	
	private String[] users = new String[100];
	private String[] groups = new String[100];
	private User[] userObjects = new User[100];
	private int numUsers = 0;
	private int numGroups = 0;

	private boolean valid = true;
	
	public static Admin getInstance() {
		if (instance == null) {
			instance = new Admin();
		}
		
		return instance;
	}
	
	private Admin(){
		JFrame jfrm = new JFrame("Admin");
		jfrm.setSize(800,400);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Border simpleBorder = BorderFactory.createLineBorder(Color.BLACK);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new TitledBorder("Admin"));
		mainPanel.setLayout(new GridLayout(1,2,7,7)	);
		
		JPanel treePanel = new JPanel();
		treePanel.setBorder(new TitledBorder("Tree View"));
		treeRoot = new DefaultMutableTreeNode("Root");
		tree = new JTree(treeRoot);
		treePanel.setBorder(simpleBorder);
		treePanel.add(tree);
		
		JPanel configPanel = new JPanel();
		configPanel.setLayout(new BorderLayout());
		
		JPanel aboveConfig = new JPanel();
		aboveConfig.setLayout(new GridLayout(3,1, 5 ,5));
		
		JPanel treeConfig = new JPanel();
		treeConfig.setLayout(new GridLayout(2,2,5,5));
		userId = new JTextArea("User Id");
		groupId = new JTextArea("Group Id");
		addUser = new JButton("Add User");
		addGroup = new JButton("Add Group");
		userId.setBorder(simpleBorder);
		groupId.setBorder(simpleBorder);
		treeConfig.add(userId);
		treeConfig.add(addUser);
		treeConfig.add(groupId);
		treeConfig.add(addGroup);
		
		JPanel userView = new JPanel();
		userView.setLayout(new BorderLayout());
		openUserView = new JButton("Open User View");
		userView.add(openUserView, BorderLayout.NORTH);
		
		JPanel showStats = new JPanel();
		showStats.setLayout(new GridLayout(2,2,5,5));
		showUserTotal = new JButton("Show User Total");
		showMessagesTotal = new JButton("Show Messages Total");
		showGroupTotal = new JButton("Show Group Total");
		IdVerification = new JButton("ID Verification");
		findLastUpdated = new JButton("Find last updated user");
		showPositivePercentage = new JButton("Show Positive Percentage");
		showStats.add(showUserTotal);
		showStats.add(showMessagesTotal);
		showStats.add(showGroupTotal);
		showStats.add(showPositivePercentage);
		
		configPanel.add(treeConfig, BorderLayout.NORTH);
		aboveConfig.add(openUserView);
		aboveConfig.add(IdVerification);
		aboveConfig.add(findLastUpdated);
		configPanel.add(aboveConfig, BorderLayout.CENTER);
		configPanel.add(showStats, BorderLayout.SOUTH);
		
		mainPanel.add(treePanel, BorderLayout.WEST);
		mainPanel.add(configPanel, BorderLayout.EAST);


		findLastUpdated.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){

				String lastUpdatedUser = userObjects[0].getId();
				User lastUpdateUserObject = userObjects[0];

				for (int x = 0; x < numUsers; x++){
					if(lastUpdateUserObject.getLastUpdate() <= userObjects[x].getLastUpdate()){
						lastUpdatedUser = userObjects[x].getId();
						lastUpdateUserObject = userObjects[x];
					}
				}

				System.out.println("Last updated user: " + lastUpdatedUser);
			}
		});

		IdVerification.addActionListener(new ActionListener() {

			boolean same = true;
			boolean space = true;
			boolean break1;
			boolean break2;

			public void actionPerformed(ActionEvent ae){
				for(int y = 0; y < numUsers; y++){
					for(int x = 0; x < numUsers; x++){
						if (userObjects[y].getId().equals(userObjects[x].getId())){
							same = false;
							break1 = true;
							break;
						}
					}

					if (break1){
						break;
					}
				}

				for(int x = 0; x < numUsers; x++){
					for(int y = 0; y < userObjects[x].getId().length(); y++){
						System.out.println(userObjects[x].getId());
						if(userObjects[x].getId().charAt(y) == ' '){
							space = false;
							break2 = true;
							break;
						}

						if (break2){
							break;
						}
					}
				}

				if (same == true && space == true){
					valid = true;
				}
				else{
					valid = false;
				}

				if (valid){
					System.out.println("Users are valid");
				}

				else{
					System.out.println("Users are not valid");
				}
				
			}
		});
		
		addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (selectedNode == null) {
	                    selectedNode = treeRoot;
	            }
				
				for (int x = 0; x < numUsers; x++) {
		            if (users[x].equals(userId.getText())) {
		                return;
		            }
		        }
				
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(userId.getText());
				selectedNode.add(newNode);
				
				
				users[numUsers] = userId.getText();
				userObjects[numUsers] = new User(userId.getText(), Admin.this);
				numUsers += 1;
				
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
	            model.nodeStructureChanged(selectedNode);
	            
	            TreePath path = new TreePath(newNode.getPath());
                tree.expandPath(path);
                
                tree.setSelectionPath(null);
			}
		});
		
		addGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				DefaultMutableTreeNode selectedNode = treeRoot;
				
				for (int x = 0; x < numGroups; x++) {
		            if (groups[x].equals(groupId.getText())) {
		                return;
		            }
		        }
				
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(groupId.getText());
				selectedNode.add(newNode);
				
				groups[numGroups] = groupId.getText();
				numGroups += 1;
				
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
	            model.nodeStructureChanged(selectedNode);
	            
	            TreePath path = new TreePath(newNode.getPath());
                tree.expandPath(path);
                
                tree.setSelectionPath(null);
			}
		});
		
		showUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.out.print("Number of users: " + numUsers + "\n");
			}
		});
		
		showGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.out.print("Number of groups: " + numGroups + "\n");
			}
		});
			
		openUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				
				for (int x = 0; x < numUsers; x++) {
					if (users[x].equals(selectedNode.toString())) {
						System.out.println("Time created: " + userObjects[x].getTimeCreated());
		                userObjects[x].setVisible();
		            }
				}
				
				return;
			}
		});
		
		showPositivePercentage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				for (int x = 0; x < numUsers; x++) {
					if (users[x].equals(selectedNode.toString())) {
						System.out.print("Positive percentage: " + userObjects[x].returnPositiveMessages() + "\n");
					}
				}
			}
		});
		
		showMessagesTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				for (int x = 0; x < numUsers; x++) {
					if (users[x].equals(selectedNode.toString())) {
						System.out.print("Total Messages: " + userObjects[x].returnTotalMessages() + "\n");
					}
				}
			}
		});
		tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
            	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (selectedNode == null) {
                    addUser.setEnabled(false);
                    addGroup.setEnabled(false);
                } else if (selectedNode.toString().equals("Root")) {
                    addUser.setEnabled(true);
                    addGroup.setEnabled(true);
                } else {
                    addUser.setEnabled(false);
                    addGroup.setEnabled(false);
                    for (int x = 0; x < numGroups; x++) {
                        if (groups[x].equals(selectedNode.toString())) {
                            addUser.setEnabled(true);
                            break;
                        }
                    }
                }
            }
        });
		
		jfrm.add(mainPanel);
		
		jfrm.setVisible(true);
	}
	
	public void updateOtherUsers(String user, String message){
		for (int x = 0; x < numUsers; x++) {
			if (users[x].equals(user)) {
				userObjects[x].addTweet(message);
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Admin();
			}
		});

	}

}
