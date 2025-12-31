package ec.fin.cooperco.aplicacionesescritorio.digcooperco.rest;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.ActualizaGarantiaCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.Mensajes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ActualizarGarantiaRest {
    ActualizaGarantiaCust actualizaGarantiaCust;
    @PostMapping(value = "subirArchivo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mensajes subirArchivo(@RequestParam String numeroGarantia,
                                 @RequestParam String nombreArchivo,
                                 @RequestParam String codigo){
        actualizaGarantiaCust.actualizaGarantia(numeroGarantia,nombreArchivo,codigo);
        return new Mensajes("ok");
    }
}
