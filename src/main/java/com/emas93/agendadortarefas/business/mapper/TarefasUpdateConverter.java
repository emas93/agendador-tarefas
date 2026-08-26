package com.emas93.agendadortarefas.business.mapper;

import com.emas93.agendadortarefas.business.dto.TarefasDTO;
import com.emas93.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring" ,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefasUpdateConverter {

    void updateTarefas (TarefasDTO tarefasDTO,@MappingTarget TarefasEntity tarefasEntity);

}
