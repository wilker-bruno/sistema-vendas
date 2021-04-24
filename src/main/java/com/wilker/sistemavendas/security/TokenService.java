package com.wilker.sistemavendas.security;

import com.wilker.sistemavendas.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    private Long EXPIRATION = 86400000L;
    private String SECRET = "89619b5d31f1f70e719a9740ed5e82d647414ee880ff059e219c2172889307354ec4824cf6eb96058c61f6cbd03cbeabef264ea7dc9ce60b270c40e0632397cb";

    public String gerarToken(Usuario usuario) {
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + this.EXPIRATION);

        return Jwts.builder()
                .setIssuer("MeuSuíno API")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public boolean verificarToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer getUsuarioID(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.SECRET).parseClaimsJws(token).getBody();
        return Integer.valueOf(claims.getSubject());
    }
}
