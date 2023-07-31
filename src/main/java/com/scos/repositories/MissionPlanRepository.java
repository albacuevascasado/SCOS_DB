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

    public void saveBaseHeaderRecord(BaseHeader baseHeader) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(baseHeader);

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

    public List<BaseHeader> baseHeaderRecord(BigInteger schedulingId) {
        entityManager = scosEmf.getEntityManager();
        /** Transaction is used to modify data(create, update or delete) in the DB  */
        List<BaseHeader> baseheaderDB = null;
        try {
            //ALWAYS ONE RECORD
            baseheaderDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_BASE_HEADER\" WHERE \"SCHEDULING_ID\" = :scheduling_id", BaseHeader.class)
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

    public void saveSequenceHeaderRecord(SequenceHeader sequenceHeader) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(sequenceHeader);

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

    public List<SequenceHeader> sequenceHeaderRecords(String taskName) {
        entityManager = scosEmf.getEntityManager();
        List<SequenceHeader> seqheaderDB = null;
        try {
            seqheaderDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SEQUENCE_HEADER\" WHERE \"TASK_NAME\" = :task_name", SequenceHeader.class)
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

    public void saveSequenceParameterRecord(SequenceParameter sequenceParameter) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(sequenceParameter);

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

    public List<SequenceParameter> sequenceParameterRecords(String seqId) {
        entityManager = scosEmf.getEntityManager();
        List<SequenceParameter> seqparamDB = null;
        try {
            seqparamDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SEQUENCE_PARAMETER\" WHERE \"SEQUENCE_ID\" = :seq_id", SequenceParameter.class)
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

    public void saveCommandHeaderRecord(CommandHeader commandHeader) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(commandHeader);

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

    public List<CommandHeader> commandHeaderRecords(String taskName) {
        entityManager = scosEmf.getEntityManager();
        List<CommandHeader> commheaderDB = null;
        try {
            commheaderDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_COMMAND_HEADER\" WHERE \"TASK_NAME\" = :task_name", CommandHeader.class)
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

    public void saveCommandParamenterRecord(CommandParameter commandParameter) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(commandParameter);

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

    public List<CommandParameter> commandParameterRecords(String commId) {
        entityManager = scosEmf.getEntityManager();
        List<CommandParameter> commparamDB = null;
        try {
            commparamDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_COMMAND_PARAMETER\" WHERE \"COMMAND_ID\" = :comm_id", CommandParameter.class)
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
