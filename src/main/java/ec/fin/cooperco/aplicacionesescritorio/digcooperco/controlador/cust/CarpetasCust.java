package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.DirectorioUsuario;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.Mensajes;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.UsuarioArchivo;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Carpetas;

import java.util.List;

public interface CarpetasCust {
    Carpetas nuevo(Carpetas carpeta);
    List<Carpetas> listar();
    List<Carpetas> generarDirectorios(UsuarioArchivo usuario);
    Mensajes subirArchivo(UsuarioArchivo usuarioArchivo);
    List<DirectorioUsuario> obtenerDirectorio(UsuarioArchivo usuarioArchivo);
    List<DirectorioUsuario> obtenerDirectorioPorFecha(UsuarioArchivo usuarioArchivo);
    void crearCarpeta(UsuarioArchivo usuarioArchivo);

    Mensajes eliminarArchivoGarantia(UsuarioArchivo usuarioArchivo);
}
