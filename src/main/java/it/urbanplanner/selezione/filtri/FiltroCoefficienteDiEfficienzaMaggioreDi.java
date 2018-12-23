package it.urbanplanner.selezione.filtri;

import it.urbanplanner.selezione.FiltroDiRicerca;
import it.urbanplanner.settore.Lotto;

/**
 * Filtra i lotti aventi un coefficienteDiEfficienza maggiore di quanto indicato.
 */
public class FiltroCoefficienteDiEfficienzaMaggioreDi implements FiltroDiRicerca<Lotto> {
    /**
     * Valore massimo.
     */
    private double max;

    /**
     * Costruttore FiltroCoefficienteDiEfficienzaMaggioreDi.
     *
     * @param max
     */
    public FiltroCoefficienteDiEfficienzaMaggioreDi(double max) {
        this.max = max;
    }

    /**
     * Imposta un nuovo valore massimo.
     *
     * @param max
     */
    public void setValore(double max) {
        this.max = max;
    }

    /**
     * {@inheritDoc}
     */
    public boolean filtra(Lotto oggettoDaFiltrare) {
        return oggettoDaFiltrare.getElementoLotto() != null
                && oggettoDaFiltrare.getElementoLotto().getCoefficienteDiEfficienza() > max;
    }
}
