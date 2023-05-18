/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
    private String advlogin;

    public Advogado() {
    }

    public Advogado(int oab, String nome, String advlogin) {
        this.oab = oab;
        this.nome = nome;
        this.advlogin = advlogin;
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

    public String getAdvlogin() {
        return advlogin;
    }

    public void setAdvlogin(String advlogin) {
        this.advlogin = advlogin;
    }
    
}
