/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package julian_sparber;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author operating
 */
public class Visitatore extends Thread {

    Random rnd;
    Varco v;
    Boolean disabile;

    public Visitatore(String name, Varco v, Boolean d) {
        super(name);
        this.rnd = new Random();
        this.v = v;
        this.disabile = d;
    }

    public Boolean getDisabile() {
        return this.disabile;
    }

    public String getInfo() {
        return "[VVV]" + "[" + this.getName().split("_")[1] + "]";
    }

    @Override
    public void run() {
        try {
            if (this.disabile) {
                Thread.sleep(this.rnd.nextInt(1000) + 100);
            } else {
                Thread.sleep(this.rnd.nextInt(500) + 100);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(
                    Visitatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(this.getInfo()
                + " Sono arriavto e sono disabilitato: " + this.disabile
                + " by " + this.getName());
        this.v.richiediAccesso(this);
        System.out.println(this.getInfo()
                + " Sono Terminato e sono disabilitato: " + this.disabile
                + " by " + this.getName());
    }

}
