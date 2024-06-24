package com.example.interceptors;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

public class TokenHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpHeaders headers = request.getHeaders();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            System.out.println(key + ": " + value);
        }

        String authToken = headers.getFirst("cookie");

        Long userId = getUserIdFromToken(authToken);

        if (userId == null) {
            return false;
        }

        attributes.put("userId", userId);

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
    public Long getUserIdFromToken(String authToken) {
        Pattern pattern = Pattern.compile("\"token\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(authToken);
        if (matcher.find()) {
            authToken = matcher.group(1);
        }

        long periodCount = authToken.chars().filter(ch -> ch == '.').count();
        if (periodCount != 2) {
            throw new IllegalArgumentException("Invalid token");
        }

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey("and0LnNlY3JldC5hY2Nlc3Muand0LnNlY3JldC5hY2Nlc3M")
                .build()
                .parseClaimsJws(authToken);

        String userId = jwsClaims.getBody().getSubject();

        return Long.parseLong(userId);
    }
}