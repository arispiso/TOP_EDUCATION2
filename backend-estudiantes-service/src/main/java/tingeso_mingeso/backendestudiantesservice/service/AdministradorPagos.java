package tingeso_mingeso.backendestudiantesservice.service;


import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdministradorPagos {

    private double arancel = 1500000;

    public double descuentoPorPagoAlContado() {
        return arancel - (arancel*0.5);
    }

    public double descuentoPorCuota(String colegio) {
        if (colegio.equalsIgnoreCase("Municipal")) {
            return arancel * 0.2;
        } else if (colegio.equalsIgnoreCase("Subvencionado")) {
            return arancel * 0.1;
        } else {
            return 0;
        }
    }

    public double descuentoPorAnyoEgreso(int anyo) {

        LocalDate fechaActual = LocalDate.now();
        int anyoActual = fechaActual.getYear();
        int diferenciaAnyos = anyoActual - anyo;

        if (diferenciaAnyos < 1) {
            return arancel * 0.15;
        } else if (diferenciaAnyos >= 1 && diferenciaAnyos <= 2) {
            return arancel * 0.08;
        } else if (diferenciaAnyos >= 3 && diferenciaAnyos <= 4) {
            return arancel * 0.04;
        } else {
            return 0;
        }
    }

    public double descuentoTotal(String colegio, int anyo) {
        return arancel - (descuentoPorCuota(colegio) + descuentoPorAnyoEgreso(anyo));
    }

    public double pagoPorCuotas(String colegio, int anyo, int numCuotas) {


        if (colegio.equalsIgnoreCase("Municipal") && numCuotas <= 10) {
            return descuentoTotal(colegio, anyo) / numCuotas;
        } else if (colegio.equalsIgnoreCase("Subvencionado") && numCuotas <= 7) {
            return descuentoTotal(colegio, anyo) / numCuotas;
        } else if (colegio.equalsIgnoreCase("Privado") && numCuotas <= 4) {
            return descuentoTotal(colegio, anyo) / numCuotas;
        } else {
            return 0;
        }
    }
}