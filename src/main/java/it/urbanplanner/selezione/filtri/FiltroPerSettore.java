package it.urbanplanner.selezione.filtri;

import it.urbanplanner.selezione.FiltroDiRicerca;
import it.urbanplanner.settore.Lotto;
import it.urbanplanner.settore.Settore;

/**
 * Filtra i lotti aventi come settore quello indicato.
 */
public class FiltroPerSettore implements FiltroDiRicerca<Lotto> {
    /**
     * Settore di appartenenza.
     */
    private Settore settore;

    /**
     * Costruttore FiltroPerSettore.
     *
     * @param settore
     */
    public FiltroPerSettore(Settore settore) {
        this.settore = settore;
    }

    /**
     * {@inheritDoc}
     */
    public boolean filtra(Lotto oggettoDaFiltrare) {
        return settore.contiene(oggettoDaFiltrare);
    }
}
