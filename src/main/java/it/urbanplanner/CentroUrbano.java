package it.urbanplanner;

import it.urbanplanner.eccezioni.LottoNonTrovatoException;
import it.urbanplanner.iteratori.IteratoreColonnaDestinazione;
import it.urbanplanner.iteratori.IteratoreLottiCentroUrbano;
import it.urbanplanner.iteratori.IteratoreRigaDestinazione;
import it.urbanplanner.iteratori.IteratoreSettori;
import it.urbanplanner.settore.Lotto;
import it.urbanplanner.settore.Settore;

import java.io.Serializable;
import java.util.Random;

/**
 * Astrazione del concetto di CentroUrbano.
 */
public class CentroUrbano implements Serializable {
    /**
     * Collezione dei settori del CentroUrbano.
     */
    private Settore[][] settori;

    /**
     * Numero di lotti che ogni Settore possiede.
     */
    private int numeroDiLottiPerSettore;

    /**
     * Numero di settori che possiede il CentroUrbano.
     */
    private int numeroDiSettori;

    /**
     * Numero di lotti presenti su ogni riga di un Settore.
     */
    private int numeroDiLottiPerRiga;

    /**
     * Numero di lotti presenti su ogni colonna di un Settore.
     */
    private int numeroDiLottiPerColonna;

    /**
     * Numero di settori presenti su ogni riga di un CentroUrbano.
     */
    private int numeroDiSettoriPerRiga;

    /**
     * Numero di settori presenti su ogni colonna di un CentroUrbano.
     */
    private int numeroDiSettoriPerColonna;

    /**
     * Demolitore del CentroUrbano.
     */
    private Demolitore demolitore;

    /**
     * Istanza di GeneratoreDisastro per centro urbano.
     */
    private GeneratoreDisastro generatoreDisastro;

    /**
     * Costruttore CentroUrbano.
     *
     * @param numeroDiSettoriPerRiga
     * @param numeroDiSettoriPerColonna
     * @param numeroDiLottiPerRiga
     * @param numeroDiLottiPerColonna
     */
    public CentroUrbano(int numeroDiSettoriPerRiga, int numeroDiSettoriPerColonna, int numeroDiLottiPerRiga, int numeroDiLottiPerColonna) {
        this.numeroDiSettoriPerRiga = numeroDiSettoriPerRiga;
        this.numeroDiSettoriPerColonna = numeroDiSettoriPerColonna;
        this.numeroDiLottiPerRiga = numeroDiLottiPerRiga;
        this.numeroDiLottiPerColonna = numeroDiLottiPerColonna;

        numeroDiSettori = numeroDiSettoriPerColonna * numeroDiSettoriPerRiga;
        numeroDiLottiPerSettore = numeroDiLottiPerColonna * numeroDiLottiPerRiga;

        demolitore = new Demolitore();
        generatoreDisastro = new GeneratoreDisastro();
    }

    /*
    |--------------------------------------------------------------------------
    | Metodi modificatori
    |--------------------------------------------------------------------------
    */

    /**
     * Demolisci un lotto.
     * <p>
     * Pre-condizione: il lotto deve trovarsi nel centro urbano
     *
     * @param lotto
     */
    public void demolisci(Lotto lotto) {
        IteratoreLottiCentroUrbano iteratore = new IteratoreLottiCentroUrbano(this);
        while (iteratore.hasNext()) {
            if (iteratore.next() == lotto) {
                demolitore.demolisci(lotto);
                return;
            }
        }
        throw new LottoNonTrovatoException();
    }

    /**
     * Casualmente viene generato un disastro all'interno
     * del CentroUrbano.
     */
    public Disastro disastro() {
        Disastro disastro;

        Random generatore = new Random();
        int rigaSettoreCasuale = generatore.nextInt(numeroDiSettoriPerColonna);
        int rigaLottoCasuale = generatore.nextInt(numeroDiLottiPerColonna);
        int colonnaSettoreCasuale = generatore.nextInt(numeroDiSettoriPerRiga);
        int colonnaLottoCasuale = generatore.nextInt(numeroDiLottiPerRiga);

        Settore settoreSelezionato = ottieni(
                rigaSettoreCasuale,
                colonnaSettoreCasuale
        );
        Lotto lottoSelezionato = settoreSelezionato.ottieni(
                rigaLottoCasuale,
                colonnaLottoCasuale
        );

        settoreSelezionato.disastro(lottoSelezionato, disastro = generatoreDisastro.ottieniCasualmente());
        return disastro;
    }

