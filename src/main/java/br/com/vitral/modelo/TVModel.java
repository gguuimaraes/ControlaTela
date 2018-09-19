package br.com.vitral.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TVModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private List<TelaTVModel> telas = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<TelaTVModel> getTelas() {
		return telas;
	}

	public void setTelas(List<TelaTVModel> telas) {
		this.telas = telas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TVModel other = (TVModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TVModel [id=" + id + ", nome=" + nome + ", telas=" + telas + "]";
	}

}