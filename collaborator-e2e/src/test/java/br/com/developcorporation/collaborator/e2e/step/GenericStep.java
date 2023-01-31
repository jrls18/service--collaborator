package br.com.developcorporation.collaborator.e2e.step;

import br.com.developcorporation.collaborator.e2e.http.HttpCustomAbstract;

import br.com.developcorporation.collaborator.e2e.http.HttpCustomConvert;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.Pt;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static org.junit.Assert.*;


public class GenericStep extends HttpCustomAbstract implements Pt {
    public GenericStep() {

        Dado("^usuario preencheu o formulario com as seguintes informacoes \"([^\"]*)\"$", (String arg0, DataTable dataTable) -> {
            Map<String, String> mapPayload =  dataTable.transpose().asMap();

            if(Objects.isNull(mapPayload) || mapPayload.isEmpty())
                throw new Exception("Por favor informe os dados para a validação do payload.");

            List<Map<String,String>> mapList = dataTable.transpose().asMaps();

            Map<String, Object> dados = HttpCustomConvert.convertJsonToMap(arg0);

            for(Map.Entry<String, String> line : mapList.get(0).entrySet()){
                if(line.getKey().contains(":")) {
                    String[] splItemArray = line.getKey().split(":");
                    if(Arrays.stream(splItemArray).count() == 2){
                        Map<String, Object> mapItem = (Map<String, Object>)dados.get(splItemArray[0]);
                        mapItem.put(splItemArray[1], setSpacesOrEmpty(line.getValue()));
                        dados.put(splItemArray[0], mapItem);
                    }else {

                        Map<String, Object> item = (Map<String, Object>) dados.get(splItemArray[0]);

                        List<Map<String, Object>> mapItem = new ArrayList<>();
                        mapItem.add(item);

                        List<Map<String, Object>> newMapItemList = new ArrayList<>();

                        int count = 0;
                        for(Map<String, Object> mapItemLine : mapItem){
                            String position = splItemArray[1].replace("[","").replace("]", "");
                            if(count == Integer.parseInt(position)){
                                mapItemLine.put(splItemArray[2], setSpacesOrEmpty(line.getValue()));
                            }
                            count ++;
                            newMapItemList.add(mapItemLine);
                        }
                        dados.put(splItemArray[0], newMapItemList);
                    }

                }else{
                    dados.put(line.getKey(), setSpacesOrEmpty(line.getValue()));
                }
            }

            super.testContext()
                    .setPayload(dados);

        });

        Dado("^usuario preencheu o formulario com as seguintes informacoes$", (DataTable dataTable) -> {
            setPayloadContext(dataTable);
        });

        Entao("^o status code da chamada deve ser \"([^\"]*)\"$", (String arg0) -> {
            Response response = testContext().getResponse();

            switch (arg0.toLowerCase()) {
                case "ok" -> assertEquals(200, response.statusCode());
                case "created" -> assertEquals(201, response.statusCode());
                case "accepted" -> assertEquals(202, response.statusCode());
                case "bad_request" -> assertEquals(400, response.statusCode());
                case "unauthorized" -> assertEquals(401, response.statusCode());
                case "forbidden" -> assertEquals(403, response.statusCode());
                case "unprocessable_entity" -> assertEquals(422, response.statusCode());
                default -> fail("Unexpected error");
            }
        });

        E("^as credenciais do sistema sendo preenchida de forma automatica$", () -> {
            super.testContext().setHeaders(getHeaderGeneric());
        });

        E("^no body da resposta deve conter as seguintes informacoes$", (DataTable dataTable) -> {

            Map<String, Object> mapPayloadExpected = convertToMap(dataTable.transpose().asMaps(String.class, Object.class));

            if(Objects.isNull(mapPayloadExpected) || mapPayloadExpected.isEmpty())
                throw new Exception("Por favor informe os dados para a validação do payload.");

            Response response = testContext().getResponse();

            Map<String, Object> responseHttp = HttpCustomConvert.convertJsonToMap(response.print());

            if(responseHttp.containsKey("data_cadastro"))
                responseHttp.remove("data_cadastro");

            Map<String, Object> mapAdapterPayloadExpected = getStringObjectMap(responseHttp);

            assertNotNull(mapAdapterPayloadExpected);

            MapDifference<String, Object> diff = Maps.difference(mapPayloadExpected, mapAdapterPayloadExpected);

            assertTrue(diff.areEqual());

        });
        E("^apos fazer o login deve-se recuperar o token gerado no campo \"([^\"]*)\"$", (String arg0) -> {

            if(StringUtils.isEmpty(arg0))
                throw new Exception("Por favor informe o nome do campo que representa o token gerado.");

            Response response = testContext().getResponse();

            Map<String, Object> responseHttp = HttpCustomConvert.convertJsonToMap(response.print());

            super.testContext().set("token", responseHttp.get(arg0));

        });

        E("^o usuario preencheu o formulado de \"([^\"]*)\" com os seguintes atributos$", (String arg0, DataTable dataTable) -> {

            if(StringUtils.isEmpty(arg0))
                throw new Exception("Por favor informe o nome do campo para ação.");

            setPayloadContext(dataTable);

        });

        E("^adicionou nos headers base o autorization com o token$", () -> {

            Map<String, String> map = this.getHeaderGeneric();

            String token = super.testContext().get("token").toString();
            map.put("Authorization", "Bearer ".concat(token));

            super.testContext().setHeaders(map);
        });

    }

