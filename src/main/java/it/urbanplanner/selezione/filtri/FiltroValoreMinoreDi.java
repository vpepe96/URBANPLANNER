package it.urbanplanner.selezione.filtri;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.selezione.FiltroDiRicerca;
import it.urbanplanner.settore.Lotto;
import it.urbanplanner.settore.Settore;
import it.urbanplanner.struttura.EdificioPrivato;

/**
 * Filtra i lotti con un EdificioPrivato avente un valore minore di quanto indicato.
 */
public class FiltroValoreMinoreDi implements FiltroDiRicerca<Lotto> {
    /**
     * Valore massimo EdificioPrivato.
     */
    private double massimo;

    /**
     * CentroUrbano di riferimento.
     */
    private CentroUrbano centroUrbano;

    /**
     * Costruttore FiltroValoreMinoreDi.
     *
     * @param massimo
     * @param centroUrbano
     */
    public FiltroValoreMinoreDi(double massimo, CentroUrbano centroUrbano) {
        this.massimo = massimo;
        this.centroUrbano = centroUrbano;
    }

    /**
     * Imposta un nuovo valore massimo.
     *
     * @param massimo
     */
    public void setValore(double massimo) {
        this.massimo = massimo;
    }

    /**
     * {@inheritDoc}
     */
    public boolean filtra(Lotto oggettoDaFiltrare) {
        boolean result = oggettoDaFiltrare.getElementoLotto() != null
                && oggettoDaFiltrare.getElementoLotto() instanceof EdificioPrivato;
        Settore settore = centroUrbano.ottieniSettore(oggettoDaFiltrare);
        if(settore != null) {
            return result && settore.getValore(oggettoDaFiltrare) < massimo;
        } else {
            throw new IllegalArgumentException("Il lotto non è presente nel CentroUrbano");
        }
    }
}
