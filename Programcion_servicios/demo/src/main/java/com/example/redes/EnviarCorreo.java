package com.example.redes;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.io.File;

public class EnviarCorreo {

    public static void main(String[] args) {
        String correoDestinatario = "destinatario@example.com";
        String correoEmisor = "emisor@example.com";
        String contraseñaGlobal = "contraseña";
        String archivoPdf = "./ruta/a/archivo.pdf";
        String archivoImagen = "./ruta/a/imagen.png";

        enviarCorreoConAdjuntos(correoEmisor, contraseñaGlobal, correoDestinatario, archivoPdf, archivoImagen);
    }

    public static void enviarCorreoConAdjuntos(String correoDesde, String contraseña, String correoPara, String rutaArchivoPdf, String rutaArchivoImagen) {
        final String correoDesdeFinal = correoDesde;
        final String contraseñaFinal = contraseña;

        final Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");

        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoDesdeFinal, contraseñaFinal);
            }
        });

        try {
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(correoDesdeFinal));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoPara));
            mensaje.setSubject("Correo con adjuntos");

            MimeBodyPart cuerpoMensaje = new MimeBodyPart();
            cuerpoMensaje.setContent("Este es un correo con un archivo adjunto.", "text/html");

            Multipart partesMultiples = new MimeMultipart();
            partesMultiples.addBodyPart(cuerpoMensaje);

            MimeBodyPart archivoAdjuntoPdf = new MimeBodyPart();
            archivoAdjuntoPdf.attachFile(new File(rutaArchivoPdf));
            partesMultiples.addBodyPart(archivoAdjuntoPdf);

            MimeBodyPart archivoAdjuntoImagen = new MimeBodyPart();
            archivoAdjuntoImagen.attachFile(new File(rutaArchivoImagen));
            partesMultiples.addBodyPart(archivoAdjuntoImagen);

            mensaje.setContent(partesMultiples);
            Transport.send(mensaje);

            System.out.println("Correo enviado con éxito!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
