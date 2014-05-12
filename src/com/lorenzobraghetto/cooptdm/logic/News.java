package com.lorenzobraghetto.cooptdm.logic;

public class News {

	private String titolo;
	private String data;
	private String ora;
	private String luogo;
	private String testo;
	private int categoria;
	private Integer id;
	private String[] tags;

	public News(Integer id, String titolo, String data, String ora, String luogo, String testo, int categoria, String tags) {
		this.id = id;
		this.titolo = titolo;
		this.data = data;
		this.ora = ora;
		this.luogo = luogo;
		this.testo = testo;
		this.categoria = categoria;
		this.tags = tags.split(",");
	}

	public int getId() {
		return id;
	}

	public String getTitolo() {
		return titolo;
	}

	public int getCategoria() {
		return categoria;
	}

	public String getData() {
		return data;
	}

	public String getTesto() {
		return testo;
	}

	public String getOra() {
		return ora;
	}

	public String getLuogo() {
		return luogo;
	}

	public String[] getTags() {
		return tags;
	}
}
