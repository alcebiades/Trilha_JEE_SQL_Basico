package com.alcebiades.trilha.service;

import com.alcebiades.trilha.Conexao;
import com.alcebiades.trilha.model.Lancamento;
import com.alcebiades.trilha.model.TipoLancamento;
import com.alcebiades.trilha.model.request.LancamentoFiltro;
import com.alcebiades.trilha.model.response.LancamentoResponse;
import com.alcebiades.trilha.model.response.TipoLancamentoResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author alcebiades
 */
@Stateless
public class LancamentoService {

    private Conexao conexao;

    public LancamentoService() {
    }

    @Inject
    public LancamentoService(Conexao conexao) {
        this.conexao = conexao;
    }

    public TipoLancamentoResponse consultarTipoLancamento() throws Exception {

        final TipoLancamentoResponse ret = new TipoLancamentoResponse();

        try (Connection conn = conexao.getConnection()) {

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id, descricao FROM TipoLancamento ORDER BY id");

            while (rs.next()) {
                TipoLancamento obj = new TipoLancamento();
                obj.setId(rs.getLong("id"));
                obj.setDescricao(rs.getString("descricao"));

                ret.getListTipoLancamento().add(obj);
            }

            conexao.close(conn, rs, stm);

        } catch (Exception e) {
            throw e;
        }

        return ret;
    }

    public TipoLancamento salvarTipoLancamento(TipoLancamento tipoLancamento) throws Exception {
        try {
            if (tipoLancamento.getId() != null && tipoLancamento.getId() > 0) {
                return atualizarTipoLancamento(tipoLancamento);
            } else {
                return inserirTipoLancamento(tipoLancamento);
            }
        } catch (Exception e) {
            conexao.rollBack();
            throw e;
        }
    }

    private TipoLancamento inserirTipoLancamento(TipoLancamento tipoLancamento) throws Exception {
        try (Connection conn = conexao.getConnection()) {

            Statement stm = conn.createStatement();
            stm.execute("INSERT INTO TipoLancamento (descricao) values ('" + tipoLancamento.getDescricao() + "') ");

            ResultSet rs = stm.executeQuery("SELECT scope_identity() AS id");

            if (rs.next()) {
                tipoLancamento.setId(rs.getLong("id"));
            }

            conexao.close(conn, rs, stm);

            return tipoLancamento;

        } catch (Exception e) {
            throw e;
        }
    }

    private TipoLancamento atualizarTipoLancamento(TipoLancamento tipoLancamento) throws Exception {
        try (Connection conn = conexao.getConnection()) {

            Statement stm = conn.createStatement();
            stm.execute("UPDATE TipoLancamento SET descricao = '" + tipoLancamento.getDescricao() + "' WHERE id = " + tipoLancamento.getId());
            conexao.close(conn, null, stm);

            return tipoLancamento;

        } catch (Exception e) {
            throw e;
        }
    }

