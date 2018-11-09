package graphics_2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.Color;

/**
 *
 * @author pau
 */
public class ImgFuegoBuff extends BufferedImage {

    private byte[] baRaster;
    private Integer[] iaRaster;

    //256 colores --> 0.B  1.G  2.R
    private ColorP[] paCo = new ColorP[256];
    private int[][] arrIntensidades;

    public ImgFuegoBuff(int with, int height) {
        super(with, height, TYPE_4BYTE_ABGR);

        //Height -> altura //width -> anchura
        this.arrIntensidades = new int[height][with];
        this.baRaster = this.convertDataRasterToByteArray(this.getRaster());
        this.iaRaster = this.copyBaRasterToIntArray(this.baRaster);

        this.rellenarPaletaColores();
    }

    public void startFuego() {
        this.generateSparks(0.2D);
        this.computeFlameIntensities();
        this.dibujarFuego();
    }

    private byte[] convertDataRasterToByteArray(Raster ras) {

        byte[] baDataRasterSource;

        baDataRasterSource = ((DataBufferByte) ras.getDataBuffer()).getData();
        return baDataRasterSource;
    }

    private Integer[] copyBaRasterToIntArray(byte[] baRaster) {
        Integer[] iaRasterCopy = new Integer[baRaster.length];

        for (int i = 0; i < baRaster.length; i++) {
            iaRasterCopy[i] = Byte.toUnsignedInt(baRaster[i]);
        }

        return iaRasterCopy;
    }

    private void computeFlameIntensities() {
        for (int fil = this.arrIntensidades.length - 2; fil > 0; fil--) {
            for (int col = 1; col < this.arrIntensidades[fil].length - 1; col++) {

                //calcula la itensidad media de cada casilla
                this.arrIntensidades[fil][col] = (int) (((this.arrIntensidades[fil][col]
                        + this.arrIntensidades[fil + 1][col]
                        + this.arrIntensidades[fil + 1][col - 1]
                        + this.arrIntensidades[fil + 1][col + 1]) / 4.92D) - 1.7D);

                if (this.arrIntensidades[fil][col] > 255) {
                    this.arrIntensidades[fil][col] = 255;
                }

                if (this.arrIntensidades[fil][col] < 0) {
                    this.arrIntensidades[fil][col] = 0;
                }
            }

        }

    }

    private void dibujarFuego() {
        int rejillaWidth = this.arrIntensidades.length;

        for (int fil = 0; fil < this.arrIntensidades.length; fil++) {
            for (int col = 0; col < this.arrIntensidades[fil].length; col++) {

                //int intColor = this.paCo[this.arrIntensidades[fil][col]];
                int intensidad = this.arrIntensidades[fil][col];

                int canalAlpha = this.paCo[intensidad].getA();
                int colorB = this.paCo[intensidad].getB();
                int colorG = this.paCo[intensidad].getG();
                int colorR = this.paCo[intensidad].getR();

                this.iaRaster[((fil * rejillaWidth) * 4 + col * 4)] = canalAlpha;
                this.iaRaster[((fil * rejillaWidth) * 4 + col * 4) + 1] = colorB;
                this.iaRaster[((fil * rejillaWidth) * 4 + col * 4) + 2] = colorG;
                this.iaRaster[((fil * rejillaWidth) * 4 + col * 4) + 3] = colorR;

            }

        }

        this.updateIaRaster(iaRaster);

    }

    private void generateSparks(double combustible) {

        for (int col = 0; col < arrIntensidades[0].length; col++) {
            double temperatura = Math.random();

            if (temperatura >= 0.45D) {
                this.arrIntensidades[col][col] = 0;
            } 

        }
        
        

//        for (int col = 200; col < arrIntensidades[0].length - 200; col++) {
//            double temperatura = Math.random();
//
//            if (temperatura >= combustible) { //0.88D
//                this.arrIntensidades[this.getHeight() - 1][col] = 255;
//            } 
//
//        }

        //Generate random sparks
        for (int col = 0; col < arrIntensidades[0].length; col++) {
            double temperatura = Math.random();

            if (temperatura >= combustible + 0.02) { //0.9D
                int test = (int) ((this.getHeight() - 1) - (col * (col - 100)));
                
                if (test > (this.getHeight() - 1)){
                    test = (this.getHeight() - 1);
                } else if (test < 0){
                    test = 255;
                }
                
                this.arrIntensidades[test][col] = 255;
            } 
        }
        
        

//        for (int col = 100; col < arrIntensidades[0].length - 100; col++) {
//            double temperatura = Math.random();
//
//            if (temperatura >= 0.568D) {
//                this.arrIntensidades[this.getHeight() - 1][col] = 255;
//            } else {
//                this.arrIntensidades[this.getHeight() - 1][col] = 0;
//            }
//        }
    }

