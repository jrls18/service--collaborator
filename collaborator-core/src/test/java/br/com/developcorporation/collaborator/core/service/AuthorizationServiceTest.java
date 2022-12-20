package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.core.service.impl.AuthorizationServiceImpl;
import br.com.developcorporation.collaborator.core.validation.AuthorizationValidation;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Authorization;
import br.com.developcorporation.collaborator.domain.port.AuthorizationPort;
import br.com.developcorporation.collaborator.domain.port.StatusPort;
import br.com.developcorporation.collaborator.core.mock.AuthorizationMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
}