    /**
     * Invecchia CentroUrbano.
     */
    public void invecchia() {
        IteratoreSettori iteratoreSettori = iteratoreSettori();
        while (iteratoreSettori.hasNext()) {
            iteratoreSettori.next().invecchia();
        }
        demolitore.invecchia();
    }

    /**
     * Crea il centro urbano inizializzando settori selezione lotti sulla base
     * delle informazioni con cui il centrourbano è stato instanziato.
     */
    public void crea() {
        settori = new Settore[numeroDiSettoriPerColonna][numeroDiSettoriPerRiga];
        Settore settore;
        for (int indiceRigaSettore = 0; indiceRigaSettore < numeroDiSettoriPerColonna; indiceRigaSettore++) {
            for (int indiceColonnaSettore = 0; indiceColonnaSettore < numeroDiSettoriPerRiga; indiceColonnaSettore++) {
                settore = new Settore(numeroDiLottiPerColonna, numeroDiLottiPerRiga);
                settore.crea();
                settori[indiceRigaSettore][indiceColonnaSettore] = settore;
            }
        }

        impostaLottiConfinanti();
    }

    /**
     * Imposta lotti confinanti.
     */
    private void impostaLottiConfinanti() {
        Settore settore;

        for (int indiceRigaSettore = 0; indiceRigaSettore < numeroDiSettoriPerColonna; indiceRigaSettore++) {
            for (int indiceColonnaSettore = 0; indiceColonnaSettore < numeroDiSettoriPerRiga; indiceColonnaSettore++) {
                settore = settori[indiceRigaSettore][indiceColonnaSettore];
                for (int indiceRigaLotto = 0; indiceRigaLotto < numeroDiLottiPerColonna; indiceRigaLotto++) {
                    for (int indiceColonnaLotto = 0; indiceColonnaLotto < numeroDiLottiPerRiga; indiceColonnaLotto++) {
                        impostaLottoSuperiore(
                                settore,
                                indiceRigaSettore,
                                indiceColonnaSettore,
                                indiceRigaLotto,
                                indiceColonnaLotto
                        );
                        impostaLottoInferiore(
                                settore,
                                indiceRigaSettore,
                                indiceColonnaSettore,
                                indiceRigaLotto,
                                indiceColonnaLotto
                        );
                        impostaLottoDestro(
                                settore,
                                indiceRigaSettore,
                                indiceColonnaSettore,
                                indiceRigaLotto,
                                indiceColonnaLotto
                        );
                        impostaLottoSinistro(
                                settore,
                                indiceRigaSettore,
                                indiceColonnaSettore,
                                indiceRigaLotto,
                                indiceColonnaLotto
                        );
                    }
                }
            }
        }
    }

    /**
     * Imposto il lotto superiore per il lotto e settori aventi gli indici indicati.
     *
     * @param settore
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     * @param indiceRigaLotto
     * @param indiceColonnaLotto
     */
    private void impostaLottoSuperiore(Settore settore,
                                       int indiceRigaSettore,
                                       int indiceColonnaSettore,
                                       int indiceRigaLotto,
                                       int indiceColonnaLotto) {
        // verifico che il lotto non sia nella prima riga del settore
        if (indiceRigaLotto > 0) {
            settore.ottieni(indiceRigaLotto, indiceColonnaLotto).setSopra(
                    settore.ottieni(indiceRigaLotto - 1, indiceColonnaLotto)
            );
        } else {
            // verifico che il settore non sia nella prima riga
            if (indiceRigaSettore > 0) {
                settore.ottieni(indiceRigaLotto, indiceColonnaLotto).setSopra(
                        settori[indiceRigaSettore - 1][indiceColonnaSettore]
                                .ottieni(numeroDiLottiPerColonna - 1, indiceColonnaLotto)
                );
            }
        }
    }

