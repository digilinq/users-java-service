package org.digilinq.platform.users.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mail.smtp")
public record SmtpServerConfigurationProperties(
        String host,
        Integer port,
        String username,
        String password
) {
}
