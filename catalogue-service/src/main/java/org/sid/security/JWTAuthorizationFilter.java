 package org.sid.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization");
		response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
		
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		else {
			String jwt = request.getHeader(SecurityParams.HEADER_NAME);
			if(jwt == null || !jwt.startsWith(SecurityParams.HEADER_PREFIX))
				{
					filterChain.doFilter(request, response);
				}
			else {
				try {
					JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();
					String myToken = jwt.substring(SecurityParams.HEADER_PREFIX.length(),jwt.length()); //sans Bearer 
					DecodedJWT decodedJWT = verifier.verify(myToken);
					String username = decodedJWT.getSubject();
					List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
					Collection<GrantedAuthority> authorities = new ArrayList<>();
					roles.forEach(r->{
						authorities.add(new SimpleGrantedAuthority(r));
					});
					UsernamePasswordAuthenticationToken uAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null,authorities);
					SecurityContextHolder.getContext().setAuthentication(uAuthenticationToken);
					filterChain.doFilter(request, response);
				}catch (JWTVerificationException e) {
					System.out.println(e.getMessage());
					filterChain.doFilter(request, response); 
				}
			}
		}
		
		
	}

}
