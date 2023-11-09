package tingeso_mingeso.backendestudiantesservice.controller;
import com.example.TOP_EDUCATION.entities.EstudianteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    EstudianteService estudianteService; //la clase controller, referencia a EstudianteService, va a usar funcionalidades de esa capa
    @GetMapping("/datos")
    public String datos(Model model) {
        ArrayList<EstudianteEntity> estudiantes = estudianteService.obtenerEstudiantes();
        model.addAttribute("estudiantes",estudiantes);
        return "datos";
    }

    @PostMapping("/index")
    public String nuevoEstudiante(@RequestParam("rut") String rut,
                                  @RequestParam("nombre") String nombre,
                                  @RequestParam("apellidos") String apellidos,
                                  @RequestParam("fecha") String fechaNacimiento,
                                  @RequestParam("NombreColegio") String nombreColegio,
                                  @RequestParam("colegio") String colegio,
                                  @RequestParam("anyoEgreso") int anyoEgreso,
                                  @RequestParam("pago") String pago,
                                  @RequestParam("numCuotas") int num_cuotas) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(rut + "  " + nombre + "    " + "  " + apellidos + "    " + "  " + fechaNacimiento + "    " + "  " + nombreColegio + "    " + colegio);
        Date fechaNacimientoModify = formato.parse(fechaNacimiento);
        EstudianteEntity e = new EstudianteEntity(rut, nombre, apellidos, fechaNacimientoModify, colegio, nombreColegio, anyoEgreso, pago, num_cuotas);
        estudianteService.guardarEstudiante(e);
        estudianteService.generarCuota(e);

        return "redirect:/datos";
    }
}
