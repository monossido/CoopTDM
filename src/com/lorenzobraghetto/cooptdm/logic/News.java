package com.lorenzobraghetto.cooptdm.logic;

public class News {

	private String titolo;
	private String data;
	private String ora;
	private String luogo;
	private String testo;
	private int categoria;

	public News(String titolo, String data, String ora, String luogo, String testo, int categoria) {
		this.titolo = titolo;
		this.data = data;
		this.ora = ora;
		this.luogo = luogo;
		this.testo = testo;
		this.categoria = categoria;
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
}
