package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.imp;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.ParametrosCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.repositorio.ParametrosRepositorio;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Parametros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Service
@Slf4j
public class ParametrosImp implements ParametrosCust {
    public static final Integer EstadoEstaActivoEnum=1;
    @Autowired
    ParametrosRepositorio parametrosRepositorio;
    @Override
    public List<Parametros> listar() {
        //log.info("Se a enviado a listar todas las carpetas de los usuarios activos");

        return parametrosRepositorio.findAllByEstaActivo(true);
    }
}
