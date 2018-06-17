package com.emprovise.service.authorizationserver.mapper;


import com.emprovise.service.authorizationserver.domain.UserAccount;
import com.emprovise.service.authorizationserver.model.UserBean;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAccountMapper {

    UserBean mapToUserBean(UserAccount account);

    @InheritInverseConfiguration
    UserAccount mapToAccount(UserBean userBean);
}
