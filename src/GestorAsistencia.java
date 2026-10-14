// GestorAsistencia.java
import java.io.*;
import java.time.*;
import java.util.*;

public class GestorAsistencia {
    private Map<String, Empleado> empleados;
    private List<Fichaje> fichajes;
    private static final String ARCHIVO_DATOS = "asistencia.dat";
    
    public GestorAsistencia() {
        empleados = new HashMap<>();
        fichajes = new ArrayList<>();
        cargarDatos();
    }
    
    public void agregarEmpleado(String id, String nombre, String departamento) {
        if (empleados.containsKey(id)) {
            throw new IllegalArgumentException("ID de empleado ya existe");
        }
        empleados.put(id, new Empleado(id, nombre, departamento));
        guardarDatos();
    }
    
    public Fichaje registrarEntrada(String idEmpleado) {
        if (!empleados.containsKey(idEmpleado)) {
            throw new IllegalArgumentException("Empleado no encontrado");
        }
        
        // Verificar si ya tiene un fichaje abierto
        Fichaje fichajeAbierto = null;
        for (Fichaje f : fichajes) {
            if (f.getIdEmpleado().equals(idEmpleado) && f.estaAbierto()) {
                fichajeAbierto = f;
                break;
            }
        }
            
        if (fichajeAbierto != null) {
            throw new IllegalStateException("El empleado ya tiene una entrada sin salida");
        }
        
        Fichaje fichaje = new Fichaje(idEmpleado, LocalDateTime.now());
        fichajes.add(fichaje);
        guardarDatos();
        return fichaje;
    }
    
    public void registrarSalida(String idEmpleado) {
        Fichaje fichajeAbierto = null;
        for (Fichaje f : fichajes) {
            if (f.getIdEmpleado().equals(idEmpleado) && f.estaAbierto()) {
                fichajeAbierto = f;
                break;
            }
        }
            
        if (fichajeAbierto == null) {
            throw new IllegalStateException("No hay entrada registrada para este empleado");
        }
        
        fichajeAbierto.registrarSalida(LocalDateTime.now());
        guardarDatos();
    }
    
    public Duration getHorasTrabajadas(String idEmpleado, LocalDate fecha) {
        Duration total = Duration.ZERO;
        for (Fichaje f : fichajes) {
            if (f.getIdEmpleado().equals(idEmpleado) && 
                f.getEntrada().toLocalDate().equals(fecha)) {
                total = total.plus(f.getDuracion());
            }
        }
        return total;
    }
    
    public List<Fichaje> getFichajes(String idEmpleado) {
        List<Fichaje> fichajesEmpleado = new ArrayList<>();
        for (Fichaje f : fichajes) {
            if (f.getIdEmpleado().equals(idEmpleado)) {
                fichajesEmpleado.add(f);
            }
        }
        return fichajesEmpleado;
    }
    
    public Empleado getEmpleado(String id) {
        return empleados.get(id);
    }
    
    public List<Empleado> getTodosEmpleados() {
        return new ArrayList<>(empleados.values());
    }
    
    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(ARCHIVO_DATOS));
            empleados = (Map<String, Empleado>) ois.readObject();
            fichajes = (List<Fichaje>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            empleados = new HashMap<>();
            fichajes = new ArrayList<>();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void guardarDatos() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS));
            oos.writeObject(empleados);
            oos.writeObject(fichajes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}