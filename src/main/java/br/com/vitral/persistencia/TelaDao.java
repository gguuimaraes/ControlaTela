package br.com.vitral.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitral.entidade.Tela;
import br.com.vitral.modelo.TelaModel;
import br.com.vitral.util.Uteis;

public class TelaDao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	Tela tela;
	EntityManager entityManager;

	public void salvar(TelaModel telaModel) {
		entityManager = Uteis.JpaEntityManager();
		if (telaModel.getId() == null) {
			tela = new Tela();
			tela.setUrl(telaModel.getUrl());
			tela.setSegundos(telaModel.getSegundos());
			tela.setPosicao(telaModel.getPosicao());
			entityManager.persist(tela);
		} else {
			tela = entityManager.find(Tela.class, telaModel.getId());
			tela.setUrl(telaModel.getUrl());
			tela.setSegundos(telaModel.getSegundos());
			tela.setPosicao(telaModel.getPosicao());
			entityManager.merge(tela);
		}
	}

	public List<TelaModel> listar() {
		List<TelaModel> telasModel = new ArrayList<TelaModel>();
		entityManager = Uteis.JpaEntityManager();
		Query query = entityManager.createNamedQuery("Tela.findAll");
		@SuppressWarnings("unchecked")
		Collection<Tela> telas = (Collection<Tela>) query.getResultList();
		TelaModel telaModel = null;
		for (Tela t : telas) {
			telaModel = new TelaModel();
			telaModel.setId(t.getId());
			telaModel.setUrl(t.getUrl());
			telaModel.setSegundos(t.getSegundos());
			telaModel.setPosicao(t.getPosicao());
			telasModel.add(telaModel);
		}
		return telasModel;
	}

	public void remover(int id) {
		entityManager = Uteis.JpaEntityManager();
		entityManager.remove(entityManager.find(Tela.class, id));
	}

	public void subir(int posicao) {
		trocarPosicao(consultarPorPosicao(posicao), consultarPorPosicao(posicao - 1));
	}

	public void descer(int posicao) {
		trocarPosicao(consultarPorPosicao(posicao), consultarPorPosicao(posicao + 1));
	}
	
	private void trocarPosicao(Tela tela, Tela outra) {
		int aux = outra.getPosicao();
		outra.setPosicao(tela.getPosicao());
		tela.setPosicao(aux);
		entityManager = Uteis.JpaEntityManager();
		entityManager.merge(tela);
		entityManager.merge(outra);
	}

	private Tela consultarPorPosicao(int posicao) {
		return (Tela) Uteis.JpaEntityManager().createNamedQuery("Tela.findPorPosicao").setParameter("posicao", posicao)
				.getSingleResult();
	}

}