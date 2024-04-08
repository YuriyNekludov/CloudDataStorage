package edu.spring.clouddatastorage.mapper;

import edu.spring.clouddatastorage.dto.UserCreateDto;
import edu.spring.clouddatastorage.model.User;
import edu.spring.clouddatastorage.security.UserDetailsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDetailsDto dtoFromEntity(User user);

    User entityFromDto(UserCreateDto dto);
}
