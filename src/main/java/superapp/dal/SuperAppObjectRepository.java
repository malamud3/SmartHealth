package superapp.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import superapp.Boundary.ObjectId;
import superapp.Boundary.User.UserId;
import superapp.data.mainEntity.SuperAppObjectEntity;
import superapp.data.mainEntity.UserEntity;

import java.util.Optional;


public interface  SuperAppObjectRepository
        extends MongoRepository<SuperAppObjectEntity, ObjectId> {

}
