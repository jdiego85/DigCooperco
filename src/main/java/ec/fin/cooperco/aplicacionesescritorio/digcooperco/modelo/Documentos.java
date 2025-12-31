package ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;

@Entity
@Table(name = "dig_documentos")
@EntityListeners(AuditingEntityListener.class)
@Comment ( "Tabla que permite los almacenar los documentos")
@Data
public class Documentos extends Concurrencia  implements java.io.Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    @Comment("Id de la tabla")
    private String id;
    @Column(length = 80)
    @Comment("Nombre del documento")
    private String nombre;
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("Permite registrar en que carpeta estará este documento")
    private Carpetas carpeta;

    @Column(columnDefinition = "number (1) default 1 not null")
    @Comment("Para determinar el eliminado lógico dato anterior")
    @Getter
    @Setter
    private Boolean estaActivo = true;
}

