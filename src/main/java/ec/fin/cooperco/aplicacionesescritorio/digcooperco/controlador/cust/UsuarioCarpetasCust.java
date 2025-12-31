package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.UsuarioArchivo;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.UsuarioCarpetas;

import java.util.List;

public interface UsuarioCarpetasCust {
    UsuarioCarpetas nuevo(UsuarioCarpetas usuarioCarpetas);
    List<UsuarioCarpetas> listar(UsuarioArchivo usuarioArchivo);
    List<String> getUsuarioCarpeta();
}
