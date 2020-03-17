package prueba.hector.rest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
/**
 *
 * @author Hector Matus
 */
@RestController
public class ConsumerController {

  @Autowired
  private JdbcTemplate jtm;

  /**
   * Ejerjcicio A 
   * @param fechaInicial
   * @param fechaFinal
   * @return
   */
  @RequestMapping(value = "/ejercicio/a/{fechaInicial}/{fechaFinal}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ejercicioA(@PathVariable("fechaInicial") String fechaInicial, @PathVariable("fechaFinal") String fechaFinal) {

    String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + fechaInicial + "&endtime=" + fechaFinal;
    RestTemplate restTemplate = new RestTemplate();
    String json = restTemplate.getForEntity(url, String.class).getBody();

    return new ResponseEntity<>(json, HttpStatus.OK);
  }

   /**
   * Ejerjcicio A 
   * 
   * @param magnitudInicial
   * @param magnitudFinal
   * @return
   */
  @RequestMapping(value = "/ejercicio/b/{magnitudInicial}/{magnitudFinal}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ejercicioB(@PathVariable("magnitudInicial") String magnitudInicial, @PathVariable("magnitudFinal") String magnitudFinal) {

    String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=" + magnitudInicial + "&maxmagnitude=" + magnitudFinal;
    RestTemplate restTemplate = new RestTemplate();

    String json = restTemplate.getForEntity(url, String.class).getBody();

    return new ResponseEntity<>(json, HttpStatus.OK);
  }

  
  /**
   * Ejercicio C
   */
  @RequestMapping(value = "/ejercicio/c", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ejercicioC() throws ParseException {

        
        
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + getFechaString() + "&endtime=" + getFechaMañanaString();
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForEntity(url, String.class).getBody();
       
        List<?> listado =(List<?>) obtieneSismo(json);
        if(listado != null && !listado.isEmpty()) {
            for(Object o : listado){          
              Sismo sis = convierteSismo(o);         
              jtm.execute("insert into Sismo(fecha, magnitud, titulo, lugar,tsunami,status) values('"+sis.getFecha()+"','"+sis.getMagnitud()+"', '"+sis.getTitulo()+"', '"+sis.getLugar()+"', '"+sis.getTsunami()+"', '"+sis.getStatus()+"')");
             
            }
        }
        return new ResponseEntity<>("Se ha ingresado un total de "+listado.size()+" sismos", HttpStatus.OK);

    }
  
  
  /**
   * Metodo que convierte la entrada a un objeto Sismo 
   * @param o (lista de objeto features)
   * @return Sismo 
   */
  public Sismo convierteSismo(Object o){
    Sismo sis = new Sismo();
    LinkedHashMap<?,?> feature = (LinkedHashMap<?,?>) o;
    LinkedHashMap<?,?> propiedades = (LinkedHashMap<?,?>) feature.get("properties");
    System.out.println("jsonObject="+ (propiedades.get("mag")) + "\n");
 
    sis.setLugar(propiedades.get("place")!=null ? propiedades.get("place").toString().replace("'", "''") : "");
    sis.setMagnitud(propiedades.get("mag").toString());
    sis.setFecha((propiedades.get("time").toString()));
    sis.setTitulo(propiedades.get("title").toString().replace("'", "''"));   
    sis.setStatus(propiedades.get("status").toString());
    sis.setTsunami(Integer.parseInt(String.valueOf(propiedades.get("tsunami"))));
    return sis;      
  }
 
   /**
   * Metodo que objtiene el listado de Features apartir de la respuesta del servicio en json   
   * @param jSon
   * @return Object que se ra la lista de features
   */
  public Object obtieneSismo(String jSon) throws ParseException{
       JSONParser parser = new JSONParser(jSon);
        LinkedHashMap<?,?> linkedHashMap = (LinkedHashMap<?,?>) parser.parse();        
        List<?> listado = (List<?>) linkedHashMap.get("features");
        return listado;
  }

  public static String getFechaString() {
        String respuesta = "";
        Date date = new Date();
        if(date != null){
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            respuesta = formato.format(date);
        }
        return respuesta;
    }
  
  public static String getFechaMañanaString() {
        String respuesta = "";
        Date someDate = new Date();
        Date proxdate = new Date(someDate.getTime() + TimeUnit.DAYS.toMillis( 1 ));
        if(proxdate != null){
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            respuesta = formato.format(proxdate);
        }
        return respuesta;
    }
  

 
  
}

