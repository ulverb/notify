package com.mend.services;


import com.mend.dal.models.UserModel;
import com.mend.dal.repositories.UserRepository;
import com.mend.mvc.requests.UserRequest;
import com.mend.mvc.responses.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service("UserStatisticsService")
public class UserStatisticsService
{
    @Autowired private final UserRepository userRepository;

    private final static ModelMapper mapper = new ModelMapper();

    public List<UserResponse> getTopActiveRegularUsers()
    {
        List<UserModel> users = userRepository.findTop10MostActiveRegularUsers();

        return users.stream()
                .map(userModel -> mapper.map(userModel, UserResponse.class))
                .collect(Collectors.toList());
    }

    public UserResponse createUser(UserRequest user)
    {
        UserModel userModel = mapper.map(user, UserModel.class);
        UserModel userModelResponse = userRepository.save(userModel);

        return mapper.map(userModelResponse, UserResponse.class);
    }
}
