/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbapp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author igor
 */
public class DbApp {

    /**
     * @param args the command line arguments
     *
     * Len pre vase otestovanie. Mozete si upravit.
     */
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("zapocet2PU");
        EntityManager em = emf.createEntityManager();

        pridajVyucujuceho(em, "OOP", "Hrach");
        pridajVyucujuceho(em, "VSA", "Mrkva");
        pridajVyucujuceho(em, "ASOS", "Mrkva");
        pridajVyucujuceho(em, "ASOS", "Mrkva");

        System.out.println("Mrkvov uvazok: " + pocetPredmetov(em, "Mrkva"));    // vypise 2  

        Set<Ucitel> zoznam = vyucujuci(em, "VSA");
        System.out.println("Pocet vyucujucich VSA: " + zoznam.size());          // vypise 1

    }

    /* Vrati zoznam vyucujucich predmetu so zadanym kodom
     * Ak kod nie je zadany alebo predmet s danym kodom neexistuje vrati null.
     */
    public static Set<Ucitel> vyucujuci(EntityManager em, String kodPredmetu) throws Exception {
        if(kodPredmetu == null) return null;
        Query q = em.createQuery("select p from Predmet p WHERE p.kod = :kod", Predmet.class);
        q.setParameter("kod", kodPredmetu);
        Predmet p = (Predmet) q.getSingleResult();
        if(p == null) return null;
        return p.getVyucujuci();
   }

    /** Vrati pocet predmetov, ktore vyucuje ucitel so zadanym menom. 
     *      
     * @param em:   entity manager
     * @param meno  meno ucitela
     * 
     * Ak meno nie je zadane alebo ucitel s danym menom neexistuje vrati 0.
     * Metoda sa moze spolahnut na to, ze v DB je meno ucitela jedinecne
     */
    public static int pocetPredmetov(EntityManager em, String meno) throws Exception {
        if(meno == null) return 0;
        Query q = em.createQuery("select u from Ucitel u WHERE u.meno = :meno", Ucitel.class);
        q.setParameter("meno", meno);
        Ucitel u = (Ucitel) q.getSingleResult();
        /*List<Ucitel> list = new ArrayList<>();
        Ucitel u = new Ucitel();
        for(Ucitel uc : list){
            if(uc == null) break;
            u = uc;
        }*/
        //Ucitel u = em.find(Ucitel.class, meno);
        //if(u == null) return 0;
        
        return u.getPredmety().size();
    }

    /** prida predmetu vyucujuceho.
     *
     * @param em:   entity manager
     * @param meno  meno ucitela
     * @param kod   kod predmetu
     *
     * Metoda vyhlada ucitela podla mena a predmet podla kodu. 
     * Ak ucitel s danym menom v DB neexistuje vytvori ho, pricom 
     *   datum narodenia nebude zadany (ostane null) 
     * Ak predmet s danym kodom neexistuje, vytvori ho, pricom odbor bude "BIS". 
     * Nasledne zaradi ucitela medzi vyucujucich predmetu. 
     *
     * Ak meno alebo kodPredmetu nie su zadane vrati false inak vrati true. 
     * V pripade inej chyby vyhodi vynimku.
     */
    public static boolean pridajVyucujuceho(EntityManager em, String kodPredmetu, String meno) throws Exception {
        if(kodPredmetu == null || meno == null) return false;
        Query q = em.createQuery("select u from Ucitel u WHERE u.meno = :meno", Ucitel.class);
        q.setParameter("meno", meno);
        List<Ucitel> list = new ArrayList<>();
        list = q.getResultList();
        Ucitel u = new Ucitel();
        for(Ucitel uc : list){
            if(uc == null) break;
            u = uc;
        }
        
       
        //u = new Ucitel();
        u.setNarodeny(null);
        u.setMeno(meno);
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        
        
        //Ucitel u = em.find(Ucitel.class, meno);
        
        
        Predmet p = em.find(Predmet.class, kodPredmetu);
        if(p == null){
            p = new Predmet();
            p.setKod(kodPredmetu);
            p.setOdbor("BIS");
        }
        p.getVyucujuci().add(u);
        
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        return true;
        //throw new Exception("NEIMPLEMENTOVANE");
    }
}
