package it.urbanplanner.selezione.criteri;

import it.urbanplanner.settore.Lotto;

import java.util.Comparator;

/**
 * Criterio di ordinamente crescente in base alla lunghezza del nome.
 */
public class LunghezzaNomeCrescente implements Comparator<Lotto> {
    /**
     * {@inheritDoc}
     */
    public int compare(Lotto e1, Lotto e2) {
        if (e1.getElementoLotto() == null || e1.getElementoLotto().getNome() == null) {
            return -1;
        }
        if (e2.getElementoLotto() == null || e2.getElementoLotto().getNome() == null) {
            return 1;
        }
        int e1LunghezzaNome = e1.getElementoLotto().getNome().length();
        int e2LunghezzaNome = e2.getElementoLotto().getNome().length();

        return e1LunghezzaNome > e2LunghezzaNome ? 1 : e1LunghezzaNome < e2LunghezzaNome ? -1 : 0;
    }
}
