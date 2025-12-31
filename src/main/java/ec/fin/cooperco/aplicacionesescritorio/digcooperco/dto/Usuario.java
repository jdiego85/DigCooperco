package ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto;

import lombok.Data;

@Data
public class Usuario {
    Mensajes mensaje;
    String nombre;
    Integer tiempoSesion;
}
