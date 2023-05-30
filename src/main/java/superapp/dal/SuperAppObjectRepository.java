package superapp.dal;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import superapp.Boundary.Location;
import superapp.Boundary.ObjectId;
import superapp.data.SuperAppObjectEntity;

import java.util.List;
import java.util.Optional;


public interface  SuperAppObjectRepository
        extends MongoRepository<SuperAppObjectEntity, ObjectId> {

	Optional<SuperAppObjectEntity> findByObjectId(ObjectId objectId);
	
	Optional<SuperAppObjectEntity> findByObjectIdAndActiveIsTrue(ObjectId objectId);
    
	List<SuperAppObjectEntity> findByActiveIsTrue(Pageable pageable);
	
	//List<SuperAppObjectEntity> findByLocationNear(Point location, Distance distance, Pageable pageable);
	
	@Query(value = "{'location': {$geoWithin: {$centerSphere: [ [ ?0, ?1 ], ?2 ]}}}")
    List<SuperAppObjectEntity> findByLocationWithinRadius(double latitude, double longitude, Distance radius, Pageable pageable);
	
	List<SuperAppObjectEntity> findByLocationNearAndActiveIsTrue(Point location, Distance distance, Pageable pageable);
	
    List<SuperAppObjectEntity> findByAlias(String alias, Pageable pageable);
    
    List<SuperAppObjectEntity> findByAliasAndActiveIsTrue(String alias, Pageable pageable);
    
    List<SuperAppObjectEntity> findByType(String type, Pageable pageable);
    
    List<SuperAppObjectEntity> findByTypeAndActiveIsTrue(String type, Pageable pageable);
    
    List<SuperAppObjectEntity> findByParentObjects_ObjectIdAndActiveIsTrue(ObjectId parentId, Pageable pageable);
    
    List<SuperAppObjectEntity> findByParentObjects_ObjectId(ObjectId parentId, Pageable pageable);
    
    List<SuperAppObjectEntity> findByChildObjects_ObjectId(ObjectId childObjectId, Pageable pageable);
    
    List<SuperAppObjectEntity> findByChildObjects_ObjectIdAndActiveIsTrue(ObjectId childObjectId, Pageable pageable);


}
