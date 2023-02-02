package br.com.developcorporation.collaborator.e2e.step;

import br.com.developcorporation.collaborator.e2e.http.HttpCustomAbstract;
import br.com.developcorporation.collaborator.e2e.http.HttpCustomConvert;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.Pt;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.Objects;


import static org.junit.Assert.assertTrue;

public class AutenticacaoStep extends HttpCustomAbstract implements Pt {

    public AutenticacaoStep() {

        Quando("^o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao$", () -> {
            super.executePost("/auth/v1/signin");
        });

        E("^no body da resposta deve conter os seguintes atributos de autenticacao$", (DataTable dataTable) -> {

            List<Map<String, Object>> mapDados =  dataTable.transpose().asMaps(String.class, Object.class);

            if(Objects.isNull(mapDados) || mapDados.isEmpty())
                throw new Exception("Por favor informe os dados para a validação do payload.");

            Response response = testContext().getResponse();

            Map<String, Object> responseHttp = HttpCustomConvert.convertJsonToMap(response.print());

            for(Map.Entry<String, Object> line: mapDados.get(0).entrySet()){
                assertTrue(responseHttp.containsKey(line.getKey()));
            }
        });

    }
}
