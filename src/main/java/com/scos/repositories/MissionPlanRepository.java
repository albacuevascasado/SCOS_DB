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

public class MissionPlanRepository {
    /** createQuery -> OBJECTS | createNativeQuery -> FIELDS */

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
        System.out.println("Scheduling DB: " + sysSchedulingProvaDB.getSchedulingId());
        return sysSchedulingProvaDB;
    }

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

    public List<SysTaskScheduled> taskScheduledRecord(SysSchedulingProva sysSchedulingProva) {
        entityManager = scosEmf.getEntityManager();
        List<SysTaskScheduled> taskscheduledDB = null;
        try {
            //one scheduling_id has MANY SCHEDULED TASKS
            TypedQuery<SysTaskScheduled> taskScheduledQuery = entityManager.createQuery("SELECT t FROM SysTaskScheduled t WHERE t.sysScheduling = :schedulingProva", SysTaskScheduled.class)
                    .setParameter("schedulingProva", sysSchedulingProva);
            taskscheduledDB = taskScheduledQuery.getResultList();

//            taskscheduledDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_TASK_SCHEDULED\" WHERE \"SCHEDULING_ID\" = :scheduling_id", SysTaskScheduled.class)
//                                .setParameter("scheduling_id", schedulingId)
//                                .setMaxResults(1)
//                                .getResultList();

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

    public SysBaseHeader baseHeaderRecord(SysSchedulingProva sysSchedulingProva) {
        entityManager = scosEmf.getEntityManager();
        /** Transaction is used to modify data(create, update or delete) in the DB  */
        //List<SysBaseHeader> baseheaderDB = null; .getResultList(); -> createNativeQuery
        SysBaseHeader baseheaderDB = new SysBaseHeader();
        try {
            TypedQuery<SysBaseHeader> baseHeaderQuery = entityManager.createQuery("SELECT b FROM SysBaseHeader b WHERE b.sysScheduling = :schedulingProva ", SysBaseHeader.class)
                    .setParameter("schedulingProva", sysSchedulingProva);
            baseheaderDB = baseHeaderQuery.getSingleResult();

            //ALWAYS ONE RECORD
//            baseheaderDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_BASE_HEADER\" WHERE \"TASK_NAME\" = :task_name", SysBaseHeader.class)
//                            .setParameter("task_name", taskName)
//                            .setMaxResults(1)
//                            .getResultList();

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

    public List<SysSequenceHeader> sequenceHeaderRecords(List<SysTaskScheduled> sysTasksScheduled) {
        entityManager = scosEmf.getEntityManager();
        List<SysSequenceHeader> seqheaderDB = new ArrayList<>();
        try {
            for (SysTaskScheduled sysTaskScheduled: sysTasksScheduled) {
//                System.out.println("Task Scheduled: " + sysTaskScheduled);
                TypedQuery<SysSequenceHeader> sequenceHeaderQuery = entityManager.createQuery("SELECT s FROM SysSequenceHeader s WHERE s.sysTaskScheduled = :taskScheduled", SysSequenceHeader.class)
                        .setParameter("taskScheduled", sysTaskScheduled);
//                seqheaderDB = sequenceHeaderQuery.getResultList();

                for(int i=0; i<sequenceHeaderQuery.getResultList().size(); i++) {
                    seqheaderDB.add(sequenceHeaderQuery.getResultList().get(i));
                }
            }

//            for(int i=0;i<seqheaderDB.size();i++) {
//                System.out.println("Sequence Header List: " + seqheaderDB.get(i));
//            }

//            seqheaderDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_SEQUENCE_HEADER\" WHERE \"TASK_NAME\" = :task_name", SysSequenceHeader.class)
//                    .setParameter("task_name", taskName)
//                    .getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }

        return seqheaderDB;
    }

    public SysSequenceHeader sequenceHeaderByTaskANDSequenceId (SysTaskScheduled sysTaskScheduled, String sequenceId) {
        entityManager = scosEmf.getEntityManager();
        SysSequenceHeader sequenceHeader = new SysSequenceHeader();
        try {
            TypedQuery<SysSequenceHeader> sequenceHeaderQuery = entityManager.createQuery("SELECT s FROM SysSequenceHeader s WHERE s.sysTaskScheduled = :taskScheduled AND s.sequenceId = :sequenceID", SysSequenceHeader.class)
                    .setParameter("taskScheduled", sysTaskScheduled)
                    .setParameter("sequenceID", sequenceId);
            sequenceHeader = sequenceHeaderQuery.getSingleResult(); // query with parameter sequenceId UNIQUE for each sequence header
            System.out.println("Sequence Header by Task and sequence Id: " + sequenceHeader);

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }

        return sequenceHeader;
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

    //WITHOUT TASK SCHEDULED BECAUSE ALREADY SEQUENCE HEADER HAS BEEN FILTERED BY TASK OR ADD FIELD TASK NAME FROM SEQUENCE HEADER
    public List<SysSequenceParameter> sequenceParameterRecords(SysSequenceHeader sysSequenceHeader) {
        entityManager = scosEmf.getEntityManager();
        List<SysSequenceParameter> seqparamDB = new ArrayList<>();
        try {
            //TypedQuery<SysSequenceParameter> sequenceParameterQuery = entityManager.createQuery("SELECT p FROM SysSequenceParameter p WHERE p.sysTaskScheduled = :taskScheduled AND p.sysSequenceHeader = :sequenceHeader" , SysSequenceParameter.class)
            TypedQuery<SysSequenceParameter> sequenceParameterQuery = entityManager.createQuery("SELECT p FROM SysSequenceParameter p WHERE p.sysSequenceHeader = :sequenceHeader" , SysSequenceParameter.class)
                    //.setParameter("taskScheduled", sysTaskScheduled)
                    .setParameter("sequenceHeader", sysSequenceHeader);
            seqparamDB = sequenceParameterQuery.getResultList();

//            seqparamDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_SEQUENCE_PARAMETER\" WHERE \"TASK_NAME\" = :task_name AND \"SEQUENCE_ID\" = :seq_id AND \"STARTTIME\" = start_time " , SysSequenceParameter.class)
//                    .setParameter("task_name", taskName)
//                    .setParameter("seq_id", seqId)
//                    .setParameter("start_time", startTime)
//                    .getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        System.out.println("Sequence Parameter DB: " + seqparamDB);
        return seqparamDB;
    }

    public List<SysSequenceParameter> sequenceParametersForEachSequenceHeader(SysTaskScheduled sysTaskScheduled, String sequenceId) {
        SysSequenceHeader sysSequenceHeader = sequenceHeaderByTaskANDSequenceId(sysTaskScheduled, sequenceId);
        List<SysSequenceParameter> sysSequenceParametersBySequenceHeader = sequenceParameterRecords(sysSequenceHeader);

        return sysSequenceParametersBySequenceHeader;
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

    //QUERY ONLY WITH TASK - SEQUENCE HEADER IS AN OPTIONAL FIELD
    public List<SysCommandHeader> commandHeaderRecords(List<SysTaskScheduled> sysTasksScheduled) {
        entityManager = scosEmf.getEntityManager();
        List<SysCommandHeader> commheaderDB = new ArrayList<>();
        try {
            for(SysTaskScheduled sysTaskScheduled: sysTasksScheduled) {
//                TypedQuery<SysCommandHeader> commandHeaderQuery = entityManager.createQuery("SELECT h FROM SysCommandHeader h WHERE h.sysTaskScheduled = :taskScheduled AND h.sysSequenceHeader = :sequenceHeader" , SysCommandHeader.class)
                TypedQuery<SysCommandHeader> commandHeaderQuery = entityManager.createQuery("SELECT h FROM SysCommandHeader h WHERE h.sysTaskScheduled = :taskScheduled" , SysCommandHeader.class)
                        .setParameter("taskScheduled", sysTaskScheduled);
//                    .setParameter("sequenceHeader", sysSequenceHeader);

                for(int i=0;i<commandHeaderQuery.getResultList().size();i++) {
                    commheaderDB.add(commandHeaderQuery.getResultList().get(i));
                }

//                commheaderDB = commandHeaderQuery.getResultList();
            }

            for(int i=0;i<commheaderDB.size();i++) {
                System.out.println("Command Header List: " + commheaderDB.get(i));
            }

//            commheaderDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_COMMAND_HEADER\" WHERE \"TASK_NAME\" = :task_name ORDER BY \"COMMAND_ID\" DESC", SysCommandHeader.class)
//                    .setParameter("task_name", taskName)
//                    .getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }
        System.out.println("Command Header DB: " + commheaderDB);
        return commheaderDB;
    }

    public SysCommandHeader commandHeaderByTaskANDCommandId (SysTaskScheduled sysTaskScheduled, String commandId) {
        entityManager = scosEmf.getEntityManager();
        SysCommandHeader commandHeader = new SysCommandHeader();
        try {
            TypedQuery<SysCommandHeader> commandHeaderQuery = entityManager.createQuery("SELECT c FROM SysCommandHeader c WHERE c.sysTaskScheduled = :taskScheduled AND c.commandId = :commandID", SysCommandHeader.class)
                    .setParameter("taskScheduled", sysTaskScheduled)
                    .setParameter("commandID", commandId);
            commandHeader = commandHeaderQuery.getSingleResult(); // query with parameter commandId UNIQUE for each command header
            System.out.println("Command Header by Task and command Id: " + commandHeader);

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }

        return commandHeader;
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

    public List<SysCommandParameter> commandParameterRecords(SysCommandHeader sysCommandHeader) {
        entityManager = scosEmf.getEntityManager();
        List<SysCommandParameter> commparamDB = new ArrayList<>();
        try {
            TypedQuery<SysCommandParameter> commandParameterQuery = entityManager.createQuery("SELECT p FROM SysCommandParameter p WHERE p.sysCommandHeader = :commandHeader" , SysCommandParameter.class)
                    //.setParameter("taskScheduled", sysTaskScheduled)
                    .setParameter("commandHeader", sysCommandHeader);
            commparamDB = commandParameterQuery.getResultList();

//            commparamDB = entityManager.createNativeQuery("SELECT * FROM mps_schema.\"T_SYS_COMMAND_PARAMETER\" WHERE \"TASK_NAME\" = :task_name AND \"COMMAND_ID\" = :comm_id AND \"COMMAND_PROGRESSIVE_ID\" = :commd_prog_id ", SysCommandParameter.class)
//                    .setParameter("task_name", taskName)
//                    .setParameter("comm_id", commId)
//                    .setParameter("commd_prog_id", commdProgressiveId)
//                    .getResultList();

        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        } finally {
            entityManager.close();
        }

        return commparamDB;
    }

    public List<SysCommandParameter> commandParametersForEachCommandHeader(SysTaskScheduled sysTaskScheduled, String commandId) {
        SysCommandHeader sysCommandHeader = commandHeaderByTaskANDCommandId(sysTaskScheduled, commandId);
        List<SysCommandParameter> sysCommandParametersBySequenceHeader = commandParameterRecords(sysCommandHeader);

        return sysCommandParametersBySequenceHeader;
    }

}
