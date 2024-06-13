package org.digilinq.platform.users.web.resources;

import org.digilinq.platform.users.api.UserService;
import org.digilinq.platform.users.configuration.LoggingConfiguration;
import org.digilinq.platform.users.configuration.Metrics;
import org.digilinq.platform.users.configuration.security.WebSecurityConfig;
import org.digilinq.platform.users.web.mapping.EncryptedPasswordMapper;
import org.digilinq.platform.users.web.mapping.UserMapperImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.digilinq.platform.users.web.constants.Endpoints.ENDPOINT_USERS;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Import({
        UserMapperImpl.class, EncryptedPasswordMapper.class,
        LoggingConfiguration.class, WebSecurityConfig.class
})
class UsersResourceTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private Metrics metrics;

    @Test
    @WithMockUser
    void should_return_ok_when_underlying_service_work_properly() throws Exception {
        Mockito.when(userService.findAll(anyInt(), anyInt())).thenReturn(Page.empty());
        mockMvc.perform(
                get(ENDPOINT_USERS)
        ).andExpect(
                status().isOk()
        );
    }

    @Disabled
    @Test
    void should_return_unauthorized_when_user_is_not_login() throws Exception {
        Mockito.when(userService.findAll(anyInt(), anyInt())).thenReturn(Page.empty());
        mockMvc.perform(get(ENDPOINT_USERS)).andDo(print()).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    @WithMockUser
    void should_return_error_as_response_when_content_type_is_not_supported() throws Exception {
        mockMvc.perform(
                get(ENDPOINT_USERS).accept(MediaType.APPLICATION_XML)
        ).andDo(print()).andExpectAll(
                status().isNotAcceptable(),
                content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                jsonPath("$").exists()
        );
    }
}