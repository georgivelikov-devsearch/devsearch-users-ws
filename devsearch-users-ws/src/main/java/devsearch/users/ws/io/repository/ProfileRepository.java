package devsearch.users.ws.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devsearch.users.ws.io.entity.ProfileEntity;
import devsearch.users.ws.io.entity.UserEntity;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Long> {

    public ProfileEntity findByProfileId(String profileId);

    public ProfileEntity findByUser(UserEntity user);
}
