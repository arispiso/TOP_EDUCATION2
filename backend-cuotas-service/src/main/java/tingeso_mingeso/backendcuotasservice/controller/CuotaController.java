package tingeso_mingeso.backendcuotasservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tingeso_mingeso.backendcuotasservice.entity.CuotaEntity;
import tingeso_mingeso.backendcuotasservice.model.EstudianteModel;
import tingeso_mingeso.backendcuotasservice.service.CuotaService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cuota")
public class CuotaController {

    @Autowired
    CuotaService cuotaService;

    /*

    @GetMapping("/pagos")
    public String cuotas(Model model) {
        ArrayList<CuotaEntity> cuotas = cuotaService.obtenerCuotas();
        model.addAttribute("cuotas",cuotas);
        return "pagos";
    }

    @PostMapping("/datos")
    public String nuevaCuota() {
        return "redirect:/pagos";
    }

    @PostMapping("/pagos")
    public String pagarCuota() {
        cuotaService.pagarCuota();
        return "redirect:/datos";
    }
*/


    @GetMapping("/{rut}/{cuotas}")
    public ResponseEntity<List<CuotaEntity>> cuotas(@PathVariable("rut") String rut, @PathVariable("cuotas") String cuotas){

        EstudianteModel estudianteEntity = cuotaService.findByRut(rut);
        System.out.println(estudianteEntity);

        if(estudianteEntity != null){
            if(cuotaService.obtenerCuotasPorRUT(estudianteEntity.getRut()).isEmpty()){
                //cuotaService.descuentoArancel_generacionCuotas(estudianteEntity, Integer.parseInt(cuotas));
                List<CuotaEntity> cuotasEntities = cuotaService.obtenerCuotasPorRUT(estudianteEntity.getRut());
                System.out.println(cuotasEntities);
                return ResponseEntity.ok(cuotasEntities);
            }
        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<List<CuotaEntity>> listado(@PathVariable("rut") String rut){
        System.out.println("Listar");
        System.out.println("rut: "+rut+"\n");
        EstudianteModel estudianteEntity = cuotaService.findByRut(rut);
        System.out.println(estudianteEntity);
        if(estudianteEntity != null){
            List<CuotaEntity> cuotasEntities = cuotaService.obtenerCuotasPorRUT(estudianteEntity.getRut());
            return ResponseEntity.ok(cuotasEntities);
        }
        return ResponseEntity.ok(null);
    }

}
