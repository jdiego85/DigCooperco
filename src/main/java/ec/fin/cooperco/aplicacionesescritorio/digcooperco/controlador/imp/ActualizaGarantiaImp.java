package ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.imp;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.controlador.cust.ActualizaGarantiaCust;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActualizaGarantiaImp implements ActualizaGarantiaCust {
    private final JdbcTemplate oracleJdbcTemplate;

    private SimpleJdbcCall ejecutarSqlGarantia;

    public ActualizaGarantiaImp(JdbcTemplate oracleJdbcTemplate) {
        this.oracleJdbcTemplate = oracleJdbcTemplate;
    }

    @Override
    public String actualizaGarantia(String numeroGarantia, String nombreArchivo, String codigoDigitalizador) {


        try{
            //System.out.println("Genrado SQL");
            ejecutarSqlGarantia = new SimpleJdbcCall(oracleJdbcTemplate)
                    .withSchemaName("UCONSULTA")
                    .withCatalogName("PKG_FUNCTIONS")
                    .withProcedureName("PR_REFERENCIA_DOCGARANTIAS");

            //System.out.println("Establecimiento de parametros");
            SqlParameterSource parametros = new MapSqlParameterSource()
                    .addValue("piv_garantia", numeroGarantia)
                    .addValue("piv_referencia", nombreArchivo)
                    .addValue("piv_codigo", codigoDigitalizador);

            //System.out.println("Ejecucion de garantia");

            ejecutarSqlGarantia.execute(parametros);


        }catch (Exception e){
            return "Error en la actualizacion: "+e;
        }

        return "Actualizacion del enlace exitosa";
    }
}
