package com.wilker.sistemavendas.controller;

import com.github.javafaker.Faker;
import com.wilker.sistemavendas.DTO.AutenticacaoDTO;
import com.wilker.sistemavendas.DTO.UsuarioDTO;
import com.wilker.sistemavendas.entity.Usuario;
import com.wilker.sistemavendas.entity.enuns.UsuarioEnum;
import com.wilker.sistemavendas.exception.ErrorDetail;
import com.wilker.sistemavendas.form.AutenticacaoForm;
import com.wilker.sistemavendas.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MarcaControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AutenticacaoController autenticacaoController;

    private HttpHeaders headers;
    private UsuarioDTO usuario;
    private Faker faker = new Faker(new Locale("pt-BR"));

    @BeforeAll
    public void configCredentials() {
        Usuario userFaker = new Usuario();
        userFaker.setId(this.faker.number().randomDigitNotZero());
        userFaker.setNome(this.faker.name().firstName());
        userFaker.setEmail(this.faker.internet().emailAddress());
        userFaker.setTipo(UsuarioEnum.ADMIN);

        String password = this.faker.internet().password();
        userFaker.setSenha(this.passwordEncoder.encode(password));

        AutenticacaoForm autenticacaoForm = new AutenticacaoForm();
        autenticacaoForm.setEmail(userFaker.getEmail());
        autenticacaoForm.setSenha(password);

        when(this.usuarioRepository.findByEmail(userFaker.getEmail())).thenReturn(java.util.Optional.of(userFaker));

        AutenticacaoDTO response = this.autenticacaoController.autenticar(autenticacaoForm);

        this.headers = new HttpHeaders();
        this.headers.add("Authorization", "Bearer " + response.getToken());
        this.usuario = response.getUsuario();
    }

    @Test
    void ShouldReturnStatusCode403AndErrorDetailInBodyIfTokenNotProvided() {
        HttpEntity<Usuario> httpEntity = new HttpEntity<>(null);

        ResponseEntity<ErrorDetail> response = this.testRestTemplate
                .exchange("/api/marcas", HttpMethod.POST, httpEntity, ErrorDetail.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
        assertThat(response.getBody()).isInstanceOf(ErrorDetail.class);
    }
}
