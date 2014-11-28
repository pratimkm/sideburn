package org.pratim.sideburn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

public class InMemoryQueryMaker {

	Set<FIVE_TUPLE_DATA> sessionEnd = null;
	
	public InMemoryQueryMaker() throws Exception {
		sessionEnd = new HashSet<FIVE_TUPLE_DATA>();
		populate(sessionEnd);
	}

	public void lambdaQueryMaker() throws Exception {

		/*************** CASE 1 ****************************************/

		long startTime = System.nanoTime();
		List<FIVE_TUPLE_DATA> filtered = sessionEnd
				.stream()
				.filter(l1 -> 
						 l1.getProtocol().equals("HTTP")
						&& l1.getServerPort() == 80)
				.collect(Collectors.toList());

		/*************** END CASE 1 ****************************************/

		long endTime = System.nanoTime();

//		System.out.println("Count of traffic for HTTP protocol on port 80 is "
//				+ filtered.size());
		System.out.println("Total rows returned : " + filtered.size() + " Total time taken on lambda : " + (endTime - startTime)
				+ " nano seconds");

		// /*************** CASE 2 ****************************************/
//		Map<String, List<FIVE_TUPLE_DATA>> filteredSet = sessionEnd
//				.stream()
//				.collect(Collectors.groupingBy(FIVE_TUPLE_DATA::getServerIP))
//				;

	}

	private static void populate(Set<FIVE_TUPLE_DATA> sessionEnd)
			throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				InMemoryQueryMaker.class.getClassLoader().getResourceAsStream(
						"LX_session_query_big.csv")));

		String str = "";

		while ((str = reader.readLine()) != null) {
			String[] tokens = StringUtils.split(str, "|");// str.split("|");
			FIVE_TUPLE_DATA lse = new FIVE_TUPLE_DATA(tokens[0].trim(),
					Long.parseLong(tokens[1].trim()), Long.parseLong(tokens[2]
							.trim()), tokens[3].trim(),
					Integer.parseInt(tokens[4].trim()),
					Integer.parseInt(tokens[5].trim()), tokens[6].trim());
			sessionEnd.add(lse);
		}

		reader.close();

	}


}
