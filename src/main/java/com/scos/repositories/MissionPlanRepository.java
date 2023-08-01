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

    public void saveTaskScheduledRecord(TaskScheduled taskScheduled) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(taskScheduled);

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

    public List<TaskScheduled> taskScheduledRecord(BigInteger schedulingId) {
        entityManager = scosEmf.getEntityManager();
        List<TaskScheduled> taskscheduledDB = null;
        try {
            taskscheduledDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_TASK_SCHEDULED\" WHERE \"SCHEDULING_ID\" = :scheduling_id", TaskScheduled.class)
                                .setParameter("scheduling_id", schedulingId)
                                .setMaxResults(1)
                                .getResultList();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        System.out.println("Task Scheduled DB: " + taskscheduledDB);
        return taskscheduledDB;
    }

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

    public List<SysBaseHeader> baseHeaderRecord(BigInteger schedulingId) {
        entityManager = scosEmf.getEntityManager();
        /** Transaction is used to modify data(create, update or delete) in the DB  */
        List<SysBaseHeader> baseheaderDB = null;
        try {
            //ALWAYS ONE RECORD
            baseheaderDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_BASE_HEADER\" WHERE \"SCHEDULING_ID\" = :scheduling_id", SysBaseHeader.class)
                            .setParameter("scheduling_id", schedulingId)
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

    public List<SysSequenceParameter> sequenceParameterRecords(String seqId) {
        entityManager = scosEmf.getEntityManager();
        List<SysSequenceParameter> seqparamDB = null;
        try {
            seqparamDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_SEQUENCE_PARAMETER\" WHERE \"SEQUENCE_ID\" = :seq_id", SysSequenceParameter.class)
                    .setParameter("seq_id", seqId)
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

    public List<SysCommandParameter> commandParameterRecords(String commId) {
        entityManager = scosEmf.getEntityManager();
        List<SysCommandParameter> commparamDB = null;
        try {
            commparamDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_COMMAND_PARAMETER\" WHERE \"COMMAND_ID\" = :comm_id", SysCommandParameter.class)
                    .setParameter("comm_id", commId)
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
