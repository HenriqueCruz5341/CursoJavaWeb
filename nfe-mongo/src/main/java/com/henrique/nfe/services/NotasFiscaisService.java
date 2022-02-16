package com.henrique.nfe.services;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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
import com.henrique.nfe.utils.genericquery.GenericQuery;
import com.henrique.nfe.utils.genericquery.SearchCriteria;
import com.henrique.nfe.utils.genericquery.SearchOperation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class NotasFiscaisService implements NotasFiscais {

        private NotasFiscaisRepository notasFiscaisRepository;
        private EmpresasRepository empresasRepository;
        private MongoTemplate mongoTemplate;

        public NotasFiscaisService(NotasFiscaisRepository notasFiscaisRepository,
                        EmpresasRepository empresasRepository, MongoTemplate mongoTemplate) {
                this.notasFiscaisRepository = notasFiscaisRepository;
                this.empresasRepository = empresasRepository;
                this.mongoTemplate = mongoTemplate;
        }

        @Override
        public ResponseEnvelopePage<NotaFiscal, NotaFiscalViewDto> get(Integer pageIndex, Integer pageSize,
                        String prestador, String tomador, Double valorMinimo, String servicos,
                        String dataEmissaoFim) {
                GenericQuery genericQuery = new GenericQuery();

                if (prestador != null && !prestador.isEmpty())
                        genericQuery.add(new SearchCriteria("prestador.id", prestador, SearchOperation.EQUAL));
                if (tomador != null && !tomador.isEmpty())
                        genericQuery.add(new SearchCriteria("tomador.id", tomador, SearchOperation.EQUAL));
                if (valorMinimo != null)
                        genericQuery.add(new SearchCriteria("valorTotal", valorMinimo,
                                        SearchOperation.GREATER_THAN_EQUAL));
                if (servicos != null && !servicos.isEmpty()) {
                        List<String> servicosArray = Arrays.asList(servicos.split(","));
                        genericQuery.add(new SearchCriteria("servicos.descricao", servicosArray, SearchOperation.IN));
                }
                if (dataEmissaoFim != null && !dataEmissaoFim.isEmpty()) {
                        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dataEmissaoFim,
                                        DateTimeFormatter.ISO_DATE_TIME);
                        LocalDate dataEmissaoFimLocalDate = zonedDateTime.toLocalDate();
                        genericQuery.add(new SearchCriteria("dataEmissao", dataEmissaoFimLocalDate,
                                        SearchOperation.LESS_THAN_EQUAL));
                }

                Pageable pageable = PageRequest.of(pageIndex, pageSize);
                Query query = new Query(genericQuery.generate()).with(pageable);
                List<NotaFiscal> listaNotaFiscal = mongoTemplate.find(query, NotaFiscal.class);
                final Page<NotaFiscal> notaFiscalPage = PageableExecutionUtils.getPage(
                                listaNotaFiscal,
                                pageable,
                                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), NotaFiscal.class));

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
                        notaFiscal.setServicos(notaFiscalInputDto.getServicos());
                        notaFiscal.setValorTotal(notaFiscalInputDto.getServicos().stream().mapToDouble(
                                        servico -> servico.getValor()).sum());
                        notaFiscal.setDataEmissao(
                                        LocalDate.parse(notaFiscalInputDto.getDataEmissao(),
                                                        DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        notaFiscal.setPrestador(prestador);
                        notaFiscal.setTomador(tomador);

                        final NotaFiscal novaNotaFiscal = notasFiscaisRepository.save(notaFiscal);

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
                notaFiscalView.setServicos(notaFiscal.getServicos());
                notaFiscalView.setValorTotal(notaFiscal.getServicos().stream().mapToDouble(
                                servico -> servico.getValor()).sum());
                notaFiscalView.setDataEmissao(notaFiscal.getDataEmissao());
                notaFiscalView.setStatus(notaFiscal.getStatus());
                notaFiscalView.setPrestador(getEmpresaView.apply(notaFiscal.getPrestador()));
                notaFiscalView.setTomador(getEmpresaView.apply(notaFiscal.getTomador()));

                return notaFiscalView;

        };

}
