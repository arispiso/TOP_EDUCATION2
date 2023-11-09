package tingeso_mingeso.backendcuotasservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tingeso_mingeso.backendcuotasservice.entity.CuotaEntity;
import tingeso_mingeso.backendcuotasservice.model.EstudianteEntity;
import tingeso_mingeso.backendcuotasservice.repository.CuotaRepository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CuotaService {

    @Autowired
    CuotaRepository cuotaRepository;

    @Autowired
    RestTemplate restTemplate;

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

    public ArrayList<CuotaEntity> obtenerCuotas(){
        return (ArrayList<CuotaEntity>) cuotaRepository.findAll();
    }

    public ArrayList<CuotaEntity> obtenerCuotasPorRUT(String rut){
        return cuotaRepository.findByRutEstudiante(rut);
    }

    public double calcularValorCuotas(String rut){

        ArrayList<CuotaEntity> cuotas = obtenerCuotasPorRUT(rut);
        double valor = 0;
        for (int i = 0; i< cuotas.size(); i++){
            valor+=cuotas.get(i).getValor();
        }
        return valor;
    }

    public double obtenerValorPagado(String rut){
        List<Double> resultado = cuotaRepository.obtenerValorPagado(rut);
        if(resultado.get(0)==null){
            return 0;
        }
        return resultado.get(0);
    }
    public double obtenerValorPendiente(String rut){
        List<Double> resultado = cuotaRepository.obtenerValorPendiente(rut);
        if(resultado.get(0)==null){
            return 0;
        }
        return resultado.get(0);
    }
    public int obtenerNumCuotasPagadas(String rut){
        return cuotaRepository.obtenerNumCuotasPagadas(rut);
    }

    public int obtenerNumCuotasPendientes(String rut){
        return cuotaRepository.obtenerNumCuotasPendientes(rut);
    }

    public CuotaEntity obtenerPorId(int id) {
        return cuotaRepository.findById(id);
    }
    public void guardarCuota(CuotaEntity cuota){
        cuotaRepository.save(cuota);
    }

    public void eliminarCuota(CuotaEntity cuota){
        cuotaRepository.delete(cuota);
    }

    //Método para pasar el estado de la cuota de Pendiente a Pagado
    public void actualizarEstado(int id){

        LocalDate fechaActual = LocalDate.now();
        int diaActual = fechaActual.getDayOfMonth();

        //MENOS % EN LOS TEST PORQUE NO HACE ESTA PARTE!
        if(diaActual>=5 && diaActual<=10){
            CuotaEntity c =  cuotaRepository.findById(id);

            c.setEstado("Solicitado para pagar");

            guardarCuota(c);
        }
    }

    public void pagarCuota(){

        ArrayList<CuotaEntity> cuotas =  obtenerCuotas();

        for(int i = 0; i<=cuotas.size()-1;i++){

            CuotaEntity c = cuotas.get(i);

            if(c.getEstado().equalsIgnoreCase("Pendiente")){
                actualizarEstado(c.getId());
            }
        }
        interesPorAtraso();
    }


    public void descuentoPorExamenAdmision(int puntaje) {

        ArrayList<CuotaEntity> cuotas =  cuotaRepository.findAllCuotas();
        for(int i=0; i<=cuotas.size()-1; i++){
            CuotaEntity c = cuotas.get(i);
            if (c.getEstado().equalsIgnoreCase("Pendiente")) {
                if (puntaje >= 950 && puntaje <= 1000) {
                    c.setValor(c.getValor() - (c.getValor() * 0.1));
                    guardarCuota(c);
                } else if (puntaje >= 900 && puntaje <= 949) {
                    c.setValor(c.getValor() - (c.getValor() * 0.05));
                    guardarCuota(c);
                } else if (puntaje >= 850 && puntaje <= 899) {
                    c.setValor(c.getValor() - (c.getValor() * 0.02));
                    guardarCuota(c);
                }
            }
        }
    }

    public void interesPorAtraso() {

        ArrayList<CuotaEntity> cuotas = obtenerCuotas();

        //El estudiante tiene 1 mes desde que solicitó pagar la cuota para que no le cobren intereses
        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();

        for (int i = 0; i<= cuotas.size()-1; i++) {

            CuotaEntity c = cuotas.get(i);

            String fecha = c.getFecha_cuota();
            String[] partes = fecha.split("-");
            int mes = Integer.parseInt(partes[1]);

            int diferenciaMeses = mesActual - mes;

            if (diferenciaMeses <= 0) {
                guardarCuota(c);
            }
            else if (diferenciaMeses == 1) {
                c.setValor(c.getValor() + (c.getValor() * 0.03));
                guardarCuota(c);
            }
            else if (diferenciaMeses == 2) {
                c.setValor(c.getValor() + (c.getValor() * 0.06));
                guardarCuota(c);
            }
            else if (diferenciaMeses == 3) {
                c.setValor(c.getValor() + (c.getValor() * 0.09));
                guardarCuota(c);
            }
            else {
                c.setValor(c.getValor() + (c.getValor() * 0.15));
                guardarCuota(c);
            }
        }
    }
}
