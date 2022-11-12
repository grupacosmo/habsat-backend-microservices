package pl.edu.pk.cosmo.habsatbackend.userservice.dummy;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.User;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.AddRolesRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangeRoleRequest;

import java.util.List;

@NoArgsConstructor
public class DummyData {
    private Faker faker = new Faker();

    public User getUser() {
        return new User()
                .setId(100L)
                .setFirstLogin(true)
                .setPassword(faker.chuckNorris().fact())
                .setEmail(faker.internet().emailAddress())
                .setRoles("AI OBC WEBDEV");
    }

    public ChangeRoleRequest getChangeRoleRequest() {
        return new ChangeRoleRequest(faker.internet().emailAddress(), "AI");
    }

    public AddRolesRequest getAddRolesRequest() {
        return new AddRolesRequest()
                .setEmail(faker.internet().emailAddress())
                .setRoles(List.of("AI", "OBC", "TESTDATA"));
    }
}
