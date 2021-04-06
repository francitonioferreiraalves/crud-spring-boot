package com.tito.crud.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tito.crud.domain.exception.EntidadeNaoEncontradaException;
import com.tito.crud.domain.model.Pessoa;
import com.tito.crud.domain.repository.PessoaRepository;

@Service
public class PessoaService {

	private PessoaRepository pessoaRepository;

	public PessoaService(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	public Page<Pessoa> listar(Pageable pageable) {
		return pessoaRepository.findAll(pageable);
	}

	public Pessoa buscar(Long id) {
		return findOrFail(id);
	}

	public Pessoa salvar(Pessoa pessoa) {
		pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
		return this.pessoaRepository.save(pessoa);
	}

	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = findOrFail(id);

		pessoaSalva.getContatos().clear();

		pessoaSalva.getContatos().addAll(pessoa.getContatos());

		pessoaSalva.getContatos().forEach(c -> c.setPessoa(pessoaSalva));

		BeanUtils.copyProperties(pessoa, pessoaSalva, "id", "contatos");

		return pessoaRepository.save(pessoaSalva);
	}

	public void delete(Long id) {
		Pessoa p = findOrFail(id);
		pessoaRepository.delete(p);

	}

	private Pessoa findOrFail(Long id) {
		return pessoaRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não localizada na base de dados"));

	}

}