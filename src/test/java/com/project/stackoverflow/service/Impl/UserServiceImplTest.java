package com.project.stackoverflow.service.Impl;

import com.project.stackoverflow.constant.UserStatus;
import com.project.stackoverflow.dto.UserDTO;
import com.project.stackoverflow.entity.UserInfo;
import com.project.stackoverflow.exception.UserAlreadyExistsException;
import com.project.stackoverflow.repository.UserInfoRepository;
import com.project.stackoverflow.service.ValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserInfoRepository userRepository;

    @Mock
    private ValidatorService validatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterUser() throws UserAlreadyExistsException {
        UserDTO createUserRequest = new UserDTO();
        createUserRequest.setName("John Doe");
        createUserRequest.setEmail("john.doe@example.com");
        createUserRequest.setPhone("1234567890");
        createUserRequest.setUsername("johndoe");
        createUserRequest.setPassword("password");

        when(validatorService.isUserInfoUnique(createUserRequest.getEmail(), createUserRequest.getPhone(), createUserRequest.getUsername()))
                .thenReturn(true);

        UserInfo savedUser = new UserInfo();
        savedUser.setId(1L);
        savedUser.setStatus(UserStatus.ACTIVE);
        savedUser.setName(createUserRequest.getName());
        savedUser.setEmail(createUserRequest.getEmail());
        savedUser.setPhone(createUserRequest.getPhone());
        savedUser.setUsername(createUserRequest.getUsername());
        savedUser.setPassword(createUserRequest.getPassword());
        savedUser.setReputationPoints(10);

        when(userRepository.save(Mockito.any(UserInfo.class))).thenReturn(savedUser);

        UserInfo registeredUser = userService.registerUser(createUserRequest);

        assertNotNull(registeredUser);
        assertEquals(UserStatus.ACTIVE, registeredUser.getStatus());
        assertEquals(createUserRequest.getName(), registeredUser.getName());
        assertEquals(createUserRequest.getEmail(), registeredUser.getEmail());
        assertEquals(createUserRequest.getPhone(), registeredUser.getPhone());
        assertEquals(createUserRequest.getUsername(), registeredUser.getUsername());
        assertEquals(createUserRequest.getPassword(), registeredUser.getPassword());
        assertEquals(10, registeredUser.getReputationPoints());
    }

    @Test
    void testRegisterUserThrowsUserAlreadyExistsException() {
        UserDTO createUserRequest = new UserDTO();
        createUserRequest.setEmail("existing.email@example.com");
        createUserRequest.setPhone("1234567890");
        createUserRequest.setUsername("existinguser");

        when(validatorService.isUserInfoUnique(createUserRequest.getEmail(), createUserRequest.getPhone(), createUserRequest.getUsername()))
                .thenReturn(false);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerUser(createUserRequest);
        });
    }

    @Test
    void testGetUserDetails() {
        Long userId = 1L;
        UserInfo user = new UserInfo();
        user.setId(userId);
        user.setName("Test User");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<UserInfo> userDetails = userService.getUserDetails(userId);

        assertTrue(userDetails.isPresent());
        assertEquals(userId, userDetails.get().getId());
        assertEquals("Test User", userDetails.get().getName());
    }

    @Test
    void testGetUserDetailsNotFound() {
        Long userId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<UserInfo> userDetails = userService.getUserDetails(userId);

        assertFalse(userDetails.isPresent());
    }
}
