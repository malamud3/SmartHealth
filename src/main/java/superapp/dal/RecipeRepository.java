package superapp.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import superapp.data.mainEntity.RecipeEntity;

import java.util.UUID;


public interface RecipeRepository
        extends MongoRepository<RecipeEntity, UUID> {

}
