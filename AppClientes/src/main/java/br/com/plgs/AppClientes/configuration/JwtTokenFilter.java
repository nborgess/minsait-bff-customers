package br.com.plgs.AppClientes.configuration;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(
	        HttpServletRequest request, 
	        HttpServletResponse response, 
	        FilterChain filterChain)
	        throws ServletException, IOException {

	    String requestURI = request.getRequestURI();
	    
	    if (requestURI.startsWith("/v3/api-docs") || 
	            requestURI.startsWith("/swagger-ui") || 
	            requestURI.startsWith("/swagger-resources") || 
	            requestURI.startsWith("/webjars") || 
	            requestURI.equals("/token")) {  
	        
	        filterChain.doFilter(request, response); 
	        return;
	    }

	    String token = request.getHeader("Authorization");
	    
	    if (token == null || !token.startsWith("Bearer ")) {
	        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\": \"Necessario fornecer um token para acessar esta operacao.\"}");
	        return; 
	    }

	    token = token.substring(7); 
	    
	    if (jwtTokenUtil.validateToken(token)) {
	        String username = jwtTokenUtil.getUsernameFromToken(token);
	        
	        List<SimpleGrantedAuthority> authorities = 
	                List.of(new SimpleGrantedAuthority("USER"));
	        
	        Authentication authentication = new
	                UsernamePasswordAuthenticationToken(username, null, authorities);
	        
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    } else {
	        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\": \"Token invalido ou expirado.\"}");
	        return;
	    }

	    filterChain.doFilter(request, response);
	}
	
}