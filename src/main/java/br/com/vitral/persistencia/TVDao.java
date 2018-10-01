package br.com.vitral.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitral.entidade.TV;
import br.com.vitral.entidade.TelaTV;
import br.com.vitral.modelo.TVModel;
import br.com.vitral.modelo.TelaTVModel;
import br.com.vitral.util.Uteis;

public class TVDao implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5260861235067025995L;

	@Inject
	TV tv;

	transient EntityManager em;

	public TVModel salvar(TVModel tvModel) {
		em = Uteis.getEntityManager();
		if (tvModel.getId() == null) {
			tv = new TV();
			tv.setNome(tvModel.getNome());
			List<TelaTV> telas = new ArrayList<>();
			TelaTV tela = null;
			for (TelaTVModel telaModel : tvModel.getTelas()) {
				tela = new TelaTV();
				tela.setPosicao(telaModel.getId());
				tela.setUrl(telaModel.getUrl());
				tela.setSegundos(telaModel.getSegundos());
				telas.add(tela);
			}
			tv.setTelas(telas);
			em.persist(tv);
			tvModel.setId(tv.getId());
		} else {
			tv = em.find(TV.class, tvModel.getId());
			tv.setNome(tvModel.getNome());
			tv.getTelas().clear();
			TelaTV tela = null;
			for (TelaTVModel telaModel : tvModel.getTelas()) {
				tela = new TelaTV();
				tela.setPosicao(telaModel.getPosicao());
				tela.setUrl(telaModel.getUrl());
				tela.setSegundos(telaModel.getSegundos());
				tv.getTelas().add(tela);
			}
			em.merge(tv);
		}
		return tvModel;
	}

	public List<TVModel> listar() {
		List<TVModel> tvsModel = new ArrayList<>();
		em = Uteis.getEntityManager();
		Query query = em.createNamedQuery("TV.findAll");
		@SuppressWarnings("unchecked")
		Collection<TV> tvs = (Collection<TV>) query.getResultList();
		TVModel tvModel = null;
		for (TV tv : tvs) {
			tvModel = new TVModel();
			tvModel.setId(tv.getId());
			tvModel.setNome(tv.getNome());
			List<TelaTVModel> telasModel = new ArrayList<>();
			TelaTVModel telaModel = null;
			for (TelaTV tela : tv.getTelas()) {
				telaModel = new TelaTVModel();
				telaModel.setId(tela.getId());
				telaModel.setPosicao(tela.getPosicao());
				telaModel.setUrl(tela.getUrl());
				telaModel.setSegundos(tela.getSegundos());
				telasModel.add(telaModel);
			}
			tvModel.setTelas(telasModel);
			tvsModel.add(tvModel);
		}
		return tvsModel;
	}

	public void remover(int id) {
		em = Uteis.getEntityManager();
		em.remove(em.find(TV.class, id));
	}

	public TVModel consultar(int id) {
		TVModel tvModel = null;
		TV tv = Uteis.getEntityManager().find(TV.class, id);
		if (tv != null) {
			tvModel = new TVModel();
			tvModel.setId(tv.getId());
			tvModel.setNome(tv.getNome());
			List<TelaTVModel> telasModel = new ArrayList<>();
			TelaTVModel telaModel = null;
			for (TelaTV tela : tv.getTelas()) {
				telaModel = new TelaTVModel();
				telaModel.setId(tela.getId());
				telaModel.setPosicao(tela.getPosicao());
				telaModel.setUrl(tela.getUrl());
				telaModel.setSegundos(tela.getSegundos());
				telasModel.add(telaModel);
			}
			tvModel.setTelas(telasModel);
		}
		return tvModel;
	}
}