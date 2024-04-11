package edu.spring.clouddatastorage.mapper;

import edu.spring.clouddatastorage.dto.UserDtoResponse;
import edu.spring.clouddatastorage.dto.UserRegistrationDto;
import edu.spring.clouddatastorage.model.User;
import edu.spring.clouddatastorage.security.UserDetailsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDetailsDto detailsDtoFromEntity(User user);

    User entityFromDto(UserRegistrationDto dto);

    UserDtoResponse dtoResponseFromEntity(User user);
}
