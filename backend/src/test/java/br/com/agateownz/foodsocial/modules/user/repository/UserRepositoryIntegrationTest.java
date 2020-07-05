package br.com.agateownz.foodsocial.modules.user.repository;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import br.com.agateownz.foodsocial.modules.user.dto.response.MentionUserResponse;
import br.com.agateownz.foodsocial.modules.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static br.com.agateownz.foodsocial.modules.user.UserFakeDataHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(ApplicationProfiles.TEST)
@DataJpaTest
@Sql("classpath:/sql/users.test.sql")
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("findByUsername should return an user if exists")
    @Test
    public void findByUsernameSuccess() {
        var maybeUser = userRepository.findByUsername(EXISTING_USERNAME);

        assertThat(maybeUser)
            .hasValueSatisfying(user -> assertThat(user)
                .extracting(User::getId, User::getUsername, User::getEmail)
                .containsExactly(EXISING_USER_ID, EXISTING_USERNAME, EXISTING_EMAIL));
    }

    @DisplayName("findByUsername should return empty if not exists")
    @Test
    public void findByUsernameEmpty() {
        var maybeUser = userRepository.findByUsername(INEXISTENT_USERNAME);

        assertThat(maybeUser).isEmpty();
    }

    @DisplayName("findByIdsIn should return users with given ids if exists")
    @Test
    public void findByIdsInExistingUsers() {
        var users = userRepository.findByIdIn(EXISTING_USER_IDS);

        assertThat(users)
            .extracting(User::getId)
            .matches(list -> list.containsAll(EXISTING_USER_IDS));
    }

    @DisplayName("findByIdsIn should return empty if no users exists")
    @Test
    public void findByIdsInNonexistingUsers() {
        var users = userRepository.findByIdIn(INEXISTENT_USER_IDS);

        assertThat(users).isEmpty();
    }

    @DisplayName("findUsersToMention should return user if he follows the user or the other way around")
    @Test
    public void findUsersToMentionSuccess() {
        var users = userRepository.findUsersToMention(EXISING_USER_ID, SECOND_USERNAME);

        assertThat(users)
            .extracting(MentionUserResponse::getId)
            .containsExactly(SECOND_USER_ID);
    }

    @DisplayName("findUsersToMention should return empty if user does not follow or is not followed by")
    @Test
    public void findUsersToMentionFail() {
        var users = userRepository.findUsersToMention(EXISING_USER_ID, THIRD_USERNAME);

        assertThat(users).isEmpty();
    }
}