package com.emas93.agendadortarefas.controller;

import com.emas93.agendadortarefas.business.TarefasService;
import com.emas93.agendadortarefas.business.dto.TarefasDTO;
import com.emas93.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.emas93.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {
    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO tarefasDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.salvaTarefa(tarefasDTO, token));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscarTarefasPorPeriodoEvento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {
        return ResponseEntity.ok(tarefasService.buscaTarefasPorDataAgendamento(dataInicial, dataFinal));

    }

    @GetMapping("/email")
    public ResponseEntity<List<TarefasDTO>> buscarTarefasPorEmail(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token));

    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTarefa(String id) {
        tarefasService.deletaTarefaPorId(id);
        return ResponseEntity.ok().build();

    }

    @PatchMapping
    public ResponseEntity<TarefasDTO> alterarStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                                               @RequestParam("id") String id){
        return ResponseEntity.ok(tarefasService.alteraStatus(status,id));
    }

    @PutMapping
    public ResponseEntity<TarefasDTO> updateTarefas(@RequestBody TarefasDTO tarefasDTO,@RequestParam("id") String id){
        return ResponseEntity.ok(tarefasService.updateTarefas(tarefasDTO,id));
    }
}
