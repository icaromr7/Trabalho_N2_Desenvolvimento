/**
 * Classe Usuario representa um usuario com informações específicas.
 * Possui métodos para acessar e modificar os atributos do usuario.
 */
package model;

/**
 *
 * @author icaro
 */
public class Usuario {
    private int id;
    private String login;
    private String senha;
    private String cargo;

    public Usuario() {
        this.cargo= "Advogado";
    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 
    
}
