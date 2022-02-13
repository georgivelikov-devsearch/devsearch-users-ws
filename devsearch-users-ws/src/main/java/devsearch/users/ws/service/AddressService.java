package devsearch.users.ws.service;

import java.util.List;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.shared.dto.AddressDto;

public interface AddressService {

    public AddressDto getAddressByAddressId(String addressId) throws UsersRestApiException;

    public List<AddressDto> getAddresses(String profileId) throws UsersRestApiException;

    public AddressDto updateAddress(String addressId, AddressDto addressDto) throws UsersRestApiException;

    public AddressDto createAddress(AddressDto addressDto) throws UsersRestApiException;

    public void deleteAddress(String addressId) throws UsersRestApiException;
}
