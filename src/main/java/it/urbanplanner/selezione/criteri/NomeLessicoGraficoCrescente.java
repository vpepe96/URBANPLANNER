package it.urbanplanner.selezione.criteri;

import it.urbanplanner.settore.Lotto;

import java.util.Comparator;

/**
 * Criterio di ordinamente crescente in base al valore lessico grafico del nome.
 */
public class NomeLessicoGraficoCrescente implements Comparator<Lotto> {
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
        String e1Nome = e1.getElementoLotto().getNome();
        String e2Nome = e2.getElementoLotto().getNome();

        return e1Nome.compareTo(e2Nome);
    }
}
