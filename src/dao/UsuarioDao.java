/*
Classe de conexão com a tabela usuario no banco de dados.
realiza inclusão, alteração, exclusão e pesquisa nessa tabela.
*/

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Connect;
import model.Usuario;

/**
 *
 * @author icaro
 */
public class UsuarioDao extends Connect{
    //Incluir
    public boolean incluir(Usuario u) throws SQLException{
        boolean retorno = false;
        int intRetorno=0;
        Connection con = null;
        try{
            con=this.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO usuario (login, senha, cargo) VALUES (?, ? , ?)");
            ps.setString(1,u.getLogin());
            ps.setString(2,u.getSenha());
            ps.setString(3,u.getCargo());
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
    public boolean alterar(Usuario u) throws SQLException{
        boolean retorno = false;
        int intRetorno=0;
        Connection con = null;
        try{
            con=this.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE usuario SET login=?,senha=? WHERE id=?");
            ps.setString(1,u.getLogin());
            ps.setString(2,u.getSenha());
            ps.setInt(3,u.getId());
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
    public boolean excluir(Usuario u) throws SQLException{
        boolean retorno = false;
        int intRetorno=0;
        Connection con = null;
        try{
            con=this.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM usuario WHERE id=?");
            ps.setInt(1,u.getId());
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
    public Usuario consultar(Usuario u) throws SQLException{
        Connection con=null;
        Usuario aux = new Usuario();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM usuario WHERE id=?");
            ps.setInt(1, u.getId());
            rs=ps.executeQuery();
            if(rs.next()){
                aux.setId(rs.getInt("id"));
                aux.setLogin(rs.getString("login"));
                aux.setSenha(rs.getString("senha"));
                aux.setCargo(rs.getString("cargo"));
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
    public Usuario consultar(String login) throws SQLException{
        Connection con=null;
        Usuario aux = new Usuario();
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM usuario WHERE login=?");
            ps.setString(1, login);
            rs=ps.executeQuery();
            if(rs.next()){
                aux.setId(rs.getInt("id"));
                aux.setLogin(rs.getString("login"));
                aux.setSenha(rs.getString("senha"));
                aux.setCargo(rs.getString("cargo"));
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
    public ArrayList<Usuario> consultarAdms() throws SQLException{
        Connection con=null;
        Usuario aux = new Usuario();
        PreparedStatement ps= null;
        ResultSet rs=null;
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM usuario WHERE cargo='Administrador'");
            rs=ps.executeQuery();
            while(rs.next()){
                aux = new Usuario();
                aux.setId(rs.getInt("id"));
                aux.setLogin(rs.getString("login"));
                aux.setSenha(rs.getString("senha"));
                aux.setCargo(rs.getString("cargo"));
                usuarios.add(aux);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con, ps, rs);           
        }
        return usuarios;
    }
    //Consultar
    public ArrayList<Usuario> consultarAdm(String login) throws SQLException{
        Connection con=null;
        Usuario aux = new Usuario();
        PreparedStatement ps= null;
        ResultSet rs=null;
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try{
            con=this.getConnection();
            ps = con.prepareStatement("SELECT * FROM usuario WHERE login LIKE'%?%' AND cargo='Administrador'");
            ps.setString(1, login);
            rs=ps.executeQuery();
            while(rs.next()){
                aux = new Usuario();
                aux.setId(rs.getInt("id"));
                aux.setLogin(rs.getString("login"));
                aux.setSenha(rs.getString("senha"));
                aux.setCargo(rs.getString("cargo"));
                usuarios.add(aux);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con, ps, rs);           
        }
        return usuarios;
    }
    public int obterQuantidadeUsuarios() throws SQLException {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int quantidade = 0;
    
    try {
        con = this.getConnection();
        ps = con.prepareStatement("SELECT COUNT(*) AS quantidade FROM usuario WHERE cargo='Administrador'");
        rs = ps.executeQuery();
        
        if (rs.next()) {
            quantidade = rs.getInt("quantidade");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection(con, ps, rs);
    }
    
    return quantidade;
}
    
}
