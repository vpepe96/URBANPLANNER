package it.urbanplanner.selezione.criteri;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.settore.Lotto;
import it.urbanplanner.settore.Settore;
import it.urbanplanner.struttura.EdificioPrivato;

import java.util.Comparator;

/**
 * Criterio di ordinamente crescente in base al valore dell'edificio privato.
 */
public class ValoreCrescente implements Comparator<Lotto> {
    /**
     * CentroUrbano di riferimento.
     */
    private CentroUrbano centroUrbano;

    /**
     * Costruttore ValoreCrescente.
     *
     * @param centroUrbano
     */
    public ValoreCrescente(CentroUrbano centroUrbano) {
        this.centroUrbano = centroUrbano;
    }

    /**
     * {@inheritDoc}
     */
    public int compare(Lotto e1, Lotto e2) {
        if (e1.getElementoLotto() == null || !(e1.getElementoLotto() instanceof EdificioPrivato)) {
            return -1;
        }
        if (e2.getElementoLotto() == null || !(e2.getElementoLotto() instanceof EdificioPrivato)) {
            return 1;
        }
        Settore e1Settore = centroUrbano.ottieniSettore(e1);
        Settore e2Settore = centroUrbano.ottieniSettore(e2);
        double e1Valore = e1Settore.getValore(e1);
        double e2Valore = e2Settore.getValore(e2);

        return e1Valore > e2Valore ? 1 : e1Valore < e2Valore ? -1 : 0;
    }
}