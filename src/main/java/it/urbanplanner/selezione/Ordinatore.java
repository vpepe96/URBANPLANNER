package it.urbanplanner.selezione;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Astrazione del concetto di ordinamento su un insieme di dati.
 *
 * @param <T>
 */
public class Ordinatore<T> {
    /**
     * Insieme di dati.
     */
    private ArrayList<T> dati;

    /**
     * Rappresenta il criterio di ordinamento selezionato.
     *
     */
    private Comparator<T> criterioDiOrdinamento;

    /**
     * Costruttore Ordinatore.
     *
     * @param dati
     * @param criterioDiOrdinamento
     */
    public Ordinatore(ArrayList<T> dati, Comparator<T> criterioDiOrdinamento) {
        this.dati = dati;
        this.criterioDiOrdinamento = criterioDiOrdinamento;
    }

    /**
     * Ordina la collezione utilizzando il criterio corrente.
     */
    public void ordina() {
        for (int i = 0; i < dati.size(); i++) {
            for (int j = i; j < dati.size(); j++) {
                if (criterioDiOrdinamento.compare(dati.get(i), dati.get(j)) > 0) {
                    T temp = dati.get(i);
                    dati.set(i, dati.get(j));
                    dati.set(j, temp);
                }
            }
        }
    }

    /**
     * Modifica il criterio di ordinamento.
     *
     * @param criterioDiOrdinamento
     */
    public void setCriterioDiOrdinamento(Comparator<T> criterioDiOrdinamento) {
        this.criterioDiOrdinamento = criterioDiOrdinamento;
    }
}
