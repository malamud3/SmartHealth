package superapp.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import superapp.Boundary.User.UserId;
import superapp.data.mainEntity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, UserId> {
}
