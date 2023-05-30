package superapp.dal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import superapp.Boundary.CommandId;
import superapp.data.MiniAppCommandEntity;

import java.util.List;

public interface MiniAppCommandRepository
        extends MongoRepository<MiniAppCommandEntity, CommandId> {
	
	List<MiniAppCommandEntity> findAllByCommandIdMiniapp(String miniapp, Pageable pageable);
}