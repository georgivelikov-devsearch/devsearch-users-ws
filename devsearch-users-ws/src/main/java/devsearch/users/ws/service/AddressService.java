package devsearch.users.ws.service;

import java.util.List;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.shared.dto.AddressDto;

public interface AddressService {

    public AddressDto getAddressById(String addressId) throws UsersRestApiException;

    public List<AddressDto> getAddresses(String userId) throws UsersRestApiException;
}
