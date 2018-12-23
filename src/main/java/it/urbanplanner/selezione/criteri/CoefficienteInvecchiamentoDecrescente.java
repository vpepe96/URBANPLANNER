package it.urbanplanner.selezione.criteri;

import it.urbanplanner.settore.Lotto;

import java.util.Comparator;

/**
 * Criterio di ordinamente decrescente in base al coefficiente di invecchiamento.
 */
public class CoefficienteInvecchiamentoDecrescente implements Comparator<Lotto> {
    /**
     * {@inheritDoc}
     */
    public int compare(Lotto e1, Lotto e2) {
        if (e1.getElementoLotto() == null) {
            return 1;
        }
        if (e2.getElementoLotto() == null) {
            return -1;
        }
        int e1CoefficienteDiInvecchiamento = e1.getElementoLotto().getCoefficienteDiInvecchiamento();
        int e2CoefficienteDiInvecchiamento = e2.getElementoLotto().getCoefficienteDiInvecchiamento();

        return e1CoefficienteDiInvecchiamento > e2CoefficienteDiInvecchiamento ? -1 : e1CoefficienteDiInvecchiamento < e2CoefficienteDiInvecchiamento ? 1 : 0;
    }
}
