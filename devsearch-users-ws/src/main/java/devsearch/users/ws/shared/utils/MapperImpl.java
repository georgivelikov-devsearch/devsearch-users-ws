package devsearch.users.ws.shared.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperImpl implements Mapper {

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
	return modelMapper.map(source, destinationType);
    }

}
