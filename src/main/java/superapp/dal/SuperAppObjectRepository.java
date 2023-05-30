package superapp.dal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import superapp.Boundary.ObjectId;
import superapp.data.mainEntity.SuperAppObjectEntity;

import java.util.List;
import java.util.Optional;


public interface  SuperAppObjectRepository
        extends MongoRepository<SuperAppObjectEntity, ObjectId> {

	Optional<SuperAppObjectEntity> findByObjectId(ObjectId objectId);
    List<SuperAppObjectEntity> findByActiveTrue();
    Page<SuperAppObjectEntity> findByAlias(String alias, Pageable pageable);
    Page<SuperAppObjectEntity> searchByType(String type, Pageable pageable);

    List<SuperAppObjectEntity> findByChildObjects(ObjectId childObjectId, Pageable pageable);
}
