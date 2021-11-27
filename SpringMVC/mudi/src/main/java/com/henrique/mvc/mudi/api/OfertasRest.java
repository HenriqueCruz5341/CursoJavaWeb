package com.henrique.mvc.mudi.api;

import java.util.Optional;

import javax.validation.Valid;

import com.henrique.mvc.mudi.dto.RequisicaoNovaOferta;
import com.henrique.mvc.mudi.model.Oferta;
import com.henrique.mvc.mudi.model.Pedido;
import com.henrique.mvc.mudi.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ofertas")
public class OfertasRest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping
    public ResponseEntity<Oferta> criaOferta(@Valid @RequestBody RequisicaoNovaOferta requisicao) {
        Optional<Pedido> pedidoBuscado = pedidoRepository.findById(requisicao.getPedidoId());
        if (!pedidoBuscado.isPresent()) {
            return null;
        }

        Pedido pedido = pedidoBuscado.get();
        Oferta oferta = requisicao.toOferta();

        oferta.setPedido(pedido);
        pedido.getOfertas().add(oferta);

        pedidoRepository.save(pedido);

        return ResponseEntity.ok().body(oferta);
    }
}
