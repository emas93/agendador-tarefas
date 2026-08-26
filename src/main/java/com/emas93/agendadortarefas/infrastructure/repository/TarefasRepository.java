package com.emas93.agendadortarefas.infrastructure.repository;

import com.emas93.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TarefasRepository extends MongoRepository <TarefasEntity,String>{

}
