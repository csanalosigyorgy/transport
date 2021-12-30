package com.webuni.transport.service.util;

import java.util.Objects;

public class BaseService {

	protected void checkIfEntityNull(Object entity){
		if(Objects.isNull(entity))
			throw new IllegalArgumentException("Nem küldött be objektumot.");
	}

	protected void checkIfEntityIdNonNull(Long id){
		if(Objects.nonNull(id))
			throw new IllegalArgumentException("Az objektum azonosítója már ki van töltve.");
	}

}
