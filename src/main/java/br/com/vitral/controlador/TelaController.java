package br.com.vitral.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import br.com.vitral.modelo.TelaTVModel;
import br.com.vitral.persistencia.TelaDao;
import br.com.vitral.util.Uteis;

@Named(value = "telaController")
@SessionScoped
public class TelaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TelaTVModel telaModel;

	@Inject
	private TelaDao telaDao;

	@Produces
	private List<TelaTVModel> telas;

	@PostConstruct
	public void init() {
		telas = telaDao.listar();
	}

	public void cadastrar() {
		telaModel = new TelaTVModel();
		init();
		telaModel.setPosicao(telas.size() + 1);
		PrimeFaces.current().executeScript("PF('dialogCadastro').show();");
	}

	public void salvar() {
		telaDao.salvar(telaModel);
		init();
		this.telaModel = new TelaTVModel();
		PrimeFaces.current().executeScript("PF('dialogCadastro').hide();");
		Uteis.messageInformation("Tela cadastrada com sucesso");
	}

	public void excluir(TelaTVModel telaModel) {
		telaDao.remover(telaModel.getId());
		init();
	}

	public List<TelaTVModel> getTelas() {
		return telas;
	}

	public void onRowEdit(RowEditEvent event) {
		telaDao.salvar((TelaTVModel) event.getObject());
		Uteis.messageInformation("Tela alterada com sucesso");
	}

	public void onRowCancel() {
		Uteis.messageInformation("Operação cancelada");
	}

	public TelaTVModel getTelaModel() {
		return this.telaModel;
	}

	public void setTelaModel(TelaTVModel telaModel) {
		this.telaModel = telaModel;
	}

	public void subir(int posicao) {
		telaDao.subir(posicao);
		init();
	}

	public void descer(int posicao) {
		telaDao.descer(posicao);
		init();
	}

}