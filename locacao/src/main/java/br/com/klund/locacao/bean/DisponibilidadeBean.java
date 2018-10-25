package br.com.klund.locacao.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.com.klund.locacao.modelo.dao.EquipamentoDao;
import br.com.klund.locacao.modelo.negocio.Equipamento;
import br.com.klund.locacao.modelo.negocio.StatusEquipamento;
import br.com.klund.locacao.tx.Transacional;

import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class DisponibilidadeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	 private PieChartModel pieModel;
	 
	@Inject
	private EquipamentoDao equipamentoDao = new EquipamentoDao();
	@Inject
	private Equipamento equipamento = new Equipamento();
	private List<Equipamento> equipamentoTotal;
	private int alugados;
	private int disponiveis;
	private int total;
	private int inoperantes;
	private int manutenção;

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
				inoperantes = inoperantes + 1;
			}
		}
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

	public int getManutenção() {
		return manutenção;
	}

	public void setManutenção(int manutenção) {
		this.manutenção = manutenção;
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
        pieModel.set("Em manutenção", manutenção);
        pieModel.set("Inoperantes", inoperantes);
        
        pieModel.setTitle("Utilização dos Equipamentos");
        pieModel.setLegendPosition("w");
        pieModel.setShadow(false);
    }
}
