package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.core.mock.StatusMock;
import br.com.developcorporation.collaborator.core.service.impl.AuthorizationServiceImpl;
import br.com.developcorporation.collaborator.core.validation.AuthorizationValidation;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.infrastructure.ContextHolder;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Authorization;
import br.com.developcorporation.collaborator.domain.model.Status;
import br.com.developcorporation.collaborator.domain.port.AuthorizationPort;
import br.com.developcorporation.collaborator.domain.port.StatusPort;
import br.com.developcorporation.collaborator.core.mock.AuthorizationMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationServiceImpl service;

    @Mock
    private AuthorizationValidation validation;

    @Mock
    private AuthorizationPort authorizationPort;

    @Mock
    private StatusPort statusPort;


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("01 - Realiza o cadastro com sucesso")
    void addSucess() {

        doNothing().when(validation).clientIdAndClientSecret();
        doNothing().when(validation).validCorrelationId();
        doNothing().when(validation).add(any(Authorization.class));

        when(authorizationPort.existsByName(anyString())).thenReturn(false);
        when(authorizationPort.existsBySiglaApp(anyString())).thenReturn(false);
        doNothing().when(authorizationPort).add(any(Authorization.class));

        Authorization authorizationMock = AuthorizationMock.getAuthorization();
        authorizationMock.setId(null);

        Message message = service.add(authorizationMock);

        InOrder inOrder = inOrder(validation, authorizationPort);

        inOrder.verify(validation).clientIdAndClientSecret();
        inOrder.verify(validation).validCorrelationId();
        inOrder.verify(validation).add(any(Authorization.class));
        inOrder.verify(authorizationPort).existsByName(anyString());
        inOrder.verify(authorizationPort).existsBySiglaApp(anyString());
        inOrder.verify(authorizationPort).add(any(Authorization.class));

        assertEquals("201", message.getCode());
        assertEquals("Autorização cadastrada com sucesso.", message.getMessage());
        assertNull(message.getDetailsList());
    }

    @Test
    @DisplayName("02 - Valida se existe a application_name então erro.")
    void addSucessApplicationNameExists(){
        doNothing().when(validation).clientIdAndClientSecret();
        doNothing().when(validation).validCorrelationId();
        doNothing().when(validation).add(any(Authorization.class));

        when(authorizationPort.existsByName(anyString())).thenReturn(true);
        when(authorizationPort.existsBySiglaApp(anyString())).thenReturn(false);

        Authorization authorizationMock = AuthorizationMock.getAuthorization();
        authorizationMock.setId(null);
        DomainException exception = null;

        try {
             service.add(authorizationMock);
        }catch (DomainException ex){
            exception = ex;
        }

        InOrder inOrder = inOrder(validation, authorizationPort);

        inOrder.verify(validation).clientIdAndClientSecret();
        inOrder.verify(validation).validCorrelationId();
        inOrder.verify(validation).add(any(Authorization.class));
        inOrder.verify(authorizationPort).existsByName(anyString());
        inOrder.verify(authorizationPort).existsBySiglaApp(anyString());

        assertNotNull(exception);
        assertEquals("422", exception.getCode());
        assertEquals("Existe erro(s) no(s) campo(s) da autorização.", exception.getMessage());
        assertEquals(1,exception.getDetails().size());
        assertEquals("application_name", exception.getDetails().get(0).getField());
        assertEquals("Application name já existe cadastrada.", exception.getDetails().get(0).getMessage());
        assertEquals(authorizationMock.getApplicationName(), exception.getDetails().get(0).getValue());
    }

    @Test
    @DisplayName("03 - Valida se existe a sigla_app então erro.")
    void addSucessSiglaAppExists(){
        doNothing().when(validation).clientIdAndClientSecret();
        doNothing().when(validation).validCorrelationId();
        doNothing().when(validation).add(any(Authorization.class));

        when(authorizationPort.existsByName(anyString())).thenReturn(false);
        when(authorizationPort.existsBySiglaApp(anyString())).thenReturn(true);

        Authorization authorizationMock = AuthorizationMock.getAuthorization();
        authorizationMock.setId(null);
        DomainException exception = null;

        try {
            service.add(authorizationMock);
        }catch (DomainException ex){
            exception = ex;
        }

        InOrder inOrder = inOrder(validation, authorizationPort);

        inOrder.verify(validation).clientIdAndClientSecret();
        inOrder.verify(validation).validCorrelationId();
        inOrder.verify(validation).add(any(Authorization.class));
        inOrder.verify(authorizationPort).existsByName(anyString());
        inOrder.verify(authorizationPort).existsBySiglaApp(anyString());

        assertNotNull(exception);
        assertEquals("422", exception.getCode());
        assertEquals("Existe erro(s) no(s) campo(s) da autorização.", exception.getMessage());
        assertEquals(1,exception.getDetails().size());
        assertEquals("sigla_app", exception.getDetails().get(0).getField());
        assertEquals("Sigla app já existe cadastrada.", exception.getDetails().get(0).getMessage());
        assertEquals(authorizationMock.getSiglaApp(), exception.getDetails().get(0).getValue());
    }

    @Test
    @DisplayName("04 - Valida erro 500 sistema indisponível.")
    void addSucessThow(){
        doNothing().when(validation).clientIdAndClientSecret();
        doNothing().when(validation).validCorrelationId();
        doNothing().when(validation).add(any(Authorization.class));

        when(authorizationPort.existsByName(anyString())).thenReturn(false);
        when(authorizationPort.existsBySiglaApp(anyString())).thenReturn(false);

        doThrow(new RuntimeException("Error occurred"))
                .when(authorizationPort)
                .add(any(Authorization.class));

        Authorization authorizationMock = AuthorizationMock.getAuthorization();
        authorizationMock.setId(null);
        DomainException exception = null;

        try {
            service.add(authorizationMock);
        }catch (DomainException ex){
            exception = ex;
        }

        InOrder inOrder = inOrder(validation, authorizationPort);

        inOrder.verify(validation).clientIdAndClientSecret();
        inOrder.verify(validation).validCorrelationId();
        inOrder.verify(validation).add(any(Authorization.class));
        inOrder.verify(authorizationPort).existsByName(anyString());
        inOrder.verify(authorizationPort).existsBySiglaApp(anyString());

        assertNotNull(exception);
        assertEquals("500", exception.getCode());
        assertEquals("Ocorreu um erro interno tente novamente mais tarde!", exception.getMessage());
        assertNull(exception.getDetails());
    }

    @Test
    @DisplayName("05 - Consulta todas as autorizações")
    void getAllSucess() {

        when(authorizationPort.getAll()).thenReturn(List.of(AuthorizationMock.getAuthorization()));
        List<Authorization> authorizationList = service.getAll();

        InOrder inOrder = inOrder(validation, authorizationPort);

        inOrder.verify(authorizationPort).getAll();

        assertNotNull(authorizationList);
        assertEquals(1, authorizationList.size());
    }

    @Test
    @DisplayName("06 - Autenticação")
    void isAuthenticationSucess() {

        doNothing().when(validation).clientIdAndClientSecret();
        doNothing().when(validation).validCorrelationId();

        when(authorizationPort.getByClientIdAndClientSecret(ContextHolder.get().getClientId(), ContextHolder.get().getClientSecret())).thenReturn(Optional.of(AuthorizationMock.getAuthorization()));

        service.isAuthentication();

        InOrder inOrder = inOrder(validation, authorizationPort);

        inOrder.verify(authorizationPort).getByClientIdAndClientSecret(ContextHolder.get().getClientId(), ContextHolder.get().getClientSecret());
    }

    @Test
    @DisplayName("07 - Autenticação empty")
    void isAuthenticationEmpty() {

        doNothing().when(validation).clientIdAndClientSecret();
        doNothing().when(validation).validCorrelationId();
        when(authorizationPort.getByClientIdAndClientSecret(ContextHolder.get().getClientId(), ContextHolder.get().getClientSecret())).thenReturn(Optional.empty());

        DomainException exception = null;

        try {
            service.isAuthentication();
        }catch (DomainException ex){
            exception = ex;
        }

        InOrder inOrder = inOrder(validation, authorizationPort);

        inOrder.verify(authorizationPort).getByClientIdAndClientSecret(ContextHolder.get().getClientId(), ContextHolder.get().getClientSecret());

        assertNotNull(exception);
        assertEquals("401",exception.getCode());
        assertEquals("Não autorizado.", exception.getMessage());
        assertNull(exception.getDetails());
    }

    @Test
    @DisplayName("08 - Autenticação não ativo")
    void isAuthenticationAtivoFalse() {

        doNothing().when(validation).clientIdAndClientSecret();
        doNothing().when(validation).validCorrelationId();

        Authorization authorization = AuthorizationMock.getAuthorization();
        Status status = StatusMock.getStatus();
        status.setId(2L);
        authorization.setStatus(status);

        when(authorizationPort.getByClientIdAndClientSecret(ContextHolder.get().getClientId(), ContextHolder.get().getClientSecret())).thenReturn(Optional.of(authorization));

        DomainException exception = null;

        try {
            service.isAuthentication();
        }catch (DomainException ex){
            exception = ex;
        }

        InOrder inOrder = inOrder(validation, authorizationPort);

        inOrder.verify(authorizationPort).getByClientIdAndClientSecret(ContextHolder.get().getClientId(), ContextHolder.get().getClientSecret());

        assertNotNull(exception);
        assertEquals("401",exception.getCode());
        assertEquals("Não autorizado.", exception.getMessage());
        assertNull(exception.getDetails());
    }

}