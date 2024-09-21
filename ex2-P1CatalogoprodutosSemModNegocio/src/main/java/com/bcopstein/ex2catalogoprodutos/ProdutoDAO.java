package com.bcopstein.ex2catalogoprodutos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProdutoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Produto> getProdutosDisponiveis() {
        List<Produto> resp = this.jdbcTemplate.query("SELECT * from produtos where qtdadeEstoque > 0",
                (rs, rowNum) -> new Produto(rs.getLong("codigo"),
                                            rs.getString("descricao"), 
                                            rs.getDouble("precoUnitario"),
                                            rs.getInt("qtdadeEstoque")));
        return resp;
    }

    public Produto buscaPorCodig(long codigo){
        List<Produto> resp = this.jdbcTemplate.query("SELECT * from produtos where codigo="+codigo,
                (rs, rowNum) -> new Produto(rs.getLong("codigo"),
                                            rs.getString("descricao"), 
                                            rs.getDouble("precoUnitario"),
                                            rs.getInt("qtdadeEstoque")));
        if (resp.size() == 0){
            return null;
        }else{
            return resp.get(0);
        }
    }

    public boolean atualizaQuantidade(long codigo,int novaQuantidade){
        this.jdbcTemplate.update(
            "UPDATE produtos SET qtdadeEstoque = "+novaQuantidade+" where codigo = "+codigo+";");
        return true;
    }

    public List<Produto> comprasNecessarias(){
        List<Produto> resp = this.jdbcTemplate.query("SELECT * from produtos where qtdadeEstoque < 2",
        (rs, rowNum) -> new Produto(rs.getLong("codigo"),
                                    rs.getString("descricao"), 
                                    rs.getDouble("precoUnitario"),
                                    rs.getInt("qtdadeEstoque")));       
        return resp; 
    }
}