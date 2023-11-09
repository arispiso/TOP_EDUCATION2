package tingeso_mingeso.backendestudiantesservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaEntity {

    private String estado;
    private double valor;
    private int cantidad_cuotas;
    private String rutEstudiante;
    private String fecha_cuota;

}
