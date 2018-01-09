package com.alcebiades.trilha.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Alcebiades
 */
public class Lancamento implements Serializable {

    private Long id;
    private String descricao;
    private Date data;
    private Double valor;
    private TipoLancamento tipoLancamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Timestamp getDataDb() {
        return new Timestamp(data.getTime());
    }

    public void setDataDb(Timestamp data) {
        this.data = new Date(data.getTime());
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public static String getDbName() {
        return Lancamento.class.getCanonicalName();
    }
}
