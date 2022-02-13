package devsearch.users.ws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.io.repository.ProfileRepository;
import devsearch.users.ws.service.AddressService;
import devsearch.users.ws.shared.dto.AddressDto;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.shared.utils.Utils;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private Mapper modelMapper;

    @Autowired
    private Utils utils;

    @Override
    public AddressDto getAddressByAddressId(String addressId) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<AddressDto> getAddresses(String profileId) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public AddressDto updateAddress(String addressId, AddressDto addressDto) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public AddressDto createAddress(AddressDto addressDto) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void deleteAddress(String addressId) throws UsersRestApiException {
	// TODO Auto-generated method stub

    }

}
