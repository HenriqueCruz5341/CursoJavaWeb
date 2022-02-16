package com.henrique.nfe.domains;

import com.henrique.nfe.entities.NotaFiscal;
import com.henrique.nfe.models.envelopes.ResponseEnvelopePage;
import com.henrique.nfe.models.envelopes.ResponseEnvelopeSingleObject;
import com.henrique.nfe.models.notasfiscais.NotaFiscalInputDto;
import com.henrique.nfe.models.notasfiscais.NotaFiscalViewDto;

public interface NotasFiscais {
    public ResponseEnvelopePage<NotaFiscal, NotaFiscalViewDto> get(Integer pageIndex, Integer pageSize,
            String prestador, String tomador, Double valorMinimo, String servicos, String dataEmissaoFim);

    public ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto> getById(String id);

    public ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto> post(NotaFiscalInputDto notaFiscalInputDto);

    public ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto> darBaixa(String id);

    public ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto> delete(String id);

}
