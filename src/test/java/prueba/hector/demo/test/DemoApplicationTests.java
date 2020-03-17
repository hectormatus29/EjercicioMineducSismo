package prueba.hector.demo.test;

import org.apache.tomcat.util.json.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;


import prueba.hector.rest.ConsumerController;

class DemoApplicationTests {

        /**
        * Test encargado de obtener los sismos con una fecha inicial y una fecha final
        */
	@Test
	void buscaPorFecha() {
            ConsumerController c= new ConsumerController();
            ResponseEntity<?> respuesta= c.ejercicioA("2014-01-01","2014-01-02");
            System.out.print(respuesta.toString());
	}
        
         /**
        * Test encargado de obtener los sismos con una magnitud inicial y una magnitud final
        */
        @Test
	void buscaPorMagnitud() {
            ConsumerController c= new ConsumerController();
            ResponseEntity<?> respuesta= c.ejercicioB("6","7");
            System.out.print(respuesta.toString());
	}
        
         /**
        * Test encargado de guardar en BD los sismos del dia actual.
        */
        //@Test
	void guardarMagnitud() throws ParseException {
           
            
            ConsumerController c= new ConsumerController();
            c.ejercicioC();
          
	}
        
}
