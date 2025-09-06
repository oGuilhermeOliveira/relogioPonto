package com.ponto.RelogioPonto.dto;

import java.time.LocalDateTime;

public class RegistroPontoDTO {
    private Long id;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private boolean almoco;
    private long horas;

    public RegistroPontoDTO(Long id, LocalDateTime entrada, LocalDateTime saida, boolean almoco, long horas) {
        this.id = id;
        this.entrada = entrada;
        this.saida = saida;
        this.almoco = almoco;
        this.horas = horas;
    }

    public Long getId() { return id; }
    public LocalDateTime getEntrada() { return entrada; }
    public LocalDateTime getSaida() { return saida; }
    public boolean isAlmoco() { return almoco; }
    public long getHoras() { return horas; }
}