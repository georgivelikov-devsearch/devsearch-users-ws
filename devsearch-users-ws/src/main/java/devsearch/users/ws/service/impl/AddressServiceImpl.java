package devsearch.users.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsearch.users.ws.exception.ExceptionMessages;
import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.io.entity.AddressEntity;
import devsearch.users.ws.io.repository.AddressRepository;
import devsearch.users.ws.service.AddressService;
import devsearch.users.ws.shared.dto.AddressDto;
import devsearch.users.ws.shared.utils.AppConstants;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.shared.utils.Utils;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Mapper modelMapper;

    @Autowired
    private Utils utils;

    @Override
    public AddressDto getAddressByAddressId(String addressId) throws UsersRestApiException {
	AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

	if (addressEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	return modelMapper.map(addressEntity, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAddresses(String profileId) throws UsersRestApiException {
	List<AddressDto> returnValue = new ArrayList<>();
	Iterable<AddressEntity> addresses = addressRepository.findAll();

	for (AddressEntity addressEntity : addresses) {
	    AddressDto addressDto = modelMapper.map(addressEntity, AddressDto.class);
	    returnValue.add(addressDto);
	}

	return returnValue;
    }

    @Override
    public AddressDto updateAddress(String addressId, AddressDto addressDto) throws UsersRestApiException {
	AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
	if (addressEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	addressEntity.setCity(addressDto.getCity());
	addressEntity.setCountry(addressDto.getCountry());
	addressEntity.setStreet(addressDto.getStreet());
	addressEntity.setPostalCode(addressDto.getPostalCode());
	addressEntity.setType(addressDto.getType());

	AddressEntity updatedAddressEntity = null;
	try {
	    updatedAddressEntity = addressRepository.save(addressEntity);
	} catch (Exception ex) {
	    throw new UsersRestApiException(ExceptionMessages.UPDATE_RECORD_FAILED, ex.getMessage());
	}

	return modelMapper.map(updatedAddressEntity, AddressDto.class);
    }

    @Override
    public AddressDto createAddress(AddressDto addressDto) throws UsersRestApiException {
	AddressEntity addressEntity = modelMapper.map(addressDto, AddressEntity.class);

	addressEntity.setAddressId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));

	AddressEntity storedAddressEntity = null;
	try {
	    storedAddressEntity = addressRepository.save(addressEntity);
	} catch (Exception ex) {
	    throw new UsersRestApiException(ExceptionMessages.CREATE_RECORD_FAILED, ex.getMessage());
	}

	return modelMapper.map(storedAddressEntity, AddressDto.class);
    }

    @Override
    public void deleteAddress(String addressId) throws UsersRestApiException {
	AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
	if (addressEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	try {
	    addressRepository.delete(addressEntity);
	} catch (Exception ex) {
	    throw new UsersRestApiException(ExceptionMessages.DELETE_RECORD_FAILED, ex.getMessage());
	}
    }
}
