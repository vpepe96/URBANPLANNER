package it.urbanplanner.selezione.filtri;

import it.urbanplanner.selezione.FiltroDiRicerca;
import it.urbanplanner.settore.Lotto;

/**
 * Filtra i lotti aventi un coefficienteDiEfficienza minore di quanto indicato.
 */
public class FiltroCoefficienteDiEfficienzaMinoreDi implements FiltroDiRicerca<Lotto> {
    /**
     * Valore minimo.
     */
    private double min;

    /**
     * Costruttore FiltroCoefficienteDiEfficienzaMinoreDi.
     *
     * @param min
     */
    public FiltroCoefficienteDiEfficienzaMinoreDi(double min) {
        this.min = min;
    }

    /**
     * Imposta un nuovo valore minimo.
     *
     * @param min
     */
    public void setValore(double min) {
        this.min = min;
    }

    /**
     * {@inheritDoc}
     */
    public boolean filtra(Lotto oggettoDaFiltrare) {
        return oggettoDaFiltrare.getElementoLotto() != null
                && oggettoDaFiltrare.getElementoLotto().getCoefficienteDiEfficienza() < min;
    }
}