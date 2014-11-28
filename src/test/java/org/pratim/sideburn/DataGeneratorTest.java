package org.pratim.sideburn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.junit.Test;

public class DataGeneratorTest {

	final String[] protocols = new String[] { "POP3", "HTTP", "FTP", "SMTP" };
	final int[] ports = new int[] { 22, 23, 25, 80, 90 };

	@Test
	public void test() throws IOException {
		Random r = new Random();
		FileWriter fw = new FileWriter("test_data.csv");
		BufferedWriter writer = new BufferedWriter(fw);
		
		for (int i = 0; i < 2334222; i++) {
			
			FIVE_TUPLE_DATA ftd = new FIVE_TUPLE_DATA(getRandomIP(r),
					r.nextInt(999999), r.nextInt(999999), getRandomIP(r),
					fetchPort(r), fetchPort(r), fetchProtocol(r));
		
			writer.write(ftd.toString() + "\n");
			
			if(i % 1000 == 0)
				System.out.println("Counter at : " + i);
			
		}
		writer.flush();
		writer.close();
		fw.close();

	}

	private String getRandomIP(Random r) {

		return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256)
				+ "." + r.nextInt(256);

	}

	private int fetchPort(Random r) {
		return ports[r.nextInt(4)];
	}

	private String fetchProtocol(Random r) {
		return protocols[r.nextInt(3)];
	}

}
