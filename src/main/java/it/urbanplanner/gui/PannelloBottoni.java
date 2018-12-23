package it.urbanplanner.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Pannello contenente una lista di bottoni.
 */
public class PannelloBottoni extends JPanel {
    /**
     * Aggiunge un bottone assegnandogli un nome e un actionListener.
     *
     * @param nome
     * @param actionListener
     */
    public void creaBottone(String nome, ActionListener actionListener) {
        add(ottieniBottone(nome, actionListener));
    }

    /**
     * Aggiunge un bottone assegnandogli un nome, un actionListener, una larghezza
     * e un altezza.
     *
     * @param nome
     * @param actionListener
     * @param larghezza
     * @param altezza
     */
    public void creaBottone(String nome, ActionListener actionListener, int larghezza, int altezza) {
        JButton bottone = ottieniBottone(nome, actionListener);
        bottone.setPreferredSize(new Dimension(larghezza, altezza));
        add(bottone);
    }

    /**
     * Instanziato un JButton con nome e actionListener indicati.
     *
     * @param nome
     * @param actionListener
     * @return bottone instanziato
     */
    private JButton ottieniBottone(String nome, ActionListener actionListener) {
        JButton bottone = new JButton(nome);
        bottone.addActionListener(actionListener);
        add(bottone);
        return bottone;
    }
}
