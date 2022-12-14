package br.com.developcorporation.collaborator.e2e.step;

import br.com.developcorporation.collaborator.e2e.http.HttpCustomAbstract;

import br.com.developcorporation.collaborator.e2e.http.HttpCustomConvert;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.Pt;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static org.junit.Assert.*;


public class GenericStep extends HttpCustomAbstract implements Pt {
    public GenericStep() {

        Dado("^usuario preencheu o formulario com as seguintes informacoes$", (DataTable dataTable) -> {

            Map<String, String> mapPayload =  dataTable.transpose().asMap();

            if(Objects.isNull(mapPayload) || mapPayload.isEmpty())
                throw new Exception("Por favor informe os dados para a validação do payload.");

            super.testContext()
                    .setPayload(mapPayload);
        });

        Entao("^o status code da chamada deve ser \"([^\"]*)\"$", (String arg0) -> {
            Response response = testContext().getResponse();

            switch (arg0.toLowerCase()) {
                case "ok":
                    assertEquals(200, response.statusCode());
                    break;
                case "created":
                    assertEquals(201, response.statusCode());
                    break;
                case "accepted":
                    assertEquals(202, response.statusCode());
                    break;
                case "bad_request":
                    assertEquals(400, response.statusCode());
                    break;
                case "unauthorized":
                    assertEquals(401, response.statusCode());
                    break;
                case "forbidden":
                    assertEquals(403, response.statusCode());
                    break;
                case "unprocessable_entity":
                    assertEquals(422, response.statusCode());
                    break;
                default:
                    fail("Unexpected error");
            }
        });

        E("^as credenciais do sistema sendo preenchida de forma automatica$", () -> {
            super.testContext().setHeaders(getHeaderGeneric());
        });

        E("^no body da resposta deve conter os seguintes informacoes$", (DataTable dataTable) -> {

            List<Map<String, Object>> mapPayloadExpected =  dataTable.transpose().asMaps(String.class, Object.class);

            if(Objects.isNull(mapPayloadExpected) || mapPayloadExpected.isEmpty())
                throw new Exception("Por favor informe os dados para a validação do payload.");

            Response response = testContext().getResponse();

            Map<String, Object> responseHttp = HttpCustomConvert.convertJsonToMap(response.print());

            Map<String, Object> mapAdapterPayloadExpected = new HashMap<>();

            for(Map.Entry<String, Object> line: responseHttp.entrySet()){

                if(line.getKey().equalsIgnoreCase("detalhes")){

                }
                mapAdapterPayloadExpected.put(line.getKey(), line.getValue());
            }

            MapDifference<String, Object> diff = Maps.difference(mapPayloadExpected.get(0), mapAdapterPayloadExpected);

            assertTrue(diff.areEqual());
        });

    }


    private Map<String, String> getHeaderGeneric(){
        Map<String, String> headers = new HashMap<>(3);
        headers.put("client_id","89e7b6e5-a61d-4f67-b688-7b7449db096b");
        headers.put("client_secret","343a5f53-6797-4930-82b7-1a96cb416ead");
        headers.put("correlation_id", UUID.randomUUID().toString());

        return headers;
    }

    private String setSpaces(final String value) {
        if (StringUtils.isEmpty(value) || !value.contains("&nbsp:"))
            return value;

        String[] valueArray = value.split(":");

        String setNewValue = StringUtils.EMPTY;

        for (String line : valueArray) {
            setNewValue = setNewValue.concat(" ");
        }
        return setNewValue;
    }

}


