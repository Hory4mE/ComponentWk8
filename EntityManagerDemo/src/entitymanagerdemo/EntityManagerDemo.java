/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitymanagerdemo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Address;
import model.Customer;

/**
 *
 * @author Thorny
 */
public class EntityManagerDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createData();
        printAllCustomer();
        printCustomerByCity("Bangkok");
    }

    public static void createData() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        Customer customer1 = new Customer(1L, "John", "Henry", "jh@mail.com");
        Address address1 = new Address(1L, "123/4 Viphavadee Rd.", "Bangkok", "10900", "TH");
        Customer customer2 = new Customer(2L, "Marry", "Jane", "mj@mail.com");
        Address address2 = new Address(2L, "123/5 Viphavadee Rd.", "Bangkok", "10900", "TH");
        Customer customer3 = new Customer(3L, "Peter", "Parker", "pp@mail.com");
        Address address3 = new Address(3L, "543/21 Fake Rd.", "Nonthaburi", "20900", "TH");
        Customer customer4 = new Customer(4L, "Bruce", "Wayn", "bw@mail.com");
        Address address4 = new Address(4L, "678/90 Unreal Rd.", "Pathumthani", "30500", "TH");
        try {
            em.getTransaction().begin();
            address1.setCustomerFk(customer1);
            customer1.setAddressId(address1);
            em.persist(address1);
            em.persist(customer1);
            em.getTransaction().commit();

            em.getTransaction().begin();
            address2.setCustomerFk(customer2);
            customer2.setAddressId(address2);
            em.persist(address2);
            em.persist(customer2);
            em.getTransaction().commit();

            em.getTransaction().begin();
            address3.setCustomerFk(customer3);
            customer3.setAddressId(address3);
            em.persist(address3);
            em.persist(customer3);
            em.getTransaction().commit();

            em.getTransaction().begin();
            address4.setCustomerFk(customer4);
            customer4.setAddressId(address4);
            em.persist(address4);
            em.persist(customer4);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void printAllCustomer() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        List<Customer> ctm = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        System.out.printf("------------------------------------------");
        System.out.printf("\nAll Customer");
        System.out.printf("\n------------------------------------------");
        for (Customer cus : ctm) {
            System.out.print(cus.toString());
            Address add = cus.getAddressId();
            if (add != null) {
                System.out.print(add.toString());
            }
            System.out.printf("\n------------------------------------------");
        }
        em.close();
        emf.close();
    }

    public static void printCustomerByCity(String cityN) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        List<Customer> ctm = em.createQuery("SELECT c FROM Customer c WHERE c.addressId.city = :city ORDER BY c.id", Customer.class).setParameter("city", cityN).getResultList();
        System.out.printf("------------------------------------------");
        System.out.printf("\nCustomer in " + cityN);
        System.out.printf("\n------------------------------------------");
        for (Customer cus : ctm) {
            System.out.print(cus.toString());
            Address add = cus.getAddressId();
            if (add != null) {
                System.out.print(add.toString());
            }
            System.out.printf("\n------------------------------------------");
        }
        em.close();
        emf.close();
    }

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
