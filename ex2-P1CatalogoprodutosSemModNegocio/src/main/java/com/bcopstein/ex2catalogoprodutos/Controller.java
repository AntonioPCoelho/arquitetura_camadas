package com.bcopstein.ex2catalogoprodutos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private ProdutoServico produtoServico;

    @Autowired
    public Controller(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*")
    public String welcomeMessage() {
        return ("Bem vindo as lojas ACME");
    }

    @GetMapping("produtosDisponiveis")
    @CrossOrigin(origins = "*")
    public List<Produto> produtosDisponiveis() {
        return produtoServico.disponivel();
    }

    @PostMapping("venda")
    @CrossOrigin(origins = "*")
    public double venda(@PathVariable(value = "codigo") int codigo, 
                        @PathVariable(value = "quantidade") int quantidade) {
        return produtoServico.venda(codigo, quantidade);
    }

    @PostMapping("entradaNoEstoque")
    @CrossOrigin(origins = "*")
    public void entradaNoEstoque(@PathVariable(value = "codigo") int codigo,
                                 @PathVariable(value = "quantidade") int quantidade) {
        produtoServico.entradaEstoque(codigo, quantidade);
    }

    @GetMapping("comprasNecessarias")
    @CrossOrigin(origins = "*")
    public List<Produto> comprasNecessarias() {
        return produtoServico.necessario();
    }
}
