//package com.alcebiades.trilha;
//
//import java.io.File;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.wildfly.swarm.Swarm;
//import org.wildfly.swarm.datasources.DatasourceArchive;
//import org.wildfly.swarm.undertow.WARArchive;
//
///**
// *
// * @author Alcebiades
// */
//public class EntryPoint {
//
//    public static void main(String args[]) throws Exception {
//
////        Swarm container = new Swarm(args);
////        container.start();
//
////        container.deploy(Swarm.artifact("org.hsqldb.jdbc.JDBCDriver", "hsqldb"));
////
////        // Cria o dataSource
////        DatasourceArchive dsArchive = ShrinkWrap.create(DatasourceArchive.class);
////
////        dsArchive.dataSource("ds", (ds) -> {
////
////            ds.connectionUrl("jdbc:hsqldb:file:trilha/trilhadb");
////            ds.driverName("hsqldb");
////            ds.userName("SA");
////            ds.password("");
////        });
////
////        // Faz deploy no dataSource
////        container.deploy(dsArchive);
//
////        WARArchive deployment = ShrinkWrap.createFromZipFile(WARArchive.class, new File("target/trilhaJeeTeste.war"));
////
////        ClassLoader classLoader = EntryPoint.class.getClassLoader();
////        // Configura o CDI
////        deployment.addAsWebInfResource(classLoader.getResource("beans.xml"), "beans.xml");
////        deployment.addPackages(true, Package.getPackage("com.alcebiades.trilha"));
////        deployment.addAllDependencies();
////
////        container.deploy(deployment);
//    }
//}
//
//
////Create cached table TipoLancamento (
////id identity primary key,
////descricao varchar(255) not null
////);
////
////Create cached table lancamento (
////id identity primary key,
////descricao varchar(255) not null,
////data timestamp not null,
////valor numeric not null,
////idTipoLancamento TINYINT not null,
////foreign key (idTipoLancamento) references TipoLancamento (id)
////);