package psp.tema4.ejemplos;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;

public class Ac4_FTPDescargarFichero {

    private static final String SERVER = "ftp.dlptest.com";
    private static final int PORT = 21;
    private static final String USER = "dlpuser";
    private static final String PASSWORD = "rNrKYTX9g7z3RgJRmxWuGHbeu";

    public static void main(String[] args) {

        try {

            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USER, PASSWORD);

            System.out.println("Conectado al servidor: " + SERVER);

            uploadFile(ftpClient, "./src/main/img/stiven.png");
            System.out.println("-----------------------------------------------------------------------------------");
            printFileDetails(ftpClient.listFiles());

            downloadFile(ftpClient, "stiven.png", "./stiven2.png");

            ftpClient.logout();
            ftpClient.disconnect();

            System.out.println("Desconectado del servidor.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void uploadFile(FTPClient ftpClient, String localFilePath) throws IOException {
        File localFile = new File(localFilePath);
        try (FileInputStream fis = new FileInputStream(localFile)) {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            boolean uploaded = ftpClient.storeFile(localFile.getName(), fis);
            if (uploaded) {
                System.out.println("Archivo subido exitosamente: " + localFile.getName());
            } else {
                System.out.println("Error al subir el archivo.");
            }
        }
    }

    private static void printFileDetails(FTPFile[] files) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashSet<String> seenFiles = new HashSet<>();

        System.out.printf("%-40s %-15s %-20s%n", "Nombre", "Tamaño (bytes)", "Última modificación");
        System.out.println("-----------------------------------------------------------------------------------");

        for (FTPFile file : files) {
            String fileName = file.isDirectory() ? "[" + file.getName() + "]" : file.getName();

            if (seenFiles.contains(fileName)) {
                continue;
            }
            seenFiles.add(fileName);

            String fileSize = file.isDirectory() ? "-" : String.format("%,d", file.getSize());
            String lastModified = dateFormatter.format(file.getTimestamp().getTime());

            System.out.printf("%-40s %-15s %-20s%n", fileName, fileSize, lastModified);
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    private static void downloadFile(FTPClient ftpClient, String remoteFileName, String localFilePath)
            throws IOException {
        File localFile = new File(localFilePath);
        try (FileOutputStream fos = new FileOutputStream(localFile)) {
            ftpClient.enterLocalPassiveMode();
            boolean downloaded = ftpClient.retrieveFile(remoteFileName, fos);
            if (downloaded) {
                System.out.println("Archivo descargado exitosamente: " + localFilePath);
            } else {
                System.out.println("Error al descargar el archivo.");
            }
        }
    }
}
