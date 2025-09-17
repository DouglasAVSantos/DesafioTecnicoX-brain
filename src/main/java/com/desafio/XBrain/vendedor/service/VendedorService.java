package com.desafio.XBrain.vendedor.service;

import com.desafio.XBrain.shared.exception.ConflictException;
import com.desafio.XBrain.shared.exception.NotFoundException;
import com.desafio.XBrain.vendedor.dto.VendedorRequestDto;
import com.desafio.XBrain.vendedor.dto.VendedorResponseDto;
import com.desafio.XBrain.vendedor.entity.Vendedor;
import com.desafio.XBrain.vendedor.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendedorService {

    private final VendedorRepository repository;

    public VendedorResponseDto cadastrar(VendedorRequestDto request) {
        if (repository.findByNomeAndSobrenomeAndAtivoTrue(request.nome(), request.sobrenome()).isPresent()) {
            throw new ConflictException("Vendedor '" + request.nomeCompleto() + "' já cadastrado");
        }
        Vendedor novoVendedor = new Vendedor(request);
        return new VendedorResponseDto(repository.save(novoVendedor));
    }

    public VendedorResponseDto getVendedor(Long id) {
        return new VendedorResponseDto(findById(id));
    }

    public List<VendedorResponseDto> getLista(){
        return repository.findAllByAtivoTrue().stream().map(VendedorResponseDto::new).toList();
    }


    public void deletar(Long id) {
        Vendedor vendedorEncontrado = findById(id);
        vendedorEncontrado.setAtivo(false);
        repository.save(vendedorEncontrado);
    }

    public VendedorResponseDto atualizar(Long id, VendedorRequestDto request){
        Vendedor vendedorEncontrado = findById(id);
        vendedorEncontrado.setNome(request.nome());
        vendedorEncontrado.setSobrenome(request.sobrenome());
        return new VendedorResponseDto(repository.save(vendedorEncontrado));
    }

    public Vendedor findById(Long id){
        return repository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new NotFoundException("Vendedor não encontrado para o id: " + id));
    }

}
