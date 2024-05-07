package org.digilinq.platform.users.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.digilinq.platform.users.configuration.JacksonConfiguration;
import org.digilinq.platform.users.generated.v1.model.SignupRequest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


public class MockData {

    private static final ObjectMapper OBJECT_MAPPER = JacksonConfiguration.objectMapper;
    private static final JSONParser JSON_PARSER = new JSONParser(JSONParser.MODE_PERMISSIVE);

    private static String readAsString(String resourceName, Charset cs) throws IOException {
        try (InputStream in = getResourceUrl(resourceName).openStream()) {
            return readAsString3(in, cs);
        }
    }

    private static String readAsString(InputStream in, Charset cs) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, cs))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private static String readAsString1(InputStream in, Charset cs) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, cs))) {
            return reader.lines().collect(
                    StringBuilder::new,
                    StringBuilder::append,
                    StringBuilder::append
            ).toString();
        }
    }

    private static String readAsString2(InputStream in, Charset cs) throws IOException {
        int ch;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, cs))) {
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            return sb.toString();
        }
    }

    private static String readAsString3(InputStream in, Charset cs) throws IOException {
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            resultStream.write(buffer, 0, bytesRead);
        }
        return resultStream.toString(cs);
    }

    private static URI getResourceUri(String resourceName) throws URISyntaxException {
        return getResourceUrl(resourceName).toURI();
    }

    private static URL getResourceUrl(String resourceName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null) {
            throw new IllegalArgumentException(resourceName + " not found!");
        }
        return url;
    }

    private static <T> T readAsType(String resourceName, Class<T> type) throws IOException {
        return OBJECT_MAPPER.readValue(getResourceUrl(resourceName), type);
    }

    private static <T> T readAsType(InputStream in, Class<T> type) throws IOException {
        return OBJECT_MAPPER.readValue(in, type);
    }

    private static JSONObject readAsJsonObject(String resourceName) throws IOException, ParseException {
        try (InputStream in = getResourceUrl(resourceName).openStream()) {
            return (JSONObject) JSON_PARSER.parse(in);
        }
    }

    private static byte[] readAsBytes(String resourceName) throws IOException {
        try (InputStream in = getResourceUrl(resourceName).openStream()) {
            return in.readAllBytes();
        }
    }

    interface TypeResource<T> {
        Class<T> getType();

        String getResourceName();

        default T readApiModel() throws IOException {
            return readAsType(getResourceName(), getType());
        }

        default String jsonString() throws IOException {
            return readAsString(getResourceName(), StandardCharsets.UTF_8);
        }

        default JSONObject jsonObject() throws IOException, ParseException {
            return readAsJsonObject(getResourceName());
        }

        default byte[] bytes() throws IOException {
            return readAsBytes(getResourceName());
        }
    }

    public enum SignupRequestExamples implements TypeResource<SignupRequest> {
        SIGNUP_REQUEST("mock-data/signup-request.json");

        private final String resourceName;

        SignupRequestExamples(String resourceName) {
            this.resourceName = resourceName;
        }

        @Override
        public Class<SignupRequest> getType() {
            return SignupRequest.class;
        }

        @Override
        public String getResourceName() {
            return resourceName;
        }
    }
}


