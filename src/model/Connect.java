/**
 * A classe Connect é responsável por estabelecer conexão com o banco de dados PostgreSQL
 * e fornecer métodos auxiliares para fechar a conexão, declaração preparada (PreparedStatement)
 * e resultado (ResultSet).
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author icaro
 */
public class Connect {
    private static final String URL= "jdbc:postgresql://localhost:5432/sicopa";
    private static final String USER= "postgres";
    private static final String PASS= "icaro123";
    public Connect(){
        
    }
    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con=DriverManager.getConnection(URL, USER, PASS);
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na conexão: ",ex);
        }
            
    }
    
    public static void closeConnection(Connection con){
            try {
                if(con!=null){
                con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            }       
    }
    public static void closeConnection(Connection con,PreparedStatement sql){
            closeConnection(con);
            try {
                if(sql!=null){
                sql.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            }       
    }
    public static void closeConnection(Connection con,PreparedStatement sql,ResultSet rs){
            closeConnection(con,sql);
            try {
                if(rs!=null){
                rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            }       
    }
    
}
