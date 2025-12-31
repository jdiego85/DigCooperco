package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.imp;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.ParametrosCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.UsuariosCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.Mensajes;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.Usuario;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.UsuarioArchivo;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.Parametros;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.LogTypeEnum;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.LogUtils;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.MessageCodeEnum;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuariosImp implements UsuariosCust {
    @Autowired
    ParametrosCust parametrosCust;

    @Value("${version_cliente}")
    String versionCliente;

    @Override
    public Usuario login(UsuarioArchivo usuarioArchivo) {
        final Usuario usuario = new Usuario();
        usuario.setMensaje(MessageUtils.replaceUsername(
                usuarioArchivo.getUsuario(), MessageUtils.userNotExistMessageStatic
        ));

        //Validar Version
        if (usuarioArchivo.getVersion() == null || !usuarioArchivo.getVersion().equals(this.versionCliente)) {
            usuario.setMensaje(MessageUtils.replaceVersion(
                    versionCliente, MessageUtils.updateVersionMessageStatic
            ));
            return usuario;
        }

        try {
            final Parametros parametros = parametrosCust.listar().stream().findFirst().get();
            final String encoding = Base64.getEncoder().encodeToString((usuarioArchivo.getUsuario().toLowerCase() + ":" + usuarioArchivo.getContrasenia()).getBytes("UTF-8"));
            if (validarUsuario(parametros, encoding, usuarioArchivo.getUsuario())) {
                LogUtils.setupLog(LogTypeEnum.info, MessageCodeEnum.user_find, usuarioArchivo.getUsuario(), null);
                usuario.setMensaje(new Mensajes(MessageUtils.userLoginMessageStatic));
                usuario.setNombre(usuarioArchivo.getUsuario().toLowerCase());
                usuario.setTiempoSesion(parametros.getTiempoAtrasMaximoDias());
            }
        } catch (Exception e) {
            LogUtils.setupLog(LogTypeEnum.error, MessageCodeEnum.user_login_error, usuarioArchivo.getUsuario(), e);
        }
        return usuario;
    }

    private boolean validarUsuario(Parametros parametros, String encoding, String usuario) throws IOException {
        boolean credencialesCorrectas = false;
        try {
            URL url = new URL(parametros.getUrlServidor() + "/ocs/v2.php/core/getapppassword");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("OCS-APIRequest", "true");
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            String boundary = UUID.randomUUID().toString();
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            InputStream content = connection.getInputStream();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.indexOf("OK") > 0) {
                    credencialesCorrectas = true;
                    break;
                }
            }
            return credencialesCorrectas;
        } catch (Exception e) {
            LogUtils.setupLog(LogTypeEnum.error, MessageCodeEnum.user_login_password_error, usuario, e);
        }
        return credencialesCorrectas;
    }
}

