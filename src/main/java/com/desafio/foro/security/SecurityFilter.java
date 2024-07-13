package com.desafio.foro.security;

import com.desafio.foro.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //obtener token del header
        var authHeader = request.getHeader("Authorization");
//        if(token == null || token.equals("")){
//            throw new RuntimeException("El token enviado no es válido");
//        }
        if (authHeader != null){
            var token = authHeader.replace("Bearer ", "");
            //Este usuario tiene sesion?
            var subject = tokenService.getSubject(token);
            if (subject != null){
                //token valido
                var usuario = usuarioRepository.findByLogin(subject);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); //forzamos el inicio de sesión
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request, response);
    }
}
