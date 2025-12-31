package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.imp;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.CarpetasCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.ParametrosCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.UsuariosCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.repositorio.CarpetasRepositorio;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.DirectorioUsuario;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.Mensajes;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.UsuarioArchivo;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Carpetas;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Parametros;

import org.aarboard.nextcloud.api.NextcloudConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarpetasImp implements CarpetasCust {
    @Autowired
    CarpetasRepositorio carpetasRepositorio;
    @Autowired
    ParametrosCust parametrosCust;
    @Autowired
    UsuariosCust usuariosCust;
    @Autowired
    CarpetasImpAsyn carpetasImpAsyn;


    @Override
    public Carpetas nuevo(Carpetas carpeta) {
        carpeta.setVersion(0);
        carpeta.setEstado(true);
        carpeta.setEstaActivo(true);
        carpeta.setFechaCreacion(LocalDateTime.now());
        carpeta.setFechaActualizacion(LocalDateTime.now());
        return carpetasRepositorio.save(carpeta);
    }

    @Override
    public List<Carpetas> listar() {
        return null;
    }

    /**
     * Genera los directorios por defecto
     *
     * @param usuarioArchivo
     * @return devuelve las carpetas por defecto
     */
    @Override
    public List<Carpetas> generarDirectorios(UsuarioArchivo usuarioArchivo) {
        log.info("Usuario Sin directorios, Se compieza a comprobar en la base de datos");


        if (carpetasRepositorio.findAllByEstadoAndUsuario(true, String.valueOf(usuarioArchivo)).isEmpty()) {
            log.info("No se han detectado directorios para el usuario, Se crearon nuevos directorios para el usuario");
            List<Carpetas> lista = new ArrayList<>();
            Carpetas carpeta = new Carpetas();
            carpeta.setNombre("BONOS");
            carpeta.setNivel(0);
            carpeta.setOrden(1);
            carpeta.setUsuario(usuarioArchivo.getUsuario().toLowerCase());            //carpeta.setParent(carpetaAnterior);
            nuevo(carpeta);
            lista.add(carpeta);

            carpeta.setNombre("ALTA_DENOMINACIÓN");
            carpeta.setNivel(0);
            carpeta.setOrden(1);
            carpeta.setUsuario(usuarioArchivo.getUsuario().toLowerCase());            //carpeta.setParent(carpetaAnterior);
            nuevo(carpeta);
            lista.add(carpeta);

            carpeta.setNombre("REVERSOS");
            carpeta.setNivel(0);
            carpeta.setOrden(1);
            carpeta.setUsuario(usuarioArchivo.getUsuario().toLowerCase());            //carpeta.setParent(carpetaAnterior);
            nuevo(carpeta);
            lista.add(carpeta);

            carpeta.setNombre("TRANSFERENCIAS_SPI");
            carpeta.setNivel(0);
            carpeta.setOrden(1);
            carpeta.setUsuario(usuarioArchivo.getUsuario().toLowerCase());            //carpeta.setParent(carpetaAnterior);
            nuevo(carpeta);
            lista.add(carpeta);








            carpeta.setNombre("DEPÓSITOS");
            carpeta.setNivel(0);
            carpeta.setOrden(1);
            carpeta.setUsuario(usuarioArchivo.getUsuario().toLowerCase());            //carpeta.setParent(carpetaAnterior);
            nuevo(carpeta);
            lista.add(carpeta);
            carpeta = new Carpetas();
            carpeta.setNombre("RETIROS");
            carpeta.setNivel(0);
            carpeta.setOrden(1);
            carpeta.setUsuario(usuarioArchivo.getUsuario().toLowerCase());            //carpeta.setParent(carpetaAnterior);
            nuevo(carpeta);
            lista.add(carpeta);
            carpeta = new Carpetas();
            carpeta.setNombre("SERVICIOS");
            carpeta.setNivel(0);
            carpeta.setOrden(1);
            carpeta.setUsuario(usuarioArchivo.getUsuario().toLowerCase());            //carpeta.setParent(carpetaAnterior);
            nuevo(carpeta);
            lista.add(carpeta);
            carpeta = new Carpetas();
            carpeta.setNombre("LICITUD_DE_FONDOS");
            carpeta.setNivel(0);
            carpeta.setOrden(1);
            carpeta.setUsuario(usuarioArchivo.getUsuario().toLowerCase());           //carpeta.setParent(carpetaAnterior);
            nuevo(carpeta);
            lista.add(carpeta);
            carpeta = new Carpetas();
            carpeta.setNombre("OTROS");
            carpeta.setNivel(0);
            carpeta.setOrden(1);
            carpeta.setUsuario(usuarioArchivo.getUsuario().toLowerCase());           //carpeta.setParent(carpetaAnterior);
            nuevo(carpeta);
            lista.add(carpeta);
            return lista;
        } else {
            return carpetasRepositorio.findAllByEstadoAndUsuario(true, String.valueOf(usuarioArchivo));
        }
    }


    /**
     * Método para subir archivos
     *
     * @param usuarioArchivo
     * @return devuelve el mensaje si el archivo ha sido subido
     */
    @Override
    public Mensajes subirArchivo(UsuarioArchivo usuarioArchivo) {
        log.info("Subiendo imagen, Fuente: " + usuarioArchivo.getUsuario());
        Parametros parametros = parametrosCust.listar().stream().findFirst().get();
        carpetasImpAsyn.subirArchivosAsincrono(parametros, usuarioArchivo, usuarioArchivo.getMultipartFile());
        return new Mensajes("Subiendo el Archivo");
    }

    @Override
    public Mensajes eliminarArchivoGarantia(UsuarioArchivo usuarioArchivo) {


        log.info("Eliminando Garantia: " + usuarioArchivo.getUsuario());
        //System.out.println("Subiendo imagen");
        Parametros parametros = parametrosCust.listar().stream().findFirst().get();
        carpetasImpAsyn.eliminarGarantiaAsincrono(parametros, usuarioArchivo, usuarioArchivo.getMultipartFile());
        return new Mensajes("Eliminando el Archivo");
    }


    /**
     * Genera los archivos es decir busco o los crea
     *
     * @param usuarioArchivo en un DTO que tiene los credenciales del usuario y la imagen en formato base64
     * @return devuelve un listado de directorio de carpetas por usuario
     */
    @Override
    public List<DirectorioUsuario> obtenerDirectorio(UsuarioArchivo usuarioArchivo) {
        log.info("Se intenta conseguir los directorios del usuario");
        List<Carpetas> carpetasList = carpetasRepositorio.findAllByEstadoAndUsuario(true, usuarioArchivo.getUsuario().toLowerCase());
        List<DirectorioUsuario> directorioUsuarioList = new ArrayList<>();
        //System.out.println(carpetasList);

        if (carpetasList.isEmpty()) {
            log.info("No se a detectado ningun directorio, Se procede a crearlo");
            carpetasList = generarDirectorios(usuarioArchivo);
        }
        directorioUsuarioList = getDirectorios(carpetasList, usuarioArchivo, LocalDate.now());
        //System.out.println(directorioUsuarioList.toString());
        return directorioUsuarioList;
    }

    @Override
    public List<DirectorioUsuario> obtenerDirectorioPorFecha(UsuarioArchivo usuarioArchivo) {
        log.info("Se intenta conseguir los directorios del usuario por fecha");
        List<Carpetas> carpetasList = carpetasRepositorio.findAllByEstadoAndUsuario(true, usuarioArchivo.getUsuario().toLowerCase());
        List<DirectorioUsuario> directorioUsuarioList = new ArrayList<>();
        if (carpetasList.isEmpty()) {
            log.info("No se a detectado ningun directorio en la fecha señalada, Se procede a crearlo");
            carpetasList = generarDirectorios(usuarioArchivo);
        }
        directorioUsuarioList = getDirectorios(carpetasList, usuarioArchivo, usuarioArchivo.getFechaCreacion());
        return directorioUsuarioList;
    }

    /**
     * Si se utiliza La interfas de escaneos de garantias Se cambia el metodo de comprobacion de carpetas
     * Si el Escaneo enviado pertenece a la Carpeta "DIG-PERSONAS"
     */
    @Override
    public void crearCarpeta(UsuarioArchivo usuarioArchivo) {

        //System.out.println(usuarioArchivo.getPath());
        String[] parts = usuarioArchivo.getPath().split("/");
        Parametros parametros = parametrosCust.listar().stream().findFirst().get();
        //System.out.println(parametros);
        NextcloudConnector nxt = new NextcloudConnector(parametros.getUrlServidor(), usuarioArchivo.getUsuario().toLowerCase(), usuarioArchivo.getContrasenia());

        if (parts[0].equals("DIG-PERSONAS")) {
            //System.out.println("es Garantia");
            carpetasImpAsyn.crearCarpetasAsincronico(nxt, parts, usuarioArchivo.getPath());
        } else {
            //System.out.println("es Normal");
            carpetasImpAsyn.crearCarpetaAsincronico(nxt, usuarioArchivo.getPath());
        }

    }


    /**
     * Permite obtener un listado de directorios y te crea las carpetas desde la base de datos en el nextcloud
     *
     * @param carpetasList
     * @param usuarioArchivo en un DTO que tiene los credenciales del usuario y la imagen en formato base64
     * @return devuelve un listado de directorios
     */
    private List<DirectorioUsuario> getDirectorios(List<Carpetas> carpetasList, UsuarioArchivo usuarioArchivo, LocalDate fechaLocal) {
        log.info("Se procede a crear la respuesta en formato JSON");
        List<DirectorioUsuario> directorioUsuarioList = new ArrayList<>();
        String carpetaInicial = "DIG-COOPERCO";
        AtomicInteger i = new AtomicInteger(0);
        carpetasList.forEach(carpetas -> {
            DirectorioUsuario directorioUsuario = new DirectorioUsuario();
            directorioUsuario.setNombre(carpetas.getNombre());
            directorioUsuario.setOrden(i.getAndIncrement());
            directorioUsuario.setPathUsario(carpetaInicial + "/" + usuarioArchivo.getUsuario().toLowerCase() + "/");
            directorioUsuario.setPathDirectorio("/" + carpetas.getNombre());
            directorioUsuario.setPath(carpetaInicial + "/" + usuarioArchivo.getUsuario().toLowerCase() + "/" + fechaLocal.toString() + "/" + carpetas.getNombre());
            directorioUsuarioList.add(directorioUsuario);
        });
        //Se llama al método asyncrono mismo que genera las carpetas, Aqui se generan y guardan en Nexcloud


        carpetasImpAsyn.creacionCarpetasAsincrono(carpetasList, usuarioArchivo, fechaLocal);


        //Fin de llamada
        return directorioUsuarioList;
    }
}