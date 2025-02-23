
import java.util.ArrayList;
import java.util.List;
public class Anfitrion extends Usuario implements Handler, Subscriptor {
    private List<Propiedad> propiedades;
    private Handler next;
    private boolean puedeResolver;

    @Override
    public void setNext(Handler h){
        this.next=h;
    }
    /* Longh Method - Extract Method
    @Override
    public void manejarIncidente(Incidente i){
        if(i.getEstado().equals("resuelto")){
            System.out.println("El incidente " + i.getDescripcion() + " ya ha sido resuelto! ");
            return;
        }
        else if(next!=null && this.puedeResolver == false ){
            i.setEstado("escalado");
            next.manejarIncidente(i);
        }
        
        if(i.getEstado().equals("abierto") && this.puedeResolver == true){
            i.resolverIncidente();
            this.puedeResolver=false;
            System.out.println("El anfitrión " + getNombre() + " ha resuelto el incidente: " + i.getDescripcion());
        }
    }
    */

    public void manejarIncidente(Incidente i) {
        if (incidenteResuelto(i)) return;
        if (escalarIncidente(i)) return;
        resolverIncidente(i);
    }

    private boolean incidenteResuelto(Incidente i) {
        if (i.getEstado().equals("resuelto")) {
            System.out.println("El incidente " + i.getDescripcion() + " ya ha sido resuelto!");
            return true;
        }
        return false;
    }

    private boolean escalarIncidente(Incidente i) {
        if (next != null && !this.puedeResolver) {
            i.cambiarEstado(new EstadoEscalado());
            next.manejarIncidente(i);
            return true;
        }
        return false;
    }

    private void resolverIncidente(Incidente i) {
        if (i.getEstado().equals("abierto") && this.puedeResolver) {
            i.resolverIncidente();
            this.puedeResolver = false;
            System.out.println("El anfitrión " + getNombre() + " ha resuelto el incidente: " + i.getDescripcion());
        }
    }
    
    public Anfitrion(String nombre, String correo) {
        super(nombre, correo);
        this.propiedades = new ArrayList<>();
        puedeResolver = true;
    }

    public void agregarPropiedad(Propiedad propiedad) {
        propiedades.add(propiedad);
    }

    public void establecerRestriccion(Propiedad propiedad, String descripcion, String checkIn, String checkOut) {
        Restriccion restriccion = new Restriccion(descripcion, checkIn, checkOut);
        propiedad.agregarRestriccion(restriccion);
    }

    public List<Propiedad> getPropiedades() {
        return propiedades;
    }
    /* Featury Envy - Move Method
    public void calificarHuesped(Huesped huesped, int puntuacion, String comentario){
        Calificacion calificacion = new Calificacion(puntuacion, comentario, huesped);
        System.out.println("Huésped calificado: " + calificacion.mostrarCalificacion());
    }
    */
    public void calificarHuesped(Huesped huesped, int puntuacion, String comentario){
        huesped.recibirCalificacion(puntuacion, comentario);
    }
    public void rellenarFormulario(String formulario){
        System.out.println("El anfitrión " + getNombre() + " ha rellenado el formulario: " + formulario);

    }
    public void update(String mensaje) {
        System.out.println("Notificación para Anfitrión " + getNombre() + ": " + mensaje);
    }
}