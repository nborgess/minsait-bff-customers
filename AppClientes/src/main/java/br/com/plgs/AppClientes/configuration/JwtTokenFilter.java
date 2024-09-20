package br.com.plgs.AppClientes.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
	    @NonNull HttpServletRequest request,
	    @NonNull HttpServletResponse response,
	    @NonNull FilterChain filterChain
	) throws ServletException, IOException {
	    final String authHeader = request.getHeader("Authorization");
	    final String path = request.getRequestURI();

	    if (path.equals("/api/auth/login") || path.equals("/api/auth/signup") ||
	        path.startsWith("/api/v3/api-docs") || 
	        path.startsWith("/api/swagger-ui") || 
	        path.startsWith("/api/swagger-resources") || 
	        path.startsWith("/api/webjars")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.getWriter().write("{\"message\": \"Insira o token de autenticacao para acessar esta operacao.\"}");
	        return;
	    }

	    try {
	        final String jwt = authHeader.substring(7);
	        final String userEmail = jwtTokenUtil.extractUsername(jwt);

	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (userEmail != null && authentication == null) {
	            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

	            if (jwtTokenUtil.isTokenValid(jwt, userDetails)) {
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                        userDetails,
	                        null,
	                        userDetails.getAuthorities()
	                );

	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }

	        filterChain.doFilter(request, response);
	    } catch (Exception exception) {
	        response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.getWriter().write("{\"message\": \"Token de autenticacao invalido ou erro no processamento.\"}");
	    }
	}
}