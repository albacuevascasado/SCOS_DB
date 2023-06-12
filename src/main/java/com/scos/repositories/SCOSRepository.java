package com.scos.repositories;

import com.scos.JpaEntityManagerFactory;
import com.scos.data_model.*;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;
import java.util.List;

public class SCOSRepository {

    private EntityManager entityManager;

    @Autowired
    private JpaEntityManagerFactory scosEmf;

    public void truncateTableSCOS(String tablename) {
        //creation of @Entities
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        boolean tableExistsInDB = false;

        tableExistsInDB = getSCOSEntities(entityManager, tablename);
        System.out.println("Table is in DB?: " + tableExistsInDB);

        if(tableExistsInDB) {
            try {
                //TRUNCATE
                transaction = entityManager.getTransaction();
                transaction.begin();
                //System.out.println("Transaction has begun");
                String sql = "TRUNCATE TABLE public.\"" + tablename + "\"";
                Query query = entityManager.createNativeQuery(sql);
                query.executeUpdate();
                //System.out.println("Output Query: " + outputQuery); //0 successful query
                transaction.commit();

            } catch (HibernateException hibernateException) {
                if(transaction != null) {
                    transaction.rollback();
                }
                hibernateException.printStackTrace();
            } finally {
                entityManager.close();
            }
        }
    }

    public Boolean getSCOSEntities(EntityManager entityManager, String tableName) {
        Boolean tableExistsInDB = false;

        for (EntityType<?> entity : entityManager.getMetamodel().getEntities()) {
            if(tableName.equals(entity.getName())) {
                System.out.println("TABLE IN DB");
                tableExistsInDB = true;
                return tableExistsInDB;
            }
        }
        return tableExistsInDB;
    }

    /** SAVE RECORDS IN DB */

    public void saveCVSRecords(List<CVS> cvsRecords) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            for(CVS cvsRecord: cvsRecords) {
                //System.out.println("Transaction CVS persist: " + cvsRecord.getCvsId());
                entityManager.persist(cvsRecord);
                /** synchronize your database + not commit so it is possible to do the roll back */
                entityManager.flush();
                /** clear entities from the persistence context (first level cache) */
                entityManager.clear();
            }

        } catch (HibernateException hibernateException) {
            if(transaction != null) {
                transaction.rollback();
            }
            hibernateException.printStackTrace();
        } finally {
            /** data stored permanently + no possible to rollback once the commit succeeds */
            transaction.commit();
            //System.out.println("Transaction has been closed");
            entityManager.close();
        }

    }

    public void saveCAFRecords(List<CAF> cafRecords) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            for(CAF cafRecord: cafRecords) {
                //System.out.println("Transaction CAF persist: " + cafRecord.getCafNumbr());
                entityManager.persist(cafRecord);
                entityManager.flush();
                entityManager.clear();
            }

        } catch (HibernateException hibernateException) {
            if(transaction != null) {
                transaction.rollback();
            }
            hibernateException.printStackTrace();
        } finally {
            /** data stored permanently + no possible to rollback once the commit succeeds */
            transaction.commit();
            //System.out.println("Transaction has been closed");
            entityManager.close();
        }
    }

    public void saveCAPRecords(List<CAP> capRecords) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            for(CAP capRecord: capRecords) {
                entityManager.persist(capRecord);
                entityManager.flush();
                entityManager.clear();
            }

        } catch (HibernateException hibernateException) {
            if(transaction != null) {
                transaction.rollback();
            }
            hibernateException.printStackTrace();
        } finally {
            transaction.commit();
            //System.out.println("Transaction has been closed");
            entityManager.close();
        }
    }

    public void saveCCARecords(List<CCA> ccaRecords) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            for(CCA ccaRecord: ccaRecords) {
                entityManager.persist(ccaRecord);
                entityManager.flush();
                entityManager.clear();
            }

        } catch (HibernateException hibernateException) {
            if(transaction != null) {
                transaction.rollback();
            }
            hibernateException.printStackTrace();
        } finally {
            transaction.commit();
            //System.out.println("Transaction has been closed");
            entityManager.close();
        }
    }

    public void saveTESTRecords(List<TEST> testRecords) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            for (TEST testRecord: testRecords) {
                //System.out.println("Transaction TEST persist: " + testRecord.getId());
                entityManager.persist(testRecord);
                entityManager.flush();
                entityManager.clear();
            }

        } catch (HibernateException hibernateException) {
            if(transaction != null) {
                transaction.rollback();
            }
            hibernateException.printStackTrace();
        } finally {
            transaction.commit();
            //System.out.println("Transaction has been closed");
            entityManager.close();
        }
    }

}
