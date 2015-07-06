/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package julian_sparber;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author operating
 */
public class Varco {

    ReentrantLock lock;
    Semaphore coda;
    Semaphore codaDisabile;
    Condition svegliaCon;
    int disabileInAttesa; //numero di disabili in attesa
    int inCoda; //numero totale di Visitatori in attesa

    public Varco() {
        this.lock = new ReentrantLock();
        this.svegliaCon = this.lock.newCondition();
        this.coda = new Semaphore(0, true);
        this.codaDisabile = new Semaphore(0, true);
        this.inCoda = 0;
        this.disabileInAttesa = 0;
    }

    void richiediAccesso(Visitatore v) {
        try {
            this.lock.lock();
            try {
                System.out.println(v.getInfo()
                        + "Chiamo il controllore by " + v.getName());
                if (v.getDisabile()) {
                    this.disabileInAttesa++;
                }
                //Se nesun controllore e' libro (o in attesa
                //questa signal viene igniorata
                this.svegliaCon.signal();
                //ma savlo mia presenza nella coda
                this.inCoda++;
            } finally {
                this.lock.unlock();
            }
            if (v.getDisabile()) {
                this.codaDisabile.acquire();

            } else {
                this.coda.acquire();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Varco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void controllaVisitatore(Controllore c) throws InterruptedException {

        this.lock.lock();
        try {
            if (this.inCoda <= 0) {
                System.out.println(c.getInfo()
                        + " Controllore in attesa by " + c.getName());
                this.svegliaCon.await();
            }
            //decremento il numero di disabili nella coda
            if (this.disabileInAttesa > 0) {
                this.disabileInAttesa--;
            }
            this.inCoda--;
        } finally {
            this.lock.unlock();
        }

        System.out.println(c.getInfo()
                + " C'e' qualcuno in coda (disabili="
                + this.disabileInAttesa + ") by " + c.getName());
        try {

            if (this.disabileInAttesa > 0) {
                System.out.println(c.getInfo()
                        + " Controllo un disabile by " + c.getName());
                Thread.sleep(200);
                //rilasco un permeso per il primo disabile in coda
                this.codaDisabile.release();
            } else {
                System.out.println(c.getInfo()
                        + " Controllo un non disabile by " + c.getName());
                Thread.sleep(200);
                //rilasco un permeso per il primo visitatore non disabile 
                //in coda
                this.coda.release();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(
                    Controllore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
