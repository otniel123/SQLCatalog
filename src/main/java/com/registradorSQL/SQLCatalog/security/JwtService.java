package com.registradorSQL.SQLCatalog.security;

import com.registradorSQL.SQLCatalog.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSecretKey(){
        byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String gerarToken(Usuario usuario){
        Date agora = new Date();
        Date expirationDate = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("nome", usuario.getNome())
                .claim("role", usuario.getRole().name())
                .issuedAt(agora)
                .expiration(expirationDate)
                .signWith(getSecretKey())
                .compact();

    }

    public boolean validarToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Claims extrairTodasClaims(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extrairEmail(String token){
        return extrairTodasClaims(token).getSubject();
    }

    public Long extrairId(String token){
        return extrairTodasClaims(token).get("id", Long.class);
    }

    public String extrairNome(String token) {
        return extrairTodasClaims(token).get("nome", String.class);
    }

    public String extrairRole(String token) {
        return extrairTodasClaims(token).get("role", String.class);
    }

    public Date extrairExpiracao(String token) {
        return extrairTodasClaims(token).getExpiration();
    }

    public boolean tokenExpirado(String token){
        return extrairExpiracao(token).before(new Date());
    }
}
