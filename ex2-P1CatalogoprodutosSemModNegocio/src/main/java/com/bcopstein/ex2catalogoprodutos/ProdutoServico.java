package com.bcopstein.ex2catalogoprodutos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoServico {
    private ProdutoDAO produtoDAO;
    private LogicaVenda logicaVenda;

    @Autowired
    public ProdutoServico(ProdutoDAO produtoDAO, LogicaVenda logicaVenda) {
        this.produtoDAO = produtoDAO;
        this.logicaVenda = logicaVenda;
    }

    // Lista produtos disponíveis
    public List<Produto> disponivel() {
        return produtoDAO.getProdutosDisponiveis();
    }

    // Fazer uma venda
    public double venda(int codigo, int quantidade) {
        Produto prod = produtoDAO.buscaPorCodig(codigo);
        // Verifica se o produto existe
        if (prod == null) return -1;
        // Verfica se tem quantidade suficiente
        int novaQtd = prod.getQtdadeEstoque() - quantidade;
        if (novaQtd <= 0) return -1;

        // Atualiza a quantidade no estoque
        produtoDAO.atualizaQuantidade(codigo, novaQtd);

        // Calcula o valor da venda
        return logicaVenda.calculaCusto(prod, quantidade);
    }

    // Entrada do produto no estoque
    public void entradaEstoque(int codigo, int quantidade) {
        Produto prod = produtoDAO.buscaPorCodig(codigo);
        // Verifica se o produto existe
        if (prod == null) return;

    // Atualiza a quantidade no estoque
    int novaQtd = prod.getQtdadeEstoque() - quantidade;
    produtoDAO.atualizaQuantidade(codigo, novaQtd);
    }

    // Lista as compras necessarias
    public List<Produto> necessario() {
        return produtoDAO.comprasNecessarias();
    }
}
