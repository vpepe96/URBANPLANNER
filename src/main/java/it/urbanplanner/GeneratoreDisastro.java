package it.urbanplanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Astrazione dell'avvenimento casuale di disastri in un
 * CentroUrbano.
 */
public class GeneratoreDisastro implements Serializable {
    /**
     * Insieme di disastri utilizzabili.
     */
    private ArrayList<Disastro> disastri;

    /**
     * Costruttore GeneratoreDisastro.
     */
    public GeneratoreDisastro() {
        disastri = new ArrayList<>();
    }

    /**
     * Aggiungi un nuovo disastro.
     *
     * @param disastro da aggiungere
     */
    public void aggiungi(Disastro disastro) {
        disastri.add(disastro);
    }

    /**
     * @return disastro pescato casualmente all'interno della lista
     * disponibile.
     */
    public Disastro ottieniCasualmente() {
        return disastri.get(new Random().nextInt(disastri.size() - 1));
    }
}
