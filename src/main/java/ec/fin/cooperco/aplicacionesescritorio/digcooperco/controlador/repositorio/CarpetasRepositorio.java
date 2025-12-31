package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.repositorio;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Carpetas;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface  CarpetasRepositorio  extends JpaRepository<Carpetas,String > {
    List<Carpetas> findAllByEstado(Boolean estaActivo);
    List<Carpetas> findAllByIdAndEstado(String id, Boolean estaActivo);
    List<Carpetas> findAllByIdAndEstadoAndUsuario(String id, Boolean estaActivo, String usuario);
    List<Carpetas> findAllByEstadoAndUsuario(Boolean estaActivo, String usuario);
}
