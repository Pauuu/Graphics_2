/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics_2;

/**
 *
 * @author pau
 */
public class Led {

    private Integer[] iaColores;

    public Led(Integer[] iaColores) {
        this.iaColores = iaColores;
    }

    public Integer getColorMedio(char color) {

        //define por que color empezar
       int posInicial = 0;
        switch (color) {
            case 'b':
                posInicial = 0;
                break;
                
            case 'g':
                posInicial = 1;
                break;
                
            case 'r':
                posInicial = 2;
                break;
             
            default:
                System.out.println("se ha calculado el color azul por defecto");
        }

        //cuan largo es la seccion para calcular
        int totalIaRaster = this.iaColores.length;
        int iColorMedio = 0;

        for (int pos = posInicial; pos < totalIaRaster; pos += 3) {
            iColorMedio += this.iaColores[pos];
        }

        iColorMedio /= (totalIaRaster / 3);

        return iColorMedio;
    }

//    public Integer[] getColorMedio(int bgr) {
//
//        int totalPxls;
//        int iColorMedioB;
//        int iColorMedioG;
//        int iColorMedioR;
//        Integer[] bgrMedio = new Integer[3];
//
//        iColorMedioB = 0; //variable might not have been initialized
//        iColorMedioG = 0;
//        iColorMedioR = 0;
//
//        totalPxls = this.iaColores.length;
//
//        for (int pos = 0; pos < totalPxls; pos += 3) {
//
//            iColorMedioB += this.iaColores[pos]; //suma el total para luego dividir y hacer la media
//            iColorMedioG += this.iaColores[pos + 1];
//            iColorMedioR += this.iaColores[pos + 2];
//        }
//
//        iColorMedioB /= (totalPxls / 3);    //calcula el valor medio
//        iColorMedioG /= (totalPxls / 3);
//        iColorMedioR /= (totalPxls / 3);
//
//        bgrMedio[0] = iColorMedioB;
//        bgrMedio[1] = iColorMedioG;
//        bgrMedio[2] = iColorMedioR;
//
//        return bgrMedio;
//    }
}
