package ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioArchivo {
    String usuario;
    String contrasenia;
    String path;
    String nombre;
    String extension;
    LocalDate fechaCreacion;
    byte[] multipartFile;
    String version;
    public UsuarioArchivo(){

    }
    public UsuarioArchivo(String usuario, String contrasenia, String path, String nombre, String extension, byte[] multipartFile) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.path = path;
        this.nombre = nombre;
        this.extension = extension;
        this.multipartFile = multipartFile;
    }
}
