package ec.fin.cooperco.aplicacionesescritorio.digcooperco.rest;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.UsuariosCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.Usuario;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.UsuarioArchivo;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.LogTypeEnum;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.LogUtils;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.MessageCodeEnum;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Slf4j
public class UsuariosRest {
    @Autowired
    private UsuariosCust usuariosCust;

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario login(@RequestBody UsuarioArchivo usuarioArchivo) {
        LogUtils.setupLog(LogTypeEnum.info, MessageCodeEnum.start_session,usuarioArchivo.getUsuario(), null);
        return usuariosCust.login(usuarioArchivo);
    }
}
