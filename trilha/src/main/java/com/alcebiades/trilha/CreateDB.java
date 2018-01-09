package com.alcebiades.trilha;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author alcebiades
 */
@Singleton
@Startup
public class CreateDB {

    private Conexao conexao;

    public CreateDB() {
    }

    @Inject
    public CreateDB(Conexao conexao) {
        this.conexao = conexao;
    }

    @PostConstruct
    public void createDataBase() {

        try {
            System.out.println("Criou o banco de dados ------------------------");

            Connection conn = conexao.getConnection();
            Statement stm = conn.createStatement();

            stm.execute("create table if not exists TipoLancamento (id IDENTITY primary key, descricao varchar(255) not null)");
            stm.execute("create table if not exists Lancamento (id IDENTITY primary key, descricao varchar(255) not null, data timestamp not null, valor numeric not null,"
                    + "idTipoLancamento INT not null, foreign key (idTipoLancamento) references TipoLancamento (id))");

            stm.execute("insert into tipoLancamento (descricao) values('Entrada')");
            stm.execute("insert into tipoLancamento (descricao) values('Saída')");

        } catch (SQLException ex) {
            Logger.getLogger(CreateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