    public LancamentoResponse consultarLancamento(final LancamentoFiltro filtro) throws Exception {

        final LancamentoResponse ret = new LancamentoResponse();

        try (Connection conn = conexao.getConnection()) {

            StringBuilder sql = new StringBuilder("SELECT l.id, l.descricao, l.data, l.valor, ");
            sql.append(" tl.id AS idTipoLancamento, tl.descricao descTipoLancamento");
            sql.append(" FROM lancamento l ");
            sql.append(" JOIN TipoLancamento tl ON tl.id = l.idTipoLancamento ");
            sql.append(" WHERE 1 = 1");

            if (filtro != null) {
                if (filtro.getId() != null && filtro.getId() > 0) {
                    sql.append(" AND l.id = ?");
                }

                if (filtro.getDataInicial() != null) {
                    sql.append(" AND l.data >= ?");
                }

                if (filtro.getDataFinal() != null) {
                    sql.append(" AND l.data <= ?");
                }

                if (filtro.getValorInicial() != null) {
                    sql.append(" AND l.valor >= ?");
                }

                if (filtro.getValorFinal() != null) {
                    sql.append(" AND l.valor <= ?");
                }

                if (filtro.getTipoLancamento() != null && filtro.getTipoLancamento().getId() != null && filtro.getTipoLancamento().getId() > 0) {
                    sql.append(" AND tl.id = ?");
                }
            }

            final PreparedStatement ps = conn.prepareStatement(sql.toString());

            if (filtro != null) {
                int i = 0;

                if (filtro.getId() != null && filtro.getId() > 0) {
                    ps.setLong(++i, filtro.getId());
                }

                if (filtro.getDataInicial() != null) {
                    ps.setTimestamp(++i, new Timestamp(filtro.getDataInicial().getTime()));
                }

                if (filtro.getDataFinal() != null) {
                    ps.setTimestamp(++i, new Timestamp(filtro.getDataFinal().getTime()));
                }

                if (filtro.getValorInicial() != null) {
                    ps.setDouble(++i, filtro.getValorInicial());
                }

                if (filtro.getValorFinal() != null) {
                    ps.setDouble(++i, filtro.getValorFinal());
                }

                if (filtro.getTipoLancamento() != null && filtro.getTipoLancamento().getId() != null && filtro.getTipoLancamento().getId() > 0) {
                    ps.setLong(++i, filtro.getTipoLancamento().getId());
                }
            }

            final ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Lancamento l = new Lancamento();
                l.setId(rs.getLong("id"));
                l.setDescricao(rs.getString("descricao"));
                l.setDataDb(rs.getTimestamp("data"));
                l.setValor(rs.getDouble("valor"));
                l.setTipoLancamento(new TipoLancamento(rs.getLong("idTipoLancamento"), rs.getString("descTipoLancamento")));
                ret.getListLancamento().add(l);
            }

            conexao.close(conn, rs, ps);

        } catch (Exception e) {
            throw e;
        }

        return ret;
    }

    public Lancamento salvarLancamento(final Lancamento lancamento) throws Exception {
        try {
            if (lancamento.getId() != null && lancamento.getId() > 0) {
                return atualizarLancamento(lancamento);
            } else {
                return inserirLancamento(lancamento);
            }
        } catch (SQLException e) {
            conexao.rollBack();
            throw e;
        }
    }

    private Lancamento inserirLancamento(final Lancamento lancamento) throws Exception {

        try (Connection conn = conexao.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("INSERT INTO lancamento (descricao, data, valor, idTipoLancamento) VALUES (?,?,?,?)");
            ps.setString(1, lancamento.getDescricao());
            ps.setTimestamp(2, lancamento.getDataDb());
            ps.setDouble(3, lancamento.getValor());
            ps.setLong(4, lancamento.getTipoLancamento().getId());

            ps.execute();

            ResultSet rs = conn.createStatement().executeQuery("SELECT scope_identity() AS id");

            if (rs.next()) {
                lancamento.setId(rs.getLong("id"));
            }

            conexao.close(conn, rs, ps);

        } catch (SQLException e) {
            throw e;
        }

        return lancamento;
    }

    private Lancamento atualizarLancamento(final Lancamento lancamento) throws Exception {

        try (Connection conn = conexao.getConnection()) {

            PreparedStatement ps = conexao.getConnection().prepareStatement("UPDATE lancamento SET descricao = ?, data = ?, valor = ?, idTipoLancamento = ? WHERE id = ?");
            ps.setString(1, lancamento.getDescricao());
            ps.setTimestamp(2, lancamento.getDataDb());
            ps.setDouble(3, lancamento.getValor());
            ps.setLong(4, lancamento.getTipoLancamento().getId());
            ps.setLong(5, lancamento.getId());

            ps.executeUpdate();

            conexao.close(conn, null, ps);

        } catch (SQLException e) {
            throw e;
        }

        return lancamento;
    }

    public boolean excluirLancamento(long idLancamento) throws Exception {
        try (Connection conn = conexao.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("DELETE Lancamento WHERE id = ? ");
            ps.setLong(1, idLancamento);
            boolean executado = ps.execute();
            conexao.close(conn, null, ps);

            return executado;

        } catch (Exception e) {
            throw e;
        }
    }
}