    /**
     * Imposto il lotto inferiore per il lotto e settori aventi gli indici indicati.
     *
     * @param settore
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     * @param indiceRigaLotto
     * @param indiceColonnaLotto
     */
    private void impostaLottoInferiore(Settore settore,
                                       int indiceRigaSettore,
                                       int indiceColonnaSettore,
                                       int indiceRigaLotto,
                                       int indiceColonnaLotto) {
        // verifico che il lotto non sia nell'ultima riga del settore
        if (indiceRigaLotto < numeroDiLottiPerColonna - 1) {
            settore.ottieni(indiceRigaLotto, indiceColonnaLotto).setSotto(
                    settore.ottieni(indiceRigaLotto + 1, indiceColonnaLotto)
            );
        } else {
            // verifico che il settore non sia nell'ultima riga
            if (indiceRigaSettore < numeroDiSettoriPerColonna - 1) {
                settore.ottieni(indiceRigaLotto, indiceColonnaLotto).setSotto(
                        settori[indiceRigaSettore + 1][indiceColonnaSettore]
                                .ottieni(0, indiceColonnaLotto)
                );
            }
        }
    }

    /**
     * Imposto il lotto destro per il lotto e settori aventi gli indici indicati.
     *
     * @param settore
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     * @param indiceRigaLotto
     * @param indiceColonnaLotto
     */
    private void impostaLottoDestro(Settore settore,
                                    int indiceRigaSettore,
                                    int indiceColonnaSettore,
                                    int indiceRigaLotto,
                                    int indiceColonnaLotto) {
        // verifico che il lotto non sia nell'ultima colonna del settore
        if (indiceColonnaLotto != numeroDiLottiPerRiga - 1) {
            settore.ottieni(indiceRigaLotto, indiceColonnaLotto).setDestra(
                    settore.ottieni(indiceRigaLotto, indiceColonnaLotto + 1)
            );
        } else {
            // verifico che il settore non sia nell'ultima colonna
            if (indiceColonnaSettore != numeroDiSettoriPerRiga - 1) {
                settore.ottieni(indiceRigaLotto, indiceColonnaLotto).setDestra(
                        settori[indiceRigaSettore][indiceColonnaSettore + 1]
                                .ottieni(indiceRigaLotto, 0)
                );
            }
        }
    }

