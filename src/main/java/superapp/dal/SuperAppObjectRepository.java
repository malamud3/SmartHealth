package superapp.dal;

import org.springframework.data.domain.Page;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import superapp.Boundary.ObjectId;
import superapp.data.SuperAppObjectEntity;


import java.util.List;
import java.util.Optional;


public interface  SuperAppObjectRepository
        extends MongoRepository<SuperAppObjectEntity, ObjectId> {

	Optional<SuperAppObjectEntity> findByObjectId(ObjectId objectId);
    
	List<SuperAppObjectEntity> findByActiveIsTrue();
	
	List<SuperAppObjectEntity> findByLocationNear(Point location, Distance distance, Pageable pageable);
	
	List<SuperAppObjectEntity> findByLocationNearAndActiveIsTrue(Point location, Distance distance, Pageable pageable);
	
    Page<SuperAppObjectEntity> findByAlias(String alias, Pageable pageable);
    
    Page<SuperAppObjectEntity> searchByType(String type, Pageable pageable);
    
    List<SuperAppObjectEntity> findByParentObjects_ObjectIdAndActiveIsTrue(ObjectId parentId, Pageable pageable);
    
    List<SuperAppObjectEntity> findByParentObjects_ObjectId(ObjectId parentId, Pageable pageable);
    
    List<SuperAppObjectEntity> findByChildObjects_ObjectId(ObjectId childObjectId, Pageable pageable);
    
    List<SuperAppObjectEntity> findByChildObjects_ObjectIdAndActiveIsTrue(ObjectId childObjectId, Pageable pageable);


}
