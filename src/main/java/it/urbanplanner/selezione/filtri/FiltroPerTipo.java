package it.urbanplanner.selezione.filtri;

import it.urbanplanner.selezione.FiltroDiRicerca;
import it.urbanplanner.settore.Lotto;

/**
 * Filtra i lotti aventi come tipo di ElementoLotto quello indicato.
 */
public class FiltroPerTipo implements FiltroDiRicerca<Lotto> {
    /**
     * Tipo di ElementoLotto.
     */
    private Class classe;

    /**
     * Costruttore FiltroPerTipo.
     *
     * @param classe
     */
    public FiltroPerTipo(Class classe) {
        this.classe = classe;
    }

    /**
     * {@inheritDoc}
     */
    public boolean filtra(Lotto oggettoDaFiltrare) {
        return oggettoDaFiltrare.getElementoLotto() != null &&
                oggettoDaFiltrare.getElementoLotto().getClass() == classe;
    }
}
