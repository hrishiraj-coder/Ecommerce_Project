package com.ums.mapper;

import com.ums.dto.UserAccountDto;
import com.ums.entites.UserAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    UserAccountDto entityToDto(UserAccount userAccount);
    UserAccount dtoToEntity( UserAccountDto userAccountDto);

}
