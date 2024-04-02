package com.library.binhson.userservice.ultils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.library.binhson.userservice.entity.User;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class QRUtils {
    public final  static  String CHARSET = "UTF-8"; // Charset cho dữ liệu
    public final  static int WIDTH = 300; // Chiều rộng của hình ảnh mã vạch
    public final  static int HEIGHT = 300; // Chiều cao của hình ảnh mã vạch
    public static byte[] generateQRCode(User saveUser) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(new String(saveUser.getId().getBytes(CHARSET), CHARSET), BarcodeFormat.QR_CODE, WIDTH, HEIGHT);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Ghi BitMatrix ra luồng đệm dưới dạng hình ảnh PNG
            MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

            return outputStream.toByteArray();
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Bug happen on creating of QR Code.");
        }

    }
}
