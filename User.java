import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class User{
	
	private JTextArea userId;
	private JButton followUser;
	private JList<String> followers;
	private JTextArea tweetMessage;
	private JButton postTweet;
	private JList<String> newsFeed;
	private JFrame jfrm;
	private DefaultListModel<String> tweetModel;
	private DefaultListModel<String> followModel;
	
	private String[] following = new String[100];
	private String[] messages = new String[100];
	private int totalMessages = 0;
	private int positiveMessages = 0;
	private int numFollow = 0;
	
	User(String userName, Admin admin){
		
		jfrm = new JFrame("User");
		jfrm.setSize(300,400);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Border simpleBorder = BorderFactory.createLineBorder(Color.BLACK); 
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,1,7,7));
		mainPanel.setBorder(new TitledBorder(userName));
		
		JPanel userTopJPanel = new JPanel();
		userTopJPanel.setLayout(new BorderLayout());
		JPanel userBottomJPanel = new JPanel();
		userBottomJPanel.setLayout(new BorderLayout());
		
		JPanel userTopSubPanel = new JPanel();
		userTopSubPanel.setLayout(new GridLayout(1,2,5,5));
		JPanel userTopSubPanel2 = new JPanel();
		userTopSubPanel2.setLayout(new GridLayout(1,1,5,5));
		JPanel userBottomSubPanel = new JPanel();
		userBottomSubPanel.setLayout(new GridLayout(1,2,5,5));
		JPanel userBottomSubPanel2 = new JPanel();
		userBottomSubPanel2.setLayout(new GridLayout(1,1,5,5));
		
		tweetModel = new DefaultListModel<String>();
		followModel = new DefaultListModel<String>();
		
		userId = new JTextArea("User ID");
		followUser = new JButton("Follow User");
		followers = new JList<String>(followModel);
		
		tweetMessage = new JTextArea("Tweet Message");
		postTweet = new JButton("Post Tweet");
		newsFeed = new JList<String>(tweetModel);
		
		userId.setBorder(simpleBorder);
		followers.setBorder(simpleBorder);
		tweetMessage.setBorder(simpleBorder);
		newsFeed.setBorder(simpleBorder);
		
		
		userTopSubPanel2.add(followers);
		userTopSubPanel.add(userId);
		userTopSubPanel.add(followUser);
		userBottomSubPanel2.add(newsFeed);
		userBottomSubPanel.add(tweetMessage);
		userBottomSubPanel.add(postTweet);
		
		userTopJPanel.add(userTopSubPanel, BorderLayout.NORTH);
		userTopJPanel.add(userTopSubPanel2, BorderLayout.CENTER);
		userBottomJPanel.add(userBottomSubPanel,BorderLayout.NORTH);
		userBottomJPanel.add(userBottomSubPanel2, BorderLayout.CENTER);
		
		mainPanel.add(userTopJPanel);
		mainPanel.add(userBottomJPanel);
		
		jfrm.add(mainPanel);
		
		followUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				following[numFollow] = userId.getText();
				
				followModel.addElement(userId.getText());
				
				numFollow += 1;
				
				int lastIndex = followModel.getSize() - 1;
                followers.ensureIndexIsVisible(lastIndex);
                
                followers.repaint();
				
			}
		});
		
		postTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				messages[totalMessages] = tweetMessage.getText();
				totalMessages += 1;
				
				tweetModel.addElement(userName + ": " + tweetMessage.getText());
				
				newsFeed.repaint();
				
				if(tweetMessage.getText().indexOf("good") != 1 || tweetMessage.getText().indexOf("Good") != 1) {
					positiveMessages += 1;
				}
				
				admin.updateOtherUsers(userName, userName + ": " + tweetMessage.getText());

			}
		});
		
		
	}
	
	public void setVisible() {
		jfrm.setVisible(true);
	}
	
	public void addTweet(String message) {
		tweetModel.addElement(message);
		
		newsFeed.repaint();
	}
	
	
	public double returnPositiveMessages() {
		if (totalMessages == 0){
			return 0.0;
		}
		else
			return (float)(positiveMessages/totalMessages);
	}
	public boolean isFollower(String name){
        for (int x = 0; x < numFollow; x++){
            if (name.equals(following[x])){
                return true;
            }

        }

        return false;
    }
	public int returnTotalMessages() {
		return totalMessages;
	}
}
