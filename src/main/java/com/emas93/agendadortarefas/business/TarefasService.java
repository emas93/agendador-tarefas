package com.emas93.agendadortarefas.business;

import com.emas93.agendadortarefas.business.dto.TarefasDTO;
import com.emas93.agendadortarefas.business.mapper.TarefasConverter;
import com.emas93.agendadortarefas.business.mapper.TarefasUpdateConverter;
import com.emas93.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.emas93.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.emas93.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.emas93.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.emas93.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateConverter tarefasUpdateConverter;

    public TarefasDTO salvaTarefa(TarefasDTO tarefasDTO, String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        tarefasDTO.setEmailUsuario(email);
        tarefasDTO.setDataCriacao(LocalDateTime.now());
        tarefasDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity tarefa = tarefasConverter.paraTarefasEntity(tarefasDTO);
        return tarefasConverter.paraTarefasDTO(tarefasRepository.save(tarefa));
    }

    public List<TarefasDTO> buscaTarefasPorDataAgendamento(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefasConverter.paraListaTarefasDTOs(tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(dataInicial, dataFinal,StatusNotificacaoEnum.PENDENTE));
    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefasConverter.paraListaTarefasDTOs(tarefasRepository.findByEmailUsuario(email));
    }

    public void deletaTarefaPorId(String id) {
        tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi possível deletar tarefa pelo Id " + id + " ,pois este não consta na base de dados."));
        tarefasRepository.deleteById(id);
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity tarefasEntity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada."));
            tarefasEntity.setStatusNotificacaoEnum(status);
            tarefasEntity.setDataAlteracao(LocalDateTime.now());
        return tarefasConverter.paraTarefasDTO(tarefasRepository.save(tarefasEntity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar o status ta tarefa " + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO tarefasDTO, String id) {
        try {
            TarefasEntity tarefasEntity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada."));
            tarefasUpdateConverter.updateTarefas(tarefasDTO, tarefasEntity);
            return tarefasConverter.paraTarefasDTO(tarefasRepository.save(tarefasEntity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar o status ta tarefa " + e.getCause());
        }
    }
}
