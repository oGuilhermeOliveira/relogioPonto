package com.ponto.RelogioPonto.controller;


import com.ponto.RelogioPonto.model.RegistroPonto;
import com.ponto.RelogioPonto.repository.RegistroPontoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class PontoController {

    private final RegistroPontoRepository repository;

    public PontoController(RegistroPontoRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/ponto/entrada")
    public String registrarEntrada(@RequestParam("horaEntrada") String horaEntrada) {
        try {
            // converte "HH:mm" em LocalTime
            LocalTime time = LocalTime.parse(horaEntrada);
            // combina com a data de hoje
            LocalDateTime entrada = LocalDateTime.of(LocalDate.now(), time);

            RegistroPonto ponto = new RegistroPonto();
            ponto.setEntrada(entrada);
            repository.save(ponto);
        } catch (Exception e) {
            e.printStackTrace(); // loga erro se a string estiver inválida
        }
        return "redirect:/";
    }

    @PostMapping("/ponto/saida")
    public String registrarSaida(@RequestParam("horaSaida") String horaSaida) {
        try {
            LocalTime time = LocalTime.parse(horaSaida);
            LocalDateTime saida = LocalDateTime.of(LocalDate.now(), time);

            // pega o último registro sem saída
            RegistroPonto ponto = repository.findAll()
                    .stream()
                    .filter(p -> p.getSaida() == null)
                    .findFirst()
                    .orElse(null);

            if (ponto != null) {
                ponto.setSaida(saida);
                repository.save(ponto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @PostMapping("/ponto/almoco")
    public String registrarAlmoco(@RequestParam(value = "almoco", required = false) String almoco) {
        RegistroPonto ponto = repository.findAll()
                .stream()
                .filter(p -> p.getSaida() == null)
                .findFirst()
                .orElse(null);

        if (ponto != null) {
            ponto.setAlmoco(almoco != null); // se checkbox marcado → true
            repository.save(ponto);
        }

        return "redirect:/";
    }
}