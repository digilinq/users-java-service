package org.digilinq.platform.users.web.controllers;

import org.digilinq.platform.users.integration.IntegrationTest;
import org.digilinq.platform.users.test.MockData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.core.StringRegularExpression.matchesRegex;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class UserControllerTest {

    private static final String UUID_REGEX = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_register_an_inactive_user_when_signup() throws Exception {

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MockData.SignupRequestExamples.SIGNUP_REQUEST.jsonString())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        matchesRegex("users\\/" + UUID_REGEX)));
    }
}