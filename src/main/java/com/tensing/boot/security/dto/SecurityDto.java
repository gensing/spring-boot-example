package com.tensing.boot.security.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecurityDto {

    @Getter
    @RequiredArgsConstructor
    public enum GranType {

        USER_INFO,
        REFRESH_TOKEN;

        private static final Map<String, GranType> descMap = Stream.of(GranType.values()).collect(Collectors.toUnmodifiableMap(GranType::name, Function.identity()));

        @JsonCreator
        public static GranType from(String s) {
            var granType = descMap.get(s);
            return granType == null ? GranType.USER_INFO : granType;
        }
    }

    @Setter
    @Getter
    public static class TokenRequest {

        @Schema(description = "로그인 방식 설정 필드", defaultValue = "USER_INFO")
        private GranType grantType = GranType.USER_INFO;
        private String username;
        private String password;
        private String refreshToken;
    }

    @Getter
    @Builder
    public static class TokenResponse {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Builder
    public static class UserInfo {
        private long id;
    }

}
