package com.bcopstein.ex2catalogoprodutos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private ProdutoDAO produtoDAO;
    private LogicaVenda logicaVenda;

    @Autowired
    public Controller(ProdutoDAO produtoDAO, LogicaVenda logicaVenda) {
        this.produtoDAO = produtoDAO;
        this.logicaVenda = logicaVenda;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*")
    public String welcomeMessage() {
        return ("Bem vindo as lojas ACME");
    }

    @GetMapping("produtosDisponiveis")
    @CrossOrigin(origins = "*")
    public List<Produto> produtosDisponiveis() {
        return produtoDAO.getProdutosDisponiveis();
    }

    @GetMapping("venda/codigo/{codigo}/quantidade/{quantidade}")
    @CrossOrigin(origins = "*")
    public double venda(@PathVariable(value = "codigo") int codigo,
                        @PathVariable(value = "quantidade") int quantidade) {
        return logicaVenda.retornaValorVenda(codigo, quantidade);
    }

    @GetMapping("entradaNoEstoque/codigo/{codigo}/quantidade/{quantidade}")
    @CrossOrigin(origins = "*")
    public void entradaNoEstoque(@PathVariable(value = "codigo") int codigo,
                                 @PathVariable(value = "quantidade") int quantidade) {
        logicaVenda.atualizaEstoque(codigo, quantidade);
    }

    @GetMapping("comprasNecessarias")
    @CrossOrigin(origins = "*")
    public List<Produto> comprasNecessarias() {
        return produtoDAO.comprasNecessarias();
    }
}
