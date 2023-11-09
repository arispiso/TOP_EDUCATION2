package tingeso_mingeso.backendcuotasservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tingeso_mingeso.backendcuotasservice.entity.CuotaEntity;
import tingeso_mingeso.backendcuotasservice.service.CuotaService;

import java.util.ArrayList;

public class CuotaController {

    @Autowired
    CuotaService cuotaService;

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

}
