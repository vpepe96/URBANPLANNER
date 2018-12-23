package it.urbanplanner.selezione;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Concetto che permette aggregando Ordinatore e Filtratore di poter applicare
 * ambedue le astrazioni su una collezioni di dati arbitraria.
 *
 * @param <T>
 */
public class OrdinatoreFiltratore<T> {
    /**
     * Instanza di Filtratore.
     */
    private Filtratore<T> filtratore;

    /**
     * Instanza di Ordinatore.
     */
    private Ordinatore<T> ordinatore;

    /**
     * Collezione di dati su cui effettuare le operazioni.
     */
    private ArrayList<T> dati;

    /**
     * Costruttore OrdinatoreFiltratore.
     *
     * @param dati
     * @param filtroDiRicerca
     * @param criteriDiOrdinamento
     */
    public OrdinatoreFiltratore(ArrayList<T> dati, FiltroDiRicerca<T> filtroDiRicerca, Comparator<T> criteriDiOrdinamento) {
        this.dati = dati;

        filtratore = new Filtratore<T>(this.dati, filtroDiRicerca);
        ordinatore = new Ordinatore<T>(this.dati, criteriDiOrdinamento);
    }

    /**
     * Costruttore OrdinatoreFiltratore.
     *
     * @param dati
     */
    public OrdinatoreFiltratore(ArrayList<T> dati) {
        this(dati, null, null);
    }

    /**
     * Filtra la collezione aggregata sfruttando Filtratore.
     */
    public void filtra() {
        filtratore.filtra();
    }

    /**
     * Ordina la collezione aggregata sfruttando Ordinatore.
     */
    public void ordina() {
        ordinatore.ordina();
    }

    /**
     * Imposta un nuovo filtroDiRicerca.
     *
     * @param filtroDiRicerca nuovo filtro
     */
    public void setFiltroDiRicerca(FiltroDiRicerca<T> filtroDiRicerca) {
        filtratore.setFiltroDiRicerca(filtroDiRicerca);
    }

    /**
     * Imposta un nuovo criterioDiOrdinamento.
     *
     * @param criterioDiOrdinamento nuovo criterio
     */
    public void setCriterioDiOrdinamento(Comparator<T> criterioDiOrdinamento) {
        ordinatore.setCriterioDiOrdinamento(criterioDiOrdinamento);
    }
}
