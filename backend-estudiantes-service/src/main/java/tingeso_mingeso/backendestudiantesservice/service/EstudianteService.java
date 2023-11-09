package tingeso_mingeso.backendestudiantesservice.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tingeso_mingeso.backendestudiantesservice.entity.EstudianteEntity;
import tingeso_mingeso.backendestudiantesservice.repository.EstudianteRepository;


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

    @Autowired
    RestTemplate restTemplate;

    //ESTÁ MAL:   REVISAR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public EstudianteEntity findByRut(String rut){
        System.out.println("rut: "+rut);

        ResponseEntity<EstudianteEntity> response = restTemplate.exchange(
                "http://localhost:8080/estudiante/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<EstudianteEntity>() {}
        );
        return response.getBody();
    }

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
