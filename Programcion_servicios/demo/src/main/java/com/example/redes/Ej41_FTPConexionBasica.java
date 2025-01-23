package psp.tema4.ejemplos;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

/**
 * 
 * @author https://lineadecodigo.com/java/conectarse-a-un-ftp-con-java/
 */
public class Ej41_FTPConexionBasica {
	public static void main(String[] args) {
		FTPClient client = new FTPClient();
		
		String sFTP = "ftp.dlptest.com";
		String sUser = "dlpuser";
		String sPassword = "rNrKYTX9g7z3RgJRmxWuGHbeu";
				
		try {
		  client.connect(sFTP);
		  System.out.println("Cliente conectado");
		  System.out.println(client.printWorkingDirectory());
		 } catch (IOException ioe) {
			ioe.printStackTrace();
		}

		if (client.isConnected()) {
			try {
				//client.logout();
				client.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
