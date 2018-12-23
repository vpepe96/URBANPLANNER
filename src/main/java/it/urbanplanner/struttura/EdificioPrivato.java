package it.urbanplanner.struttura;

/**
 * Astrazione di un EdificioPrivato.
 */
public class EdificioPrivato extends Edificio {
    /**
     * Rappresenta il tipo di edificio privato.
     */
    public Tipo tipo;
    /**
     * Rappresenta il valore di base dell'edificio.
     */
    private double valore;

    /**
     * Costruttore EdificioPrivato.
     *
     * @param coefficienteDiEfficienza
     * @param coefficienteDiInvecchiamento
     * @param nome
     * @param tipo
     * @param valore
     */
    public EdificioPrivato(int coefficienteDiEfficienza, int coefficienteDiInvecchiamento, String nome, Tipo tipo, double valore) {
        super(coefficienteDiEfficienza, coefficienteDiInvecchiamento, nome);
        this.tipo = tipo;
        this.valore = valore;
    }

    /**
     * Restituisce il valore di base dell'edificio privato.
     *
     * @return valore
     */
    public double getValore() {
        return valore;
    }

     /*
    |--------------------------------------------------------------------------
    | Metodi di accesso
    |--------------------------------------------------------------------------
    */

    /**
     * Restituisce il tipo di EdificioPrivato.
     *
     * @return tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * {@inheritDoc}
     */
    public EdificioPrivato clone() {
        return (EdificioPrivato) super.clone();
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object compara) {
        if (!super.equals(compara)) {
            return false;
        }
        EdificioPrivato edificioPrivato = (EdificioPrivato) compara;
        return tipo == edificioPrivato.tipo && Math.abs(valore - edificioPrivato.valore) < 1E-12;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return super.toString() + "[tipo=" + tipo + ", valore=" + valore + "]";
    }

    /**
     * Dominio del tipo di edifici privati.
     */
    public enum Tipo {
        CASA, NEGOZIO, HOTEL
    }
}
