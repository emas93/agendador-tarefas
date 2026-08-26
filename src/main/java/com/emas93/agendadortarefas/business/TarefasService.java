package com.emas93.agendadortarefas.business;

import com.emas93.agendadortarefas.business.dto.TarefasDTO;
import com.emas93.agendadortarefas.business.mapper.TarefasConverter;
import com.emas93.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.emas93.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.emas93.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.emas93.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO salvaTarefa(TarefasDTO tarefasDTO, String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        tarefasDTO.setEmailUsuario(email);
        tarefasDTO.setDataCriacao(LocalDateTime.now());
        tarefasDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity tarefa = tarefasConverter.paraTarefasEntity(tarefasDTO);
        return tarefasConverter.paraTarefasDTO(tarefasRepository.save(tarefa));
    }

}