    private void setPayloadContext(final DataTable dataTable) throws Exception{
        Map<String, String> mapPayload =  dataTable.transpose().asMap();


        if(Objects.isNull(mapPayload) || mapPayload.isEmpty())
            throw new Exception("Por favor informe os dados para do payload.");

        Map<String,String> newMapPayload = new HashMap<>(mapPayload.size());

        for (Map.Entry<String, String> line: mapPayload.entrySet()){
            newMapPayload.put(line.getKey(), setRandomic(setSpacesOrEmpty(line.getValue())));
        }

        super.testContext()
                .setPayload(newMapPayload);
    }

    private Map<String, Object> getStringObjectMap(Map<String, Object> responseHttp) {

        if(Objects.isNull(responseHttp))
            return new HashMap<>();

        Map<String, Object> mapAdapterPayloadExpected = new HashMap<>();

        for(Map.Entry<String, Object> line: responseHttp.entrySet()){

            if(line.getKey().equalsIgnoreCase("detalhes")){
                Object objLineSec = line.getValue();
                int count = 0;
                for (Object lineObj: ((ArrayList<?>) objLineSec).toArray()){
                    Map<String, Object> mapHashPrincipal = HttpCustomConvert.convertObjectToMap(lineObj);
                    for (Map.Entry<String, Object> lineHashPrincipal: mapHashPrincipal.entrySet()) {
                        mapAdapterPayloadExpected.put(lineHashPrincipal.getKey().concat(String.valueOf(count)), lineHashPrincipal.getValue());
                    }
                    count = count + 1;
                }
            }else{
                mapAdapterPayloadExpected.put(line.getKey(), setSpacesOrEmpty(line.getValue().toString()));
            }
        }
        return mapAdapterPayloadExpected;
    }

    private Map<String, Object> convertToMap(List<Map<String, Object>> mapPayloadExpected) {
        if(Objects.isNull(mapPayloadExpected))
            return null;

        Map<String,Object> newMapPayload = new HashMap<>();
        for (Map<String, Object> map: mapPayloadExpected){
            for(Map.Entry<String, Object> line: map.entrySet()){
                newMapPayload.put(line.getKey(), setRandomic(setSpacesOrEmpty(line.getValue().toString())));
            }
        }
        return newMapPayload;
    }

    private Map<String, String> getHeaderGeneric(){
        Map<String, String> headers = new HashMap<>(3);
        headers.put("client_id","89e7b6e5-a61d-4f67-b688-7b7449db096b");
        headers.put("client_secret","343a5f53-6797-4930-82b7-1a96cb416ead");
        headers.put("correlation_id", UUID.randomUUID().toString());

        return headers;
    }

    private String setSpaces(final String value){
        if (StringUtils.isEmpty(value) || !value.contains("&nbsp:"))
            return value;

        String[] valueArray = value.split(":");

        String setNewValue = StringUtils.EMPTY;

        for (String line : valueArray) {
            line = StringUtils.SPACE;
            setNewValue = setNewValue.concat(line);
        }
        return setNewValue;
    }

    private String setSpacesOrEmpty(final String value) {

        if(value.equalsIgnoreCase("empty"))
            return StringUtils.EMPTY;

        if(value.contains("&nbsp:"))
            return setSpaces(value);

        if(value.equalsIgnoreCase("NULLO"))
            return null;

        return value;
    }

    private String setRandomic(String value){
        if(StringUtils.isEmpty(value))
            return value;

        if (value.contains("RandomText"))
            value = value.replace("-RandomText", RandomStringUtils.randomAlphabetic(6));

        if(value.contains("RandomNumber"))
            value =  value.replace("-RandomNumber", RandomStringUtils.randomNumeric(6));

        return value;
    }
}


