package com.czertainly.provider.entity.keystore.service;

import com.czertainly.api.model.common.AttributeDefinition;
import com.czertainly.api.model.common.RequestAttributeDto;

import java.util.List;

public interface AttributeService {

	List<AttributeDefinition> getAttributes(String kind);
	
	boolean validateAttributes(String kind, List<RequestAttributeDto> attributes);
}
