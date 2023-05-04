package superapp.dal;


import org.springframework.data.mongodb.repository.MongoRepository;
import superapp.Boundary.ObjectId;

public interface  SuperAppObjectRelationshipRepository
        extends MongoRepository<SuperAppObjectRelationshipRepository, ObjectId> {

}