package prueba.hector.rest;

/**
 *
 * @author Hector Matus
 */
public class Sismo{
    
   
    private String magnitud;
    private String titulo;
    private String lugar;
    private int tsunami;
    private String status;
    private String fecha;
    
    public Sismo() {
    }

    public Sismo( String magnitud, String titulo, String lugar, int tsunami, String status,String fecha) {
      
        this.magnitud = magnitud;
        this.titulo = titulo;
        this.lugar = lugar;
        this.tsunami = tsunami;
        this.status = status;
        this.fecha=fecha;
    }

  
    public String getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(String magnitud) {
        this.magnitud = magnitud;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getTsunami() {
        return tsunami;
    }

    public void setTsunami(int tsunami) {
        this.tsunami = tsunami;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Sismo{" + " magnitud=" + magnitud + ", titulo=" + titulo + ", lugar=" + lugar + ", tsunami=" + tsunami + ", status=" + status + ", fecha=" + fecha + '}';
    }
    
    

    
}