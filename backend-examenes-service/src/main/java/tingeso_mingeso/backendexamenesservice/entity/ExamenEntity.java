package tingeso_mingeso.backendexamenesservice.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "examenes")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExamenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int puntaje;
    private String fecha_examen;
    private String rut_estudiante;
}
