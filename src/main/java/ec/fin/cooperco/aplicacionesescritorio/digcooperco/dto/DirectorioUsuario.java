package ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto;

import lombok.Data;

@Data
public class DirectorioUsuario {
    String nombre;
    String path;
    String pathDirectorio;
    String pathUsario;
    Integer orden;
    DirectorioUsuario subDirectorio;
}
