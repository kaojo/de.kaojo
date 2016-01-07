/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.TransactionScoped;

/**
 *
 * @author julian
 */
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Produces
    @Default
    @TransactionScoped
    public EntityManager create() {
        return this.entityManagerFactory.createEntityManager();
    }

    public void dispose(@Disposes @Default EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
