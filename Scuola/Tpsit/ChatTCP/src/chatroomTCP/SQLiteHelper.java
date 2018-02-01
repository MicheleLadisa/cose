package chatroomTCP;

// @author ladis
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

//@author ladis

public class SQLiteHelper
{

    static String URL = "jdbc:sqlite:C:\\database\\DbAccountChat.db";

    public static void newDatabase()
    {
        Connection c = null;
        File files = new File("C:\\database");
        if (!files.exists())
        {
            files.mkdirs();
        }
        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(URL);
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void newTable()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            String command = "CREATE TABLE IF NOT EXISTS dati(\n "
                    + "username TEXT PRIMARY KEY NOT NULL,\n"
                    + "password TEXT NOT NULL\n"
                    + ");";
            stmt.executeUpdate(command);
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean addNewUser(String username, String password)
    {
        boolean created = false;
        if (!checkUsername(username))
        {
            String credentials = "INSERT INTO dati VALUES(?,?);";
            try
            {
                Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(credentials);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                created = true;
            } catch (SQLException ex)
            {
                Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return created;
    }

    static boolean checkCredentials(String username, String password)
    {
        boolean found = false;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("SELECT username,password FROM dati WHERE username=? AND password=?;");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet res = pstmt.executeQuery();
            if (res.next())
            {
                found = true;
            }
            res.close();
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }

    static boolean checkUsername(String username)
    {
        boolean found = false;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("SELECT username FROM dati WHERE username=?;");
            pstmt.setString(1, username);
            ResultSet res = pstmt.executeQuery();
            if (res.next())
            {
                found = true;
            }
            res.close();
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }
}