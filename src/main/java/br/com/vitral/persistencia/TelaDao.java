package br.com.vitral.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitral.entidade.TelaTV;
import br.com.vitral.modelo.TelaTVModel;
import br.com.vitral.util.Uteis;

public class TelaDao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	TelaTV tela;
	transient EntityManager em;

	public void salvar(TelaTVModel telaModel) {
		em = Uteis.getEntityManager();
		if (telaModel.getId() == null) {
			tela = new TelaTV();
			tela.setUrl(telaModel.getUrl());
			tela.setSegundos(telaModel.getSegundos());
			tela.setPosicao(telaModel.getPosicao());
			em.persist(tela);
		} else {
			tela = em.find(TelaTV.class, telaModel.getId());
			tela.setUrl(telaModel.getUrl());
			tela.setSegundos(telaModel.getSegundos());
			tela.setPosicao(telaModel.getPosicao());
			em.merge(tela);
		}
	}

	public List<TelaTVModel> listar() {
		List<TelaTVModel> telasModel = new ArrayList<>();
		em = Uteis.getEntityManager();
		Query query = em.createNamedQuery("TelaTV.findAll");
		@SuppressWarnings("unchecked")
		Collection<TelaTV> telas = (Collection<TelaTV>) query.getResultList();
		TelaTVModel telaModel = null;
		for (TelaTV t : telas) {
			telaModel = new TelaTVModel();
			telaModel.setId(t.getId());
			telaModel.setUrl(t.getUrl());
			telaModel.setSegundos(t.getSegundos());
			telaModel.setPosicao(t.getPosicao());
			telasModel.add(telaModel);
		}
		return telasModel;
	}

	public void remover(int id) {
		em = Uteis.getEntityManager();
		em.remove(em.find(TelaTV.class, id));
		List<TelaTV> l = em.createNamedQuery("TelaTV.findAll").getResultList();
		if (l.size() == 1) {
			l.get(0).setPosicao(1);
			em.merge(l.get(0));
		}
	}

	public void subir(int posicao) {
		trocarPosicao(consultarPorPosicao(posicao), consultarPorPosicao(posicao - 1));
	}

	public void descer(int posicao) {
		trocarPosicao(consultarPorPosicao(posicao), consultarPorPosicao(posicao + 1));
	}

	private void trocarPosicao(TelaTV tela, TelaTV outra) {
		int aux = outra.getPosicao();
		outra.setPosicao(tela.getPosicao());
		tela.setPosicao(aux);
		em = Uteis.getEntityManager();
		em.merge(tela);
		em.merge(outra);
	}

	private TelaTV consultarPorPosicao(int posicao) {
		return (TelaTV) Uteis.getEntityManager().createNamedQuery("TelaTV.findPorPosicao").setParameter("posicao", posicao)
				.getSingleResult();
	}

}