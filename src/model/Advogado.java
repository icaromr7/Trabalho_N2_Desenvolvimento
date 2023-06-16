/**
 * Classe Advogado representa um advogado com informações específicas.
 * Possui métodos para acessar e modificar os atributos do advogado.
 */
package model;

/**
 *
 * @author icaro
 */
public class Advogado {
    private int id;
    private int oab;
    private String nome;
    private int id_login;

    public Advogado() {
        this.id= 0;
    }
 
    public int getOab() {
        return oab;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOab(int oab) {
        this.oab = oab;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_login() {
        return id_login;
    }

    public void setId_login(int id_login) {
        this.id_login = id_login;
    }

    
    
}
