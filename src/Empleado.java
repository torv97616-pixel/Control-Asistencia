// Empleado.java
import java.io.Serializable;

public class Empleado implements Serializable {
    private String id;
    private String nombre;
    private String departamento;
    
    public Empleado(String id, String nombre, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
    }
    
    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDepartamento() { return departamento; }
    
    @Override
    public String toString() {
        return String.format("%s - %s (%s)", id, nombre, departamento);
    }
}