package tingeso_mingeso.backendestudiantesservice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tingeso_mingeso.backendestudiantesservice.entity.EstudianteEntity;
import tingeso_mingeso.backendestudiantesservice.service.EstudianteService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    EstudianteService estudianteService; //la clase controller, referencia a EstudianteService, va a usar funcionalidades de esa capa

    @PostMapping()
    public ResponseEntity<EstudianteEntity> nuevoEstudiante(@RequestBody EstudianteEntity estudiante) {
        /*
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(rut + "  " + nombre + "    " + "  " + apellidos + "    " + "  " + fechaNacimiento + "    " + "  " + nombreColegio + "    " + colegio);
        Date fechaNacimientoModify = formato.parse(fechaNacimiento);
        EstudianteEntity e = new EstudianteEntity(rut, nombre, apellidos, fechaNacimientoModify, colegio, nombreColegio, anyoEgreso, pago, num_cuotas);
        estudianteService.guardarEstudiante(e);
        estudianteService.generarCuota(e);

        return "redirect:/datos";
        */

        estudianteService.guardarEstudiante(estudiante);
        return ResponseEntity.ok(estudiante);

    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<EstudianteEntity>> listar() {
        ArrayList<EstudianteEntity> estudianteEntities = estudianteService.obtenerEstudiantes();
        return ResponseEntity.ok(estudianteEntities);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<EstudianteEntity> findByRut(@PathVariable("rut") String rut) {
        EstudianteEntity estudianteEntity = estudianteService.obtenerPorRut(rut);
        System.out.println(estudianteEntity);
        return ResponseEntity.ok(estudianteEntity);
    }
     /*  @GetMapping("/datos")
      public String datos(Model model) {
          ArrayList<EstudianteEntity> estudiantes = estudianteService.obtenerEstudiantes();
          model.addAttribute("estudiantes",estudiantes);
          return "datos";
      }
  */
}
