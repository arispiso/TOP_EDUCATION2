package tingeso_mingeso.backendcuotasservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuotas")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CuotaEntity {
    @Id
    @Column(unique = true, nullable = false)
    private int id;

    private String estado;
    private double valor;
    private int cantidad_cuotas;
    private String rutEstudiante;
    private String fecha_cuota;

}
