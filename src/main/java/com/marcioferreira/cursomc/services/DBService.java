package com.marcioferreira.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcioferreira.cursomc.domain.Categoria;
import com.marcioferreira.cursomc.domain.Cidade;
import com.marcioferreira.cursomc.domain.Cliente;
import com.marcioferreira.cursomc.domain.Endereco;
import com.marcioferreira.cursomc.domain.Estado;
import com.marcioferreira.cursomc.domain.ItemPedido;
import com.marcioferreira.cursomc.domain.Pagamento;
import com.marcioferreira.cursomc.domain.PagamentoComBoleto;
import com.marcioferreira.cursomc.domain.PagamentoComCartao;
import com.marcioferreira.cursomc.domain.Pedido;
import com.marcioferreira.cursomc.domain.Produto;
import com.marcioferreira.cursomc.domain.enums.EstadoPagamento;
import com.marcioferreira.cursomc.domain.enums.TipoCliente;
import com.marcioferreira.cursomc.repositories.CategoriaRepository;
import com.marcioferreira.cursomc.repositories.CidadeRepository;
import com.marcioferreira.cursomc.repositories.ClienteRepository;
import com.marcioferreira.cursomc.repositories.EnderecoRepository;
import com.marcioferreira.cursomc.repositories.EstadoRepository;
import com.marcioferreira.cursomc.repositories.ItemPedidoRepository;
import com.marcioferreira.cursomc.repositories.PagamentoRepository;
import com.marcioferreira.cursomc.repositories.PedidoRepository;
import com.marcioferreira.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public void instantiateDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Nova Categoria");
		Categoria cat4 = new Categoria(null, "Outra Categoria");
		Categoria cat5 = new Categoria(null, "Mais Uma");
		Categoria cat6 = new Categoria(null, "a Sexta");
		Categoria cat7 = new Categoria(null, "Setima");
		Categoria cat8 = new Categoria(null, "Oitava");
		Categoria cat9 = new Categoria(null, "Teste-dev");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 20.00);
		
		Produto p4 = new Produto(null, "Mesa", 2000.00);
		Produto p5 = new Produto(null, "Cadeira", 800.00);
		Produto p6 = new Produto(null, "Toalha", 20.00);
		
		Produto p7 = new Produto(null, "Colcha", 2000.00);
		Produto p8 = new Produto(null, "Rocadeira", 800.00);
		Produto p9 = new Produto(null, "Pendente", 20.00);
		
		Produto p10 = new Produto(null, "Shampoo", 2000.00);
		Produto p11 = new Produto(null, "Smart TV", 800.00);
		Produto p12 = new Produto(null, "Celular", 20.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10, p12));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat4));
		
		p7.getCategorias().addAll(Arrays.asList(cat5));
		p8.getCategorias().addAll(Arrays.asList(cat5, cat6));
		p9.getCategorias().addAll(Arrays.asList(cat7));
		
		p10.getCategorias().addAll(Arrays.asList(cat8));
		p11.getCategorias().addAll(Arrays.asList(cat3, cat6, cat8));
		p12.getCategorias().addAll(Arrays.asList(cat1, cat4, cat7));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4,cat5, cat6, cat7, cat8, cat9)); 
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
		
		Estado est1 = new Estado(null, "MG");
		Estado est2 = new Estado(null, "SP");
		Estado est3 = new Estado(null, "RJ");
		
		Cidade c1 = new Cidade(null, "Uverlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Rio de Janeiro", est3);
		Cidade c4 = new Cidade(null, "Niteroi", est3);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2));
		est3.getCidades().addAll(Arrays.asList(c3,c4));
	
		estadoRepository.saveAll(Arrays.asList(est1, est2, est3)); 
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "silvaferreira.marciojose@gmail.com", "12345678901", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("996568515", "987439606"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "apto 3", "Jardim", "99999999", cli1, c1);
		Endereco e2 = new Endereco(null, "Av Matos", "300", "apto 3", "Centro", "99999999", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1)); 
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 

	    Pedido ped1 = new Pedido(null, sdf.parse("30/05/2024 10:32"), cli1, e1);
	    Pedido ped2 = new Pedido(null, sdf.parse("07/06/2024 10:32"), cli1, e2);	    
	    
	    Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
	    ped1.setPagamento(pagto1);
	    
	    Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("07/06/2024 20:17"), null);
	    ped2.setPagamento(pagto2);
	    
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2)); 
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p2.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
