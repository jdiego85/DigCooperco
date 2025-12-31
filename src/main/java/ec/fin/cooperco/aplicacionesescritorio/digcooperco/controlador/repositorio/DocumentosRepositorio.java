package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.repositorio;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Carpetas;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Documentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentosRepositorio extends JpaRepository<Documentos,String > {
    List<Documentos> findAllByEstaActivo(Boolean estaActivo);
    List<Documentos> findAllByIdAndEstaActivo(String id, Boolean estaActivo);
    List<Documentos> findAllByIdAndEstaActivoAndUsuario(String id, Boolean estaActivo, String usuario);
    List<Documentos> findAllByIdAndEstaActivoAndUsuarioAndCarpeta(String id, Boolean estaActivo, String usuario,Carpetas carpeta);
}
