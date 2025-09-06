package com.ponto.RelogioPonto.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RegistroPonto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime entrada;
    private LocalDateTime saida;
    private boolean almoco;

    public Long getId() { return id; }
    public LocalDateTime getEntrada() { return entrada; }
    public void setEntrada(LocalDateTime entrada) { this.entrada = entrada; }
    public LocalDateTime getSaida() { return saida; }
    public void setSaida(LocalDateTime saida) { this.saida = saida; }
    public boolean isAlmoco() { return almoco; }
    public void setAlmoco(boolean almoco) { this.almoco = almoco; }
}
