package com.management.product.web.graphql.user;

import com.management.product.dtos.user.UserInfoRequest;
import com.management.product.dtos.user.UserInfoResponse;
import com.management.product.service.graphql.user.UserInfoServiceImplWithGraphql;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserInfoServiceImplWithGraphql userInfoServiceImpl;

    @QueryMapping
    public  List<UserInfoResponse> users()
    {
        return userInfoServiceImpl.listUser();
    }

    @QueryMapping
    public UserInfoResponse userById(@Argument Long id)
    {
        return userInfoServiceImpl.findUserById(id)
                .orElseThrow(() ->  new IllegalArgumentException(String.format("user with %d does not exist ", id)));
    }

    @MutationMapping
    public UserInfoResponse addUser(@Argument UserInfoRequest userInfoRequest){
        return userInfoServiceImpl.addUser(userInfoRequest);
    }

    @MutationMapping
    public UserInfoResponse updateUser(@Argument Long id, @Argument UserInfoRequest userUpdateRequest){
        return  userInfoServiceImpl.updateUserById(id,userUpdateRequest);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long id){
        userInfoServiceImpl.deleteUserById(id);
        return true;
    }

}
