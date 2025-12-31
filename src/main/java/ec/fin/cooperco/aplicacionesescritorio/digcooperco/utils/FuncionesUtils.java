package ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FuncionesUtils {
    public static String obtenerFechaNombre(){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA_NOMBRE);
        final LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

}
