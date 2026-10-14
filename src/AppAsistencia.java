import javax.swing.SwingUtilities;

public class AppAsistencia {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazAsistencia().setVisible(true);
        });
    }    
}
