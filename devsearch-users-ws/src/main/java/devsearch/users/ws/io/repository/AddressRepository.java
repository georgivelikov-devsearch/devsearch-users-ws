package devsearch.users.ws.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devsearch.users.ws.io.entity.AddressEntity;
import devsearch.users.ws.io.entity.UserEntity;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    public AddressEntity findByAddressId(String addressId);

    public List<AddressEntity> findAllByUser(UserEntity userEntity);
}
