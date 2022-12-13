package br.com.developcorporation.collaborator.e2e.step;

import br.com.developcorporation.collaborator.e2e.http.HttpCustomAbstract;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.Pt;
import io.restassured.response.Response;

public class AutenticacaoStep extends HttpCustomAbstract implements Pt {

    public AutenticacaoStep() {

        Quando("^o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao$", () -> {
            super.executePost("/auth/v1/signin");
        });

        E("^no body da resposta deve conter os seguintes atributos de autenticacao$", (DataTable dataTable) -> {
            Response response = testContext().getResponse();

        });
    }
}
