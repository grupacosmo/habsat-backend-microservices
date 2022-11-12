package pl.edu.pk.cosmo.habsatbackend.userservice.service.impl;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pk.cosmo.habsatbackend.userservice.converter.RoleConverter;
import pl.edu.pk.cosmo.habsatbackend.userservice.dummy.DummyData;
import pl.edu.pk.cosmo.habsatbackend.userservice.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleConverter roleConverter;

    private final Faker faker = new Faker();
    private final DummyData dummyData = new DummyData();

}
