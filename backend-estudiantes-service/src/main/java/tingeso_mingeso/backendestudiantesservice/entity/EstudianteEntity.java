package tingeso_mingeso.backendestudiantesservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "estudiantes") //Mapeo de la clase estudiante con la tabla estudiantes de la BD
@Data //getters y setters de lombok
@NoArgsConstructor
@AllArgsConstructor //constructor con y sin parametros de lombok


public class EstudianteEntity {

    @Id
    @Column(unique = true, nullable = false)
    private String rut;

    private String nombre;
    private String apellidos;
    private Date fecha_nacimiento;
    private String colegio_procedente;
    private String nombre_colegio;
    private int anyo_Egreso;
    private String pago;
    private int num_cuotas;
}