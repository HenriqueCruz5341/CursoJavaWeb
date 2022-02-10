package com.henrique.nfe.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.henrique.nfe.consts.MessagesConsts;
import com.henrique.nfe.domains.NotasFiscais;
import com.henrique.nfe.entities.Empresa;
import com.henrique.nfe.entities.NotaFiscal;
import com.henrique.nfe.entities.StatusNota;
import com.henrique.nfe.models.empresas.EmpresaViewDto;
import com.henrique.nfe.models.envelopes.ResponseEnvelopePage;
import com.henrique.nfe.models.envelopes.ResponseEnvelopeSingleObject;
import com.henrique.nfe.models.notasfiscais.NotaFiscalInputDto;
import com.henrique.nfe.models.notasfiscais.NotaFiscalViewDto;
import com.henrique.nfe.repositories.EmpresasRepository;
import com.henrique.nfe.repositories.NotasFiscaisRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class NotasFiscaisService implements NotasFiscais {

        private NotasFiscaisRepository notasFiscaisRepository;
        private EmpresasRepository empresasRepository;

        public NotasFiscaisService(NotasFiscaisRepository notasFiscaisRepository,
                        EmpresasRepository empresasRepository) {
                this.notasFiscaisRepository = notasFiscaisRepository;
                this.empresasRepository = empresasRepository;
        }

        @Override
        public ResponseEnvelopePage<NotaFiscal, NotaFiscalViewDto> get(Integer pageIndex, Integer pageSize) {
                final Page<NotaFiscal> notaFiscalPage = notasFiscaisRepository
                                .findAll(PageRequest.of(pageIndex, pageSize));

                return notaFiscalPage.hasContent() ? ResponseEnvelopePage.create(notaFiscalPage, getNotaFiscalView)
                                : ResponseEnvelopePage.create(HttpStatus.NO_CONTENT.value(),
                                                MessagesConsts.NOTAS_FISCAIS_NOT_FOUND);
        }

        @Override
        public ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto> getById(String id) {
                final Optional<NotaFiscal> notaFiscal = notasFiscaisRepository.findById(id);

                return notaFiscal.isPresent()
                                ? ResponseEnvelopeSingleObject.create(notaFiscal.get(), getNotaFiscalView)
                                : ResponseEnvelopeSingleObject.create(HttpStatus.NOT_FOUND.value(),
                                                MessagesConsts.NOTA_FISCAL_NOT_FOUND);
        }

        @Override
        public ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto> post(NotaFiscalInputDto notaFiscalInputDto) {
                final BiFunction<Empresa, Empresa, ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto>> saveNotaFiscal = (
                                prestador,
                                tomador) -> {
                        final NotaFiscal notaFiscal = new NotaFiscal();
                        notaFiscal.setServico(notaFiscalInputDto.getServico());
                        notaFiscal.setValor(notaFiscalInputDto.getValor());
                        notaFiscal.setDataEmissao(
                                        LocalDate.parse(notaFiscalInputDto.getDataEmissao(),
                                                        DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        notaFiscal.setPrestador(prestador);
                        notaFiscal.setTomador(tomador);

                        final NotaFiscal novaNotaFiscal = notasFiscaisRepository.save(notaFiscal);

                        prestador.getNotasFiscais().add(novaNotaFiscal);
                        empresasRepository.save(prestador);

                        return ResponseEnvelopeSingleObject.create(novaNotaFiscal,
                                        getNotaFiscalView);

                };

                Optional<Empresa> prestador = empresasRepository.findById(notaFiscalInputDto.getPrestador());
                if (!prestador.isPresent())
                        return ResponseEnvelopeSingleObject.create(HttpStatus.NOT_FOUND.value(),
                                        MessagesConsts.PRESTADOR_NOT_FOUND);

                Optional<Empresa> tomador = empresasRepository.findById(notaFiscalInputDto.getTomador());
                if (!tomador.isPresent())
                        return ResponseEnvelopeSingleObject.create(HttpStatus.NOT_FOUND.value(),
                                        MessagesConsts.TOMADOR_NOT_FOUND);

                return saveNotaFiscal.apply(prestador.get(), tomador.get());
        }

        @Override
        public ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto> darBaixa(String id) {
                final Function<NotaFiscal, ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto>> saveNotaFiscal = notaFiscal -> {
                        notaFiscal.setStatus(StatusNota.BAIXADA);

                        return ResponseEnvelopeSingleObject.create(notasFiscaisRepository.save(notaFiscal),
                                        getNotaFiscalView);
                };

                Optional<NotaFiscal> notaFiscalOptional = notasFiscaisRepository.findById(id);

                return notaFiscalOptional.isPresent()
                                ? saveNotaFiscal.apply(notaFiscalOptional.get())
                                : ResponseEnvelopeSingleObject.create(HttpStatus.NOT_FOUND.value(),
                                                MessagesConsts.NOTA_FISCAL_NOT_FOUND);
        }

        @Override
        public ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto> delete(String id) {
                final Function<NotaFiscal, ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto>> deleteNotaFiscal = notaFiscal -> {
                        notasFiscaisRepository.delete(notaFiscal);
                        return ResponseEnvelopeSingleObject.create(notaFiscal, getNotaFiscalView);
                };

                Optional<NotaFiscal> notaFiscal = notasFiscaisRepository.findById(id);

                if (!notaFiscal.isPresent())
                        return ResponseEnvelopeSingleObject.create(HttpStatus.NOT_FOUND.value(),
                                        MessagesConsts.NOTA_FISCAL_NOT_FOUND);

                if (notaFiscal.get().getStatus() != StatusNota.BAIXADA)
                        return ResponseEnvelopeSingleObject.create(HttpStatus.BAD_REQUEST.value(),
                                        MessagesConsts.NOTA_FISCAL_ABERTA);

                return deleteNotaFiscal.apply(notaFiscal.get());

        }

        Function<NotaFiscal, NotaFiscalViewDto> getNotaFiscalView = notaFiscal -> {
                final Function<Empresa, EmpresaViewDto> getEmpresaView = empresa -> {
                        final EmpresaViewDto empresaViewDto = new EmpresaViewDto();
                        empresaViewDto.setId(empresa.getId());
                        empresaViewDto.setRazaoSocial(empresa.getRazaoSocial());
                        empresaViewDto.setCnpj(empresa.getCnpj());
                        empresaViewDto.setCidade(empresa.getCidade());

                        return empresaViewDto;
                };
                final NotaFiscalViewDto notaFiscalView = new NotaFiscalViewDto();

                notaFiscalView.setId(notaFiscal.getId());
                notaFiscalView.setServico(notaFiscal.getServico());
                notaFiscalView.setValor(notaFiscal.getValor());
                notaFiscalView.setDataEmissao(notaFiscal.getDataEmissao());
                notaFiscalView.setStatus(notaFiscal.getStatus());
                notaFiscalView.setPrestador(getEmpresaView.apply(notaFiscal.getPrestador()));
                notaFiscalView.setTomador(getEmpresaView.apply(notaFiscal.getTomador()));

                return notaFiscalView;

        };

}
