package com.henrique.mvc.mudi.api;

import java.util.List;

import com.henrique.mvc.mudi.interceptor.InterceptadorDeAcessos;
import com.henrique.mvc.mudi.interceptor.InterceptadorDeAcessos.Acesso;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("acessos")
@RestController
public class AcessosRest {

    @GetMapping
    public List<Acesso> getAcessos() {
        return InterceptadorDeAcessos.acessos;
    }
}
