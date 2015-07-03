/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package julian_sparber;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author operating
 */
public class Julian_sparber {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Varco varco = new Varco();
        Controllore c[] = new Controllore[3];
        Visitatore v[] = new Visitatore[25];

        for (int i = 0; i < c.length; i++) {
            c[i] = new Controllore("Controllore_" + i, varco);
        }

        for (int i = 0; i < v.length; i++) {
            if (i < 7) {
                v[i] = new Visitatore("Visitatore_" + i, varco, true);
            } else {
                v[i] = new Visitatore("Visitatore_" + i, varco, false);
            }
        }
        for (int i = 0; i < c.length; i++) {
            c[i].start();
        }
        for (int i = 0; i < v.length; i++) {
            v[i].start();
        }
        for (int i = 0; i < v.length; i++) {
            try {
                v[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Julian_sparber.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("[MMM] Sono terminati tutti i visitatori.");
        for (int i = 0; i < c.length; i++) {
            c[i].interrupt();
        }

        for (int i = 0; i < c.length; i++) {
            try {
                c[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Julian_sparber.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("[MMM] Sono terminati tutti i controllori.");

    }

}
