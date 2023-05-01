package superapp.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import superapp.Boundary.User.UserId;
import superapp.data.mainEntity.UserEntity;

import java.util.Optional;

public interface UserRepository
        extends MongoRepository<UserEntity, String> {

        @Query("{'userId.email': ?0}")
        Optional<UserEntity> findByEmail(String email);
}
