package it.urbanplanner;

import it.urbanplanner.eccezioni.LottoLiberoException;
import it.urbanplanner.settore.Lotto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Astrazione del concetto di demolizione di un Lotto.
 */
public class Demolitore implements Serializable {
    /**
     * Lista degli elementi da demolire.
     */
    private ArrayList<ElementoDemolizione> listaDemolizione;

    /**
     * Costruttore Demolitore.
     */
    public Demolitore() {
        listaDemolizione = new ArrayList<>();
    }

    /**
     * Aggiunge un nuovo lotto alla listaDemolizione.
     * <p>
     * Pre-condizione: lotto.occupato == false
     *
     * @param lotto è il Lotto da demolire
     */
    public void demolisci(Lotto lotto) {
        if (!lotto.occupato()) {
            throw new LottoLiberoException();
        }

        if (lotto.getElementoLotto().getDurataDemolizione() == 0) {
            lotto.liberaLotto();
        } else {
            if (!demolizionePrenotata(lotto)) {
                listaDemolizione.add(new ElementoDemolizione(lotto));
            }
        }
    }

    /**
     * Verifica se è già stato prenotata una demolizione per un Lotto.
     *
     * @param lotto
     * @return
     */
    public boolean demolizionePrenotata(Lotto lotto) {
        for (ElementoDemolizione elementoDaDemolire : listaDemolizione) {
            if (lotto == elementoDaDemolire.lotto) {
                return true;
            }
        }
        return false;
    }

    /**
     * Invecchia la listaDemolizione.
     */
    public void invecchia() {
        for (int i = 0; i < listaDemolizione.size(); i++) {
            ElementoDemolizione elementoDemolizione = listaDemolizione.get(i);

            if (elementoDemolizione.cicliRimanenti - 1 == 0) {
                elementoDemolizione.lotto.liberaLotto();
                listaDemolizione.remove(i);
                i--;
            }

            elementoDemolizione.cicliRimanenti--;
        }
    }

    /**
     * Contiene un lotto e i suoi cicli di invecchiamento rimanenti
     * prima della demolizione effettiva.
     */
    private class ElementoDemolizione implements Serializable {
        private Lotto lotto;
        private int cicliRimanenti;

        public ElementoDemolizione(Lotto lotto) {
            cicliRimanenti = lotto.getElementoLotto().getDurataDemolizione();
            this.lotto = lotto;
        }
    }
}