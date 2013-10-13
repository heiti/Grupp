package ee.ut.math.tvt.lovilouad;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Intro {
	
	private static final Logger log = Logger.getLogger(Intro.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		
		IntroUI intro = new IntroUI();
		
		log.info("System executed!");
	}

}
