package it.urbanplanner.struttura;

/**
 * Astrazione di un EdificioPubblico.
 */
public class EdificioPubblico extends Edificio {
    /**
     * Rappresenta il tipo di edificio pubblico.
     */
    public Tipo tipo;

    /**
     * Costruttore EdificioPubblico.
     *
     * @param coefficienteDiEfficienza
     * @param coefficienteDiInvecchiamento
     * @param nome
     * @param tipo
     */
    public EdificioPubblico(int coefficienteDiEfficienza, int coefficienteDiInvecchiamento, String nome, Tipo tipo) {
        super(coefficienteDiEfficienza, coefficienteDiInvecchiamento, nome);
        this.tipo = tipo;
    }

    /**
     * {@inheritDoc}
     */
    public int getDurataDemolizione() {
        return 2;
    }

     /*
    |--------------------------------------------------------------------------
    | Metodi di accesso
    |--------------------------------------------------------------------------
    */

    /**
     * Restituisce il tipo di EdificioPubblico.
     *
     * @return tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * {@inheritDoc}
     */
    public EdificioPubblico clone() {
        return (EdificioPubblico) super.clone();
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object compara) {
        if (!super.equals(compara)) {
            return false;
        }
        EdificioPubblico edificioPubblico = (EdificioPubblico) compara;
        return tipo == edificioPubblico.tipo;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return super.toString() + "[tipo=" + tipo + "]";
    }

    /**
     * Dominio del tipo di edifici pubblici.
     */
    public enum Tipo {
        SCUOLA, MUNICIPIO, BANCA, OSPEDALE
    }
}
