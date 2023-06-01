
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Advogado;
import model.Connect;
import model.Processo;


public class ProcessoDao extends Connect{
    //Incluir
    public boolean incluir(Processo p) throws SQLException{
        boolean retorno = false;
        int intRetorno=0;
        Connection con = null;
        try{
            con=this.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO processo (nome_cliente,cpf_cliente,id_tipo,data,id_advogado, situacao,classificacao, descricao, valor) VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setString(1, p.getNome_cliente());
            ps.setString(2, p.getCpf_cliente());
            ps.setInt(3, p.getId_tipo());
            ps.setDate(4, (Date) p.getData());
            ps.setInt(5, p.getId_adv());
            ps.setString(6, p.getSituacao());
            ps.setString(7, p.getClassificacao());
            ps.setString(8, p.getDescricao());
            ps.setDouble(9, p.getValor());
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
    public boolean excluir(Processo p) throws SQLException {
        boolean retorno = false;
        int intRetorno = 0;
        Connection con = null;
        try {
            con = this.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM processo WHERE id_processo = ?");
            ps.setInt(1, p.getId()); 

            intRetorno = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        if (intRetorno > 0) {
            retorno = true;
        }
        return retorno;
    }
    //Alterar
    public boolean alterar(Processo p) throws SQLException {
        boolean retorno = false;
        int intRetorno = 0;
        Connection con = null;
        try {
            con = this.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE processo SET nome_cliente = ?, cpf_cliente = ?, id_tipo = ?, data = ?, id_advogado = ?, situacao = ?, classificacao = ?, descricao = ?, valor = ? WHERE id_processo = ?");
            ps.setString(1, p.getNome_cliente());
            ps.setString(2, p.getCpf_cliente());
            ps.setInt(3, p.getId_tipo());
            ps.setDate(4, (Date) p.getData());
            ps.setInt(5, p.getId_adv());
            ps.setString(6, p.getSituacao());
            ps.setString(7, p.getClassificacao());
            ps.setString(8, p.getDescricao());
            ps.setDouble(9, p.getValor());
            ps.setInt(10, p.getId());

            intRetorno = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        if (intRetorno > 0) {
            retorno = true;
        }
        return retorno;
    }
    //Pesquisa por id
    public Processo pesquisarPorId(int id) throws SQLException {
        Processo processo = null;
        Connection con = null;
        try {
            con = this.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM processo WHERE id_processo = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                processo = new Processo();
                processo.setId(rs.getInt("id_processo"));
                processo.setNome_cliente(rs.getString("nome_cliente"));
                processo.setCpf_cliente(rs.getString("cpf_cliente"));
                processo.setId_tipo(rs.getInt("id_tipo"));
                processo.setData(rs.getDate("data"));
                processo.setId_adv(rs.getInt("id_advogado"));
                processo.setSituacao(rs.getString("situacao"));
                processo.setClassificacao(rs.getString("classificacao"));
                processo.setDescricao(rs.getString("descricao"));
                processo.setValor(rs.getDouble("valor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return processo;
    }
    //por nomeCliente
    public ArrayList<Processo> pesquisarPorNomeCliente(String nomeCliente) throws SQLException {
        ArrayList<Processo> processos = new ArrayList<>();
        Connection con = null;
        try {
            con = this.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM processo WHERE nome_cliente LIKE ?");
            ps.setString(1, "%" + nomeCliente + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Processo processo = new Processo();
                processo.setId(rs.getInt("id_processo"));
                processo.setNome_cliente(rs.getString("nome_cliente"));
                processo.setCpf_cliente(rs.getString("cpf_cliente"));
                processo.setId_tipo(rs.getInt("id_tipo"));
                processo.setData(rs.getDate("data"));
                processo.setId_adv(rs.getInt("id_advogado"));
                processo.setSituacao(rs.getString("situacao"));
                processo.setClassificacao(rs.getString("classificacao"));
                processo.setDescricao(rs.getString("descricao"));
                processo.setValor(rs.getDouble("valor"));

                processos.add(processo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return processos;
    }
    //Por cpf
    public ArrayList<Processo> pesquisarPorCpfCliente(String cpfCliente) throws SQLException {
        ArrayList<Processo> processos = new ArrayList<>();
        Connection con = null;
        try {
            con = this.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM processo WHERE cpf_cliente = ?");
            ps.setString(1, cpfCliente);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Processo processo = new Processo();
                processo.setId(rs.getInt("id_processo"));
                processo.setNome_cliente(rs.getString("nome_cliente"));
                processo.setCpf_cliente(rs.getString("cpf_cliente"));
                processo.setId_tipo(rs.getInt("id_tipo"));
                processo.setData(rs.getDate("data"));
                processo.setId_adv(rs.getInt("id_advogado"));
                processo.setSituacao(rs.getString("situacao"));
                processo.setClassificacao(rs.getString("classificacao"));
                processo.setDescricao(rs.getString("descricao"));
                processo.setValor(rs.getDouble("valor"));

                processos.add(processo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return processos;
    }
    
    //Por id advogado
    public ArrayList<Processo> pesquisarPorIdAdvogado(int id) throws SQLException {
        ArrayList<Processo> processos = new ArrayList<>();
        Connection con = null;
        try {
            con = this.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM processo WHERE id_adv = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Processo processo = new Processo();
                processo.setId(rs.getInt("id_processo"));
                processo.setNome_cliente(rs.getString("nome_cliente"));
                processo.setCpf_cliente(rs.getString("cpf_cliente"));
                processo.setId_tipo(rs.getInt("id_tipo"));
                processo.setData(rs.getDate("data"));
                processo.setId_adv(rs.getInt("id_advogado"));
                processo.setSituacao(rs.getString("situacao"));
                processo.setClassificacao(rs.getString("classificacao"));
                processo.setDescricao(rs.getString("descricao"));
                processo.setValor(rs.getDouble("valor"));

                processos.add(processo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return processos;
    }



    
}
