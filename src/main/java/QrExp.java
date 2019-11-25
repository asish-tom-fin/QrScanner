import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.MultipleBarcodeReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class QrExp {
    public static void main(){
        String b64 = "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";
        File file = new File("/home/asish/Pictures/fishyQR.png");
        System.out.println(file);
        String res = "";
        try (FileOutputStream fos = new FileOutputStream(file)) {
            // To be short I use a corrupted PDF string, so make sure to use a valid one if you want to preview the PDF file
//            byte[] decoder = Base64.getDecoder().decode(b64);
//            fos.write(decoder);
//            fos.close();
            res = decodeQRCode(file);
            System.out.println("png File Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(res);

    }
    private static String decodeQRCode(File qrCodeimage) throws IOException, NotFoundException {

        BufferedImage image = ImageIO.read(qrCodeimage);
        LuminanceSource source;
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        Result[] results;
        source = new BufferedImageLuminanceSource(image);
        System.out.println(image.getHeight());
        System.out.println(image.getWidth());
        System.out.println(image.getSource());
        System.out.println(image);
        MultipleBarcodeReader reader = new GenericMultipleBarcodeReader(multiFormatReader);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            results = new Result[]{multiFormatReader.decode(bitmap)};
            return results[0].getText();
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.printf("There is no QR code in the image: %s", e.getMessage());
            return null;
        }
    }
}
