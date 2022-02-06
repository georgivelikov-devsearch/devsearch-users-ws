package devsearch.users.ws.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import devsearch.users.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    public UserEntity findByPublicId(String publicId);

    public UserEntity findByUsername(String username);

    public UserEntity findByEmail(String email);
}
