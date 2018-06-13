package ch.bfh.bti7081.s2018.white.pms.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class TestDataImporter {

    public static final Logger log = LogManager.getLogger(TestDataImporter.class.getName());

    private static final String H2_DRIVER = "org.h2.Driver";
    private static final String H2_URL = "jdbc:h2:file:./target/h2db/db/pms";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";
    private static final String SQL_SCRIPT_DML_PATH = "src/test/resources/testData.sql";

    public static void importTestData() throws Exception {
        Class.forName(H2_DRIVER);
        Connection connection = DriverManager.getConnection(H2_URL, H2_USER, H2_PASSWORD);
        try {
            RunScript.execute(connection, new FileReader(SQL_SCRIPT_DML_PATH));
        }
        finally {
            connection.close();
        }
    }

}
