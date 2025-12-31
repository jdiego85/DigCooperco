package ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo;



import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.util.List;

@Entity
@Table(name = "dig_carpetas", indexes = {
        @Index(columnList = "nombre"),
})
@EntityListeners(AuditingEntityListener.class)
@Comment("Tabla que permite registrar las carpetas del front")
@Data
public class Carpetas extends Concurrencia implements java.io.Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    @Comment("Id de la tabla")
    private String id;
    @Column(length = 45)
    @Comment("Nombre de la carpeta")
    private String nombre;
    @Column(length = 40)
    @Comment("Icono de la carpeta")
    private String icono;
    @ManyToOne
    @Comment("utilizado para sub carpetas o submenus que pertenecen a la padre")
    private Carpetas parent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private List<Carpetas> subCarpetas;
    @Column(name = "orden")
    @Comment("permite establecer el orden de los registros de carpetas")
    private Integer orden = 9999;
    @Column(name = "nivel")
    @Comment("permite establecer el nivel siendo 0 el primer nivel")
    private Integer nivel;

    @Column(columnDefinition = "number (1) default 1 not null")
    @Comment("Para determinar el eliminado lógico dato anterior")
    @Getter
    @Setter
    private Boolean estaActivo = true;
}
