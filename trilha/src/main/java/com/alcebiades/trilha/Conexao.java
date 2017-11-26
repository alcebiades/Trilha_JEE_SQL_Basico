package com.alcebiades.trilha;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.sql.DataSource;

/**
 *
 * @author alcebiades
 */
public class Conexao {

    @Resource(mappedName = "jboss/datasources/TrilhaDS")
    private DataSource ds;

    @Resource
    private EJBContext context;

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void rollBack() {
        context.setRollbackOnly();
    }

    public void close(Connection c, ResultSet r, Statement s) throws Exception {
        try {
            if (c != null) {
                c.close();
            }
            if (r != null) {
                r.close();
            }
            if (s != null) {
                s.close();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
}
