package superapp.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import superapp.Boundary.User.UserId;
import superapp.data.UserEntity;

import java.util.Optional;


public interface UserRepository
        extends MongoRepository<UserEntity, UserId> {

        Optional<UserEntity> findByUserId(UserId userId);


}

