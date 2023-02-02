package br.com.developcorporation.collaborator.e2e.step;

import br.com.developcorporation.collaborator.e2e.http.HttpCustomAbstract;
import io.cucumber.java8.Pt;

public class AuthorizationStep extends HttpCustomAbstract implements Pt {
    public AuthorizationStep() {

        Quando("^o fomulario foi preenchido por completo o usuriario solicitou uma chamada de \"([^\"]*)\" de uma nova autorizacao$", (String arg0) -> {

            String urlBase = "/api/authorization/v1/";

            switch (arg0.toLowerCase()){
                case "cadastrar":
                    super.executePost(urlBase.concat("save"));
                    break;
                case "alterar":
                    super.executePut(urlBase.concat("save"));
                    break;
                case "consultar_id":
                    break;
                case "todos":
                    break;
                default:
                    throw new Exception("Por favor informe um comado de authorização ex: Cadastrar, Alterar, Consultar ou todos");
            }
        });



    }
}
