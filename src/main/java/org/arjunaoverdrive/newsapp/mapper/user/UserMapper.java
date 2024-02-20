package org.arjunaoverdrive.newsapp.mapper.user;

import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.web.dto.user.UserListResponse;
import org.arjunaoverdrive.newsapp.web.dto.user.UserRequest;
import org.arjunaoverdrive.newsapp.web.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    AppUser requestToUser(UserRequest request);

    @Mapping(source = "appUserId", target = "id")
    AppUser requestToUser(Long appUserId, UserRequest request);

    UserResponse userToResponse(AppUser appUser);

    default UserListResponse toUserListResponse(List<AppUser> appUsers) {
        UserListResponse userListResponse = new UserListResponse();

        userListResponse.setUsers(
                appUsers.stream()
                        .map(this::userToResponse)
                        .toList());
        return userListResponse;
    }
}
