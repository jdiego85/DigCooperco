package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.Usuario;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.UsuarioArchivo;

public interface UsuariosCust {
    public Usuario login(UsuarioArchivo usuarioArchivo);
}
