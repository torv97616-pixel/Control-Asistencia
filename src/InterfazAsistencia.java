// InterfazAsistencia.java
import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InterfazAsistencia extends JFrame {
    private GestorAsistencia gestor;
    private JComboBox<Empleado> empleadoCombo;
    private JTextArea historicArea;
    private JButton entradaBtn;
    private JButton salidaBtn;
    private JLabel statusLabel;
    
    public InterfazAsistencia() {
        gestor = new GestorAsistencia();
        configurarVentana();
        inicializarComponentes();
        actualizarInterfaz();
    }
    
    private void configurarVentana() {
        setTitle("Control de Asistencia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void inicializarComponentes() {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior para control
        JPanel controlPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        
        // Combo de empleados
        empleadoCombo = new JComboBox<>(gestor.getTodosEmpleados().toArray(new Empleado[0]));
        
        // Botones de fichaje
        entradaBtn = new JButton("Registrar Entrada");
        salidaBtn = new JButton("Registrar Salida");
        
        controlPanel.add(new JLabel("Empleado:"));
        controlPanel.add(empleadoCombo);
        controlPanel.add(entradaBtn);
        controlPanel.add(salidaBtn);
        
        // Área de histórico
        historicArea = new JTextArea();
        historicArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historicArea);
        
        // Barra de estado
        statusLabel = new JLabel();
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Panel para gestión de empleados
        JPanel gestionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton nuevoEmpBtn = new JButton("Nuevo Empleado");
        gestionPanel.add(nuevoEmpBtn);
        
        // Añadir componentes al panel principal
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        mainPanel.add(gestionPanel, BorderLayout.WEST);
        
        add(mainPanel);
        
        // Eventos
        entradaBtn.addActionListener(e -> registrarEntrada());
        salidaBtn.addActionListener(e -> registrarSalida());
        nuevoEmpBtn.addActionListener(e -> mostrarDialogoNuevoEmpleado());
        empleadoCombo.addActionListener(e -> actualizarHistorico());
    }
    
    private void registrarEntrada() {
        try {
            Empleado emp = (Empleado) empleadoCombo.getSelectedItem();
            if (emp != null) {
                gestor.registrarEntrada(emp.getId());
                actualizarInterfaz();
                statusLabel.setText("Entrada registrada para " + emp.getNombre());
            }
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void registrarSalida() {
        try {
            Empleado emp = (Empleado) empleadoCombo.getSelectedItem();
            if (emp != null) {
                gestor.registrarSalida(emp.getId());
                actualizarInterfaz();
                statusLabel.setText("Salida registrada para " + emp.getNombre());
            }
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarDialogoNuevoEmpleado() {
        JTextField idField = new JTextField();
        JTextField nombreField = new JTextField();
        JTextField deptField = new JTextField();
        
        Object[] message = {
            "ID:", idField,
            "Nombre:", nombreField,
            "Departamento:", deptField
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Nuevo Empleado", 
                                                 JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                gestor.agregarEmpleado(idField.getText().trim(), 
                                     nombreField.getText().trim(),
                                     deptField.getText().trim());
                                     
                actualizarComboEmpleados();
                statusLabel.setText("Empleado agregado correctamente");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarComboEmpleados() {
        empleadoCombo.removeAllItems();
        for (Empleado emp : gestor.getTodosEmpleados()) {
            empleadoCombo.addItem(emp);
        }
    }
    
    private void actualizarHistorico() {
        Empleado emp = (Empleado) empleadoCombo.getSelectedItem();
        if (emp == null) return;
        
        StringBuilder sb = new StringBuilder();
        sb.append("Histórico de fichajes para ").append(emp.getNombre()).append("\n\n");
        
        List<Fichaje> fichajes = gestor.getFichajes(emp.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        for (Fichaje f : fichajes) {
            sb.append("Entrada: ").append(f.getEntrada().format(formatter));
            if (f.getSalida() != null) {
                sb.append("\nSalida:  ").append(f.getSalida().format(formatter));
                Duration duracion = f.getDuracion();
                sb.append("\nDuración: ")
                  .append(duracion.toHours()).append("h ")
                  .append(duracion.toMinutesPart()).append("m");
            } else {
                sb.append("\nSalida:  Pendiente");
            }
            sb.append("\n\n");
        }
        
        // Añadir total de horas del día
        LocalDate hoy = LocalDate.now();
        Duration horasHoy = gestor.getHorasTrabajadas(emp.getId(), hoy);
        sb.append("Total horas hoy: ")
          .append(horasHoy.toHours()).append("h ")
          .append(horasHoy.toMinutesPart()).append("m");
        
        historicArea.setText(sb.toString());
    }
    
    private void actualizarInterfaz() {
        actualizarHistorico();
    }

}