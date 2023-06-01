package superapp.logic.utilitys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectRepository;
import superapp.dal.UserRepository;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.Exceptions.ObjectNotFoundException;

@Component
public class SuperAppObjectUtility {


    private SuperAppObjectRepository superAppObjectRepository;

    @Autowired
    public SuperAppObjectUtility( SuperAppObjectRepository superAppObjectRepository) {

        this.superAppObjectRepository = superAppObjectRepository;
    }

    public SuperAppObjectEntity checkSuperAppObjectEntityExist(ObjectId objectId) {
        return superAppObjectRepository.findById(objectId)
                .orElseThrow(() -> new ObjectNotFoundException("Object "+objectId.getInternalObjectId() +" not found"));
    }
}
