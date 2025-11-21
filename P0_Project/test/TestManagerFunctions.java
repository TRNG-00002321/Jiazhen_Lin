import org.junit.After;
import org.junit.Before;
import org.junit.Test ;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.FileReader;

public class TestManagerFunctions {
    Connection connection;

    @Before
    public void setUp() throws Exception {
        try{
            connection  = DriverManager.getConnection("jdbc:sqlite:memory:");

        }
        catch(SQLException e){
            System.err.println(e.getStackTrace());
        }
    }
    @After
    public void tearDown() throws Exception {
        connection.close();
    }
}
