/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package julian_sparber;

/**
 *
 * @author operating
 */
public class Controllore extends Thread {

    Boolean toContinue;
    Varco v;

    public Controllore(String name, Varco v) {
        super(name);
        this.v = v;
        this.toContinue = true;
    }

    public String getInfo() {
        return "[CCC]" + "[" + this.getName().split("_")[1] + "]";
    }

    @Override
    public void run() {
        while (this.toContinue) {
            try {
                this.v.controllaVisitatore(this);
            } catch (InterruptedException e) {
                this.toContinue = false;
            }
        }
    }
}
