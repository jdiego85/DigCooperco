package ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "dig_usuario_carpetas")

@EntityListeners(AuditingEntityListener.class)
@Comment( "Tabla que permite generar la estructura de directorios para un usuario")
@Data
public class UsuarioCarpetas extends Concurrencia  implements java.io.Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    @Comment("Id de la tabla")
    private String id;
    @Column(length = 255)
    @Comment("Nombre del path para nextcloud")
    private String path;
    @Column(length = 25)
    @Comment("Nombre de la carpeta utilizado para poner con nombre como fecha")
    private String aliasCarpeta;
    @Column(length = 80)
    @Comment("Carpeta del usuario")
    private Carpetas carpetas;

    @Column(columnDefinition = "number (1) default 1 not null")
    @Comment("Para determinar el eliminado lógico dato anterior")
    @Getter
    @Setter
    private Boolean estaActivo = true;
}
