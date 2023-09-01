package com.scos.repositories;

import com.scos.JpaEntityManagerFactory;
import com.scos.data_model.mps_db.*;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FlightPlanRepository {

    private EntityManager entityManager;

    @Autowired
    private JpaEntityManagerFactory scosEmf;

    public void saveSysSchedulingProva(SysSchedulingProva sysSchedulingProva) {
        entityManager = scosEmf.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(sysSchedulingProva);

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

    public SysSchedulingProva schedulingProvaRecord(BigInteger sysSchedulingIdProva) {
        entityManager = scosEmf.getEntityManager();
        SysSchedulingProva sysSchedulingProvaDB = null;
        try {
            TypedQuery<SysSchedulingProva> schedulingQuery = entityManager.createQuery("SELECT s FROM SysSchedulingProva s WHERE s.schedulingId = :sysSchedulingId", SysSchedulingProva.class)
                    .setParameter("sysSchedulingId", sysSchedulingIdProva);
            sysSchedulingProvaDB = schedulingQuery.getSingleResult(); //REDUNDANT = ALWAYS WILL BE ONE VALUE

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        //System.out.println("Scheduling DB: " + sysSchedulingProvaDB.getSchedulingId());
        return sysSchedulingProvaDB;
    }

    public List<SysTaskScheduled> taskScheduledRecords(SysSchedulingProva sysSchedulingProva) {
        entityManager = scosEmf.getEntityManager();
        List<SysTaskScheduled> taskscheduledDB = null;
        try {
            //one scheduling_id has MANY SCHEDULED TASKS
            TypedQuery<SysTaskScheduled> taskScheduledQuery = entityManager.createQuery("SELECT t FROM SysTaskScheduled t WHERE t.sysScheduling = :schedulingProva", SysTaskScheduled.class)
                    .setParameter("schedulingProva", sysSchedulingProva);
            taskscheduledDB = taskScheduledQuery.getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        //System.out.println("Task Scheduled DB: " + taskscheduledDB);
        return taskscheduledDB;
    }

    public SysBaseHeader baseHeaderRecord(SysSchedulingProva sysSchedulingProva) {
        entityManager = scosEmf.getEntityManager();
        /** Transaction is used to modify data(create, update or delete) in the DB  */
        SysBaseHeader baseheaderDB = new SysBaseHeader();
        try {
            TypedQuery<SysBaseHeader> baseHeaderQuery = entityManager.createQuery("SELECT b FROM SysBaseHeader b WHERE b.sysScheduling = :schedulingProva ", SysBaseHeader.class)
                    .setParameter("schedulingProva", sysSchedulingProva);
            baseheaderDB = baseHeaderQuery.getSingleResult();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        //System.out.println("Base Header DB: " + baseheaderDB);
        return baseheaderDB;
    }

    public List<SysSequenceHeader> sequenceHeaderRecords(SysTaskScheduled sysTaskScheduled) {
        entityManager = scosEmf.getEntityManager();
        List<SysSequenceHeader> seqheaderDB = new ArrayList<>();
        try {
            TypedQuery<SysSequenceHeader> sequenceHeaderQuery = entityManager.createQuery("SELECT s FROM SysSequenceHeader s WHERE s.sysTaskScheduled = :taskScheduled", SysSequenceHeader.class)
                    .setParameter("taskScheduled", sysTaskScheduled);
            seqheaderDB = sequenceHeaderQuery.getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }

        return seqheaderDB;
    }

    //WITHOUT TASK SCHEDULED BECAUSE ALREADY SEQUENCE HEADER HAS BEEN FILTERED BY TASK OR ADD FIELD TASK NAME FROM SEQUENCE HEADER
    public List<SysSequenceParameter> sequenceParameterRecords(SysSequenceHeader sysSequenceHeader) {
        entityManager = scosEmf.getEntityManager();
        List<SysSequenceParameter> seqparamDB = new ArrayList<>();
        try {
            TypedQuery<SysSequenceParameter> sequenceParameterQuery = entityManager.createQuery("SELECT p FROM SysSequenceParameter p WHERE p.sysSequenceHeader = :sequenceHeader" , SysSequenceParameter.class)
                    .setParameter("sequenceHeader", sysSequenceHeader);
            seqparamDB = sequenceParameterQuery.getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        return seqparamDB;
    }

    //QUERY ONLY WITH TASK - SEQUENCE HEADER IS AN OPTIONAL FIELD
    public List<SysCommandHeader> commandHeaderRecords(SysTaskScheduled sysTaskScheduled) {
        entityManager = scosEmf.getEntityManager();
        List<SysCommandHeader> commheaderDB = new ArrayList<>();
        try {
                TypedQuery<SysCommandHeader> commandHeaderQuery = entityManager.createQuery("SELECT h FROM SysCommandHeader h WHERE h.sysTaskScheduled = :taskScheduled" , SysCommandHeader.class)
                        .setParameter("taskScheduled", sysTaskScheduled);
                commheaderDB = commandHeaderQuery.getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        System.out.println("Command Header DB: " + commheaderDB);
        return commheaderDB;
    }

    public List<SysCommandParameter> commandParameterRecords(SysCommandHeader sysCommandHeader) {
        entityManager = scosEmf.getEntityManager();
        List<SysCommandParameter> commparamDB = new ArrayList<>();
        try {
            TypedQuery<SysCommandParameter> commandParameterQuery = entityManager.createQuery("SELECT p FROM SysCommandParameter p WHERE p.sysCommandHeader = :commandHeader" , SysCommandParameter.class)
                    .setParameter("commandHeader", sysCommandHeader);
            commparamDB = commandParameterQuery.getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }

        return commparamDB;
    }

}
