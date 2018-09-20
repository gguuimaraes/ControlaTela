package br.com.vitral.controlador;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vitral.modelo.TVModel;
import br.com.vitral.modelo.TelaTVModel;
import br.com.vitral.persistencia.TVDao;

@Named(value = "verTVController")
@SessionScoped
public class VerTVController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TVDao tvDao;

	private TVModel tv;

	private int posicao;

	public void onPageLoad() {
		try {
			atualizarTV(Integer.parseInt(
					FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tv")));
		} catch (Exception e) {

		}
	}

	public void listener() {
		atualizarTV(tv.getId());
		posicao = posicao < tv.getTelas().size() -1 ? posicao + 1 : 0;
	}

	public TelaTVModel getTela() {
		return tv.getTelas().get(posicao);
	}

	private void atualizarTV(int id) {
		tv = tvDao.consultar(id);
	}

}