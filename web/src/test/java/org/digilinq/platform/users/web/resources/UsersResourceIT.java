package org.digilinq.platform.users.web.resources;

import org.digilinq.platform.users.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.digilinq.platform.users.web.constants.WebUtils.ENDPOINT_USERS_BY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@IntegrationTest
class UsersResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void should_return_proper_error_message_when_user_not_found() throws Exception {
        mockMvc.perform(get(ENDPOINT_USERS_BY_ID, "1")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}