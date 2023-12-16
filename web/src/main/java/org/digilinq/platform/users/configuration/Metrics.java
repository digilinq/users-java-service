package org.digilinq.platform.users.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("singleton")
@Component
@Getter
public class Metrics {
    public static final String SIGNUP_COUNTER = "signup";

    private final Counter signup;

    public Metrics(MeterRegistry registry) {
        this.signup = registry.counter(SIGNUP_COUNTER);
    }
}