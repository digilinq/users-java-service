package org.digilinq.platform.users.api;

import org.digilinq.platform.users.dto.User;

public interface MailService {
    void sendEmail(User recipient, String subject, String templateName);
}
