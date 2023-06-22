package com.scos.repositories;

import com.scos.JpaEntityManagerFactory;
import com.scos.data_model.mps_db.ODBData;
import com.scos.data_model.mps_db.ODBFiles;
import com.scos.data_model.mps_db.ODBFilesKey;
import com.scos.data_model.mps_db.common.InputFileStatus;
import com.scos.data_model.scos_db.*;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SCOSRepository {

    private EntityManager entityManager;

    @Autowired
    @Qualifier("SCOSentityManager")
    private JpaEntityManagerFactory scosEmf;

    public boolean getSCOSEntities(String tableName) {
        entityManager = scosEmf.getEntityManager();
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

    public  void saveSCOSDBRecord(SCOSDB scosdbRecord) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //only one record in this case DIFFERENT VALUES OF ID (not equal file's name)
            entityManager.persist(scosdbRecord);

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

    public void saveODBFILESRecord(ODBFiles odbFiles) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //only one record in this case DIFFERENT VALUES OF ID (not equal file's name)
            entityManager.persist(odbFiles);

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

    public void updateODBFILESStatus(ODBFiles odbFiles) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            ODBFiles odbFilesUpdate = entityManager.find(ODBFiles.class, new ODBFilesKey(odbFiles.getOdbFileId(),odbFiles.getOdbFileName()));
            odbFilesUpdate.setOdbFileStatus(InputFileStatus.ARCHIVED);
            odbFilesUpdate.setOdbProcessDate(LocalDateTime.now());
            entityManager.merge(odbFilesUpdate);

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

    public void saveCVSRecords(List<CVS> cvsRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CVS\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CVS RECORD
            int i = 0;
            for(CVS cvsRecord: cvsRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(cvsRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesCCF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCCF != null) {
                scostablesCCF.setUpdateDate(LocalDateTime.now());
                scostablesCCF.setScosDB(scostables.getScosDB());
                scostablesCCF.setDimension(BigInteger.valueOf(cvsRecords.size()));
                scostablesCCF.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesCCF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCCF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCCF != null) {
                odbDataCCF.setOdbTableSize(BigInteger.valueOf(cvsRecords.size()));
                odbDataCCF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCCF);
            } else {
                entityManager.persist(odbData);
            }

            /** data stored permanently + no possible to rollback once the commit succeeds */
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

    public void saveCAFRecords(List<CAF> cafRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CAF\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CAF RECORD
            int i = 0;
            for(CAF cafRecord: cafRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(cafRecord);
                i++;
            }
            //SCOS TABLE
            SCOSTABLES scostablesCCF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCCF != null) {
                scostablesCCF.setUpdateDate(LocalDateTime.now());
                scostablesCCF.setScosDB(scostablesCCF.getScosDB());
                scostablesCCF.setDimension(BigInteger.valueOf(cafRecords.size()));
                entityManager.merge(scostablesCCF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCCF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCCF != null) {
                odbDataCCF.setOdbTableSize(BigInteger.valueOf(cafRecords.size()));
                odbDataCCF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCCF);
            } else {
                entityManager.persist(odbData);
            }

            /** data stored permanently + no possible to rollback once the commit succeeds */
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

    public void saveCAPRecords(List<CAP> capRecords,SCOSTABLES scostables,ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CAP\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CAP RECORD
            int i = 0;
            for(CAP capRecord: capRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(capRecord);
                i++;
            }
            //SCOS TABLE
            SCOSTABLES scostablesCCF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCCF != null) {
                scostablesCCF.setUpdateDate(LocalDateTime.now());
                scostablesCCF.setScosDB(scostables.getScosDB());
                scostablesCCF.setDimension(BigInteger.valueOf(capRecords.size()));
                entityManager.merge(scostablesCCF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCCF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCCF != null) {
                odbDataCCF.setOdbTableSize(BigInteger.valueOf(capRecords.size()));
                odbDataCCF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCCF);
            } else {
                entityManager.persist(odbData);
            }

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

    public void saveCCARecords(List<CCA> ccaRecords,SCOSTABLES scostables,ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CCA\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CCA RECORD
            int i = 0;
            for(CCA ccaRecord: ccaRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(ccaRecord);
                i++;
            }
            //SCOS TABLE
            SCOSTABLES scostablesCCF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCCF != null) {
                scostablesCCF.setUpdateDate(LocalDateTime.now());
                scostablesCCF.setScosDB(scostables.getScosDB());
                scostablesCCF.setDimension(BigInteger.valueOf(ccaRecords.size()));
                entityManager.merge(scostablesCCF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCCF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCCF != null) {
                odbDataCCF.setOdbTableSize(BigInteger.valueOf(ccaRecords.size()));
                odbDataCCF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCCF);
            } else {
                entityManager.persist(odbData);
            }

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

    public void saveCCFRecords(List<CCF> ccfRecords,SCOSTABLES scostables,ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CCF\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CCF RECORD
            int i = 0;
            for(CCF ccfRecord: ccfRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(ccfRecord);
                i++;
            }
            //SCOS TABLE
            SCOSTABLES scostablesCCF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCCF != null) {
                scostablesCCF.setUpdateDate(LocalDateTime.now());
                scostablesCCF.setScosDB(scostables.getScosDB());
                scostablesCCF.setDimension(BigInteger.valueOf(ccfRecords.size()));
                entityManager.merge(scostablesCCF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCCF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCCF != null) {
                odbDataCCF.setOdbTableSize(BigInteger.valueOf(ccfRecords.size()));
                odbDataCCF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCCF);
            } else {
                entityManager.persist(odbData);
            }

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

    public void saveCCSRecords(List<CCS> ccsRecords,SCOSTABLES scostables,ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CCS\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CCS RECORD
            int i = 0;
            for(CCS ccsRecord: ccsRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(ccsRecord);
                i++;
            }
            //SCOS TABLE
            SCOSTABLES scostablesCCF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCCF != null) {
                scostablesCCF.setUpdateDate(LocalDateTime.now());
                scostablesCCF.setScosDB(scostables.getScosDB());
                scostablesCCF.setDimension(BigInteger.valueOf(ccsRecords.size()));
                entityManager.merge(scostablesCCF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCCF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCCF != null) {
                odbDataCCF.setOdbTableSize(BigInteger.valueOf(ccsRecords.size()));
                odbDataCCF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCCF);
            } else {
                entityManager.persist(odbData);
            }

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
