package com.scos;

import com.scos.data_model.mps_db.ODBData;
import com.scos.data_model.mps_db.ODBFiles;
import com.scos.data_model.scos_db.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.postgresql.ds.PGSimpleDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Setter
public class JpaEntityManagerFactory {

    //creates an EntityManagerFactory

    private String DB_URL;
    private String DB_USER_NAME;
    private String DB_PASSWORD;

    public static int BATCH_SIZE = 50; //== global variable

    private String persistenceUnitName;

    private Class[] entityClasses = new Class[] {
            SCOSDB.class,
            SCOSTABLES.class,
            CAF.class,
            CAP.class,
            CCA.class,
            CCF.class,
            CCS.class,
            CDF.class,
            CVS.class,
            ODBFiles.class,
            ODBData.class
    };


    //(application-context.xml) es un argumento del constructor de esta clase <constructor-arg>
    public  JpaEntityManagerFactory(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
        System.out.println("Persistence Unit Name: " + persistenceUnitName);

    }

    protected HibernatePersistenceUnitInfo getPersistenceUnitInfo(String name) {
        return new HibernatePersistenceUnitInfo(name, getEntityClassNames(), getProperties());
    }

    protected EntityManagerFactory getEntityManagerFactory() {
        PersistenceUnitInfo persistenceUnitInfo =
                getPersistenceUnitInfo(
                        persistenceUnitName);
        Map<String,Object> configuration = new HashMap<>();
        return new EntityManagerFactoryBuilderImpl(
                new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration)
                .build();
    }

    public  EntityManager getEntityManager() {
        return  getEntityManagerFactory().createEntityManager();
    }

    protected List<String> getEntityClassNames() {
        return Arrays.asList(getEntities())
                .stream()
                .map(Class::getName)
                .collect(Collectors.toList());
    }

    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
//        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.jdbc.batch_size", BATCH_SIZE);
        /** batch all insert statements of the same entity type together */
        properties.put("hibernate.order_inserts", true);
        /** batch updates to insert together */
        properties.put("hibernate.order_updates", "true");
        properties.put("hibernate.batch_versioned_data", "true");

        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.naming.implicit-strategy", "org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl");
        properties.put("hibernate.naming.physical-strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        properties.put("hibernate.connection.datasource", getPGSimpleDataSource());
        return properties;
    }

    protected Class[] getEntities() {
        return entityClasses;
    }

    protected DataSource getPGSimpleDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(DB_URL);
        dataSource.setUser(DB_USER_NAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }

}
