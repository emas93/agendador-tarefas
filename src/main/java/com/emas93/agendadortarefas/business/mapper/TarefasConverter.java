package com.emas93.agendadortarefas.business.mapper;

import com.emas93.agendadortarefas.business.dto.TarefasDTO;
import com.emas93.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConverter {
    TarefasEntity paraTarefasEntity(TarefasDTO tarefasDTO);

    TarefasDTO paraTarefasDTO(TarefasEntity tarefasEntity);
}
