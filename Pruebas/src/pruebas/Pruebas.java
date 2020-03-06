
package pruebas;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Pruebas {

    public static void main(String arg[]) throws TwitterException, IOException {

        String moreLikesTweet = null;
        String moreLikesUser = null;
        int likes = 0;
        ConfigurationBuilder cb = new ConfigurationBuilder();//Llama el constructo y crea la instancia
        cb.setDebugEnabled(true)//Metodo, deja activado el debug que el codigo que agarre
                .setOAuthConsumerKey("CtucpTyxuxaEevSWvZOJmmcGP")//Parte del codigo
                .setOAuthConsumerSecret("ffZYTPfiTBy2ZHizi25tVC018UNqrLN8LpNWqfikk5HW6t6aM3")
                .setOAuthAccessToken("537465175-WU4BnmxgiAS8H6t3xCbNhCycL4x6a68zowtZlQdD")//Una vez para copiar y guardar, sirven para que tengan los parametros para hacer conexion con API,S
                .setOAuthAccessTokenSecret("wk4FYOg9fXUVlgGn4XeE8EuxovBspTZyx6LMzeP5DG9HT");
        TwitterFactory tf = new TwitterFactory(cb.build());//Instala la conexion.
        Twitter twitter = tf.getInstance();//Clase que crea un objeto, y crea la conexion por defecto

//Extraer el timeline de un usuario.
        List<Status> statuses = twitter.getUserTimeline("udistrital", new Paging(1, 40));//Lista de objetos tipo statics,llama a la clase twitter para mirar lo que la persona ha posteado en un rango
        System.out.println("Showing home timeline.");
        for (Status status : statuses) {
            if (!status.isRetweet()) {
            System.out.println(status.getUser().getName() + ":" +//imprima el usuario, nombre
                    status.getText());//cuerpo
            System.out.println("Likes: " + status.getRetweetCount());//Imprima el conteo de retweets
            
            if (status.getRetweetCount() > likes) {//ahora ese es el usuario que tiene mayor likes
                moreLikesTweet = status.getText();//cuerpo
                moreLikesUser = status.getUser().getName();//usuario
                likes = status.getFavoriteCount();//corazones,likes
            }
            }
        }
final String username = "lclopezp@correo.udistrital.edu.co";
        final String password = "pepyelkglvzdpqds";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("camila.lopez.pardo@gmail.com,bryant.ortega1010@gmail.com")
            );
            message.setSubject("El twitte con mas likes es:");
            
            message.setText(moreLikesTweet+moreLikesUser);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }       
    }
}
