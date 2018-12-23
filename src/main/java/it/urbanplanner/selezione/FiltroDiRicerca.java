package it.urbanplanner.selezione;

/**
 * Quest'interfaccia permette di filtrare un elemento
 * verificando la condizione espressa.
 *
 * @param <T>
 */
public interface FiltroDiRicerca<T> {
    /**
     * @param oggettoDaFiltrare
     * @return true se la condizione espressa risulta vera, false altrimenti.
     */
    boolean filtra(T oggettoDaFiltrare);
}
