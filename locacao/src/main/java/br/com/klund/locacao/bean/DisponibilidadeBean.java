package br.com.klund.locacao.bean;

import java.io.Serializable;
import java.text.NumberFormat;

import javax.annotation.PostConstruct;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.com.klund.locacao.modelo.dao.EquipamentoDao;
import br.com.klund.locacao.modelo.dao.LocacaoDao;
import br.com.klund.locacao.modelo.negocio.Equipamento;
import br.com.klund.locacao.modelo.negocio.Locacao;
import br.com.klund.locacao.modelo.negocio.StatusEquipamento;
import br.com.klund.locacao.tx.Transacional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Named
@ViewScoped
public class DisponibilidadeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	 private PieChartModel pieModel;
	 private PieChartModel pieModel2;
	 
	@Inject
	private EquipamentoDao equipamentoDao = new EquipamentoDao();
	@Inject
	private LocacaoDao locacaoDao = new LocacaoDao();
	@Inject
	private Equipamento equipamento = new Equipamento();
	private List<Equipamento> equipamentoTotal;
	private int alugados;
	private int disponiveis;
	private int total;
	private int inoperantes;
	private int manutencao;
	private float valorAlugado;
	private float valorPossivel;

	@PostConstruct
	public void init() {
		equipamentoTotal = new ArrayList<Equipamento>();
		equipamentoTotal = equipamentoDao.listarTodos();
		calcularTotais();
		createPieModel();
	}
	
	 public PieChartModel getPieModel1() {
	        return pieModel;
	    }
	     

	public List<Equipamento> getEquipamentoTotal() {
		return equipamentoTotal;
	}

	public void setEquipamentoTotal(List<Equipamento> equipamentoTotal) {
		this.equipamentoTotal = equipamentoTotal;
	}

	@Transacional
	public void calcularTotais() {
		total = equipamentoTotal.size();
		for (int i = 0; i < equipamentoTotal.size(); i++) {
			if ((equipamentoTotal.get(i).getStatus() == (StatusEquipamento.Alugado))) {
				alugados = alugados + 1;
			}
			if ((equipamentoTotal.get(i).getStatus() == (StatusEquipamento.Disponível))) {
				disponiveis = disponiveis + 1;
			}
			if ((equipamentoTotal.get(i).getStatus() == (StatusEquipamento.Inoperante))) {
				inoperantes = inoperantes + 1;
			}
			if ((equipamentoTotal.get(i).getStatus() == (StatusEquipamento.Manutenção))) {
				manutencao = manutencao + 1;
			}
			valorPossivel = (equipamentoTotal.get(i).getPrecoDiariaMensal()* 30) + valorPossivel; 
		}
		calcularTotalAlugado();
	}
	
	
	public void calcularTotalAlugado() {
		float acumulado = 0;
		List<Locacao> locacoes = locacaoDao.locacoesAtivas();
		for (int i = 0; i< locacoes.size(); i++) {
			acumulado = locacoes.get(i).getValorTotal() + acumulado;
		}
		valorAlugado = acumulado;
	}
	
	
	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
	}


	public float getValorAlugado() {
		return valorAlugado;
	}

	public void setValorAlugado(float valorAlugado) {
		this.valorAlugado = valorAlugado;
	}

	public float getValorPossivel() {
	
		return valorPossivel;
	}
	
	@Transacional
	public String valorPossivelString() {
		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valorPossivel);
		return valorString;
	}
	
	@Transacional
	public String valorAlugadoString() {
		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valorAlugado);
		return valorString;
	}

	public void setValorPossivel(float valorPossivel) {
		this.valorPossivel = valorPossivel;
	}

	public int getAlugados() {
		return alugados;
	}

	public void setAlugados(int alugados) {
		this.alugados = alugados;
	}

	public int getDisponiveis() {
		return disponiveis;
	}

	public void setDisponiveis(int disponiveis) {
		this.disponiveis = disponiveis;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getInoperantes() {
		return inoperantes;
	}

	

	public int getManutencao() {
		return manutencao;
	}

	public void setManutencao(int manutencao) {
		this.manutencao = manutencao;
	}

	public void setInoperantes(int inoperantes) {
		this.inoperantes = inoperantes;
	}
	
	public PieChartModel getPieModel() {
        return pieModel;
    }
	
	private void createPieModel() {
        pieModel = new PieChartModel();
        pieModel.set("Alugados", alugados);
        pieModel.set("Disponíveis", disponiveis);
        pieModel.set("Em manutenção", manutencao);
        pieModel.set("Inoperantes", inoperantes);
        
        pieModel.setTitle("Utilização dos Equipamentos");
        pieModel.setLegendPosition("w");
        pieModel.setShadow(false);
        
        
        pieModel2 = new PieChartModel();
        pieModel2.set("Alugado", valorAlugado );
        pieModel2.set("Disponivel para Locação", valorPossivel);
        
        pieModel2.setTitle("Capacidade de Locação");
        pieModel2.setLegendPosition("w");
        pieModel2.setShadow(false);
    }
	
}
