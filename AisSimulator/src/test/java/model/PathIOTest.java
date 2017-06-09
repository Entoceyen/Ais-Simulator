package model;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import model.io.PathIO;

public class PathIOTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void pathReadTest() {
		System.out.println("\nTest read\n");
		URL myTestURL = ClassLoader.getSystemResource("path.csv");
		File f;
		try {
			f = new File(myTestURL.toURI());
			Path p = PathIO.readPath(f);
			
			assertTrue(p.getNbSteps() == 5);
			
			for(int i=0; i<p.getNbSteps() ; i++) {
				System.out.println(p.getStep(i).getLatitudeDouble() +" "+ p.getStep(i).getLongitudeDouble());
				assertTrue((long) (p.getStep(i).getLatitudeDouble()*600000) == p.getStep(i).getLatitude());
				assertTrue((long) (p.getStep(i).getLongitudeDouble()*600000) == p.getStep(i).getLongitude());
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
