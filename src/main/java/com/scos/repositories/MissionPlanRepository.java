package com.scos.repositories;

import com.scos.JpaEntityManagerFactory;
import com.scos.data_model.mps_db.*;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigInteger;
import java.util.List;

public class MissionPlanRepository {

    private EntityManager entityManager;

    @Autowired
    private JpaEntityManagerFactory scosEmf;

    public void saveTaskScheduledRecord(SysTaskScheduled sysTaskScheduled) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(sysTaskScheduled);

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

//    public List<SysTaskScheduled> taskScheduledRecord(BigInteger schedulingId) {
//        entityManager = scosEmf.getEntityManager();
//        List<SysTaskScheduled> taskscheduledDB = null;
//        try {
//            taskscheduledDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_TASK_SCHEDULED\" WHERE \"SCHEDULING_ID\" = :scheduling_id", SysTaskScheduled.class)
//                                .setParameter("scheduling_id", schedulingId)
//                                .setMaxResults(1)
//                                .getResultList();
//        } catch (HibernateException hibernateException) {
//            hibernateException.printStackTrace();
//        } finally {
//            entityManager.close();
//        }
//        System.out.println("Task Scheduled DB: " + taskscheduledDB);
//        return taskscheduledDB;
//    }

    public void saveBaseHeaderRecord(SysBaseHeader sysBaseHeader) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(sysBaseHeader);

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

    public List<SysBaseHeader> baseHeaderRecord(String taskName) {
        entityManager = scosEmf.getEntityManager();
        /** Transaction is used to modify data(create, update or delete) in the DB  */
        List<SysBaseHeader> baseheaderDB = null;
        try {
            //ALWAYS ONE RECORD
            baseheaderDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_BASE_HEADER\" WHERE \"TASK_NAME\" = :task_name", SysBaseHeader.class)
                            .setParameter("task_name", taskName)
                            .setMaxResults(1)
                            .getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        System.out.println("Base Header DB: " + baseheaderDB);
        return baseheaderDB;
    }

    public void saveSequenceHeaderRecord(SysSequenceHeader sysSequenceHeader) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(sysSequenceHeader);

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

    public List<SysSequenceHeader> sequenceHeaderRecords(String taskName) {
        entityManager = scosEmf.getEntityManager();
        List<SysSequenceHeader> seqheaderDB = null;
        try {
            seqheaderDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_SEQUENCE_HEADER\" WHERE \"TASK_NAME\" = :task_name", SysSequenceHeader.class)
                    .setParameter("task_name", taskName)
                    .getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        System.out.println("Sequence Header DB: " + seqheaderDB);
        return seqheaderDB;
    }

    public void saveSequenceParameterRecord(SysSequenceParameter sysSequenceParameter) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(sysSequenceParameter);

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

    public List<SysSequenceParameter> sequenceParameterRecords(String taskName, String seqId, BigInteger startTime) {
        entityManager = scosEmf.getEntityManager();
        List<SysSequenceParameter> seqparamDB = null;
        try {
            seqparamDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_SEQUENCE_PARAMETER\" WHERE \"TASK_NAME\" = :task_name AND \"SEQUENCE_ID\" = :seq_id AND \"STARTTIME\" = start_time " , SysSequenceParameter.class)
                    .setParameter("task_name", taskName)
                    .setParameter("seq_id", seqId)
                    .setParameter("start_time", startTime)
                    .getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        System.out.println("Sequence Parameter DB: " + seqparamDB);
        return seqparamDB;
    }

    public void saveCommandHeaderRecord(SysCommandHeader sysCommandHeader) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(sysCommandHeader);

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

    public List<SysCommandHeader> commandHeaderRecords(String taskName) {
        entityManager = scosEmf.getEntityManager();
        List<SysCommandHeader> commheaderDB = null;
        try {
            commheaderDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_COMMAND_HEADER\" WHERE \"TASK_NAME\" = :task_name ORDER BY \"COMMAND_ID\" DESC", SysCommandHeader.class)
                    .setParameter("task_name", taskName)
                    .getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        System.out.println("Command Header DB: " + commheaderDB);
        return commheaderDB;
    }

    public void saveCommandParamenterRecord(SysCommandParameter sysCommandParameter) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(sysCommandParameter);

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

    public List<SysCommandParameter> commandParameterRecords(String taskName, String commId, BigInteger commdProgressiveId) {
        entityManager = scosEmf.getEntityManager();
        List<SysCommandParameter> commparamDB = null;
        try {
            commparamDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_COMMAND_PARAMETER\" WHERE \"TASK_NAME\" = :task_name AND \"COMMAND_ID\" = :comm_id AND \"COMMAND_PROGRESSIVE_ID\" = :commd_prog_id ", SysCommandParameter.class)
                    .setParameter("task_name", taskName)
                    .setParameter("comm_id", commId)
                    .setParameter("commd_prog_id", commdProgressiveId)
                    .getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        System.out.println("Command Parameter DB: " + commparamDB);
        return commparamDB;
    }

}
