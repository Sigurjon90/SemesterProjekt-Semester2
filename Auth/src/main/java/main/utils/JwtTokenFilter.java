package main.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import main.services.IJwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private IJwtService jwtService;

    private String header = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        getTokenString(request.getHeader(header)).ifPresent(token -> {
            jwtService.getSubFromToken(token).ifPresent(id -> {

            });
        });

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenString(String header) {
        if (header == null) return Optional.empty();
        String[] split = header.split(" ");
        if (split.length < 2) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(split[1]);
        }
    }
}
