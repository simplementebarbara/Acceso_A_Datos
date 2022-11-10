

package com.mycompany.tema4conectores;

/**
 *
 * @author 34686
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionMysql {
        /*
    Definimos e inicializamos constantes:
    DRIVER. Nombre del driver que vamos a utilizar. En este caso de MYSQL
    URL_CONEXION. equipo:puerto/basededatos
    
        */
        private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
        private static final String URL_CONEXION = "jdbc:mysql://localhost:3306/prueba";
        
        public static void main(String args[]) throws SQLException {

            final String usuario = "root";
            final String password = "mysql2022";
            Connection dbConnection = null;
            Statement statement = null;
            Statement statement2 = null;
            try {
                //Registramos el driver de mysql
                Class.forName(DRIVER);
                //Clase DriverManager gestiona todos los drivers que tenemos en nuestra aplicación
                //Devuelve un objeto conexión que utilizaremos para conectarnos a la base de datos
                Connection conn = DriverManager.getConnection(URL_CONEXION, usuario, password);
                //Guardamos en una variable la consulta
                String selectTableSQL = "SELECT ID,USERNAME,PASSWORD,NOMBRE FROM usuarios";  
                // ---- CONSULTA CON WHERE 
                //String where = " WHERE NOMBRE = 'Luis'";
                //String selectTableSQL = "SELECT ID,USERNAME,PASSWORD,NOMBRE FROM usuarios" + where;
                
                //Ejecutamos el método createStatement y creamos un objeto statement
                //que nos va a permitir ejecutar consultas
                statement = conn.createStatement();
                //executeQuery(String). Ejecutamos la consulta y nos devuelve un Resultset (cursor)
                ResultSet rs = statement.executeQuery(selectTableSQL);
                
                //Recorremos el resultset y mostramos la información de las columnas
                while (rs.next()) {
                    String id = rs.getString("ID");
                    String usr = rs.getString("USERNAME");
                    String psw = rs.getString("PASSWORD");
                    String nombre = rs.getString("NOMBRE");
                    System.out.println("userid : " + id);
                    System.out.println("usr : " + usr);
                    System.out.println("psw : " + psw);
                    System.out.println("nombre : " + nombre);
                }
                
                System.out.println("    ----- JOIN -------  ");
                String joinTableSQL = "SELECT u.ID,u.USERNAME,m.NOMBRE FROM usuarios u JOIN  modulos m ON u.id = m.id_usuario";
                //statement2 = conn.createStatement();
                ResultSet rsJoin = statement.executeQuery(joinTableSQL);
                //Recorremos el resultset y mostramos la información de las columnas
                while (rsJoin.next()) {
                    String id_usuario = rsJoin.getString("ID");
                    String usr_username = rsJoin.getString("USERNAME");
                    String nombre_modulo = rsJoin.getString("NOMBRE");
              
                    System.out.println("userid : " + id_usuario);
                    System.out.println("usr : " + usr_username);
                    System.out.println("nombre_modulo : " + nombre_modulo);
                
                }
            
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            }
    }   
}

