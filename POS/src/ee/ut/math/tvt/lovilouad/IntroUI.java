package ee.ut.math.tvt.lovilouad;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.*;

public class IntroUI extends JFrame{
	
	
	public IntroUI() {
		Properties appProp = new Properties();
		Properties verProp = new Properties();
		
		//The JFrame's settings
		setTitle("Intro");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create a container
		Container intro = getContentPane();
		intro.setBackground(new Color(248, 187, 14));
		intro.setLayout(new BoxLayout(intro, BoxLayout.Y_AXIS));
		
		//Read in contents
		try {
			appProp.load(new FileInputStream("application.properties"));
			verProp.load(new FileInputStream("version.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel teamName = new JLabel("Team name: " + appProp.getProperty("teamName"));
		teamName.setFont(new Font("Arial", Font.PLAIN, 15));
		teamName.setForeground(Color.BLACK);
		
		JLabel teamLeader = new JLabel("Team leader: " + appProp.getProperty("teamLeader"));
		teamLeader.setFont(new Font("Arial", Font.PLAIN, 15));
		teamLeader.setForeground(Color.BLACK);
		
		JLabel leaderEmail = new JLabel("Leader email: " + appProp.getProperty("leaderEmail"));
		leaderEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		leaderEmail.setForeground(Color.BLACK);
		
		JLabel teamMembers = new JLabel("Team members: " + appProp.getProperty("teamMembers"));
		teamMembers.setFont(new Font("Arial", Font.PLAIN, 15));
		teamMembers.setForeground(Color.BLACK);
		
		JLabel buildVersion = new JLabel("Version: " + verProp.getProperty("build.number"));
		buildVersion.setFont(new Font("Arial", Font.PLAIN, 15));
		buildVersion.setForeground(Color.BLACK);
		
		ImageIcon logoImage = new ImageIcon("images/lovilouad_logo.gif");
		JLabel logoLabel = new JLabel(logoImage);
		
		intro.add(teamName);
		intro.add(teamLeader);
		intro.add(leaderEmail);
		intro.add(teamMembers);
		intro.add(buildVersion);
		intro.add(logoLabel);

		pack();	
		
		//Position the JFrame
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension actualSize = getContentPane().getSize();
		setLocation((screen.width - actualSize.width) / 2, (screen.height - actualSize.height) / 2);
	}

}
