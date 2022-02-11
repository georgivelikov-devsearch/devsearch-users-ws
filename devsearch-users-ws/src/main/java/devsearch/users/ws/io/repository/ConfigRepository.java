package devsearch.users.ws.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devsearch.users.ws.io.entity.ConfigEntity;

@Repository
public interface ConfigRepository extends CrudRepository<ConfigEntity, Long> {

    public ConfigEntity findByName(String name);
}
