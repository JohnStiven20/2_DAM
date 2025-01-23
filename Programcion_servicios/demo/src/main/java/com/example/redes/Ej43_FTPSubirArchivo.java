package psp.tema4.ejemplos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Ej43_FTPSubirArchivo {
    public static void main(String[] args) {

        String server = "ftp.dlptest.com";
        int port = 21;
        String user = "dlpuser";
        String pass = "rNrKYTX9g7z3RgJRmxWuGHbeu";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            if (ftpClient.login(user, pass)) {
                System.out.println("Conexión exitosa al servidor FTP.");
            } else {
                System.out.println("Error al iniciar sesión en el servidor FTP.");
                return;
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File firstLocalFile = new File("./src/main/img/stiven.png");

            showServerReply(ftpClient);

            FTPFile[] files1 = ftpClient.listFiles("/");
            printFileDetails(files1);


            if (!firstLocalFile.exists()) {
                System.out.println("El archivo no existe: " + firstLocalFile.getAbsolutePath());
                return;
            }

            System.out.println("-------------------------------------------------------------------------------------------------------");

            String firstRemoteFile = "stiven.png";

            try (InputStream inputStream = new FileInputStream(firstLocalFile)) {
                System.out.println("Iniciando la subida del archivo...");
                boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
                if (done) {
                    System.out.println("Archivo subido exitosamente.");
                } else {
                    System.out.println("Error al subir el archivo: " + ftpClient.getReplyString());
                }
            }

            System.out.println("-------------------------------------------------------------------------------------------------------");

            FTPFile[] files2 = ftpClient.listFiles("/");
            printFileDetails(files2);


        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

  
    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            System.out.println("\n--- Respuesta del servidor ---");
            for (String reply : replies) {
                System.out.println("SERVER: " + reply);
            }
            System.out.println("------------------------------\n");
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
}
