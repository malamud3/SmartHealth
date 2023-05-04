package superapp.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import superapp.Boundary.CommandId;
import superapp.data.mainEntity.MiniAppCommandEntity;

public interface MiniAppCommandRepository
        extends MongoRepository<MiniAppCommandEntity, CommandId> {
}
