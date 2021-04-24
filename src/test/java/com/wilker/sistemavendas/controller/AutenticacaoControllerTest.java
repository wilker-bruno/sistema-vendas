package com.wilker.sistemavendas.controller;

import com.wilker.sistemavendas.DTO.AutenticacaoDTO;
import com.wilker.sistemavendas.entity.Usuario;
import com.wilker.sistemavendas.exception.ErrorDetail;
import com.wilker.sistemavendas.form.AutenticacaoForm;
import com.wilker.sistemavendas.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AutenticacaoControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UsuarioRepository usuarioRepository;

    private AutenticacaoForm validCredentials;
    private AutenticacaoForm invalidCredentials;

    @BeforeEach
    public void configValidUser() {
        this.validCredentials = new AutenticacaoForm();
        this.validCredentials.setEmail("valid@gmail.com");
        this.validCredentials.setSenha("123456");

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1);
        usuarioMock.setNome("Valid User");
        usuarioMock.setEmail("valid@gmail.com");
        usuarioMock.setSenha(this.passwordEncoder.encode("123456"));

        when(this.usuarioRepository.findByEmail("valid@gmail.com")).thenReturn(java.util.Optional.of(usuarioMock));
    }

    @BeforeEach
    public void configInvalidUser() {
        this.invalidCredentials = new AutenticacaoForm();
        this.invalidCredentials.setEmail("invalid@gmail.com");
        this.invalidCredentials.setSenha("123456");

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1);
        usuarioMock.setNome("Invalid User");
        usuarioMock.setEmail("invalid@gmail.com");
        usuarioMock.setSenha(this.passwordEncoder.encode("123456"));

        when(this.usuarioRepository.findByEmail("invalid@gmail.com")).thenReturn(null);
    }

    @Test
    public void AutenticacaoControllerShouldReturnTokenAndUsuarioDTOForValidCredentials() {
        HttpEntity<AutenticacaoForm> request = new HttpEntity<>(this.validCredentials);

        ResponseEntity<AutenticacaoDTO> responseEntity = this.testRestTemplate
                .exchange("/api/autenticacao", HttpMethod.POST, request, AutenticacaoDTO.class);

        assertThat(responseEntity.getBody()).isInstanceOf(AutenticacaoDTO.class);
    }

    @Test
    public void AutenticacaoControllerShouldReturnStatusCode200ForValidCredentials() {
        HttpEntity<AutenticacaoForm> request = new HttpEntity<>(this.validCredentials);

        ResponseEntity<AutenticacaoDTO> responseEntity = this.testRestTemplate
                .exchange("/api/autenticacao", HttpMethod.POST, request, AutenticacaoDTO.class);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void AutenticacaoControllerShouldReturnErrorDetailForInvalidCredentials() {
        HttpEntity<AutenticacaoForm> request = new HttpEntity<>(this.invalidCredentials);

        ResponseEntity<ErrorDetail> responseEntity = this.testRestTemplate
                .exchange("/api/autenticacao", HttpMethod.POST, request, ErrorDetail.class);

        assertThat(responseEntity.getBody()).isInstanceOf(ErrorDetail.class);
    }

    @Test
    public void AutenticacaoControllerShouldReturnStatusCode401ForInvalidCredentials() {
        HttpEntity<AutenticacaoForm> request = new HttpEntity<>(this.invalidCredentials);

        ResponseEntity<AutenticacaoDTO> responseEntity = this.testRestTemplate
                .exchange("/api/autenticacao", HttpMethod.POST, request, AutenticacaoDTO.class);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
    }
}
