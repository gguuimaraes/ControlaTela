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

import br.com.vitral.modelo.TVModel;
import br.com.vitral.modelo.TelaTVModel;
import br.com.vitral.persistencia.TVDao;
import br.com.vitral.util.Uteis;

@Named(value = "tvController")
@SessionScoped
public class TVController implements Serializable {

	private static final long serialVersionUID = 1L;

	private TVModel tvModel;

	@Inject
	private TVDao tvDao;

	@Produces
	private List<TVModel> tvs;

	@PostConstruct
	public void init() {
		tvs = tvDao.listar();
	}

	public void cadastrar() {
		tvModel = new TVModel();
		PrimeFaces.current().executeScript("PF('dialogCadastro').show();");
	}

	public void salvar() {
		tvDao.salvar(tvModel);
		init();
		this.tvModel = new TVModel();
		PrimeFaces.current().executeScript("PF('dialogCadastro').hide();");
		Uteis.messageInformation("TV cadastrada com sucesso");
	}

	public void excluir(TVModel tvModel) {
		tvDao.remover(tvModel.getId());
		tvs.remove(tvModel);
		Uteis.messageInformation("TV excluída com sucesso");
	}

	public void adicionarTela(TVModel tvModel) {
		TelaTVModel telaTVModel = new TelaTVModel();
		telaTVModel.setPosicao(tvModel.getTelas().size() + 1);
		tvModel.getTelas().add(telaTVModel);
	}

	public void removerTelas(TVModel tvModel) {
		tvModel.getTelas().clear();
	}

	public void removerTela(TVModel tvModel, TelaTVModel telaModel) {
		tvModel.getTelas().remove(telaModel);
		if (tvModel.getTelas().size() == 1)
			tvModel.getTelas().get(0).setPosicao(1);
	}

	public void subirTela(TVModel tvModel, TelaTVModel tela) {
		int antigo = tela.getPosicao();
		int novo = tela.getPosicao() - 1;
		tela.setPosicao(novo);
		TelaTVModel tela2 = tvModel.getTelas().remove(novo - 1);
		tela2.setPosicao(antigo);
		tvModel.getTelas().add(antigo - 1, tela2);
	}

	public void descerTela(TVModel tvModel, TelaTVModel tela) {
		int antigo = tela.getPosicao();
		int novo = tela.getPosicao() + 1;
		tela.setPosicao(novo);
		TelaTVModel tela2 = tvModel.getTelas().remove(novo - 1);
		tela2.setPosicao(antigo);
		tvModel.getTelas().add(antigo - 1, tela2);
	}

	public TVModel getTvModel() {
		return tvModel;
	}

	public void setTvModel(TVModel tvModel) {
		this.tvModel = tvModel;
	}

	public List<TVModel> getTvs() {
		return tvs;
	}

	public void setTvs(List<TVModel> tvs) {
		this.tvs = tvs;
	}

	public void onRowEdit(RowEditEvent event) {
		tvDao.salvar((TVModel) event.getObject());
		Uteis.messageInformation("TV alterada com sucesso");
	}

	public void onRowCancel(RowEditEvent event) {
		TVModel tvModel = (TVModel) event.getObject();
		tvs.set(tvs.indexOf(tvModel), tvDao.consultar(tvModel.getId()));
		Uteis.messageInformation("Operação cancelada");
	}
}