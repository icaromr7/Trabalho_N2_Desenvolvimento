/**
 * Classe TipoDeProcesso representa um tipo de processo com informações específicas.
 * Possui métodos para acessar e modificar os atributos do tipo de processo.
 */
package model;

public class TipoDeProcesso {
    private int id;
    private String tipoDoProcesso;

    public TipoDeProcesso() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoDoProcesso() {
        return tipoDoProcesso;
    }

    public void setTipoDoProcesso(String tipoDoProcesso) {
        this.tipoDoProcesso = tipoDoProcesso;
    }
    @Override
    public String toString() {
        return getTipoDoProcesso();
    }
}
