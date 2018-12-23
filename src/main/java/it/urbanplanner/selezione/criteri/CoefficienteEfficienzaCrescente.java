package it.urbanplanner.selezione.criteri;

import it.urbanplanner.settore.Lotto;

import java.util.Comparator;

/**
 * Criterio di ordinamente crescente in base al coefficiente di efficienza.
 */
public class CoefficienteEfficienzaCrescente implements Comparator<Lotto> {
    /**
     * {@inheritDoc}
     */
    public int compare(Lotto e1, Lotto e2) {
        if (e1.getElementoLotto() == null) {
            return -1;
        }
        if (e2.getElementoLotto() == null) {
            return 1;
        }
        int e1CoefficienteDiEfficienza = e1.getElementoLotto().getCoefficienteDiEfficienza();
        int e2CoefficienteDiEfficienza = e2.getElementoLotto().getCoefficienteDiEfficienza();

        return e1CoefficienteDiEfficienza > e2CoefficienteDiEfficienza ? 1 : e1CoefficienteDiEfficienza < e2CoefficienteDiEfficienza ? -1 : 0;
    }
}
