package devsearch.users.ws.io.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import devsearch.users.ws.io.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Long> {

    public ProfileEntity findByProfilePrivateId(String profilePrivateId);

    public ProfileEntity findByProfilePublicId(String profilePublicId);

    @Transactional
    @Query(value = "SELECT p FROM ProfileEntity p where p.userId=:userId")
    public ProfileEntity findByUserId(String userId);
}
