package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.repositorio;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Parametros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParametrosRepositorio extends JpaRepository<Parametros,String > {
    List<Parametros> findAllByEstaActivo(Boolean estaActivo);
}
