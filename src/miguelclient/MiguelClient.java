package miguelclient;

import com.mysql.jdbc.Connection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Miguel Angel Flores Sirvent
 */
public class MiguelClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String servidor = "";
        String puerto = "";
        String nombreBD = "";
        String usuarioBD = "";
        String password = "";

        File archivo = null;

        FileReader fr = null;

        BufferedReader br = null;

        try {

            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("confi.txt");

            fr = new FileReader(archivo);

            br = new BufferedReader(fr);

            // Lectura del fichero
            System.out.println("Leyendo el archivo de configuracion");

            String linea;
            int cont = 0;

            while ((linea = br.readLine()) != null) {
                linea = linea.replaceAll(" ", "");
                
                if(cont == 0){
                    servidor = linea;
                }
                if(cont == 1){
                    puerto = linea;
                }
                if(cont == 2){
                    nombreBD = linea;
                }
                if(cont == 3){
                    usuarioBD = linea;
                }
                if(cont == 4){
                    password = linea;
                }
                
                cont++;
            }

        } catch (Exception ex) {

            System.out.println(ex.getMessage());

        } finally {

            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {

                if (null != fr) {

                    fr.close();

                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        
        servidor = servidor.substring(servidor.indexOf("=")+1, servidor.length());
        puerto = puerto.substring(puerto.indexOf("=")+1, puerto.length());
        nombreBD = nombreBD.substring(nombreBD.indexOf("=")+1, nombreBD.length());
        usuarioBD = usuarioBD.substring(usuarioBD.indexOf("=")+1, usuarioBD.length());
        password = password.substring(password.indexOf("=")+1, password.length());
        
        
        String urlBD = "jdbc:mysql://"+ servidor +":"+ puerto +"/"+nombreBD;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.195.132:3306/sirvent", "sirvent", "sirvent1234");
            Connection con = (Connection) DriverManager.getConnection(urlBD, usuarioBD, password);
            String sql = "SELECT * from ip";
            PreparedStatement smt = con.prepareStatement(sql);
            ResultSet rs = smt.executeQuery();

            while (rs.next()) {
                System.out.println("Tu ip remota es: " + rs.getString("ip"));
            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
