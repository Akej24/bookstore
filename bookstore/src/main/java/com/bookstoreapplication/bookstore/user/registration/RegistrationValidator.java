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

    public boolean validateFields(RegistrationRequest request){
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(1, Integer.MAX_VALUE));
        rules.add(new WhitespaceRule());

        var validator = new PasswordValidator(rules);
        var emailData = new PasswordData(request.getEmail());
        var usernameData = new PasswordData(request.getUsername());
        var nameData = new PasswordData(request.getName());
        var surnameData = new PasswordData(request.getSurname());

        RuleResult emailResult = validator.validate(emailData);
        RuleResult usernameResult = validator.validate(usernameData);
        RuleResult nameResult = validator.validate(nameData);
        RuleResult surnameResult = validator.validate(surnameData);

        return (
                emailResult.isValid() && usernameResult.isValid() && nameResult.isValid() && surnameResult.isValid()
        );
    }

}
