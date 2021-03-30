/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.pegov.DB;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Андрей
 */
public class TTManagerDAO implements TTManager{

    @Override
    public boolean addTT(TroubleTicket tt) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.saveOrUpdate(tt);
            tx.commit();
        }catch (HibernateException e) {
            
            System.err.println("[" + (new Timestamp((new GregorianCalendar().getTimeInMillis()))) + 
                    "]ru.Pegov.Macragge.DB.TTManagerDAO can't add TT: "
                    + tt);
            
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close(); 
        }
        return true;
    }

    @Override
    public boolean addArrayTT(List<TroubleTicket> ttList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            ttList.stream().forEach((TroubleTicket tt) ->{
                session.saveOrUpdate(tt);
            });
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;
        }

        session.close();

        return true;
    }

    @Override
    public List<TroubleTicket> getTTs(Timestamp from, Timestamp to) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<TroubleTicket> result = null;
        try{
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(TroubleTicket.class);
            result = cr.add(Restrictions.or(
                            Restrictions.between("openingDate", from, to), 
                            Restrictions.between("endingDate", from, to))
                    ).list();
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return null;
        }finally {
            session.close(); 
        }
        
        return result;
    }
    
    @Override
    public boolean deleteTT(String id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            TroubleTicket tt;
            tx = session.beginTransaction();
            tt = (TroubleTicket)session.load(TroubleTicket.class, id);
            session.delete(tt);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close(); 
        }
        return true;
    }
   }
