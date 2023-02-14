package principal;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

public class Arquivo {
	private boolean alterado;
	private String caminho;
	private int posicaoNaAba;
	private JTextArea texto;

	public Arquivo() {
		texto = new JTextArea();
		texto.setLineWrap(true);
		texto.setFont(new Font("Arial", Font.PLAIN, 18));
		texto.setBackground(Color.WHITE);
	}

	public Arquivo(String caminho, int posicaoNaAba) {
		texto = new JTextArea();
		texto.setLineWrap(true);
		texto.setFont(new Font("Arial", Font.PLAIN, 18));
		texto.setBackground(Color.WHITE);
		this.caminho = caminho;
		this.posicaoNaAba = posicaoNaAba;
	}

	public boolean isAlterado() {
		return alterado;
	}

	public void setAlterado(boolean alterado) {
		this.alterado = alterado;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public int getPosicaoNaAba() {
		return posicaoNaAba;
	}

	public void setPosicaoNaAba(int posicaoNaAba) {
		this.posicaoNaAba = posicaoNaAba;
	}

	public JTextArea getTexto() {
		return texto;
	}

	public void setTexto(JTextArea texto) {
		this.texto = texto;
	}

}
