package org.digilinq.platform.users.web.controllers;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.digilinq.platform.users.integration.IntegrationTest;
import org.digilinq.platform.users.test.MockData;
import org.digilinq.platform.users.web.constants.Endpoints;
import org.digilinq.platform.users.web.constants.UserInfo.ChrisBrown;
import org.digilinq.platform.users.web.constants.UserInfo.SarahConnor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.digilinq.platform.users.test.MockData.SignupRequestExamples.SIGNUP_REQUEST_CHRIS_BROWN;
import static org.digilinq.platform.users.test.MockData.SignupRequestExamples.SIGNUP_REQUEST_SARAH_CONNOR;
import static org.digilinq.platform.users.web.constants.Endpoints.SIGNUP_ENDPOINT;
import static org.hamcrest.core.StringRegularExpression.matchesRegex;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
class UserControllerIT {

    private static final String UUID_REGEX = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Test
    void should_register_an_inactive_user_when_signup() throws Exception {
        mockMvc.perform(post(SIGNUP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MockData.SignupRequestExamples.SIGNUP_REQUEST.jsonString())
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        matchesRegex("users\\/" + UUID_REGEX)));
    }

    @Test
    void should_reject_with_400_when_email_is_empty() throws Exception {
        mockMvc.perform(post(SIGNUP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MockData.SignupRequestExamples.SIGNUP_REQUEST_WITH_EMPTY_EMAIL.jsonString())
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_reject_with_bad_request_when_password_is_empty() throws Exception {
        mockMvc.perform(post(SIGNUP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MockData.SignupRequestExamples.SIGNUP_REQUEST_WITH_EMPTY_PASSWORD.jsonString())
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_reject_with_bad_request_when_password_not_match_confirm_password() throws Exception {
        mockMvc.perform(post(SIGNUP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MockData.SignupRequestExamples.SIGNUP_REQUEST_AND_PASSWORD_NOT_MATCH.jsonString())
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Sql("classpath:sql/users.sql")
    @DisplayName("Chris has same username and email")
    void existing_username() throws Exception {
        mockMvc.perform(get(Endpoints.ENDPOINT_USERS_BY_ID, UUID.fromString(ChrisBrown.USER_ID))).andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.userId").value(ChrisBrown.USER_ID),
                        jsonPath("$.username").value(ChrisBrown.USERNAME),
                        jsonPath("$.email").value(ChrisBrown.EMAIL)
                );

        mockMvc.perform(post(SIGNUP_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(SIGNUP_REQUEST_CHRIS_BROWN.jsonString())
        ).andDo(print()).andExpect(
                status().isConflict()
        ).andExpectAll(
                jsonPath("$.detail").value(matchesRegex("Username chrisbrown@example.edu is already exists"))
        );
    }

    @Test
    @Transactional
    @Sql("classpath:sql/users.sql")
    @DisplayName("Sarah has different username than email in database")
    void existing_email() throws Exception {
        mockMvc.perform(get(Endpoints.ENDPOINT_USERS_BY_ID, UUID.fromString(SarahConnor.USER_ID))).andDo(print()
        ).andExpect(
                status().isOk()
        ).andExpectAll(
                jsonPath("$.userId").value(SarahConnor.USER_ID),
                jsonPath("$.username").value(SarahConnor.USERNAME),
                jsonPath("$.email").value(SarahConnor.EMAIL)
        );

        mockMvc.perform(post(SIGNUP_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(SIGNUP_REQUEST_SARAH_CONNOR.jsonString())
        ).andDo(print()).andExpect(
                status().isConflict()
        ).andExpectAll(
            jsonPath("$.detail").value(matchesRegex("Email sarahconnor@example.co is already exists"))
        );
    }
}
