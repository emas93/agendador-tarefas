package com.emas93.agendadortarefas.controller;

import com.emas93.agendadortarefas.business.TarefasService;
import com.emas93.agendadortarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {
    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO tarefasDTO, @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(tarefasService.salvaTarefa(tarefasDTO,token));
    }

}
