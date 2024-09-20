package com.bcopstein.ex2catalogoprodutos;

import org.springframework.stereotype.Component;

@Component
public class LogicaVenda {
    private ProdutoDAO produtoDAO;
    private LogicaVenda logicaVenda;
    public double calculaCusto(Produto produto, int quantidade){
        double custo = produto.getPrecoUnitario() * quantidade;
        double desconto = 0;
        if (quantidade > 10){
            desconto = custo * 0.1;
        }
        double imposto = (custo - desconto) * 0.05;
        double valor_a_pagar = custo - desconto + imposto;
        return valor_a_pagar;
    }

    public double retornaValorVenda(int codigo, int quantidade) {
        Produto produto = produtoDAO.buscaPorCodig(codigo);
        // Verifica se o produto existe
        if (produto == null) {
            return -1;
        }
        // Verifica se tem quantidade suficiente
        int novaQuantidade = produto.getQtdadeEstoque() - quantidade;
        if (novaQuantidade <= 0) {
            return -1;
        }
        // Atualiza a quantidade no estoque
        produtoDAO.atualizaQuantidade(codigo, novaQuantidade);
        // Calcula o valor da venda
        double valor = logicaVenda.calculaCusto(produto, quantidade);
        return valor;
    }

    public void atualizaEstoque(int codigo, int quantidade ) {
         // Recupera o produto
         Produto produto = produtoDAO.buscaPorCodig(codigo);
         // Verifica se o produto existe
         if (produto == null) {
             return;
         }
         // Calcula a nova quantidade e atualiza o estoque
         int novaQuantidade = produto.getQtdadeEstoque() + quantidade;
         produtoDAO.atualizaQuantidade(codigo, novaQuantidade);
    }
}
