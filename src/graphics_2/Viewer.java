/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics_2;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author pau
 *
 *
 */
public class Viewer extends Canvas {

    private MyFrame v;
    private MyBufferedImg img1;
    private ImgFuegoBuff imgFuego;
    private MyBufferedImg bi;

    public Viewer(MyFrame v) {
        this.v = v;
    }

    public void addLed() {
        //crear un metodo privado para el algotitmo que calcule las secciones

        Led l = new Led(this.calcularSeccion());

        System.out.println("El color azul medio es: " + l.getColorMedio('b'));
        System.out.println("El color amarillo medio es: " + l.getColorMedio('g'));
        System.out.println("El color rojo medio es: " + l.getColorMedio('r'));
    }

    public void aplicarFilroConvolucion() {
       
        
    }

    public void doFadeVie(String src, int numFrames) throws InterruptedException {
        this.img1.doFade(src, numFrames);
    }

    @Override
    public void paint(Graphics g) {
        //g.drawImage(imgFuego, 0, 0, null);
    }

    @Override
    public void repaint() {
    }

    public void searchImage(String imgSrc) {

        //intenta buscar y pintar la imagen
        try {
            this.img1 = new MyBufferedImg(ImageIO.read(new File(imgSrc)), this.getGraphics());
        } catch (IOException ex) {
            System.out.println("Imagen no cargada");
        }
    }

    public void showFuego() {

        //leer y pintar imagen 
        try {
            this.bi = new MyBufferedImg(ImageIO.read(new File("img/b.jpeg")), this.getGraphics());
        
        } catch (IOException ex) {
            System.out.println("Imagen no cargada");
        }

        this.imgFuego = new ImgFuegoBuff(bi.getWidth(), bi.getHeight());

        //animacion fuego
        while (true) {
            this.imgFuego.startFuego();
            //this.imgFuego.doPaint(this.getGraphics());
            this.getGraphics().drawImage(bi, 0, 0, null);
            this.getGraphics().drawImage(this.imgFuego, 0, 1, null);

            try {
                TimeUnit.MILLISECONDS.sleep(14);

            } catch (InterruptedException ex) {
                Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    //Public methods
    public void showImage(String imgSrc, int x, int y) {

        //intenta buscar y pintar la imagen
        try {
            this.img1 = new MyBufferedImg(ImageIO.read(new File(imgSrc)), this.getGraphics());
        } catch (IOException ex) {
            System.out.println("Imagen no cargada");
        }

        this.img1.aplicarFiltroConvolucion();
        this.getGraphics().drawImage(img1, x, y, null);
        

        //this.img1.paint(this.getGraphics());
    }
    
    public void showOriginalImage(String imgSrc, int x, int y) {

        //intenta buscar y pintar la imagen
        try {
            this.img1 = new MyBufferedImg(ImageIO.read(new File(imgSrc)), this.getGraphics());
        } catch (IOException ex) {
            System.out.println("Imagen no cargada");
        }

        //this.img1.aplicarFiltroConvolucion();
        this.getGraphics().drawImage(img1, x, y, null);
        

        //this.img1.paint(this.getGraphics());
    }

    //Private methds
    private Integer[] calcularSeccion() {

        //ancho total de la imagen
        int width = this.img1.getWidth();

        //Int Array raster de la imagen
        Integer[] iaRaster = this.img1.getIaRaster();

        //lista de los valores de los colores de la sección
        Integer[] arrColores = new Integer[width * 3];

        //rellena el array con los la info de la primera fila de pxls
        for (int col = 0; col < width * 3; col++) {
            arrColores[col] = iaRaster[col];

        }

        return arrColores;

//        //lista de los valores de los colores de la sección
//        Integer[] arrayColores;
//
//        //Int Array raster de la imagen
//        Integer[] iaRaster = this.img1.getIaRaster();
//
//        //ancho total de la imagen
//        int width = this.img1.getWidth();
//
//        //altura y anchura que será pasada al obj. led
//        int altura = 1;
//        int anchura = width;
//
//        //coordenadas iniciales para aplicar altura y anchura
//        int x = 0;
//        int y = 0;
//
//        //de momento es 0
//        int filaInicial = (width * 3) * y;
//
//        //El área de la seccion es el tamaño del array de colores
//        arrayColores = new Integer[altura * (anchura * 3)];
//
//        int posInicial;
//        for (int row = filaInicial; row < (width * 3) * (y + altura); row += (width * 3)) {
//
//            posInicial = row + (x * 3); //(pos XY inicial)
//            int test = row + ((x + anchura) * 3);
//            int pasadas = 0;
//
//            for (int col = posInicial; col < test; col++) {
//
//                arrayColores[col] = iaRaster[col];
//                pasadas++;
//            }
//            System.out.println("hey");
//        }
//
//        return arrayColores;
    }
}
