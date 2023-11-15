package tingeso_mingeso.backendcuotasservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data //getters y setters de lombok
@NoArgsConstructor
@AllArgsConstructor //constructor con y sin parametros de lombok
public class EstudianteModel {
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