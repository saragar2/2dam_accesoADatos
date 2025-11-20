package org.educa.pool;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Clase que gestiona el pool de conexiones a la base de datos
public class ConnectionPool {

    // Constructor privado para evitar instanciación
    private ConnectionPool() {
        throw new IllegalStateException();
    }

    // Variable estática para almacenar la instancia del pool de conexiones
    private static BasicDataSource basicDataSource;

    // Método para obtener la instancia del pool de conexiones
    public static BasicDataSource getDataSource() {
        // Verifica si el pool de conexiones ya está inicializado
        if (basicDataSource == null) {
            Properties properties = new Properties();
            try (InputStream input = ConnectionPool.class.getClassLoader().getResourceAsStream("database.properties")) {
                // Carga las propiedades desde el archivo database.properties
                properties.load(input);

                basicDataSource = new BasicDataSource();
                // Configura la URL de la base de datos
                basicDataSource.setUrl(properties.getProperty("db.url"));
                // Configura el nombre de usuario para la conexión
                basicDataSource.setUsername(properties.getProperty("db.username"));
                // Configura la contraseña para la conexión
                basicDataSource.setPassword(properties.getProperty("db.password"));
                // Configura el número mínimo de conexiones inactivas
                basicDataSource.setMinIdle(Integer.parseInt(properties.getProperty("db.initialSize")));
                // Configura el número máximo de conexiones inactivas
                basicDataSource.setMaxIdle(Integer.parseInt(properties.getProperty("db.maxTotal")));

            } catch (IOException e) {
                // Manejo de excepciones en caso de error al cargar las propiedades
                throw new RuntimeException(e);
            }
        }
        return basicDataSource;
    }
}
