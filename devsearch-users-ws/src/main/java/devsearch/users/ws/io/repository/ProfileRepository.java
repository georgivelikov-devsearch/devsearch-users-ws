package devsearch.users.ws.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import devsearch.users.ws.io.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends PagingAndSortingRepository<ProfileEntity, Long> {

    public ProfileEntity findByProfilePrivateId(String profilePrivateId);

    public ProfileEntity findByProfilePublicId(String profilePublicId);

    @Transactional
    @Query(value = "SELECT p FROM ProfileEntity p where p.userId=:userId")
    public ProfileEntity findByUserId(String userId);

    @Transactional
    @Query(value = "SELECT p FROM ProfileEntity p where p.adminProfile=false")
    Page<ProfileEntity> findAllNotAdmin(Pageable pageable);

    @Transactional
    @Query(value = "SELECT p FROM ProfileEntity p where p.adminProfile=false AND (p.firstName LIKE %:searchText% OR p.lastName LIKE %:searchText%)")
    Page<ProfileEntity> findAllNotAdminAndByText(Pageable pageable, String searchText);
}
