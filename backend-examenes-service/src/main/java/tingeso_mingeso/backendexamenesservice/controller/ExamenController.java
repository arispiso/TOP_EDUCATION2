package tingeso_mingeso.backendexamenesservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tingeso_mingeso.backendexamenesservice.entity.ExamenEntity;
import tingeso_mingeso.backendexamenesservice.service.ExamenService;

import java.util.ArrayList;
@RestController
@RequestMapping("/examen")
public class ExamenController {

    @Autowired
    ExamenService examenService;

    @GetMapping("/adjuntarPrueba")
    public String examenes(Model model) {
        ArrayList<ExamenEntity> examenes = examenService.obtenerExamenes();
        model.addAttribute("examenes",examenes);
        return "adjuntarPrueba";
    }

    @PostMapping("/adjuntarPrueba")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        examenService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        examenService.leerCsv(file.getOriginalFilename());
        return "redirect:/adjuntarPrueba";
    }
}
