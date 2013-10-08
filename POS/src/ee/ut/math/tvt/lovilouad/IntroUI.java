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
		introFrame.setSize(400,300);
		introFrame.setLocation(400, 250);
		introFrame.setResizable(false);
		introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		introFrame.setVisible(true);
		
		try {
			appProp.load(new FileInputStream("application.properties"));
			
			JLabel teamName = new JLabel(appProp.getProperty("teamName"));
			teamName.setFont(new Font("Arial", Font.PLAIN, 20));
			teamName.setForeground(Color.BLACK);
			teamName.setBounds(20, 20, 200, 20);
			introFrame.add(teamName);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	//Team name
	//Team leader
	//Team leader emali
	//Team members
	//Team logo (random image)
	//Your software version number


}
