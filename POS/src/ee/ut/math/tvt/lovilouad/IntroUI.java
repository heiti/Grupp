package ee.ut.math.tvt.lovilouad;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.*;
import java.util.Properties;

public class IntroUI {

	IntroUI() {
		Properties appProp = new Properties();
		Properties verProp = new Properties();
		
		JFrame introFrame = new JFrame("POS Intro");
		introFrame.setLayout(null);
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 500, 150);
		mainPanel.setLayout(new GridLayout(5,1));
		mainPanel.setBackground(new Color(248, 187, 14));
		ImageIcon logoImage = new ImageIcon("images/lovilouad_logo.gif");
		JLabel logoLabel = new JLabel(logoImage);
		logoLabel.setBounds(0, 150, 500, 150);
		introFrame.add(mainPanel);
		introFrame.add(logoLabel);
		introFrame.setSize(500,330);
		introFrame.setLocation(400, 250);
		introFrame.setResizable(false);
		introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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

		mainPanel.add(teamName);
		mainPanel.add(teamLeader);
		mainPanel.add(leaderEmail);
		mainPanel.add(teamMembers);
		mainPanel.add(buildVersion);
		
		introFrame.setVisible(true);
	}

}
