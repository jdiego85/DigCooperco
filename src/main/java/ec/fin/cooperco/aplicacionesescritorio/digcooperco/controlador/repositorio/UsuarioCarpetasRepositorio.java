package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.repositorio;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Carpetas;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.UsuarioCarpetas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioCarpetasRepositorio extends JpaRepository<UsuarioCarpetas,String > {
    List<UsuarioCarpetas> findAllByEstaActivo(Boolean estaActivo);
    List<UsuarioCarpetas> findAllByIdAndEstaActivo(String id, Boolean estaActivo);
    List<UsuarioCarpetas> findAllByIdAndEstaActivoAndUsuario(String id, Boolean estaActivo, String usuario);
    List<UsuarioCarpetas> findAllByEstaActivoAndUsuario(Boolean estaActivo, String usuario);
}
