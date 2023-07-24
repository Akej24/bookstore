package com.bookstoreapplication.bookstore.user.value_objects;

import com.bookstoreapplication.bookstore.user.exception.InvalidPasswordException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Password implements Serializable {

    @NotBlank(message="Password must not be blank")
    private String password;

    public Password(String password) {
        this.password = password;
        validateRawFormat();
    }

    @Access(AccessType.FIELD)
    public String getPassword() {
        validateRawFormat();
        return password;
    }

    public void validateRawFormat() {
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
        if(!result.isValid()){
            throw new InvalidPasswordException();
        }
    }

    public EncodedPassword toEncoded(String encodedFormat){
        return new EncodedPassword(encodedFormat);
    }

}
