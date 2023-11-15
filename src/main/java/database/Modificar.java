package database;

import java.sql.*;

public class Modificar {

    public static void modificar() {
        // Establecer conexión a la base de datos
        /*Connection connection = ConexionMySQL.conectar();

        Clases.LeerDatos.mostrarEnPantalla("Elige entre una de las siguientes tablas : ");
        // Pedir al usuario que proporcione el nombre de la tabla
        String tableName = Clases.LeerDatos.pideCadena("Ingrese el nombre de la tabla que deseas modificar: ");
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet rs = meta.getColumns(null, null, tableName, null);
        System.out.println("Columnas de la tabla " + tableName + ":");
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            System.out.println(columnName);
        }

        // Pedir al usuario que proporcione el nombre de la columna
        String columnName = Clases.LeerDatos.pideCadena("Ingrese el nombre de la columna que deseas modificar: ");

        // Pedir al usuario que proporcione el valor de la columna
        String columnValue = Clases.LeerDatos.pideCadena("Ingrese el valor de la columna que deseas modificar: ");

        // Pedir al usuario que proporcione el nombre de la columna a modificar
        String newColumnName = columnName;

        // Pedir al usuario que proporcione el nuevo valor de la columna
        String newColumnValue = Clases.LeerDatos.pideCadena("Ingrese el nuevo valor de la columna: ");

        // Crear la sentencia SQL con el nombre de la tabla y los datos a insertar
        String sql = "UPDATE " + tableName + " SET " + newColumnName + " = '" + newColumnValue + "' WHERE " + columnName + " = '" + columnValue + "'";

        // Crear un prepared statement para ejecutar la sentencia SQL
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // Ejecutar la sentencia SQL
        int rowsUpdated = preparedStatement.executeUpdate();
        System.out.println(rowsUpdated + " fila(s) actualizada(s) en la tabla " + tableName);

        // Cerrar la conexión y liberar recursos
        preparedStatement.close();
        connection.close();*/
    }
}
