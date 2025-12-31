package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.imp;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.UsuarioCarpetasCust;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.repositorio.UsuarioCarpetasRepositorio;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.UsuarioArchivo;
import ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo.UsuarioCarpetas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedCaseInsensitiveMap;

@Service
@Slf4j
public class UsuarioCarpetasImp implements UsuarioCarpetasCust {

    @Autowired
    UsuarioCarpetasRepositorio usuarioCarpetasRepositorio;
    private final JdbcTemplate oracleJdbcTemplate;
    private SimpleJdbcCall ejecutarSql;
    @Value("${simple_config.schema}")

    private String schema;
    @Value("${simple_config.catalog_dig_utils}")
    private String digiUtils;
    @Value("${simple_config.get_user_folder}")
    private String procedureUserFolder;

    public UsuarioCarpetasImp(JdbcTemplate oracleJdbcTemplate) {
        this.oracleJdbcTemplate = oracleJdbcTemplate;
    }

    @Override
    public UsuarioCarpetas nuevo(UsuarioCarpetas usuarioCarpetas) {
        log.info("Se definen las nuevas carpetas para el usuario");
        usuarioCarpetas.setVersion(0);
        usuarioCarpetas.setFechaCreacion(LocalDateTime.now());
        usuarioCarpetas.setEstado(true);
        return usuarioCarpetasRepositorio.save(usuarioCarpetas);
    }

    @Override
    public List<UsuarioCarpetas> listar(UsuarioArchivo usuarioArchivo) {
        return null;
    }

    @Override
    public List<String> getUsuarioCarpeta() {
        final List<String> listaUsuarios = new ArrayList<String>();
        try {
            ejecutarSql = new SimpleJdbcCall(oracleJdbcTemplate)
                    .withSchemaName(this.schema)
                    .withCatalogName(this.digiUtils)
                    .withProcedureName(this.procedureUserFolder);

            Map<String, Object> objects = ejecutarSql.execute();
            ArrayList<LinkedCaseInsensitiveMap<String>> datos = (ArrayList<LinkedCaseInsensitiveMap<String>>) objects.get("POC_DATOS");
            for (LinkedCaseInsensitiveMap<String> dato : datos) {
                listaUsuarios.add(dato.get("USUARIO"));
            }
        } catch (Exception e) {
            log.error("Error al ejecutar la consulta: ", e.getMessage());
        }

        return listaUsuarios;
    }
}
