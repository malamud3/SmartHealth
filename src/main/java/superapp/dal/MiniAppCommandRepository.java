package superapp.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import superapp.Boundary.CommandId;
import superapp.Boundary.ObjectId;
import superapp.data.mainEntity.MiniAppCommandEntity;
import superapp.data.mainEntity.SuperAppObjectEntity;

import java.util.List;

public interface MiniAppCommandRepository
        extends MongoRepository<MiniAppCommandEntity, CommandId> {
    List<MiniAppCommandEntity> findAllByCommandIdMiniapp(String miniAppName);
}