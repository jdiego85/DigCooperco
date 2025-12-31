package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.imp;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.ActualizaGarantiaCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.ParametrosCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.UsuarioArchivo;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Carpetas;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Parametros;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.Constantes;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.FuncionesUtils;
import org.aarboard.nextcloud.api.NextcloudConnector;
import org.aarboard.nextcloud.api.filesharing.Share;
import org.aarboard.nextcloud.api.filesharing.SharePermissions;
import org.aarboard.nextcloud.api.filesharing.ShareType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CarpetasImpAsyn {

    @Autowired
    ParametrosCust parametrosCust;

    @Autowired
    ActualizaGarantiaCust actualizaGarantiaCust;

    @Value("${try_upload_file}")
    private int numeroIntentos;

    /**
     * Este es el metodo encargado de crear las carpetas en el Nexcloud,
     * desde aqui se les asigna su usuario, fecha y nombre
     * <p>
     * Se usaba cuando el usuario iniciaba sesion, en ese momento se creaban
     * todas las carpetas que correspondian a este usuario
     * <p>
     * Ahora solo verifica y si es necesario, crea La carpeta principal, la carpeta del usuario y la fecha
     * <p>
     * Al ser un Metodo @Async, Puede que se produsca una perdida de datos al intentar producir un escaneo
     * mientras el resto de carpetas se esta creando
     *
     * @param carpetasList
     * @param usuarioArchivo
     * @param fechaLocal
     */
    @Async("asyncExecutor")
    public void creacionCarpetasAsincrono(List<Carpetas> carpetasList, UsuarioArchivo usuarioArchivo, LocalDate fechaLocal) {
        log.info("Comienza el metodo para crear carpetas de manera Asincrona");
        Parametros parametros = parametrosCust.listar().stream().findFirst().get();
        NextcloudConnector nxt = new NextcloudConnector(parametros.getUrlServidor(), usuarioArchivo.getUsuario().toLowerCase(), usuarioArchivo.getContrasenia());
        //NextcloudClient nxt = new NextcloudClient(parametros.getUrlServidor(), usuarioArchivo.getUsuario().toLowerCase(), usuarioArchivo.getContrasenia());
        nxt.trustAllCertificates(true);
        try {
            String carpetaInicial = "DIG-COOPERCO";
            crearCarpetaAsincronico(nxt, carpetaInicial);
            crearCarpetaAsincronico(nxt, carpetaInicial + "/" + usuarioArchivo.getUsuario().toLowerCase());
            crearCarpetaAsincronico(nxt, carpetaInicial + "/" + usuarioArchivo.getUsuario().toLowerCase() + "/" + fechaLocal.toString()); //Aqui no llega la fecha inicial

//            En esta parte del codigo se crean las carpetas a las que el usuario tiene acceso
//            carpetasList.forEach(carpetas -> {
//                crearCarpetaAsincronico(nxt, carpetaInicial + "/" + usuarioArchivo.getUsuario().toLowerCase() + "/" + /*LocalDate.now()*/fechaLocal.toString() + "/" + carpetas.getNombre());
//            });


        } catch (Exception e) {
            log.error("Error al momento de crear la carpeta, Problema con la libreria ", e);
        }

        try {
            nxt.shutdown();
        } catch (IOException e) {
            log.error("Error al momento de borrar la sesion actual de la libreria Nexcloud durante la creacion de carpetas", e);
        }
    }

    /**
     * Valida si existe el directorio en nexcloud, si no, los crea
     *
     * @param nxt  conector de servidor nextcloud
     * @param path dirección local de nextcloud
     */

    @Async("asyncExecutor")
    public void crearCarpetaAsincronico(NextcloudConnector nxt, String path) {
        log.info("Creando Carpeta: " + path);
        //System.out.println("exsite ?" + path);
        try {
            if (!nxt.folderExists(path)) {
                //System.out.println("No existe");
                nxt.createFolder(path);
            }
        } catch (Exception e) {
            log.error("Error controlado" + e + "-- repitiendo proceso");
            //crearCarpeta(nxt,path);
        }

    }


    public void crearCarpeta(NextcloudConnector nxt, String path) {
        log.info("Creando Carpeta: " + path);
        //System.out.println("exsite ?" + path);
        try {
            if (!nxt.folderExists(path)) {
                //System.out.println("No existe");
                nxt.createFolder(path);
            }
        } catch (Exception e) {
            log.error("Error controlado " + e + " --repitiendo proceso");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            //crearCarpeta(nxt,path);
        }
    }


    /**
     * Método que permite subir archivos de manera asincrónica
     *
     * @param parametros
     * @param usuarioArchivo
     * @param multipartFile  archivo en partes
     * @return devuelve el mensaje de estado
     **/
    @Async("asyncExecutor")
    public void subirArchivosAsincrono(
            Parametros parametros,
            UsuarioArchivo usuarioArchivo,
            byte[] multipartFile) {
        if (usuarioArchivo.getUsuario() == null) {
            log.warn("El usuario que intenta subir archivos es nulo");
            return;
        }
        for (int i = 0; i < this.numeroIntentos; i++) {
            log.info("Se verifica la existencia de la carpeta: " + usuarioArchivo.getPath() + ". Intento: " + (i + 1));
            final String[] parts = usuarioArchivo.getPath().split("/");
            final String directorioActual = parts[0];
            final NextcloudConnector nxt = new NextcloudConnector(parametros.getUrlServidor(), usuarioArchivo.getUsuario().toLowerCase(), usuarioArchivo.getContrasenia());
            try {
                if (!nxt.folderExists(usuarioArchivo.getPath())) {
                    log.info("Se espera a que este la carpeta" + usuarioArchivo.getPath());
                    crearCarpetas(nxt, parts);
                    log.info("Espera terminada");
                }
            } catch (Exception e) {
                log.error("Posible Directorio bloqueado. Ya existe Por lo tanto la verificacion se salta: " + e);
                continue;
            }

            final String nombreUnico = FuncionesUtils.obtenerFechaNombre();

            try {
                final InputStream inputStream = new ByteArrayInputStream(multipartFile);
                if (directorioActual.equals("DIG-PERSONAS")) {
                    String input = usuarioArchivo.getPath();

                    if (input.endsWith("/")) {
                        input = input.substring(0, input.length() - 1);
                    }

                    nxt.uploadFile(inputStream, input + usuarioArchivo.getNombre() + usuarioArchivo.getExtension(), true);
                    final SharePermissions es = new SharePermissions(SharePermissions.SingleRight.READ);
                    final Share ste = nxt.doShare(input + usuarioArchivo.getNombre() + usuarioArchivo.getExtension(), ShareType.PUBLIC_LINK, "", false, "", es);

                    final int lastIndex = ste.getUrl().lastIndexOf('/');
                    final int lastIndexnom = ste.getPath().lastIndexOf('/');

                    // Extract the last part of the URL
                    final String lastPart = ste.getUrl().substring(lastIndex + 1);
                    final String lastPartNom = ste.getPath().substring(lastIndexnom + 1);

                    final String nombreDoc = lastPartNom.replace(".pdf", "");

                    log.info("##### DATOS A ENVIAR |" + parts[3] + "|" + nombreDoc + "|" + lastPart + " -Autor-: " + usuarioArchivo.getUsuario());

                    final String resp = actualizaGarantiaCust.actualizaGarantia(parts[3], nombreDoc, lastPart);
                    log.info("||||||||||||||||| Respuesta de la Base: " + resp);
                } else {
                    log.info("################ Comienza el metodo para subir un archivo ################");
                    log.info("Nombre: " + usuarioArchivo.getPath() + usuarioArchivo.getNombre() + nombreUnico + usuarioArchivo.getExtension());
                    nxt.uploadFile(inputStream, usuarioArchivo.getPath() + usuarioArchivo.getNombre() + nombreUnico + usuarioArchivo.getExtension(), true);
                }
                nxt.shutdown();
                break;
            } catch (Exception e) {
                log.error("Error al recibir el archivo a subir a nexcloud ", e);
            }
        }
    }

    /**
     * Metodo para eliminar garantias de forma inmediata
     **/
    @Async("asyncExecutor")
    public void eliminarGarantiaAsincrono(Parametros parametros, UsuarioArchivo usuarioArchivo, byte[] multipartFile) {

        NextcloudConnector nxt = new NextcloudConnector(parametros.getUrlServidor(), usuarioArchivo.getUsuario().toLowerCase(), usuarioArchivo.getContrasenia());

        String input = usuarioArchivo.getPath();
        log.info("!!!!!!!!!!! A eliminarse " + input + usuarioArchivo.getNombre() + usuarioArchivo.getExtension());
        System.out.println("!!!!!!!!!!! A eliminarse " + input + usuarioArchivo.getNombre() + usuarioArchivo.getExtension());

        nxt.removeFile(input + usuarioArchivo.getNombre() + usuarioArchivo.getExtension());

        try {
            nxt.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Async("asyncExecutor")
    public void crearCarpetasAsincronico(NextcloudConnector nxt, String[] parts, String pathComplete) {
        String pathActual = parts[0];
        //System.out.println(parts.length);
        try {
            if (!nxt.folderExists(pathComplete)) {
                for (int i = 0; i < parts.length; i++) {
                    log.info("Creando Arbol: " + pathActual);
                    try {
                        if (!nxt.folderExists(pathActual)) {
                            nxt.createFolder(pathActual);
                        }
                        //System.out.println(pathActual);
                        if (i < parts.length - 1) {
                            pathActual = pathActual + "/" + parts[i + 1];
                        }
                    } catch (Exception e) {
                        log.error("Se produjo la siguiente exepcion controlada (CrearCarpetaAsync): " + e);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void crearCarpetas(NextcloudConnector nxt, String[] parts) {
        String pathActual = parts[0];
        for (int i = 0; i < parts.length; i++) {
            try {
                log.info("Creando Arbol: " + pathActual);
                if (!nxt.folderExists(pathActual)) {
                    nxt.createFolder(pathActual);
                }
                if (i < parts.length - 1) {
                    pathActual = pathActual + "/" + parts[i + 1];
                }

            } catch (Exception e) {
                log.error("Se produjo la siguiente excepcion controlada: " + e.getMessage() + "--Reintentando--");
            }
        }

    }
}
