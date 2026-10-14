// Fichaje.java
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Duration;

public class Fichaje implements Serializable {
    private String idEmpleado;
    private LocalDateTime entrada;
    private LocalDateTime salida;
    
    public Fichaje(String idEmpleado, LocalDateTime entrada) {
        this.idEmpleado = idEmpleado;
        this.entrada = entrada;
    }
    
    public void registrarSalida(LocalDateTime salida) {
        this.salida = salida;
    }
    
    public Duration getDuracion() {
        if (salida == null) return Duration.ZERO;
        return Duration.between(entrada, salida);
    }
    
    public boolean estaAbierto() {
        return salida == null;
    }
    
    // Getters
    public String getIdEmpleado() { return idEmpleado; }
    public LocalDateTime getEntrada() { return entrada; }
    public LocalDateTime getSalida() { return salida; }
}