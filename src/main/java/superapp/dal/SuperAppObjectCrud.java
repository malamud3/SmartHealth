package superapp.dal;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import superapp.Boundary.ObjectId;
import superapp.data.SuperAppObjectEntity;


import java.util.List;
import java.util.Optional;


public interface SuperAppObjectCrud
extends MongoRepository<SuperAppObjectEntity, ObjectId> {

	Optional<SuperAppObjectEntity> findByObjectId(ObjectId objectId);

	Optional<SuperAppObjectEntity> findByObjectIdAndActiveIsTrue(ObjectId objectId);

	List<SuperAppObjectEntity> findByActiveIsTrue(Pageable pageable);


	@Query("{ 'location' : { $geoWithin : { $centerSphere : [ [ ?1, ?0 ], ?2 ] } } }")
	List<SuperAppObjectEntity> findWithinCircle(double centerLat, double centerLng, double radius,Pageable pageable);

	@Query("{ $and: [ { 'location': { $geoWithin: { $centerSphere: [ [ ?1, ?0 ], ?2 ] } } }, { 'active': true } ] }")
	List<SuperAppObjectEntity> findWithinCircleAndActiveIsTrue(double centerLat, double centerLng, double radius,Pageable pageable);


	List<SuperAppObjectEntity> findByLocationNearAndActiveIsTrue(Point location, Distance distance, Pageable pageable);

	List<SuperAppObjectEntity> findByAlias(String alias, Pageable pageable);

	List<SuperAppObjectEntity> findByAliasAndActiveIsTrue(String alias, Pageable pageable);

	List<SuperAppObjectEntity> findByType(String type, Pageable pageable);

	List<SuperAppObjectEntity> findByTypeAndActiveIsTrue(String type, Pageable pageable);

	List<SuperAppObjectEntity> findByParentObjectsContaining(SuperAppObjectEntity parentObject, Pageable pageable);

	List<SuperAppObjectEntity> findByParentObjectsContainingAndActiveIsTrue(SuperAppObjectEntity parentObject, Pageable pageable);

	List<SuperAppObjectEntity> findByChildObjectsContaining(SuperAppObjectEntity childObject, Pageable pageable);

	List<SuperAppObjectEntity> findByChildObjectsContainingAndActiveIsTrue(SuperAppObjectEntity childObject, Pageable pageable);





}


