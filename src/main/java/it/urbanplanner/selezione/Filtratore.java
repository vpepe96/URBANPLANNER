package it.urbanplanner.selezione;

import java.util.ArrayList;

/**
 * Astrazione del concetto di filtraggio su un insieme di dati.
 *
 * @param <T>
 */
public class Filtratore<T> {
    /**
     * Insieme di dati.
     */
    private ArrayList<T> dati;

    /**
     * Esprime il filtro di ricerca.
     */
    private FiltroDiRicerca<T> filtroDiRicerca;

    /**
     * Costruttore Filtratore.
     *
     * @param dati
     * @param filtroDiRicerca
     */
    public Filtratore(ArrayList<T> dati, FiltroDiRicerca<T> filtroDiRicerca) {
        this.dati = dati;
        this.filtroDiRicerca = filtroDiRicerca;
    }

    /**
     * Filtra i dati utilizzando il filtro corrente.
     */
    public void filtra() {
        for (int i = 0; i < dati.size(); i++) {
            if (!filtroDiRicerca.filtra(dati.get(i))) {
                dati.remove(dati.get(i));
                i--;
            }
        }
    }

    /**
     * Modifica il filtro di ricerca.
     *
     * @param filtroDiRicerca
     */
    public void setFiltroDiRicerca(FiltroDiRicerca<T> filtroDiRicerca) {
        this.filtroDiRicerca = filtroDiRicerca;
    }
}
