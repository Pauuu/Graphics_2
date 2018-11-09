package graphics_2;

import java.awt.image.Raster;

public class FiltroConvolucion {

    private int[] kernelConvolucion;
    private Integer[] iaRaster;

    public FiltroConvolucion(Integer[] iaRaster) {
        this.iaRaster = iaRaster;
    }

    //PUBLIC
    public int aplicarFiltro(int fil, int col, int[] kernel) {
        int valor;
        int[] pxl = new int[9];

        //desde esquina superior izquierda hasta esquina inferior derecha
        for (fil--; fil < fil + 2; fil++) {
            for (col--; col < col + 2; col++) {
                //valor += pxl[width * fil];
            }

        }

        return 0;
    }

    //PRIVATE
    private int[] copyArray(int[] arrayOrigin) {
        int[] newArray = new int[arrayOrigin.length];

        for (int pos = 0; pos < newArray.length; pos++) {

        }

        return newArray;
    }

}
