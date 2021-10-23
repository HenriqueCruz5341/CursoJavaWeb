package com.henrique;

import org.apache.http.client.fluent.Request;

public class ClientWebService {
    public static void main(String[] args) throws Exception {
        String conteudo = Request.Get("http://localhost:8080/gerenciador/empresas")
                .setHeader("Accept", "application/json").execute().returnContent().asString();

        System.out.println(conteudo);
    }
}
