package ec.fin.cooperco.aplicacionesescritorio.digcooperco.tareas;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.ParametrosCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.imp.CarpetasImpAsyn;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.imp.UsuarioCarpetasImp;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.repositorio.CarpetasRepositorio;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Carpetas;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Parametros;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.Constantes;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.UserAdmin;
import lombok.extern.slf4j.Slf4j;
import org.aarboard.nextcloud.api.NextcloudConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CarpetaTask {
    @Autowired
    private UsuarioCarpetasImp usuarioCapeta;
    @Autowired
    private UserAdmin userAdmin;
    @Autowired
    private CarpetasImpAsyn carpetasImpAsyn;
    @Autowired
    private ParametrosCust parametrosCust;
    @Autowired
    private CarpetasRepositorio carpetasRepositorio;

    public CarpetaTask() {
    }

    /*
     * Tarea en segundo plano para la creación de carpetas a la fecha de todos los usuarios
     * */
    @Scheduled(cron = "${cron_schedule_folders}", zone = "America/Guayaquil")
    @Async
    public void crearCarpetaTaskAsync() throws InterruptedException {
        try {
            log.info("********** Inicio de creación de carpetas **********");
            final List<String> usuarios = usuarioCapeta.getUsuarioCarpeta();
            if (usuarios.isEmpty()) {
                log.warn("No hay usuarios en la lista");
                throw new InterruptedException("No hay usuarios en la lista");
            }
            final LocalDateTime fechaSiguiente = LocalDateTime.now().plusDays(1L);
            final String fechaCorrespondiente = fechaSiguiente.format(DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA));
            final Parametros parametros = parametrosCust.listar().stream().findFirst().get();
            final NextcloudConnector nxt = new NextcloudConnector(parametros.getUrlServidor(), this.userAdmin.getUsername(), this.userAdmin.getPassword());
            final var user = nxt.getCurrentUser();
            if (user == null) {
                log.warn("No se pudo iniciar sesión en NextCloud");
                throw new InterruptedException("No se pudo iniciar sesión en NextCloud");
            }
            final List<String> paths = new ArrayList<>();
            log.info("Generando paths de los usuarios disponibles a la fecha: " + fechaCorrespondiente);
            for (String usuario : usuarios) {
                if (usuario != null && !usuario.equals("null")) {
                    final String pathBase = Constantes.PATH_INICIAL_GARANTIAS + usuario.toLowerCase() + Constantes.PATH_SEPARACION + fechaCorrespondiente + Constantes.PATH_SEPARACION;
                    final List<Carpetas> carpetas = this.carpetasRepositorio
                            .findAllByEstadoAndUsuario(true, usuario.toLowerCase());
                    for (Carpetas carpeta : carpetas) {
                        paths.add(pathBase + carpeta.getNombre());
                    }
                }
            }
            log.info("Se procede a crear las carpetas a la fecha: " + fechaCorrespondiente);
            paths.forEach(path -> {
                this.carpetasImpAsyn.crearCarpetas(nxt, path.split(Constantes.PATH_SEPARACION));
            });
            log.info("********** Creación de carpetas finalizada **********");
        } catch (Exception ex) {
            log.info("Se produjo una excepción: " + ex.getMessage());
            throw new InterruptedException("Se produjo una excepción: " + ex.getMessage());
        }
    }
}
