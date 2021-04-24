package com.wilker.sistemavendas.controller;

import com.wilker.sistemavendas.entity.Usuario;
import com.wilker.sistemavendas.DTO.AutenticacaoDTO;
import com.wilker.sistemavendas.form.AutenticacaoForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MarcaControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AutenticacaoController autenticacaoController;

    private HttpHeaders headers;
    private Usuario usuario;

    @BeforeAll
    public void start(){
        AutenticacaoForm autenticacaoForm = new AutenticacaoForm();
        autenticacaoForm.setEmail("wilker@gmail.com");
        autenticacaoForm.setSenha("123456");
        AutenticacaoDTO response = this.autenticacaoController.autenticar(autenticacaoForm);

        this.headers = new HttpHeaders();
        this.headers.add("Authorization", "Bearer " + response.getToken());
        this.usuario = response.getUsuario();
    }

    @Test
    public void deve_retornar_200_para_GET_marcas_id(){
        HttpEntity<Usuario> httpEntity = new HttpEntity<>(this.headers);

        ResponseEntity<Usuario> response = this.testRestTemplate
                .exchange("/api/usuarios/" + this.usuario.getId(), HttpMethod.GET, httpEntity, Usuario.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
