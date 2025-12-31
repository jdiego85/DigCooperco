package ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto;

import lombok.Data;

@Data
public class Mensajes {
    public Mensajes(String mensaje) {
        this.mensaje = mensaje;
    }

    String mensaje;

    @Override
    public String toString() {
        return this.mensaje;
    }
}
