package org.digilinq.platform.users.web.resources;

import org.digilinq.platform.users.api.UserService;
import org.digilinq.platform.users.web.mapping.RegisterUserMapperImpl;
import org.digilinq.platform.users.web.mapping.UserMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Import({UserMapperImpl.class, RegisterUserMapperImpl.class})
class UsersResourceTest {

    public static final String BASE_URL = "/v1";
    public static final String ENDPOINT_USERS = BASE_URL + "/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    void should_return_ok_when_underlying_service_work_properly() throws Exception {
        mockMvc.perform(get(ENDPOINT_USERS)).andDo(print()).andExpect(
                status().isOk()
        );
    }

    @Test
    void should_return_unauthorized_when_user_is_not_login() throws Exception {
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