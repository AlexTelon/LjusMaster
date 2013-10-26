/**
 * Created with IntelliJ IDEA.
 * User: vinterljus
 * Date: 10/26/13
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import javax.sound.sampled.*;

public class Main {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/EMP";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name, text, source, geo, time FROM Database";
            ResultSet rs = stmt.executeQuery(sql);
            int id;
            String name,text,source,geo,time;

            boolean loopOn = true;
            while(loopOn) {



                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
                    id  = rs.getInt("id");
                    name = rs.getString("name");
                    text = rs.getString("text");
                    source = rs.getString("source");
                    geo = rs.getString("geo");
                    time = rs.getString("time");

                    //Display values
                    System.out.print("ID: " + id);
                    System.out.print(", name: " + name);
                    System.out.print(", text: " + text);
                    System.out.print(", source: " + source);
                    System.out.println(", geo: " + geo);
                    System.out.println(", time: " + time);
                }
            }

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    public static void playSound(String nameOfSong) throws LineUnavailableException {
        File file = new File("src/fantasy.wav");
        Clip clip = AudioSystem.getClip();
        // getAudioInputStream() also accepts a File or InputStream
        try {
            AudioInputStream ais = AudioSystem.
                    getAudioInputStream( file );
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    // A GUI element to prevent the Clip's daemon Thread
                    // from terminating at the end of the Main()
                    JOptionPane.showMessageDialog(null, "Close to exit!");
                }
            });
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Wrong file format" + e.getMessage());
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

