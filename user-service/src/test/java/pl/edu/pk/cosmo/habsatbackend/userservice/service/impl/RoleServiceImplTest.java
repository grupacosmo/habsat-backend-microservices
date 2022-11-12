package pl.edu.pk.cosmo.habsatbackend.userservice.service.impl;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pk.cosmo.habsatbackend.userservice.converter.RoleConverter;
import pl.edu.pk.cosmo.habsatbackend.userservice.dummy.DummyData;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.User;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.AddRolesRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangeRoleRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoRoleException;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoUserException;
import pl.edu.pk.cosmo.habsatbackend.userservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleConverter roleConverter;

    private final Faker faker = new Faker();
    private final DummyData dummyData = new DummyData();

    @Nested
    class AddRoles {
        @Test
        void emailDoesNotExist_shouldThrowNoUserException() {
            AddRolesRequest addRolesRequest = dummyData.getAddRolesRequest();

            when(userRepository.existsByEmail(any(String.class))).thenReturn(false);

            assertThrows(NoUserException.class, ()-> roleService.addAllRoles(addRolesRequest));
            verify(userRepository, times(0)).save(any(User.class));
        }

        @Test
        void emailExists_shouldAddRoles() {
            AddRolesRequest addRolesRequest = dummyData.getAddRolesRequest();
            User user = dummyData.getUser();

            when(userRepository.existsByEmail(any(String.class))).thenReturn(true);
            when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
            when(userRepository.save(any(User.class))).thenReturn(user);
            when(roleConverter.toString(any(Set.class))).thenReturn("AI OBC TESTDATA");


            assertDoesNotThrow(()-> roleService.addAllRoles(addRolesRequest));
            Set<String> actual = Set.of(user.getRoles().split(" "));

            assertEquals(Set.of("AI", "OBC", "TESTDATA"), actual);
            verify(userRepository, times(1)).save(any(User.class));
        }
    }

    @Nested
    class AddRole {
        @Test
        void emailDoesNotExist_shouldThrowNoUserException() {
            ChangeRoleRequest changeRoleRequest = new ChangeRoleRequest(faker.internet().emailAddress(), "AI");

            when(userRepository.existsByEmail(any(String.class))).thenReturn(false);

            assertThrows(NoUserException.class, ()-> roleService.addRole(changeRoleRequest));
            verify(userRepository, times(0)).save(any(User.class));
        }

        @Test
        void emailExists_shouldAddRole() throws NoUserException {
            ChangeRoleRequest changeRoleRequest = dummyData.getChangeRoleRequest();
            User user = dummyData.getUser();
            List<String> roles = new ArrayList<>(List.of(user.getRoles().split(" ")));

            when(userRepository.existsByEmail(any(String.class))).thenReturn(true);
            when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
            when(userRepository.save(any(User.class))).thenReturn(user);
            when(roleConverter.toList(any(String.class))).thenReturn(roles);
            when(roleConverter.toString(any(List.class))).thenReturn(String.join(" ", roles));

            roleService.addRole(changeRoleRequest);
            assertThat(user.getRoles().contains(changeRoleRequest.getRole())).isEqualTo(true);
            verify(userRepository, times(1)).save(any(User.class));
        }
    }

    @Nested
   class DeleteRole {

        @Test
        void emailDoesNotExist_shouldThrowNoUserException() {
            final ChangeRoleRequest changeRoleRequest = dummyData.getChangeRoleRequest();

            when(userRepository.existsByEmail(any(String.class))).thenReturn(false);

            assertThrows(NoUserException.class, ()-> roleService.deleteRole(changeRoleRequest));
            verify(userRepository, times(0)).save(any(User.class));
        }

        @Test
        @DisplayName("Email exists, but there is no role. Should throw no role exception")
        void shouldThrowNoRoleException() {
            ChangeRoleRequest changeRoleRequest = dummyData
                    .getChangeRoleRequest()
                    .setRole("RANDOMROLE");
            User user = dummyData.getUser();

            when(userRepository.existsByEmail(any(String.class))).thenReturn(true);
            when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

            assertThrows(NoRoleException.class, ()-> roleService.deleteRole(changeRoleRequest));
            verify(userRepository, times(0)).save(any(User.class));
        }

        @Test
        void emailAndRoleExist_shouldRemoveRole() {
            ChangeRoleRequest changeRoleRequest = dummyData.getChangeRoleRequest()
                    .setRole("AI");
            User user = dummyData.getUser();
            List<String> roles = new ArrayList<>(List.of(user.getRoles().split(" ")));

            when(userRepository.existsByEmail(any(String.class))).thenReturn(true);
            when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
            when(userRepository.save(any(User.class))).thenReturn(user);
            when(roleConverter.toList(any(String.class))).thenReturn(roles);

            assertDoesNotThrow(() -> roleService.deleteRole(changeRoleRequest));
            assertEquals(List.of("OBC", "WEBDEV"), roles);
            verify(userRepository, times(1)).save(any(User.class));
        }
   }

   @Nested
    class GetRolesByEmail {

       @Test
       void emailExists_shouldReturnRoles() {
           final ChangeRoleRequest changeRoleRequest = dummyData.getChangeRoleRequest();
           final User user = dummyData.getUser();
           final List<String> expectedRoles = List.of(user.getRoles().split(" "));

           when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
           when(roleConverter.toList(any(String.class))).thenReturn(expectedRoles);

           List<String> actualRoles = assertDoesNotThrow(()-> roleService.getRolesByEmail(changeRoleRequest.getEmail()));
           assertEquals(expectedRoles, actualRoles);
       }

       @Test
       void emailDoesNotExist_shouldThrowNoUserException() {
           final ChangeRoleRequest changeRoleRequest = dummyData.getChangeRoleRequest();

           when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

           assertThrows(NoUserException.class, ()-> roleService.getRolesByEmail(changeRoleRequest.getEmail()));
           verify(userRepository, times(0)).save(any(User.class));
       }
    }
}