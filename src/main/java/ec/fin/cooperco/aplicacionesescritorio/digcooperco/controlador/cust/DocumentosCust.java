package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Carpetas;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Documentos;

import java.util.List;

public interface DocumentosCust {
    Documentos nuevo(Documentos carpeta);
    List<Documentos> listar();

}
