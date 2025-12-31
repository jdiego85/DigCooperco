package ec.fin.cooperco.aplicacionesescritorio.digcooperco.modelo;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

@Entity
@Table(name = "dig_parametros")

@EntityListeners(AuditingEntityListener.class)
@Data
@Comment ("Tabla que contiene los parámetros a utilizar de manera general para la digitalización de documentos")
public class Parametros extends Concurrencia  implements java.io.Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    @Comment("Id de la tabla")
    private String id;
    @Comment("Almacena la la URL del servidor de nextcloud")
    @Column(name = "url_servidor")
    private String urlServidor;
    @Comment("Almacena el tiempo atrás máximo que se puede escanear los documentos")
    @Column(name = "tiempo_atras_maximo_dias")
    private Integer tiempoAtrasMaximoDias;

    @Column(columnDefinition = "number (1) default 1 not null")
    @Comment("Para determinar el eliminado lógico dato anterior")
    @Getter
    @Setter
    private Boolean estaActivo = true;
}
