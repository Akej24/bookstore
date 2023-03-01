package com.bookstoreapplication.bookstore.user.registration;

import org.passay.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class RegistrationValidator {
    public boolean validatePassword(String password) {
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8, 16));
        rules.add(new WhitespaceRule());
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Special, 1));

        var validator = new PasswordValidator(rules);
        var passwordData = new PasswordData(password);
        RuleResult result = validator.validate(passwordData);

        return result.isValid();
    }
}
