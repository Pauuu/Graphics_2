/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics_2;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author pau
 */
public class MyFrame extends JFrame {

    private JPanel mainPanel;
    private Viewer viewer;
    private int width = 1100;
    private int height = 600;

    public MyFrame() {

        this.mostrarVentana();
        this.setSize(this.width, this.height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        this.viewer.showFuego();
        this.viewer.showImage("img/b.jpeg", 50, 50);
        this.viewer.showOriginalImage("img/b.jpeg", 600, 50);
//        this.viewer.showImage("img/hqdefault.jpg", 700, 100);
//        this.viewer.addLed();
//        this.viewer.doFadeVie("img/fondo5.jpeg", 1);
    }

    public MyFrame getVentana() {
        return this;
    }

    public void mostrarVentana() {

        this.mainPanel = new JPanel();
        this.addViewer();

        Container cp = this.getContentPane();
        cp.add(this.mainPanel);
    }

    public void addViewer() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        this.viewer = new Viewer(this);
        this.viewer.setSize(this.width, this.height);

        this.mainPanel.add(viewer, c);
    }

    public void addButtons() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;

        JButton buttonFade = new JButton("Recrear fundido");
        this.mainPanel.add(buttonFade, c);
    }

}
