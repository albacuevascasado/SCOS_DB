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
            SCOSTABLES scostablesCAF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCAF != null) {
                scostablesCAF.setUpdateDate(LocalDateTime.now());
                scostablesCAF.setScosDB(scostablesCAF.getScosDB());
                scostablesCAF.setDimension(BigInteger.valueOf(cafRecords.size()));
                entityManager.merge(scostablesCAF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCAF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCAF != null) {
                odbDataCAF.setOdbTableSize(BigInteger.valueOf(cafRecords.size()));
                odbDataCAF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCAF);
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
            SCOSTABLES scostablesCAP = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCAP != null) {
                scostablesCAP.setUpdateDate(LocalDateTime.now());
                scostablesCAP.setScosDB(scostables.getScosDB());
                scostablesCAP.setDimension(BigInteger.valueOf(capRecords.size()));
                entityManager.merge(scostablesCAP);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCAP = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCAP != null) {
                odbDataCAP.setOdbTableSize(BigInteger.valueOf(capRecords.size()));
                odbDataCAP.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCAP);
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
            SCOSTABLES scostablesCCA = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCCA != null) {
                scostablesCCA.setUpdateDate(LocalDateTime.now());
                scostablesCCA.setScosDB(scostables.getScosDB());
                scostablesCCA.setDimension(BigInteger.valueOf(ccaRecords.size()));
                entityManager.merge(scostablesCCA);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCCA = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCCA != null) {
                odbDataCCA.setOdbTableSize(BigInteger.valueOf(ccaRecords.size()));
                odbDataCCA.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCCA);
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
            SCOSTABLES scostablesCCS = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCCS != null) {
                scostablesCCS.setUpdateDate(LocalDateTime.now());
                scostablesCCS.setScosDB(scostables.getScosDB());
                scostablesCCS.setDimension(BigInteger.valueOf(ccsRecords.size()));
                entityManager.merge(scostablesCCS);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCCS = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCCS != null) {
                odbDataCCS.setOdbTableSize(BigInteger.valueOf(ccsRecords.size()));
                odbDataCCS.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCCS);
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

    public void saveCDFRecords(List<CDF> cdfRecords,SCOSTABLES scostables,ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CDF\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CDF RECORD
            int i = 0;
            for(CDF cdfRecord: cdfRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(cdfRecord);
                i++;
            }
            //SCOS TABLE
            SCOSTABLES scostablesCDF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCDF != null) {
                scostablesCDF.setUpdateDate(LocalDateTime.now());
                scostablesCDF.setScosDB(scostables.getScosDB());
                scostablesCDF.setDimension(BigInteger.valueOf(cdfRecords.size()));
                entityManager.merge(scostablesCDF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCDF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCDF != null) {
                odbDataCDF.setOdbTableSize(BigInteger.valueOf(cdfRecords.size()));
                odbDataCDF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCDF);
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

    public void saveCPCRecords(List<CPC> cpcRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CPC\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CPC RECORD
            int i = 0;
            for(CPC cpcRecord: cpcRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(cpcRecords);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesCPC = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCPC != null) {
                scostablesCPC.setUpdateDate(LocalDateTime.now());
                scostablesCPC.setScosDB(scostables.getScosDB());
                scostablesCPC.setDimension(BigInteger.valueOf(cpcRecords.size()));
                scostablesCPC.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesCPC);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCPC = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCPC != null) {
                odbDataCPC.setOdbTableSize(BigInteger.valueOf(cpcRecords.size()));
                odbDataCPC.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCPC);
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

    public void saveCURRecords(List<CUR> curRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CUR\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CUR RECORD
            int i = 0;
            for(CUR curRecord: curRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(curRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesCUR = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCUR != null) {
                scostablesCUR.setUpdateDate(LocalDateTime.now());
                scostablesCUR.setScosDB(scostables.getScosDB());
                scostablesCUR.setDimension(BigInteger.valueOf(curRecords.size()));
                scostablesCUR.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesCUR);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCUR = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCUR != null) {
                odbDataCUR.setOdbTableSize(BigInteger.valueOf(curRecords.size()));
                odbDataCUR.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCUR);
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

    public void saveCVERecords(List<CVE> cveRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CVE\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CVE RECORD
            int i = 0;
            for(CVE cveRecord: cveRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(cveRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesCVE = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCVE != null) {
                scostablesCVE.setUpdateDate(LocalDateTime.now());
                scostablesCVE.setScosDB(scostables.getScosDB());
                scostablesCVE.setDimension(BigInteger.valueOf(cveRecords.size()));
                scostablesCVE.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesCVE);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCVE = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCVE != null) {
                odbDataCVE.setOdbTableSize(BigInteger.valueOf(cveRecords.size()));
                odbDataCVE.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCVE);
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

    public void saveCVPRecords(List<CVP> cvpRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"CVP\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //CVP RECORD
            int i = 0;
            for(CVP cvpRecord: cvpRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(cvpRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesCVP = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCVP != null) {
                scostablesCVP.setUpdateDate(LocalDateTime.now());
                scostablesCVP.setScosDB(scostables.getScosDB());
                scostablesCVP.setDimension(BigInteger.valueOf(cvpRecords.size()));
                scostablesCVP.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesCVP);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCVP = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCVP != null) {
                odbDataCVP.setOdbTableSize(BigInteger.valueOf(cvpRecords.size()));
                odbDataCVP.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCVP);
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
            SCOSTABLES scostablesCVS = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesCVS != null) {
                scostablesCVS.setUpdateDate(LocalDateTime.now());
                scostablesCVS.setScosDB(scostables.getScosDB());
                scostablesCVS.setDimension(BigInteger.valueOf(cvsRecords.size()));
                scostablesCVS.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesCVS);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataCVS = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataCVS != null) {
                odbDataCVS.setOdbTableSize(BigInteger.valueOf(cvsRecords.size()));
                odbDataCVS.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataCVS);
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

    public void saveDPCRecords(List<DPC> dpcRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"DPC\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //DPC RECORD
            int i = 0;
            for(DPC dpcRecord: dpcRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(dpcRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesDPC = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesDPC != null) {
                scostablesDPC.setUpdateDate(LocalDateTime.now());
                scostablesDPC.setScosDB(scostables.getScosDB());
                scostablesDPC.setDimension(BigInteger.valueOf(dpcRecords.size()));
                scostablesDPC.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesDPC);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataDPC = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataDPC != null) {
                odbDataDPC.setOdbTableSize(BigInteger.valueOf(dpcRecords.size()));
                odbDataDPC.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataDPC);
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

    public void saveGRPRecords(List<GRP> grpRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"GRP\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //GRP RECORD
            int i = 0;
            for(GRP grpRecord: grpRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(grpRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesGRP = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesGRP != null) {
                scostablesGRP.setUpdateDate(LocalDateTime.now());
                scostablesGRP.setScosDB(scostables.getScosDB());
                scostablesGRP.setDimension(BigInteger.valueOf(grpRecords.size()));
                scostablesGRP.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesGRP);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataGRP = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataGRP != null) {
                odbDataGRP.setOdbTableSize(BigInteger.valueOf(grpRecords.size()));
                odbDataGRP.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataGRP);
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

    public void saveGRPARecords(List<GRPA> grpaRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"GRPA\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //GRPA RECORD
            int i = 0;
            for(GRPA grpaRecord: grpaRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(grpaRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesGRPA = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesGRPA != null) {
                scostablesGRPA.setUpdateDate(LocalDateTime.now());
                scostablesGRPA.setScosDB(scostables.getScosDB());
                scostablesGRPA.setDimension(BigInteger.valueOf(grpaRecords.size()));
                scostablesGRPA.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesGRPA);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataGRPA = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataGRPA != null) {
                odbDataGRPA.setOdbTableSize(BigInteger.valueOf(grpaRecords.size()));
                odbDataGRPA.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataGRPA);
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

    public void saveGRPKRecords(List<GRPK> grpkRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"GRPK\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //GRPK RECORD
            int i = 0;
            for(GRPK grpkRecord: grpkRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(grpkRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesGRPK = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesGRPK != null) {
                scostablesGRPK.setUpdateDate(LocalDateTime.now());
                scostablesGRPK.setScosDB(scostables.getScosDB());
                scostablesGRPK.setDimension(BigInteger.valueOf(grpkRecords.size()));
                scostablesGRPK.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesGRPK);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataGRPK = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataGRPK != null) {
                odbDataGRPK.setOdbTableSize(BigInteger.valueOf(grpkRecords.size()));
                odbDataGRPK.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataGRPK);
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

    public void saveLGFRecords(List<LGF> lgfRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"LGF\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //LGF RECORD
            int i = 0;
            for(LGF lgfRecord: lgfRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(lgfRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesLGF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesLGF != null) {
                scostablesLGF.setUpdateDate(LocalDateTime.now());
                scostablesLGF.setScosDB(scostables.getScosDB());
                scostablesLGF.setDimension(BigInteger.valueOf(lgfRecords.size()));
                scostablesLGF.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesLGF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataLGF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataLGF != null) {
                odbDataLGF.setOdbTableSize(BigInteger.valueOf(lgfRecords.size()));
                odbDataLGF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataLGF);
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

    public void saveMCFRecords(List<MCF> mcfRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"MCF\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //MCF RECORD
            int i = 0;
            for(MCF mcfRecord: mcfRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(mcfRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesMCF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesMCF != null) {
                scostablesMCF.setUpdateDate(LocalDateTime.now());
                scostablesMCF.setScosDB(scostables.getScosDB());
                scostablesMCF.setDimension(BigInteger.valueOf(mcfRecords.size()));
                scostablesMCF.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesMCF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataMCF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataMCF != null) {
                odbDataMCF.setOdbTableSize(BigInteger.valueOf(mcfRecords.size()));
                odbDataMCF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataMCF);
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

    public void saveOCFRecords(List<OCF> ocfRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"OCF\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //OCF RECORD
            int i = 0;
            for(OCF ocfRecord: ocfRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(ocfRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesOCF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesOCF != null) {
                scostablesOCF.setUpdateDate(LocalDateTime.now());
                scostablesOCF.setScosDB(scostables.getScosDB());
                scostablesOCF.setDimension(BigInteger.valueOf(ocfRecords.size()));
                scostablesOCF.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesOCF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataOCF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataOCF != null) {
                odbDataOCF.setOdbTableSize(BigInteger.valueOf(ocfRecords.size()));
                odbDataOCF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataOCF);
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

    public void saveOCPRecords(List<OCP> ocpRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"OCP\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //OCP RECORD
            int i = 0;
            for(OCP ocpRecord: ocpRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(ocpRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesOCP = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesOCP != null) {
                scostablesOCP.setUpdateDate(LocalDateTime.now());
                scostablesOCP.setScosDB(scostables.getScosDB());
                scostablesOCP.setDimension(BigInteger.valueOf(ocpRecords.size()));
                scostablesOCP.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesOCP);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataOCP = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataOCP != null) {
                odbDataOCP.setOdbTableSize(BigInteger.valueOf(ocpRecords.size()));
                odbDataOCP.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataOCP);
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

    public void savePASRecords(List<PAS> pasRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"PAS\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //PAS RECORD
            int i = 0;
            for(PAS pasRecord: pasRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(pasRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesPAS = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesPAS != null) {
                scostablesPAS.setUpdateDate(LocalDateTime.now());
                scostablesPAS.setScosDB(scostables.getScosDB());
                scostablesPAS.setDimension(BigInteger.valueOf(pasRecords.size()));
                scostablesPAS.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesPAS);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataPAS = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataPAS != null) {
                odbDataPAS.setOdbTableSize(BigInteger.valueOf(pasRecords.size()));
                odbDataPAS.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataPAS);
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

    public void savePCDFRecords(List<PCDF> pcdfRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"PCDF\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //PCDF RECORD
            int i = 0;
            for(PCDF pcdfRecord: pcdfRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(pcdfRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesPCDF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesPCDF != null) {
                scostablesPCDF.setUpdateDate(LocalDateTime.now());
                scostablesPCDF.setScosDB(scostables.getScosDB());
                scostablesPCDF.setDimension(BigInteger.valueOf(pcdfRecords.size()));
                scostablesPCDF.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesPCDF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataPCDF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataPCDF != null) {
                odbDataPCDF.setOdbTableSize(BigInteger.valueOf(pcdfRecords.size()));
                odbDataPCDF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataPCDF);
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

    public void savePCPCRecords(List<PCPC> pcpcRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"PCPC\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //PCPC RECORD
            int i = 0;
            for(PCPC pcpcRecord: pcpcRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(pcpcRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesPCPC = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesPCPC != null) {
                scostablesPCPC.setUpdateDate(LocalDateTime.now());
                scostablesPCPC.setScosDB(scostables.getScosDB());
                scostablesPCPC.setDimension(BigInteger.valueOf(pcpcRecords.size()));
                scostablesPCPC.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesPCPC);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataPCPC = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataPCPC != null) {
                odbDataPCPC.setOdbTableSize(BigInteger.valueOf(pcpcRecords.size()));
                odbDataPCPC.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataPCPC);
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

    public void savePICRecords(List<PIC> picRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"PIC\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //PIC RECORD
            int i = 0;
            for(PIC picRecord: picRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(picRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesPIC = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesPIC != null) {
                scostablesPIC.setUpdateDate(LocalDateTime.now());
                scostablesPIC.setScosDB(scostables.getScosDB());
                scostablesPIC.setDimension(BigInteger.valueOf(picRecords.size()));
                scostablesPIC.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesPIC);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataPIC = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataPIC != null) {
                odbDataPIC.setOdbTableSize(BigInteger.valueOf(picRecords.size()));
                odbDataPIC.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataPIC);
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

    public void savePLFRecords(List<PLF> plfRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"PLF\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //PLF RECORD
            int i = 0;
            for(PLF plfRecord: plfRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(plfRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesPLF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesPLF != null) {
                scostablesPLF.setUpdateDate(LocalDateTime.now());
                scostablesPLF.setScosDB(scostables.getScosDB());
                scostablesPLF.setDimension(BigInteger.valueOf(plfRecords.size()));
                scostablesPLF.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesPLF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataPLF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataPLF != null) {
                odbDataPLF.setOdbTableSize(BigInteger.valueOf(plfRecords.size()));
                odbDataPLF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataPLF);
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

    public void savePRFRecords(List<PRF> prfRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"PRF\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //PRF RECORD
            int i = 0;
            for(PRF prfRecord: prfRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(prfRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesPRF = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesPRF != null) {
                scostablesPRF.setUpdateDate(LocalDateTime.now());
                scostablesPRF.setScosDB(scostables.getScosDB());
                scostablesPRF.setDimension(BigInteger.valueOf(prfRecords.size()));
                scostablesPRF.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesPRF);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataPRF = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataPRF != null) {
                odbDataPRF.setOdbTableSize(BigInteger.valueOf(prfRecords.size()));
                odbDataPRF.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataPRF);
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

    public void savePRVRecords(List<PRV> prvRecords, SCOSTABLES scostables, ODBData odbData) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //TRUNCATE
            String sql = "TRUNCATE TABLE scos_schema.\"PRV\"";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            //PRV RECORD
            int i = 0;
            for(PRV prvRecord: prvRecords) {
                if(i > 0 && i % (JpaEntityManagerFactory.BATCH_SIZE) == 0){
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(prvRecord);
                i++;
            }
            //SCOS TABLE -> scostables | odbData UPDATED
            SCOSTABLES scostablesPRV = entityManager.find(SCOSTABLES.class,scostables.getTableName());
            if(scostablesPRV != null) {
                scostablesPRV.setUpdateDate(LocalDateTime.now());
                scostablesPRV.setScosDB(scostables.getScosDB());
                scostablesPRV.setDimension(BigInteger.valueOf(prvRecords.size()));
                scostablesPRV.setScosDB(scostables.getScosDB());
                entityManager.merge(scostablesPRV);
            } else {
                entityManager.persist(scostables);
            }
            //ODB DATA
            ODBData odbDataPRV = entityManager.find(ODBData.class, odbData.getOdbTableName());
            if(odbDataPRV != null) {
                odbDataPRV.setOdbTableSize(BigInteger.valueOf(prvRecords.size()));
                odbDataPRV.setOdbFiles(odbData.getOdbFiles());
                entityManager.merge(odbDataPRV);
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


}
