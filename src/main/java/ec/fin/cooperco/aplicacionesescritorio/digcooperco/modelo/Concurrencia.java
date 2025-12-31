package ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@JsonIgnoreProperties(value = {"fechaCreacion", "fechaActualizacion", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
@ToString
public abstract class Concurrencia implements Serializable {

    @Comment("Numero de version para manejar la concurrencia")
    @Version
    @Column(columnDefinition = "number (38,0) default 0 not null")
    @Getter
    @Setter
    private Integer version = 0;

    @Column(columnDefinition = "number (1) default 1 not null")
    @Comment("Para determinar el eliminado lógico")
    @Getter @Setter
    private Boolean estado = true;

    @Comment("Fecha de creacion del registro")
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Getter @Setter
    private LocalDateTime fechaCreacion;

    @Comment("Fecha de actualizacion del registro")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion", nullable = false)
    @LastModifiedDate
    @Getter @Setter
    private LocalDateTime fechaActualizacion;

    @Comment("Usuario que crea el registro")
    @Column(name = "usuario", length = 100, nullable = false, updatable = false)
    @Getter @Setter
    @CreatedBy
    private String usuario;

    @Comment("Usuario que actualiza el registro")
    @Column(name = "usuario_actualiza", length = 100)
    @Getter @Setter
    @LastModifiedBy
    private String usuarioActualiza;

}