    private void rellenarPaletaColores() {

//        for (int i = 200; i < 256; i++) {
//            this.paCo[i] = new ColorP(255, 255, 255);
//        }
//        
//        for (int i = 180; i < 200; i++) {
//            this.paCo[i] = new ColorP(4, 100, 255);
//        }
//        
//        for (int i = 140; i < 180; i++) {
//            this.paCo[i] = new ColorP(4, 70, 255);
//        }
//        
//        for (int i = 100; i < 140; i++) {
//            this.paCo[i] = new ColorP(0, 70, 130);
//        }
//        
//        for (int i = 0; i < 100; i++) {
//            this.paCo[i] = new ColorP(0, 0, 0);
//        }
        //Paleta de Bernat
        for (int i = 255; i >= 0; i--) {

            if (i < 256 && i >= 200) {
                this.paCo[i] = new ColorP(255, 255, 255, 255);
            }

            for (int j = 255; i < 200 && i >= 170; i--, j -= 6) {
                this.paCo[i] = new ColorP(255, 255, 255, j);
            }
            if (i >= 150 && i < 170) {
                this.paCo[i] = new ColorP(255, 0, 0, 250);
            }
            for (int j = 255; i < 170 && i >= 150; i--, j -= 2) {
                this.paCo[i] = new ColorP(255, 0, j, 255);
            }
            if (i >= 130 && i < 150) {
                this.paCo[i] = new ColorP(255, 0, 255, 255);
            }
            for (int j = 200; i > 100 && i < 130; i--, j -= 4) {
                this.paCo[i] = new ColorP(255, 0, j, 255);
            }
            for (int j = 255; i <= 100 && i > 20; i--, j -= 2) {
                this.paCo[i] = new ColorP(255, 0, 0, j);
            }
            for (int j = 100; i <= 20 && i > 0; i--, j -= 3) {
                this.paCo[i] = new ColorP(255, 0, 0, j);
            }
            if (i == 0) {
                this.paCo[i] = new ColorP(0, 0, 0, 0);
            }
        }
    }

    private void updateIaRaster(Integer[] iaRaster) {

        for (int i = 0; i < iaRaster.length; i++) {
            this.baRaster[i] = iaRaster[i].byteValue();

        }
    }

    public void doPaint(Graphics gg) {
        //gg.drawImage(this, 0, 0, null);
    }

}

class ColorP {

    int a, b, g, r;

    public ColorP(int a, int b, int g, int r) {
        this.a = a;
        this.b = b;
        this.g = g;
        this.r = r;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }

    public int getR() {
        return r;
    }

}

/*
    //por defecto todos la lista está a 0 (Color negro)
    public void crearChispas() {

        //colorea pixles random a lo largo de la imagen
        for (int i = 0; i < this.rejillaIntensidades[0].length; i++) {

            //numeros aleatorios entre 0 y 1
            int b = (int) (Math.random() * 2);

            //si 1 pintar, negro
            if (b == 1) {
                //la ultima fila, posicion "i"
                this.rejillaIntensidades[this.getHeight() - 1][i] = 0;

                //si no, pintar blanco
            } else {
                //la ultima fila, posicion "i"
                this.rejillaIntensidades[this.getHeight() - 1][i] = 255;
            }
        }

        //pasa de la rejilla bidimensional a la lista unidimensional 
        for (int fil = 0; fil < this.rejillaIntensidades.length; fil++) {
            for (int col = 0; col < this.rejillaIntensidades[fil].length; col++) {

                int intColor = paletaColores[this.rejillaIntensidades[fil][col]];
                int rejillaWidth = this.rejillaIntensidades.length;

                this.iaRaster[((fil * rejillaWidth) * 3 + col * 3)] = intColor;
                this.iaRaster[((fil * rejillaWidth) * 3 + col * 3) + 1] = intColor;
                this.iaRaster[((fil * rejillaWidth) * 3 + col * 3) + 2] = intColor;

            }

        }

        this.updateIaRaster(iaRaster);
    }

    private void calcularIntensidades() {
        for (int fil = 0; fil < this.rejillaIntensidades.length - 3; fil++) {
            for (int col = 0; col < this.rejillaIntensidades[0].length - 3; col++) {
                this.rejillaIntensidades[fil][col] = (this.rejillaIntensidades[fil + 1][col]
                        + this.rejillaIntensidades[fil + 1][col + 1]
                        + this.rejillaIntensidades[fil + 1][col - 1]);
            }

        }

    }

    private void calcularBase() {

        //colorea pixles random a lo largo de la imagen
        for (int i = 0; i < this.rejillaIntensidades[0].length; i++) {

            //numeros aleatorios entre 0 y 1
            int b = (int) (Math.random() * 2);

            //si 1 pintar, negro
            if (b == 1) {
                //la ultima fila, posicion "i"
                this.rejillaIntensidades[this.getHeight() - 1][i] = 0;

                //si no, pintar blanco
            } else {
                //la ultima fila, posicion "i"
                this.rejillaIntensidades[this.getHeight() - 1][i] = 255;
            }
        }
    }
    
    private void rellenarPaleta() {

        for (int i = 0; i < this.paletaColores.length; i += 3) {

            this.paletaColores[i] = i;
            this.paletaColores[i + 1] = i;
            this.paletaColores[i + 2] = i;

        }
    }
 */
