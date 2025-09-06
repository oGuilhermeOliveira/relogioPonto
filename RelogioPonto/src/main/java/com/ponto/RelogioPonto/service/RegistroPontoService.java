package com.ponto.RelogioPonto.service;

import com.ponto.RelogioPonto.model.RegistroPonto;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class RegistroPontoService {
    public long calcularHoras(RegistroPonto ponto) {
        if (ponto.getEntrada() != null && ponto.getSaida() != null) {
            long horas = Duration.between(ponto.getEntrada(), ponto.getSaida()).toHours();
            if (ponto.isAlmoco()) horas -= 1;
            return Math.max(horas, 0);
        }
        return 0;
    }
}
