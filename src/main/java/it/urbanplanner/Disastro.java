package it.urbanplanner;

import java.io.Serializable;

/**
 * Astrazione del concetto di Disastro.
 */
public class Disastro implements Cloneable, Serializable {
    /**
     * Percentuale di disastro sul coefficiente di efficienza del lotto selezionato (1/2 per lotti adiacenti)
     */
    private double percentualeDisastro;

    /**
     * Nome del disastro.
     */
    private String nome;

    /**
     * Costruttore di disastro.
     */
    public Disastro(String nome, double percentualeDisastro) {
        this.percentualeDisastro = percentualeDisastro;
        this.nome = nome;
    }

    /*
    |--------------------------------------------------------------------------
    | Metodi di accesso
    |--------------------------------------------------------------------------
    */

    /**
     * @return percentualeDiDisastro
     */
    public double getPercentualeDisastro() {

        return percentualeDisastro;
    }

    /**
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * {@inheritDoc}
     */
    public Disastro clone() {
        try {
            Disastro disastro = (Disastro) super.clone();
            return disastro;
        } catch (CloneNotSupportedException e) {
            // Impossibile
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object compara) {
        if (compara == null || getClass() != compara.getClass()) return false;
        Disastro disastroCompara = (Disastro) compara;
        return percentualeDisastro == disastroCompara.percentualeDisastro && nome.equals(disastroCompara.nome);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return getClass().getName() + "["
                + "nome="
                + nome
                + ", percentualeDisastro="
                + percentualeDisastro
                + "]";
    }
}
