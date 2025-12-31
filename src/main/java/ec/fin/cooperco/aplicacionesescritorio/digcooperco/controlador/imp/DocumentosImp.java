package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.imp;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.CarpetasCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.DocumentosCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Carpetas;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Documentos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentosImp implements DocumentosCust{
    @Override
    public Documentos nuevo(Documentos carpeta) {
        return null;
    }
    @Override
    public List<Documentos> listar() {
        return null;
    }
}