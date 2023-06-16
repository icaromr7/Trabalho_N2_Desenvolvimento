/*
Classe de conexão com a tabela advogado no banco de dados.
realiza inclusão, alteração, exclusão e pesquisa nessa tabela.
*/
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            PreparedStatement ps = con.prepareStatement("INSERT INTO advogado (oab, nome, id_login) VALUES (?, ? , ?)");
            ps.setInt(1,a.getOab());
            ps.setString(2,a.getNome());
            ps.setInt(3,a.getId_login());
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
    public boolean alterar(Advogado a) throws SQLException{
        boolean retorno = false;
        int intRetorno=0;
        Connection con = null;
        try{
            con=this.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE advogado SET oab=?,nome=? WHERE id=?");
            ps.setInt(1,a.getOab());
            ps.setString(2,a.getNome());
            ps.setInt(3, a.getId());
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
            PreparedStatement ps = con.prepareStatement("DELETE FROM advogado WHERE id=?");
            ps.setInt(1,a.getId());
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
                aux.setId_login(rs.getInt("id_login"));
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
    //Consultar
    public Advogado consultar(Advogado a) throws SQLException{
        Connection con=null;
        Advogado aux = new Advogado();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM advogado WHERE id=?");
            ps.setInt(1, a.getId());
            rs=ps.executeQuery();
            if(rs.next()){
                aux.setId(rs.getInt("id"));
                aux.setOab(rs.getInt("oab"));
                aux.setNome(rs.getString("nome"));
                aux.setId_login(rs.getInt("id_login"));
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
    //Consultar todos
    public ArrayList<Advogado>consultar()throws SQLException{
        Connection con=null;
        Advogado a= new Advogado();;
        ArrayList<Advogado> ac= new ArrayList<>();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps=con.prepareStatement("SELECT * FROM advogado order by nome");
            rs=ps.executeQuery();
            while(rs.next()){
                a= new Advogado();
                a.setId(rs.getInt("id"));
                a.setOab(rs.getInt("oab"));
                a.setNome(rs.getString("nome"));
                a.setId_login(rs.getInt("id_login"));
                ac.add(a);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con, ps, rs);
        }
        return ac;
    }
    //Consulta por nome
    public ArrayList<Advogado>consultar(String nome)throws SQLException{
        Connection con=null;
        Advogado a= new Advogado();;
        ArrayList<Advogado> ac= new ArrayList<>();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM advogado WHERE nome like'%"+nome+"%'");
            rs=ps.executeQuery();
            while(rs.next()){
                a= new Advogado();
                a.setId(rs.getInt("id"));
                a.setOab(rs.getInt("oab"));
                a.setNome(rs.getString("nome"));
                a.setId_login(rs.getInt("id_login"));
                ac.add(a);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con, ps, rs);
        }
        return ac;
    }
    //Consultar
    public Advogado consultarIDLogin(int id_login) throws SQLException{
        Connection con=null;
        Advogado aux = new Advogado();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM advogado WHERE id_login=?");
            ps.setInt(1, id_login);
            rs=ps.executeQuery();
            if(rs.next()){
                aux.setId(rs.getInt("id"));
                aux.setOab(rs.getInt("oab"));
                aux.setNome(rs.getString("nome"));
                aux.setId_login(rs.getInt("id_login"));
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
    public Advogado consultarID(int id) throws SQLException{
        Connection con=null;
        Advogado aux = new Advogado();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM advogado WHERE id=?");
            ps.setInt(1, id);
            rs=ps.executeQuery();
            if(rs.next()){
                aux.setId(rs.getInt("id"));
                aux.setOab(rs.getInt("oab"));
                aux.setNome(rs.getString("nome"));
                aux.setId_login(rs.getInt("id_login"));
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
