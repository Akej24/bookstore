package com.bookstoreapplication.bookstore.user.account;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtTokenResponse {
    private String token;
}
