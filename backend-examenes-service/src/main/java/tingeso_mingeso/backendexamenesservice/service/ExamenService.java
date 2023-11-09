package tingeso_mingeso.backendexamenesservice.service;

import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tingeso_mingeso.backendexamenesservice.entity.ExamenEntity;
import tingeso_mingeso.backendexamenesservice.model.CuotaEntity;
import tingeso_mingeso.backendexamenesservice.repository.ExamenRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class ExamenService {


    @Autowired
    ExamenRepository examenRepository;

    @Autowired
    CuotaService cuotaService;

    @Autowired
    RestTemplate restTemplate;

    private final Logger logg = LoggerFactory.getLogger(ExamenService.class);


    //ESTÁ MAL:   REVISAR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public CuotaEntity findByRut(String rut){
        System.out.println("rut: "+rut);

        ResponseEntity<CuotaEntity> response = restTemplate.exchange(
                "http://localhost:8080/estudiante/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CuotaEntity>() {}
        );
        return response.getBody();
    }


    public ArrayList<ExamenEntity> obtenerExamenes(){
        return (ArrayList<ExamenEntity>) examenRepository.findAll();
    }

    public int obtenerNumExamenes(String rut){
        return examenRepository.obetenerNumExamenes(rut);
    }
    public double obtenerPromedioExamenes(String rut){
        return examenRepository.obetenerpromedioExamenes(rut);
    }
    public void guardarExamen(ExamenEntity examen){
        examenRepository.save(examen);
        cuotaService.descuentoPorExamenAdmision(examen.getPuntaje());
    }

    public void guardarExamenBD(int puntaje, String fecha_examen, String rut_estudiante){
        ExamenEntity examen = new ExamenEntity();
        examen.setPuntaje(puntaje);
        examen.setFecha_examen(fecha_examen);
        examen.setRut_estudiante(rut_estudiante);
        guardarExamen(examen);
        cuotaService.descuentoPorExamenAdmision(puntaje);
    }

    public void eliminarExamen(ExamenEntity examen){
        examenRepository.delete(examen);
    }

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void leerCsv(String direccion){

        String texto = "";
        BufferedReader bf = null;

        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            System.out.println("DENTRO");
            while((bfRead = bf.readLine()) != null){
                System.out.println("DENTRO1");
                if (count == 1){
                    count = 0;
                    System.out.println("DENTRO2");
                }
                else{
                    System.out.println("DENTRO3");
                    guardarExamenBD(Integer.parseInt(bfRead.split(";")[0]), bfRead.split(";")[1], bfRead.split(";")[2]);
                    System.out.println("DENTRO4");
                    temp = temp + "\n" + bfRead;
                    System.out.println("DENTRO5");
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

}
