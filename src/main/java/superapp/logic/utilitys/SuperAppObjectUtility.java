package superapp.logic.utilitys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.ObjectId;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.dal.SuperAppObjectRepository;
import superapp.dal.UserRepository;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.Exceptions.ObjectNotFoundException;

@Component
public class SuperAppObjectUtility {


    private final SuperAppObjectRepository superAppObjectRepository;

    @Autowired
    public SuperAppObjectUtility( SuperAppObjectRepository superAppObjectRepository) {

        this.superAppObjectRepository = superAppObjectRepository;
    }

    public SuperAppObjectEntity checkSuperAppObjectEntityExist(ObjectId objectId) {
        return superAppObjectRepository.findById(objectId)
                .orElseThrow(() -> new ObjectNotFoundException("Object "+objectId.getInternalObjectId() +" not found"));
    }


    public void validateObject(SuperAppObjectBoundary obj) {
        GeneralUtility generalUtility = new GeneralUtility();
        // Check if alias is valid
        if (generalUtility.isStringEmptyOrNull(obj.getAlias())) {
            throw new RuntimeException("alias is empty");

        }
        // Check if type is valid
        if (generalUtility.isStringEmptyOrNull(obj.getType())) {
            throw new RuntimeException("type is empty");
            // Check if created by is valid
        }
        if (generalUtility.isStringEmptyOrNull(obj.getCreatedBy().getUserId().getEmail()) ||
                generalUtility.isStringEmptyOrNull(obj.getCreatedBy().getUserId().getSuperapp())) {
            throw new RuntimeException("created by is empty");
        }
    }
}
