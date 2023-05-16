package juegoapp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;



class JuegoFrame extends JFrame {
private JTextField txtTitulo;
private JTextField txtGenero;
private JTextField txtPlataforma;
private JButton btnRegistrar;
private JButton btnBorrar;
private JButton btnEditar;
private JTable tableJuegos;
private JScrollPane scrollPane;
private JuegoDatabase juegoDatabase;

public JuegoFrame() {
    juegoDatabase = new JuegoDatabase();
    initialize();
    setLocationRelativeTo(this);
}


private void initialize() {
    setTitle("Registro de Juegos");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 600, 400);
    getContentPane().setLayout(null);

    JLabel lblTitulo = new JLabel("Título:");
    lblTitulo.setBounds(10, 10, 46, 14);
    getContentPane().add(lblTitulo);

    txtTitulo = new JTextField();
    txtTitulo.setBounds(100, 10, 86, 20);
    getContentPane().add(txtTitulo);
    txtTitulo.setColumns(10);

    JLabel lblGenero = new JLabel("Género:");
    lblGenero.setBounds(10, 40, 46, 14);
    getContentPane().add(lblGenero);

    txtGenero = new JTextField();
    txtGenero.setBounds(100, 40, 86, 20);
    getContentPane().add(txtGenero);
    txtGenero.setColumns(10);

    JLabel lblPlataforma = new JLabel("Plataforma:");
    lblPlataforma.setBounds(10, 70, 86, 14);
    getContentPane().add(lblPlataforma);

    txtPlataforma = new JTextField();
    txtPlataforma.setBounds(100, 70, 86, 20);
    getContentPane().add(txtPlataforma);
    txtPlataforma.setColumns(10);

    btnRegistrar = new JButton("Registrar");
    btnRegistrar.setBounds(10, 100, 89, 23);
    getContentPane().add(btnRegistrar);

    btnBorrar = new JButton("Borrar Juego");
    btnBorrar.setBounds(109, 100, 120, 23);
    getContentPane().add(btnBorrar);

    btnEditar = new JButton("Editar Juego");
    btnEditar.setBounds(239, 100, 120, 23);
    getContentPane().add(btnEditar);

    scrollPane = new JScrollPane();
    scrollPane.setBounds(10, 130, 564, 220);
    getContentPane().add(scrollPane);

    tableJuegos = new JTable();
    scrollPane.setViewportView(tableJuegos);

    btnRegistrar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String titulo = txtTitulo.getText();
            String genero = txtGenero.getText();
            String plataforma = txtPlataforma.getText();
            juegoDatabase.registrarJuego(titulo, genero, plataforma);
            limpiarCampos();
            actualizarTablaJuegos();
        }
    });

    btnBorrar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String titulo = JOptionPane.showInputDialog(null, "Ingrese el título del juego a borrar:");
if (titulo != null && !titulo.isEmpty()) {
juegoDatabase.borrarJuego(titulo);
actualizarTablaJuegos();
} else {
JOptionPane.showMessageDialog(null, "El título del juego no puede estar vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
}
}
});
        btnEditar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String titulo = JOptionPane.showInputDialog(null, "Ingrese el título del juego a editar:");
            if (titulo != null && !titulo.isEmpty()) {
                String nuevoTitulo = JOptionPane.showInputDialog(null, "Ingrese el nuevo título del juego:");
                String genero = JOptionPane.showInputDialog(null, "Ingrese el nuevo género del juego:");
                String plataforma = JOptionPane.showInputDialog(null, "Ingrese la nueva plataforma del juego:");
                juegoDatabase.editarJuego(titulo, nuevoTitulo, genero, plataforma);
                limpiarCampos();
                actualizarTablaJuegos();
            } else {
                JOptionPane.showMessageDialog(null, "El título del juego a editar no puede estar vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    });

    // Inicializar la tabla con los datos de la base de datos
    actualizarTablaJuegos();
}

private void limpiarCampos() {
    txtTitulo.setText("");
    txtGenero.setText("");
    txtPlataforma.setText("");
}

private void actualizarTablaJuegos() {
    DefaultTableModel tableModel = juegoDatabase.getJuegosTableModel();
    tableJuegos.setModel(tableModel);
}
}