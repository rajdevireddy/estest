package com.estest.main;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.util.Iterator;

/**
 *
 * Created by RReddy on 3/29/2016.
 */
public class LoadData {

    public static void main(String args[])throws Exception{

        String esURL = //"httP://localhost:9200/_cluster/health";
                       "http://localhost:9200/empi.phcs/patients";
                       //"http://localhost:9200/megacorp/employee/8";
        String filePath = "data/patients_from_mackaroo.json";

        String data;

        //Read JSON
        ObjectMapper m = new ObjectMapper();
        JsonNode rootNode = m.readTree(new FileInputStream(filePath));
        System.out.println("Total number of records = "+rootNode.size());
        Iterator<JsonNode> elements = rootNode.getElements();

        //Create a REST client
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(esURL);

        //POST to ESearch
        while(elements.hasNext()) {

            //System.out.println(elements.next());
            data = elements.next().toString();
            String response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(data), String.class);
            System.out.println(response);
        }
    }
}