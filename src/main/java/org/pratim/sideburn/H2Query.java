package org.pratim.sideburn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;

public class H2Query {

	List<FIVE_TUPLE_DATA> sessionEnd = new ArrayList<FIVE_TUPLE_DATA>();
	Connection conn = null;

	public H2Query() throws Exception {
		DataSource ds = this.initH2();
		conn = ds.getConnection();
		// Set auto-commit to false
		conn.setAutoCommit(false);
		populate(sessionEnd);
		Statement tableCreate = conn.createStatement();
		tableCreate.execute("create table L7_SESSION_END("
				+ "serverIP varchar(100)," + "bytesRecv bigint,"
				+ "bytesSent bigint," + "clientIP varchar(100),"
				+ "clientPort int," + "protocol varchar(20),"
				+ "serverPort int)");

		PreparedStatement bstmt = conn
				.prepareStatement("INSERT INTO L7_SESSION_END(serverIP,bytesRecv,bytesSent,clientIP,clientPort,protocol,serverPort) VALUES(?,?,?,?,?,?,?)");

		for (FIVE_TUPLE_DATA lse : sessionEnd) {
			// Create SQL statement

			bstmt.setString(1, lse.getServerIP());
			bstmt.setLong(2, lse.getBytesRecv());
			bstmt.setLong(3, lse.getBytesSent());
			bstmt.setString(4, lse.getClientIP());
			bstmt.setInt(5, lse.getClientPort());
			bstmt.setString(6, lse.getProtocol());
			bstmt.setInt(7, lse.getServerPort());

			// Add above SQL statement in the batch.
			bstmt.addBatch();

		}

		System.out.println("Ready to execute batch insert");
		bstmt.executeBatch();

		conn.commit();
	}

	public BasicDataSource initH2() {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:db");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");

		return dataSource;

	}

	public void runH2Query() throws Exception {

		long startTime = System.nanoTime();
		

		Statement query = conn.createStatement();

		ResultSet rset = query
				.executeQuery("Select serverIP,bytesRecv,bytesSent,clientIP,clientPort,serverPort,protocol from L7_SESSION_END "
						+ " where serverPort = 80 and protocol = 'HTTP'");

		List<FIVE_TUPLE_DATA> five_tuples_filtered = new ArrayList<FIVE_TUPLE_DATA>();
		
		
		while (rset.next()) {
			
			five_tuples_filtered.add(new FIVE_TUPLE_DATA(rset.getString(1), rset.getLong(2), rset.getLong(3), rset.getString(4), rset.getInt(5), rset.getInt(6), rset.getString(7)));
			// System.out.println("Server IP: " + rset.getString(1)
			// + " bytesRecvd " + rset.getLong(2)
			// + " Server Port " + rset.getInt(3));
		}

		System.out.println("H2 Query Returned " + five_tuples_filtered.size()
				+ " rows,total time taken : "
				+ (System.nanoTime() - startTime) + " nano seconds");

//		conn.close();

	}

	private static void populate(List<FIVE_TUPLE_DATA> sessionEnd)
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
