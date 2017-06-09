package model;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import model.io.DataIO;

public class DataIOTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testReadDatas() {
		System.out.println("\nTest read\n");
		URL myTestURL = ClassLoader.getSystemResource("dataRead.csv");
		File f;
		try {
			f = new File(myTestURL.toURI());
			HashMap<String, String> data = DataIO.readDatas(f);
			
			for(Entry<String, String> e : data.entrySet())
				System.out.println(e.getKey()+";"+e.getValue());
			
			assertTrue(data.get("mmsi").equals("222222222"));
			assertTrue(data.get("callsign").equals("@@@@@@@"));
			assertTrue(data.get("shipType").equals("36"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Test
	public void testWriteDatas() {
		System.out.println("\nTest write\n");
		HashMap<String,String> data = new HashMap<String,String>();
		data.put("mmsi", "123456789");
		data.put("name", "ceciestunnom");
		data.put("speed", "80");
		try {
			File f = DataIO.writeDatas(data);
			HashMap<String, String> data2 = DataIO.readDatas(f);
			
			for(Entry<String, String> e : data2.entrySet())
				System.out.println(e.getKey()+";"+e.getValue());
			
			assertTrue(data.get("mmsi").equals("123456789"));
			assertTrue(data.get("name").equals("ceciestunnom"));
			assertTrue(data.get("speed").equals("80"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
