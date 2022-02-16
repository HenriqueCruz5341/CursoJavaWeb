package com.henrique.nfe.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.henrique.nfe.consts.MessagesConsts;
import com.henrique.nfe.domains.Empresas;
import com.henrique.nfe.entities.Empresa;
import com.henrique.nfe.entities.NotaFiscal;
import com.henrique.nfe.models.empresas.EmpresaEditDto;
import com.henrique.nfe.models.empresas.EmpresaInputDto;
import com.henrique.nfe.models.empresas.EmpresaViewDto;
import com.henrique.nfe.models.envelopes.ResponseEnvelopePage;
import com.henrique.nfe.models.envelopes.ResponseEnvelopeSingleObject;
import com.henrique.nfe.repositories.EmpresasRepository;
import com.henrique.nfe.repositories.NotasFiscaisRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EmpresasService implements Empresas {

    private EmpresasRepository empresasRepository;
    private NotasFiscaisRepository notasFiscaisRepository;
    private MongoTemplate mongoTemplate;

    public EmpresasService(NotasFiscaisRepository notasFiscaisRepository,
            EmpresasRepository empresasRepository, MongoTemplate mongoTemplate) {
        this.notasFiscaisRepository = notasFiscaisRepository;
        this.empresasRepository = empresasRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ResponseEnvelopePage<Empresa, EmpresaViewDto> get(Integer pageIndex, Integer pageSize) {
        final Page<Empresa> empresaPage = empresasRepository.findAll(PageRequest.of(pageIndex, pageSize));

        return empresaPage.hasContent()
                ? ResponseEnvelopePage.create(empresaPage, getEmpresaView)
                : ResponseEnvelopePage.create(HttpStatus.NO_CONTENT.value(), MessagesConsts.EMPRESAS_NOT_FOUND);

    }

    @Override
    public ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto> getById(String id) {
        final Optional<Empresa> empresa = empresasRepository.findById(id);

        return empresa.isPresent()
                ? ResponseEnvelopeSingleObject.create(empresa.get(), getEmpresaView)
                : ResponseEnvelopeSingleObject.create(HttpStatus.NOT_FOUND.value(), MessagesConsts.EMPRESA_NOT_FOUND);
    }

    @Override
    public ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto> post(EmpresaInputDto empresaInputDto) {
        final Supplier<ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto>> saveEmpresa = () -> {
            final Empresa empresa = new Empresa();
            empresa.setCnpj(empresaInputDto.getCnpj());
            empresa.setCidade(empresaInputDto.getCidade());
            empresa.setRazaoSocial(empresaInputDto.getRazaoSocial());

            return ResponseEnvelopeSingleObject.create(empresasRepository.save(empresa), getEmpresaView);
        };

        final Empresa empresaOptional = empresasRepository.findByCnpj(empresaInputDto.getCnpj());

        return empresaOptional == null
                ? saveEmpresa.get()
                : ResponseEnvelopeSingleObject.create(HttpStatus.CONFLICT.value(), MessagesConsts.CNPJ_CONFLICT);
    }

    @Override
    public ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto> put(String id, EmpresaEditDto empresaEditDto) {
        final Function<Empresa, ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto>> saveEmpresa = empresa -> {
            if (empresaEditDto.getCidade() != null)
                empresa.setCidade(empresaEditDto.getCidade());
            if (empresaEditDto.getRazaoSocial() != null)
                empresa.setRazaoSocial(empresaEditDto.getRazaoSocial());

            final Empresa updatedEmpresa = empresasRepository.save(empresa);

            mongoTemplate.updateMulti(new Query(where("tomador._id").is(id)),
                    new Update().set("tomador", updatedEmpresa), NotaFiscal.class);

            mongoTemplate.updateMulti(new Query(where("prestador._id").is(id)),
                    new Update().set("prestador", updatedEmpresa), NotaFiscal.class);

            return ResponseEnvelopeSingleObject.create(updatedEmpresa, getEmpresaView);
        };

        final Optional<Empresa> empresaOptional = empresasRepository.findById(id);

        return empresaOptional.isPresent()
                ? saveEmpresa.apply(empresaOptional.get())
                : ResponseEnvelopeSingleObject.create(HttpStatus.NOT_FOUND.value(), MessagesConsts.EMPRESA_NOT_FOUND);
    }

    @Override
    public ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto> delete(String id) {
        final Function<Empresa, ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto>> deleteEmpresa = empresa -> {
            final List<NotaFiscal> notasFiscais = notasFiscaisRepository.findByPrestadorIdOrTomadorId(id);

            if (!notasFiscais.isEmpty())
                return ResponseEnvelopeSingleObject.create(HttpStatus.CONFLICT.value(),
                        MessagesConsts.EMPRESA_TEM_NOTA);

            empresasRepository.delete(empresa);

            return ResponseEnvelopeSingleObject.create(empresa, getEmpresaView);
        };

        final Optional<Empresa> empresaOptional = empresasRepository.findById(id);

        return empresaOptional.isPresent()
                ? deleteEmpresa.apply(empresaOptional.get())
                : ResponseEnvelopeSingleObject.create(HttpStatus.NOT_FOUND.value(), MessagesConsts.EMPRESA_NOT_FOUND);
    }

    Function<Empresa, EmpresaViewDto> getEmpresaView = empresa -> {
        final EmpresaViewDto empresaViewDto = new EmpresaViewDto();
        empresaViewDto.setId(empresa.getId());
        empresaViewDto.setCnpj(empresa.getCnpj());
        empresaViewDto.setCidade(empresa.getCidade());
        empresaViewDto.setRazaoSocial(empresa.getRazaoSocial());

        return empresaViewDto;
    };

}
