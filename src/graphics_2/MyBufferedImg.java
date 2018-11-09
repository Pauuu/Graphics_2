package graphics_2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class MyBufferedImg extends BufferedImage {

    private byte[] baRaster;
    private Graphics gr;
    private Integer[] iaRaster;

    public MyBufferedImg(BufferedImage img, Graphics gr) {
        super(img.getColorModel(), img.getRaster(), img.getColorModel().isAlphaPremultiplied(), null);
        this.baRaster = this.convertDataRasterToByteArray(this.getRaster());
        this.iaRaster = this.copyBaRasterToIntArray(this.baRaster);
        //this.gr = gr;
    }

    //piblic methods
    public byte[] getBaRaster() {
        return this.baRaster;
    }

    public Integer[] getIaRaster() {
        return this.copyBaRasterToIntArray(this.baRaster);
    }

    public void aplicarFiltroConvolucion() {

        //matriz convolucion
        //Crear nuevo array de misma longitud
        int[] newRastImage = new int[this.iaRaster.length];

        int totaLength = this.iaRaster.length;

        //OJO que ya está multiplicado dando la anchura "real" <===== 0J0 ======
        int totalWidth = this.getWidth() * 3;

        int pos = 0;

        for (int row = 1; row < (totaLength / (totalWidth)) - 1; row++) {

            for (int col = 3; col < totalWidth - 1; col += 3) {

                pos = (row * totalWidth) + col;

                newRastImage[pos] = this.aplicarFiltro(pos);
                newRastImage[pos + 1] = this.aplicarFiltro(pos + 1);
                newRastImage[pos + 2] = this.aplicarFiltro(pos + 2);
            }

        }

        this.updateImg(newRastImage);
    }

    public void doFade(String imgSrc, int frames) throws InterruptedException {

        //Obtener el raster de la segunda imagen
        Raster rasImg = this.getRasterImgToFade(imgSrc);

        //Obtener la diferencia de las dos iaRasters
        float[] iaDiferencia = this.calcularDiferenciaIaRasters(rasImg, frames);

        //Update raster img
        for (int i = 0; i < frames; i++) {
            Thread.sleep(100);
            this.updateImgFloat(iaDiferencia);
            //this.paint();
        }

    }

    public void paint(Graphics grr) {

        grr.drawImage(this, 800, 0, 500, 500, null);

    }

    //private methods
    private int getValuesConvolution(int[] kernel, int fila, int columna, int width) {

        int position;
        int posKernel = 0;
        int total = 0;

        for (int fil = fila - 1; fil <= fila + 1; fil++) {
            for (int col = columna - 1; col <= columna + 1; col++) {

                position = (width * fil) + col;

                if (position > 398390) {
                    System.out.println(position);
                }

                total += this.iaRaster[position] * kernel[posKernel];

                total = ((total) > 255 ? 255 : total);

                posKernel++;
            }

        }

        return total;

    }

    private byte[] convertDataRasterToByteArray(Raster ras) {

        byte[] baDataRasterSource;

        baDataRasterSource = ((DataBufferByte) ras.getDataBuffer()).getData();
        return baDataRasterSource;
    }

    private byte[] copyFloatArrayToByteArray(float[] fiSource) {

        byte[] isCopy = new byte[fiSource.length];

        for (int i = 0; i < fiSource.length; i++) {
            isCopy[i] = (byte) fiSource[i];
        }

        return isCopy;
    }

    private byte[] convertIntRasterToByteRaster(int[] iaRasterOriginal) {
        byte[] ibRasterCopy = new byte[iaRasterOriginal.length];

        for (int i = 0; i < iaRasterOriginal.length; i++) {
            ibRasterCopy[i] = (byte) iaRasterOriginal[i];
        }

        return ibRasterCopy;
    }

    private float[] calcularDiferenciaIaRasters(Raster rasImg, int numFrames) {

        Integer[] iaRasterOriginal = this.copyDataRasterToIntArray(rasImg);
        Integer[] iaRasterToFade = this.copyDataRasterToIntArray(this.getRaster());

        float[] iaDiferencia = new float[iaRasterToFade.length];

        for (int pos = 0; pos < iaDiferencia.length; pos++) {
            iaDiferencia[pos] = (iaRasterOriginal[pos] - iaRasterToFade[pos]) / numFrames;
        }

        return iaDiferencia;
    }

    private int aplicarFiltro(int pos) {

        //store the value of each color
        int bgrValue = 0;

        //kernel
        int[] filtro = {
            0, 0, 0,
            0, 1, 0,
            0, 0, 0
        };

        int[] filtro1 = {
            1, 0, -1,
            0, 0, 0,
            -1, 0, 1
        };

        int posFilter = 0;

        for (int i = (-1); i <= 1; i++) {
            for (int j = (-1); j <= 1; j++) {
                //pos + (totalWitdh * i) + (j * j);

                bgrValue += this.iaRaster[pos + (((this.getWidth() - 1) * 3) * i) + (j * 3)]
                        * filtro1[posFilter];

                posFilter++;
            }
        }

        // corregir correccionde color
        if (bgrValue < 0) {
            bgrValue = 0;

        } else if (bgrValue > 255) {
            bgrValue = 255;
        }

        return bgrValue;
    }

    private Integer[] copyBaRasterToIntArray(byte[] baRaster) {
        Integer[] iaRasterCopy = new Integer[baRaster.length];

        for (int i = 0; i < baRaster.length; i++) {
            iaRasterCopy[i] = Byte.toUnsignedInt(baRaster[i]);
        }

        return iaRasterCopy;
    }

    private Integer[] copyDataRasterToIntArray(Raster ras) {

        byte[] baDataRasterSource;
        Integer[] iaRaster;

        baDataRasterSource = ((DataBufferByte) ras.getDataBuffer()).getData();
        iaRaster = new Integer[baDataRasterSource.length];

        for (int i = 0; i < baDataRasterSource.length; i++) {
            iaRaster[i] = Byte.toUnsignedInt(baDataRasterSource[i]);
        }

        return iaRaster;
    }

    private Raster getRasterImgToFade(String imgSrc) {

        Raster ras;
        try {
            ras = ImageIO.read(new File(imgSrc)).getRaster();

        } catch (IOException ex) {
            Logger.getLogger(MyBufferedImg.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            //lanzar excepción
        }

        return ras;
    }

    private void updateImg(int[] inputIaRaster) {
        byte[] newBaRaster = this.convertIntRasterToByteRaster(inputIaRaster);

        for (int i = 0; i < newBaRaster.length; i++) {
            this.baRaster[i] = newBaRaster[i];
        }

    }

    private void updateImgFloat(float[] faDiferencia) {
        byte[] baRasterDiferencia = this.copyFloatArrayToByteArray(faDiferencia);

        for (int i = 0; i < baRaster.length; i++) {
            this.baRaster[i] += baRasterDiferencia[i];
        }

    }

}
