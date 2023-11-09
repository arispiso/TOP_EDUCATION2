package tingeso_mingeso.backendestudiantesservice.service;


import com.example.TOP_EDUCATION.entities.CuotaEntity;
import com.example.TOP_EDUCATION.entities.EstudianteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.TOP_EDUCATION.repositories.EstudianteRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;

    @Autowired
    AdministradorPagos administradorPagos;

    @Autowired
    CuotaService cuotaService;


    public ArrayList<EstudianteEntity> obtenerEstudiantes(){
        return estudianteRepository.findAllStudents();
    }

    public EstudianteEntity obtenerPorRut(String rut){
        return estudianteRepository.findByRut(rut);
    }

    public void guardarEstudiante(EstudianteEntity estudiante){
        estudianteRepository.save(estudiante);
    }

    public void eliminarEstudiante(EstudianteEntity estudiante){
        estudianteRepository.delete(estudiante);
    }

    public int obtenerNumeroCuotas(String rut) {
        EstudianteEntity e = estudianteRepository.findByRut(rut);
        return e.getNum_cuotas();
    }

    public String obtenerTipoColegio (String rut) {
        EstudianteEntity e = estudianteRepository.findByRut(rut);
        return e.getColegio_procedente();
    }
    public int obtenerAnyoEgreso (String rut) {
        EstudianteEntity e = estudianteRepository.findByRut(rut);
        return e.getAnyo_Egreso();
    }

    public void generarCuota(EstudianteEntity e){

        String rut = e.getRut();
        int numCuotas = e.getNum_cuotas();
        String tipoColegio = e.getColegio_procedente();
        int anyo = e.getAnyo_Egreso();

        LocalDate fechaActual = LocalDate.now();
        String fecha = fechaActual.toString();

        double valor = 0;

        if (numCuotas == 1){
            valor = administradorPagos.descuentoPorPagoAlContado();
        }
        else {
            valor = administradorPagos.descuentoTotal(tipoColegio,anyo);
        }

        for (int i = 1; i<= numCuotas; i++) {
            cuotaService.guardarCuota(new CuotaEntity(i,"Pendiente", valor/numCuotas, numCuotas, rut,fecha));
        }

    }

}
