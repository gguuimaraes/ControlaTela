package br.com.vitral.controlador;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vitral.modelo.TVModel;
import br.com.vitral.modelo.TelaTVModel;
import br.com.vitral.persistencia.TVDao;

@Named(value = "indexController")
@SessionScoped
public class IndexController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	private List<TelaTVModel> telas;

	@Inject
	TVDao tvDao;

	TVModel tvModel;
	
	TelaTVModel telaAtual;
	
	int posicao;

	public void onPageLoad() {
		try {
			String strId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tv");
			Integer tvId = Integer.parseInt(strId);
			tvModel = tvDao.consultar(tvId);
			telas = tvModel.getTelas();
			if (!telas.isEmpty()) {
				telaAtual = telas.get(0);
				posicao = 0;
			}
		} catch (Exception e) {

		}
	}

	public void listener() {
		if (posicao + 1 < telas.size()) {
			posicao++;
		} else {
			posicao = 0;
		}
		if (!telas.isEmpty()) {
			telaAtual = telas.get(posicao);
		}
	}

	public TelaTVModel getTelaAtual() {
		return telaAtual;
	}

	public void setTelaAtual(TelaTVModel telaAtual) {
		this.telaAtual = telaAtual;
	}

}