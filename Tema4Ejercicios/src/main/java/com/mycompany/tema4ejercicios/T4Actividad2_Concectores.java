

package com.mycompany.tema4ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 34686
 */
public class T4Actividad2_Concectores {

     /*Definimos e inicializamos constantes:
    DRIVER. Nombre del driver que vamos a utilizar. En este caso de MYSQL
    URL_CONEXION. equipo:puerto/basededatos    
        */
        private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
        private static final String URL_CONEXION = "jdbc:mysql://localhost:3306/PRUEBA2";
        
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
                String selectTableSQL = "SELECT ID,nombre,apellidos,edad FROM empleado";  
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
                    String id = rs.getString("id");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String edad = rs.getString("edad");
                    System.out.println("ID : " + id);
                    System.out.println("nombre : " + nombre);
                    System.out.println("apellidos : " + apellidos);
                    System.out.println("edad : " + edad);
                }
                
                System.out.println("    ----- JOIN -------  ");
                String joinTableSQL = """
                                      SELECT 
                                          d.nombre as nombreDepartamento, e.nombre as NombreEmpleado, e.apellidos
                                      FROM
                                          departamento d
                                              INNER JOIN
                                          empleado e ON d.ID = e.id_departamento
                                      GROUP BY e.nombre , e.apellidos , d.nombre;""";
                
                //statement2 = conn.createStatement();
                ResultSet rsJoin = statement.executeQuery(joinTableSQL);
                //Recorremos el resultset y mostramos la información de las columnas
                while (rsJoin.next()) {
                    String nombreDepartamento = rsJoin.getString("nombreDepartamento");
                    String nombreEmpleado = rsJoin.getString("NombreEmpleado");
                    String apellidos = rsJoin.getString("apellidos");
              
                    System.out.println("Departamento : " + nombreDepartamento);
                    System.out.println("Nombre Empleado : " + nombreEmpleado);
                    System.out.println("Apellidos : " + apellidos);
                
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