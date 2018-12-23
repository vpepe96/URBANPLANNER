package it.urbanplanner.selezione.filtri;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.selezione.FiltroDiRicerca;
import it.urbanplanner.settore.Lotto;
import it.urbanplanner.settore.Settore;
import it.urbanplanner.struttura.EdificioPrivato;

/**
 * Filtra i lotti con un EdificioPrivato avente un valore maggiore di quanto indicato.
 */
public class FiltroValoreMaggioreDi implements FiltroDiRicerca<Lotto> {
    /**
     * Valore minimo EdificioPrivato.
     */
    private double minimo;

    /**
     * CentroUrbano di riferimento.
     */
    private CentroUrbano centroUrbano;

    /**
     * Costruttore FiltroValoreMaggioreDi.
     *
     * @param minimo
     * @param centroUrbano
     */
    public FiltroValoreMaggioreDi(double minimo, CentroUrbano centroUrbano) {
        this.minimo = minimo;
        this.centroUrbano = centroUrbano;
    }

    /**
     * Imposta un nuovo valore minimo.
     *
     * @param minimo
     */
    public void setValore(double minimo) {
        this.minimo = minimo;
    }

    /**
     * {@inheritDoc}
     */
    public boolean filtra(Lotto oggettoDaFiltrare) {
        boolean result = oggettoDaFiltrare.getElementoLotto() != null
                && oggettoDaFiltrare.getElementoLotto() instanceof EdificioPrivato;
        Settore settore = centroUrbano.ottieniSettore(oggettoDaFiltrare);
        if(settore != null) {
            return result && settore.getValore(oggettoDaFiltrare) > minimo;
        } else {
            throw new IllegalArgumentException("Il lotto non è presente nel CentroUrbano");
        }
    }
}