    /**
     * Imposto il lotto sinistro per il lotto e settori aventi gli indici indicati.
     *
     * @param settore
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     * @param indiceRigaLotto
     * @param indiceColonnaLotto
     */
    private void impostaLottoSinistro(Settore settore,
                                      int indiceRigaSettore,
                                      int indiceColonnaSettore,
                                      int indiceRigaLotto,
                                      int indiceColonnaLotto) {
        // verifico che il lotto non sia nella prima colonna del settore
        if (indiceColonnaLotto != 0) {
            settore.ottieni(indiceRigaLotto, indiceColonnaLotto).setSinistra(
                    settore.ottieni(indiceRigaLotto, indiceColonnaLotto - 1)
            );
        } else {
            // verifico che il settore non sia nella prima colonna
            if (indiceColonnaSettore != 0) {
                settore.ottieni(indiceRigaLotto, indiceColonnaLotto).setSinistra(
                        settori[indiceRigaSettore][indiceColonnaSettore - 1]
                                .ottieni(indiceRigaLotto, numeroDiLottiPerRiga - 1)
                );
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Metodi di accesso
    |--------------------------------------------------------------------------
    */

    /**
     * @return true se i lotti sono sulla stessa riga, false altrimenti.
     */
    public boolean stessaRiga(int indiceRigaSettoreA, int indiceRigaLottoA, int indiceRigaSettoreB, int indiceRigaLottoB) {
        return indiceRigaLottoA == indiceRigaLottoB && indiceRigaSettoreA == indiceRigaSettoreB;
    }

    /**
     * @return true se i lotti sono sulla stessa riga, false altrimenti.
     */
    public boolean stessaColonna(int indiceColonnaSettoreA, int indiceColonnaLottoA, int indiceColonnaSettoreB, int indiceColonnaLottoB) {
        return indiceColonnaLottoA == indiceColonnaLottoB && indiceColonnaSettoreA == indiceColonnaSettoreB;
    }

    /**
     * Pre-condizioni: riga < settoriPerRiga && colonna < settoriPerColonna.
     *
     * @param riga    indice riga
     * @param colonna indice colonna
     * @return il settore di riga e colonna selezionati
     */
    public Settore ottieni(int riga, int colonna) {
        return settori[riga][colonna];
    }

    /**
     * @param lotto
     * @return settore se il lotto è stato trovato all'interno di un
     * settore, null altrimenti.
     */
    public Settore ottieniSettore(Lotto lotto) {
        IteratoreSettori iteratoreSettori = iteratoreSettori();
        while (iteratoreSettori.hasNext()) {
            Settore settore = iteratoreSettori.next();
            if (settore.contiene(lotto)) {
                return settore;
            }
        }
        return null;
    }

    /**
     * @return generatoreDisastro
     */
    public GeneratoreDisastro getGeneratoreDisastro() {
        return generatoreDisastro;
    }

    /**
     * @return settori
     */
    public int getNumeroDiLottiPerSettore() {
        return numeroDiLottiPerSettore;
    }

    /**
     * @return numeroDiSettori
     */
    public int getNumeroDiSettori() {
        return numeroDiSettori;
    }

    /**
     * @return numeroDiLottiPerRiga
     */
    public int getNumeroDiLottiPerRiga() {
        return numeroDiLottiPerRiga;
    }

    /**
     * @return numeroDiSettoriPerRiga
     */
    public int getNumeroDiSettoriPerRiga() {
        return numeroDiSettoriPerRiga;
    }

    /**
     * @return numeroDiLottiPerColonna
     */
    public int getNumeroDiLottiPerColonna() {
        return numeroDiLottiPerColonna;
    }

    /**
     * @return numeroDiSettoriPerColonna
     */
    public int getNumeroDiSettoriPerColonna() {
        return numeroDiSettoriPerColonna;
    }

    /**
     * @return numeroDiLottiPerSettore * numeroDiSettori
     */
    public int getNumeroDiLotti() {
        return numeroDiLottiPerSettore * numeroDiSettori;
    }

    /**
     * @return istanza di un IteratoreLottiCentroUrbano
     */
    public IteratoreLottiCentroUrbano iteratoreLotti() {
        return new IteratoreLottiCentroUrbano(this);
    }

    /**
     * @return istanza di un IteratoreSettori
     */
    public IteratoreSettori iteratoreSettori() {
        return new IteratoreSettori(this);
    }

    /**
     * Pre-condizione: stessaColonna(indiceRigaSettore,indiceRigaLotto,indiceRigaSettoreDestinazione,indiceRigaLottoDestinazione) == true
     *
     * @param indiceRigaSettore
     * @param indiceRigaLotto
     * @param indiceRigaSettoreDestinazione
     * @param indiceRigaLottoDestinazione
     * @param indiceColonnaSettori
     * @param indiceColonnaLotti
     * @return istanza di un IteratoreRigaDestinazione
     */
    public IteratoreColonnaDestinazione iteratoreColonnaDestinazione(int indiceRigaSettore,
                                                                     int indiceRigaLotto,
                                                                     int indiceRigaSettoreDestinazione,
                                                                     int indiceRigaLottoDestinazione,
                                                                     int indiceColonnaSettori,
                                                                     int indiceColonnaLotti) {

        return new IteratoreColonnaDestinazione(
                this,
                indiceRigaSettore,
                indiceRigaLotto,
                indiceRigaSettoreDestinazione,
                indiceRigaLottoDestinazione,
                indiceColonnaSettori,
                indiceColonnaLotti
        );
    }

    /**
     * Pre-condizione: stessaRiga(indiceColonnaSettore,indiceColonnaLotto,indiceColonnaSettoreDestinazione,indiceColonnaLottoDestinazione) == true
     *
     * @param indiceColonnaSettore
     * @param indiceColonnaLotto
     * @param indiceColonnaSettoreDestinazione
     * @param indiceColonnaLottoDestinazione
     * @param indiceRigaSettori
     * @param indiceRigaLotti
     * @return istanza di un IteratoreRigaDestinazione
     */
    public IteratoreRigaDestinazione iteratoreRigaDestinazione(int indiceColonnaSettore,
                                                               int indiceColonnaLotto,
                                                               int indiceColonnaSettoreDestinazione,
                                                               int indiceColonnaLottoDestinazione,
                                                               int indiceRigaSettori,
                                                               int indiceRigaLotti) {

        return new IteratoreRigaDestinazione(
                this,
                indiceColonnaSettore,
                indiceColonnaLotto,
                indiceColonnaSettoreDestinazione,
                indiceColonnaLottoDestinazione,
                indiceRigaSettori,
                indiceRigaLotti
        );
    }
}
