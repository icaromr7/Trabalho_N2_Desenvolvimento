/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Advogado;
import model.Connect;
import static model.Connect.closeConnection;
import model.Usuario;

/**
 *
 * @author icaro
 */
public class AdvogadoDao extends Connect{
    //Incluir
    public boolean incluir(Advogado a) throws SQLException{
        boolean retorno = false;
        int intRetorno=0;
        Connection con = null;
        try{
            con=this.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO advogado (oab, nome, advlogin) VALUES (?, ? , ?)");
            ps.setInt(1,a.getOab());
            ps.setString(2,a.getNome());
            ps.setString(3,a.getAdvlogin());
            intRetorno=ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            con.close();
        }
        if(intRetorno>0)retorno=true;
        return retorno;
    }
    //Excluir
    public boolean excluir(Advogado a) throws SQLException{
        boolean retorno = false;
        int intRetorno=0;
        Connection con = null;
        try{
            con=this.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM advogado WHERE oab=?");
            ps.setInt(1,a.getOab());
            intRetorno=ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            con.close();
        }
        if(intRetorno>0)retorno=true;
        return retorno;
    }
    //Consultar
    public Advogado consultar(int oab) throws SQLException{
        Connection con=null;
        Advogado aux = new Advogado();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM advogado WHERE oab=?");
            ps.setInt(1, oab);
            rs=ps.executeQuery();
            if(rs.next()){
                aux.setId(rs.getInt("id"));
                aux.setOab(rs.getInt("oab"));
                aux.setNome(rs.getString("nome"));
                aux.setAdvlogin(rs.getString("advlogin"));
            }else{
                aux=null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con, ps, rs);           
        }
        return aux;
    }
}
