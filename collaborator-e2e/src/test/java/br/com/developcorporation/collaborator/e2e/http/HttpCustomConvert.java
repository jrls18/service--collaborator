package br.com.developcorporation.collaborator.e2e.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpCustomConvert {

    private HttpCustomConvert(){

    }

    private static final String resourcesDirectory = "src/test/resources/json/";

    public static String getResourceFileJson(final String nameFileJson) throws Exception {

        if(StringUtils.isEmpty(nameFileJson))
            throw new Exception("Enter the name of the file that contains the payload data.");

        File file = new File(resourcesDirectory + nameFileJson);

        if(!file.exists())
            throw new Exception("There is no "+ nameFileJson +" file in the test resource.");

        return Files.readString(file.toPath(), StandardCharsets.US_ASCII);
    }

    public static String changeKeyValue(final String nameFileJson, final String keyString, final String newValue) throws Exception{

        if(StringUtils.isEmpty(nameFileJson) || StringUtils.isEmpty(keyString) || StringUtils.isEmpty(newValue))
            throw new Exception("\n" +
                    "Enter the name of the file that contains the payload data, the key you want to change the value and the new value.");

        String json = getResourceFileJson(nameFileJson);
        return updateJson(new JSONObject(json), keyString, newValue).toString();
    }

    public static String changeKeyValueJson(final String json, final String keyString, final String newValue) throws Exception{

        if(StringUtils.isEmpty(json) || StringUtils.isEmpty(keyString) || StringUtils.isEmpty(newValue))
            throw new Exception("\n" +
                    "Enter the name of the file that contains the payload data, the key you want to change the value and the new value.");

        return updateJson(new JSONObject(json), keyString, newValue).toString();

    }

    public static JSONObject updateJson(JSONObject obj, String keyString, String newValue) throws Exception {
        JSONObject json = new JSONObject();
        // get the keys of json object
        Iterator iterator = obj.keys();
        String key = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            // if the key is a string, then update the value
            if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
                if ((key.equals(keyString))) {
                    // put new value
                    obj.put(key, newValue);
                    return obj;
                }
            }

            // if it's jsonobject
            if (obj.optJSONObject(key) != null) {
                updateJson(obj.getJSONObject(key), keyString, newValue);
            }

            // if it's jsonarray
            if (obj.optJSONArray(key) != null) {
                JSONArray jArray = obj.getJSONArray(key);
                for (int i = 0; i < jArray.length(); i++) {
                    updateJson(jArray.getJSONObject(i), keyString, newValue);
                }
            }
        }
        return obj;
    }

    public static Map<String, Object> convertJsonToMap(final String json) throws Exception{
        return new ObjectMapper().readValue(json, HashMap.class);
    }

    public static Map<String, Object> convertObjectToMap(final Object obj){
        return new ObjectMapper().convertValue(obj, Map.class);
    }

}

