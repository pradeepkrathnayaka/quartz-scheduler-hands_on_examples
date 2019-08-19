package com.rmpksoft.qrtz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.utils.ConnectionProvider;
import org.quartz.utils.DBConnectionManager;

public class QrtzUtils {

	private static final String DATABASE_DRIVER_CLASS = "org.hsqldb.jdbc.JDBCDriver";
	private static final String DATABASE_CONNECTION_PREFIX = "jdbc:hsqldb:memory:";
	private static List<String> DATABASE_SETUP_STATEMENTS = null;

	static {
		try {
			Class.forName(DATABASE_DRIVER_CLASS).newInstance();
		} catch (ClassNotFoundException e) {
			throw new AssertionError(e);
		} catch (InstantiationException e) {
			throw new AssertionError(e);
		} catch (IllegalAccessException e) {
			throw new AssertionError(e);
		}

		List<String> setup = new ArrayList<>();
		String setupScript;
		
		try {
            InputStream setupStream = QrtzUtils.class.getClassLoader().getResourceAsStream("sql/tables_hsqldb.sql");
            try {
                BufferedReader r = new BufferedReader(new InputStreamReader(setupStream, StandardCharsets.US_ASCII));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = r.readLine();
                    if (line == null) {
                        break;
                    } else if (!line.startsWith("--")) {
                        sb.append(line).append("\n");
                    }
                }
                setupScript = sb.toString();
            } finally {
                setupStream.close();
            }
        } catch (IOException e) {
            throw new AssertionError(e);
        }
		
		for (String command : setupScript.split(";")) {
            if (!command.matches("\\s*")) {
                setup.add(command);
            }
        }
        DATABASE_SETUP_STATEMENTS = setup;
	}
	
	public static void createDatabase(String name) throws SQLException {
        /*DBConnectionManager.getInstance().addConnectionProvider(name,
                new DerbyEmbeddedConnectionProvider(name));*/
    }

	private QrtzUtils() {
	}

}
