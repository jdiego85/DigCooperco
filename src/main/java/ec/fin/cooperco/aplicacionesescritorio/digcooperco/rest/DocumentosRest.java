package ec.fin.cooperco.aplicacionesescritorio.digcooperco.rest;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.CarpetasCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.DirectorioUsuario;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.Mensajes;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.UsuarioArchivo;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.LogTypeEnum;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.LogUtils;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.MessageCodeEnum;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Slf4j
public class DocumentosRest {
    @Autowired
    CarpetasCust carpetasCust;

    @PostMapping(value = "subirArchivo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mensajes subirArchivo(@RequestParam String usuario,
                                 @RequestParam String contrasenia,
                                 @RequestParam String nombre,
                                 @RequestParam String path,
                                 @RequestParam String extension,
                                 @RequestParam MultipartFile multipartFile) {

        try {
            return carpetasCust.subirArchivo(
                    new UsuarioArchivo(usuario, contrasenia, path, nombre, extension, multipartFile.getBytes()));
        } catch (Exception e) {
            LogUtils.setupLog(LogTypeEnum.error, MessageCodeEnum.file_general_error, null, e);
            return new Mensajes(MessageUtils.serviceErrorMessageStatic);
        }
    }

    @PostMapping(value = "eliminarGarantia", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mensajes eliminarGarantia(@RequestBody UsuarioArchivo usuarioArchivo) {

        System.out.println("llega a la aliminacion");


        try {
            return carpetasCust.eliminarArchivoGarantia(usuarioArchivo);
        } catch (Exception e) {
            log.error("Se produjo un error al intentar subir el archivo a Nextcloud", e);
            return new Mensajes("ERROR EN SERVICIO");
        }
    }


    @PostMapping(value = "obtenerDirectorio", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DirectorioUsuario> obtenerDirectorio(@RequestBody UsuarioArchivo usuarioArchivo) {
        try {
            //System.out.println(carpetasCust.obtenerDirectorio(usuarioArchivo));
            return carpetasCust.obtenerDirectorio(usuarioArchivo);
        } catch (Exception e) {
            DirectorioUsuario directorioUsuario = new DirectorioUsuario();
            log.error("A ocurrido un problema al obtener los directorios para el dia de hoy");
            return Arrays.asList(directorioUsuario);
        }
    }

    @PostMapping(value = "obtenerDirectorioPorFecha", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DirectorioUsuario> obtenerDirectorioPorFecha(@RequestBody UsuarioArchivo usuarioArchivo) {
        try {
            return carpetasCust.obtenerDirectorioPorFecha(usuarioArchivo);
        } catch (Exception e) {
            DirectorioUsuario directorioUsuario = new DirectorioUsuario();
            log.error("A ocurrido un problema al obtener los directorios para el dia señalado");
            return Arrays.asList(directorioUsuario);
        }
    }

    @PostMapping(value = "crearCarpetas", produces = MediaType.APPLICATION_JSON_VALUE)
    public void crearCarpetas(@RequestBody UsuarioArchivo usuarioArchivo) {
        try {
            //System.out.println("Metodo Crear Carpeta");
            carpetasCust.crearCarpeta(usuarioArchivo);
        } catch (Exception e) {
            log.error("A ocurrido un problema al Crear la carpeta necesaria");
        }
    }

}
