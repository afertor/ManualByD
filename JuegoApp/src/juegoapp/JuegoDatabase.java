
package juegoapp;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

class JuegoDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/ejemplo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "45314531Ab";
    private Connection conn;

    public JuegoDatabase() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registrarJuego(String titulo, String genero, String plataforma) {
        String query = "INSERT INTO juegos (titulo, genero, plataforma) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, titulo);
            statement.setString(2, genero);
            statement.setString(3, plataforma);
            statement.executeUpdate();
            statement.close();
            JOptionPane.showMessageDialog(null, "El juego se ha registrado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar el juego.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void borrarJuego(String titulo) {
        String query = "DELETE FROM juegos WHERE titulo = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, titulo);
            int rowsAffected = statement.executeUpdate();
            statement.close();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "El juego ha sido borrado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún juego con el título especificado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al borrar el juego.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void editarJuego(String titulo, String nuevoTitulo, String genero, String plataforma) {
        String query = "UPDATE juegos SET titulo = ?, genero = ?, plataforma = ? WHERE titulo = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, nuevoTitulo);
            statement.setString(2, genero);
            statement.setString(3, plataforma);
            statement.setString(4, titulo);
            int rowsAffected = statement.executeUpdate();
            statement.close();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "El juego ha sido actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún juego con el título especificado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al editar el juego.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public DefaultTableModel getJuegosTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel();

        try {
            String consulta = "SELECT * FROM juegos";
            Statement consultaStatement = conn.createStatement();
            ResultSet resultSet = consultaStatement.executeQuery(consulta);

           
ResultSetMetaData metaData = resultSet.getMetaData();
int columnCount = metaData.getColumnCount();
        // Obtener nombres de columna
        for (int column = 1; column <= columnCount; column++) {
            tableModel.addColumn(metaData.getColumnLabel(column));
        }

        // Obtener datos de filas
        while (resultSet.next()) {
            Object[] rowData = new Object[columnCount];
            for (int column = 1; column <= columnCount; column++) {
                rowData[column - 1] = resultSet.getObject(column);
            }
            tableModel.addRow(rowData);
        }

        resultSet.close();
        consultaStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tableModel;
}
}