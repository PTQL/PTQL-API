package com.sebasgoy.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.springframework.stereotype.Service;

@Service
public class qrCodeService {

    public static void generateQRCode(String text, String filePath) throws IOException, WriterException {
        int width = 350;
        int height = 350;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        System.out.println("Iniciando proceso de conversión");
        // Obtenemos la ruta del directorio
        File directorio = new File(filePath).getParentFile();
        // Verificamos si el directorio existe, si no, lo creamos
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado exitosamente: " + directorio.getAbsolutePath());
            } else {
                System.out.println("Error al crear el directorio: " + directorio.getAbsolutePath());
                return; // Salimos del método si no se pudo crear el directorio
            }
        }
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Proceso de conversión finalizado");
    }

}
