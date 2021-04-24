package com.wilker.sistemavendas.controller;

import com.github.javafaker.Faker;
import com.wilker.sistemavendas.DTO.AutenticacaoDTO;
import com.wilker.sistemavendas.entity.Usuario;
import com.wilker.sistemavendas.entity.enuns.UsuarioEnum;
import com.wilker.sistemavendas.exception.ErrorDetail;
import com.wilker.sistemavendas.form.AutenticacaoForm;
import com.wilker.sistemavendas.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AutenticacaoControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UsuarioRepository usuarioRepository;

    private AutenticacaoForm validCredentials;
    private AutenticacaoForm invalidCredentials;
    private Faker faker = new Faker(new Locale("pt-BR"));

    @BeforeEach
    public void configValidUser() {
        Usuario userFaker = new Usuario();
        userFaker.setId(this.faker.number().randomDigitNotZero());
        userFaker.setNome(this.faker.name().firstName());
        userFaker.setEmail(this.faker.internet().emailAddress());
        userFaker.setTipo(UsuarioEnum.ADMIN);

        String password = this.faker.internet().password();
        userFaker.setSenha(this.passwordEncoder.encode(password));

        this.validCredentials = new AutenticacaoForm();
        this.validCredentials.setEmail(userFaker.getEmail());
        this.validCredentials.setSenha(password);

        when(this.usuarioRepository.findByEmail(userFaker.getEmail())).thenReturn(java.util.Optional.of(userFaker));
    }

    @BeforeEach
    public void configInvalidUser() {
        Usuario userFaker = new Usuario();
        userFaker.setId(this.faker.number().randomDigitNotZero());
        userFaker.setNome(this.faker.name().firstName());
        userFaker.setEmail(this.faker.internet().emailAddress());
        userFaker.setTipo(UsuarioEnum.ADMIN);

        String password = this.faker.internet().password();
        userFaker.setSenha(this.passwordEncoder.encode(password));

        this.invalidCredentials = new AutenticacaoForm();
        this.invalidCredentials.setEmail(userFaker.getEmail());
        this.invalidCredentials.setSenha(password);

        when(this.usuarioRepository.findByEmail(userFaker.getEmail())).thenReturn(null);
    }

    @Test
    void AutenticacaoControllerShouldReturnTokenAndUsuarioDTOForValidCredentials() {
        HttpEntity<AutenticacaoForm> request = new HttpEntity<>(this.validCredentials);

        ResponseEntity<AutenticacaoDTO> responseEntity = this.testRestTemplate
                .exchange("/api/autenticacao", HttpMethod.POST, request, AutenticacaoDTO.class);

        assertThat(responseEntity.getBody()).isInstanceOf(AutenticacaoDTO.class);
    }

    @Test
    void AutenticacaoControllerShouldReturnStatusCode200ForValidCredentials() {
        HttpEntity<AutenticacaoForm> request = new HttpEntity<>(this.validCredentials);

        ResponseEntity<AutenticacaoDTO> responseEntity = this.testRestTemplate
                .exchange("/api/autenticacao", HttpMethod.POST, request, AutenticacaoDTO.class);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void AutenticacaoControllerShouldReturnErrorDetailForInvalidCredentials() {
        HttpEntity<AutenticacaoForm> request = new HttpEntity<>(this.invalidCredentials);

        ResponseEntity<ErrorDetail> responseEntity = this.testRestTemplate
                .exchange("/api/autenticacao", HttpMethod.POST, request, ErrorDetail.class);

        assertThat(responseEntity.getBody()).isInstanceOf(ErrorDetail.class);
    }

    @Test
    void AutenticacaoControllerShouldReturnStatusCode401ForInvalidCredentials() {
        HttpEntity<AutenticacaoForm> request = new HttpEntity<>(this.invalidCredentials);

        ResponseEntity<AutenticacaoDTO> responseEntity = this.testRestTemplate
                .exchange("/api/autenticacao", HttpMethod.POST, request, AutenticacaoDTO.class);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
    }
}
