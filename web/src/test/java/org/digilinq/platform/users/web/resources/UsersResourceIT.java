package org.digilinq.platform.users.web.resources;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.digilinq.platform.users.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.digilinq.platform.users.web.constants.Endpoints.ENDPOINT_USERS_BY_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Transactional
@Sql("classpath:sql/users.sql")
class UsersResourceIT {

    private static final UUID USER_ID = UUID.fromString("d91c9db3-24c6-442f-ab91-29921c560b17");

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void should_return_proper_error_message_when_user_not_found() throws Exception {
        mockMvc.perform(get(ENDPOINT_USERS_BY_ID, USER_ID)).andDo(print())
                .andExpect(status().isNotFound());
    }
}