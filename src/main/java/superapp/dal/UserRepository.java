package superapp.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import superapp.Boundary.User.UserId;
import superapp.data.mainEntity.UserEntity;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository
        extends MongoRepository<UserEntity, UserId> {

        Optional<UserEntity> findByUserId(UserId userId);

}

