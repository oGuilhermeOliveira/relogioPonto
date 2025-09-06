package com.ponto.RelogioPonto.controller;

import com.ponto.RelogioPonto.model.RegistroPonto;
import com.ponto.RelogioPonto.repository.RegistroPontoRepository;
import com.ponto.RelogioPonto.service.RegistroPontoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class HomeController {

    private final RegistroPontoRepository repository;
    private final RegistroPontoService service;

    public HomeController(RegistroPontoRepository repository, RegistroPontoService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "valorHora", required = false, defaultValue = "9") Integer valorHora) {

        List<RegistroPonto> registros = repository.findAll();
        long totalHoras = registros.stream().mapToLong(service::calcularHoras).sum();
        long totalReceber = totalHoras * valorHora;

        model.addAttribute("registros", registros);
        model.addAttribute("svc", service);
        model.addAttribute("valorHora", valorHora);
        model.addAttribute("totalHoras", totalHoras);
        model.addAttribute("totalReceber", totalReceber);

        return "index";
    }

    @PostMapping("/entrada")
    public String entrada(@RequestParam String horaEntrada,
                          @RequestParam(name="valorHora", required=false, defaultValue="9") Integer valorHora) {
        LocalTime time = LocalTime.parse(horaEntrada);
        LocalDateTime entrada = LocalDateTime.of(LocalDate.now(), time);

        RegistroPonto ponto = new RegistroPonto();
        ponto.setEntrada(entrada);
        repository.save(ponto);

        return "redirect:/?valorHora=" + valorHora;
    }

    @PostMapping("/saida")
    public String saida(@RequestParam String horaSaida,
                        @RequestParam(name="valorHora", required=false, defaultValue="9") Integer valorHora) {
        LocalTime time = LocalTime.parse(horaSaida);
        LocalDateTime saida = LocalDateTime.of(LocalDate.now(), time);

        RegistroPonto ponto = repository.findAll().stream()
                .filter(p -> p.getSaida() == null)
                .findFirst()
                .orElse(null);

        if (ponto != null) {
            ponto.setSaida(saida);
            repository.save(ponto);
        }

        return "redirect:/?valorHora=" + valorHora;
    }

    @PostMapping("/almoco")
    public String almoco(@RequestParam(required=false) String almoco,
                         @RequestParam(name="valorHora", required=false, defaultValue="9") Integer valorHora) {
        RegistroPonto ponto = repository.findAll().stream()
                .filter(p -> p.getSaida() == null)
                .findFirst()
                .orElse(null);

        if (ponto != null) {
            ponto.setAlmoco(almoco != null);
            repository.save(ponto);
        }

        return "redirect:/?valorHora=" + valorHora;
    }
    @PostMapping("/delete/{id}")
    public String deleteRegistro(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/deleteAll")
    public String deleteAll() {
        repository.deleteAll();
        return "redirect:/";
    }

}
