/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Connect;
import model.TipoDeProcesso;

public class TipoDeProcessoDao extends Connect{
    //Incluir
    public boolean incluir(TipoDeProcesso tp) throws SQLException{
        boolean retorno = false;
        int intRetorno=0;
        Connection con = null;
        try{
            con=this.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO tipo_de_processo (tipo) VALUES (?)");
            ps.setString(1, tp.getTipoDoProcesso());
            intRetorno=ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            con.close();
        }
        if(intRetorno>0)retorno=true;
        return retorno;
    }
    //Alterar
    public boolean alterar(TipoDeProcesso tp) throws SQLException{
        boolean retorno = false;
        int intRetorno=0;
        Connection con = null;
        try{
            con=this.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE tipo_de_processo SET tipo=? WHERE id=?");
            ps.setString(1, tp.getTipoDoProcesso());
            ps.setInt(2,tp.getId());
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
    public boolean excluir(TipoDeProcesso tp) throws SQLException{
        boolean retorno = false;
        int intRetorno=0;
        Connection con = null;
        try{
            con=this.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM tipo_de_processo WHERE id=?");
            ps.setLong(1, tp.getId());
            intRetorno=ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            con.close();
        }
        if(intRetorno>0)retorno=true;
        return retorno;
    }
    //Consulta por cod
    public TipoDeProcesso consultar(int codigo) throws SQLException{
        Connection con=null;
        TipoDeProcesso tp = new TipoDeProcesso();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM tipo_de_processo WHERE id="+codigo);
            rs=ps.executeQuery();
            if(rs.next()){
                tp.setTipoDoProcesso(rs.getString("tipo"));
                tp.setId(rs.getInt("id"));
            }else{
                tp=null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con, ps, rs);           
        }
        return tp;
    }
    //consulta por nome
    public TipoDeProcesso consultar(String nome) throws SQLException{
        Connection con=null;
        TipoDeProcesso tp = new TipoDeProcesso();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM tipo_de_processo WHERE tipo like'%"+nome+"%'");
            rs=ps.executeQuery();
            if(rs.next()){
                tp.setTipoDoProcesso(rs.getString("tipo"));
                tp.setId(rs.getInt("id"));
            }else{
                tp=null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con, ps, rs);           
        }
        return tp;
    }
    //Consultar todos
    public ArrayList<TipoDeProcesso>consultar()throws SQLException{
        Connection con=null;
        TipoDeProcesso tp = new TipoDeProcesso();;
        ArrayList<TipoDeProcesso> atp= new ArrayList<>();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps=con.prepareStatement("SELECT * FROM tipo_de_processo order by tipo");
            rs=ps.executeQuery();
            while(rs.next()){
                tp= new TipoDeProcesso();
                tp.setId(rs.getInt("id"));
                tp.setTipoDoProcesso(rs.getString("tipo"));
                atp.add(tp);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con, ps, rs);
        }
        return atp;
    }
    //Consultar todos por nome especifico
    public ArrayList<TipoDeProcesso>consultaEspecifica(String nome)throws SQLException{
        Connection con=null;
        TipoDeProcesso tp;
        ArrayList<TipoDeProcesso> atp= new ArrayList<>();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps=con.prepareStatement("SELECT * FROM tipo_de_processo WHERE tipo like '%"+nome+"%'");
            rs=ps.executeQuery();
            while(rs.next()){
                tp= new TipoDeProcesso();
                tp.setId(rs.getInt("id"));
                tp.setTipoDoProcesso(rs.getString("tipo"));
                atp.add(tp);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con, ps, rs);
        }
        return atp;
    }
}

