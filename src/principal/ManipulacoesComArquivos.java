package principal;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class ManipulacoesComArquivos {

	private static ArrayList<Arquivo> arquivosAbertos = new ArrayList<>();

	public static void criarNovoArquivo() {
		JDialog configurarArquivoNovo = new JDialog(Principal.janela);

		JLabel label = new JLabel("Nome do arquivo");
		label.setBounds(new Rectangle(20, 25, 170, 30));

		JTextField nomeDoArquivo = new JTextField();
		nomeDoArquivo.setBounds(new Rectangle(120, 30, 150, 20));

		JButton criarArquivo = new JButton("Criar Arquivo");
		criarArquivo.setBounds(new Rectangle(75, 70, 150, 30));
		criarArquivo.addActionListener((e) -> {
			Arquivo arquivo = new Arquivo();
			arquivo.setPosicaoNaAba(Janela.arquivos.getTabCount());
			arquivosAbertos.add(arquivo);
			JScrollPane pane = new JScrollPane(arquivo.getTexto());
			Janela.arquivos.addTab(nomeDoArquivo.getText(), pane);
			Janela.arquivos.setTabComponentAt(Janela.arquivos.getTabCount() - 1, new ComponenteDaAba(Janela.arquivos));
			configurarArquivoNovo.setVisible(false);
		});

		configurarArquivoNovo.add(label);
		configurarArquivoNovo.add(nomeDoArquivo);
		configurarArquivoNovo.add(criarArquivo);

		configurarArquivoNovo.setLayout(null);
		configurarArquivoNovo.setMinimumSize(new Dimension(300, 200));
		configurarArquivoNovo.setVisible(true);
	}

	public static void salvarArquivoAtual() {
		JScrollPane pane = (JScrollPane) Janela.arquivos.getComponentAt(Janela.arquivos.getSelectedIndex());
		JTextArea textArea = (JTextArea) pane.getComponent(0).getComponentAt(0, 0);
		String[] texto = textArea.getText().split("\n");
		String caminho = "";

		for (Arquivo arquivo : arquivosAbertos) {
			if (arquivo.getPosicaoNaAba() == Janela.arquivos.getSelectedIndex()) {
				caminho = arquivo.getCaminho();
			}
		}

		try {
			FileWriter writer = new FileWriter(caminho);
			for (String string : texto) {
				writer.write(string);
				writer.write("\r\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.printf("Erro: %s", e.getMessage());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Utilize a opção \"Salvar Como\" para definir o caminho deste arquivo",
					"Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}

	public static void salvarNovoArquivo() {
		JScrollPane pane = (JScrollPane) Janela.arquivos.getSelectedComponent();
		JTextArea conteudo = (JTextArea) pane.getComponent(0).getComponentAt(0, 0);

		JFileChooser salvarArquivo = new JFileChooser();
		salvarArquivo.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "*.txt";
			}

			@Override
			public boolean accept(File f) {
				String filename = f.getName();
				return filename.endsWith(".txt");
			}
		});
		int resultado = salvarArquivo.showSaveDialog(Principal.janela);

		if (resultado == JFileChooser.APPROVE_OPTION) {
			String[] texto = conteudo.getText().split("\n");
			String caminho = salvarArquivo.getCurrentDirectory().toString() + "\\"
					+ salvarArquivo.getName(salvarArquivo.getSelectedFile());
			try {
				FileWriter escritor = new FileWriter(caminho);
				for (String string : texto) {
					escritor.write(string);
					escritor.write("\r\n");
				}
				escritor.close();
			} catch (IOException e) {
				System.out.printf("\nErro: %s", e.getMessage());
			}
			Arquivo arquivo = new Arquivo();
			arquivo.setCaminho(caminho);
			arquivo.setPosicaoNaAba(Janela.arquivos.getSelectedIndex());
			Janela.arquivos.setTitleAt(Janela.arquivos.getSelectedIndex(),
					salvarArquivo.getName(salvarArquivo.getSelectedFile()));
			arquivosAbertos.add(arquivo);
		}
	}

	public static void abrirArquivo() {
		String texto = "";
		JFileChooser escolherArquivo = new JFileChooser();
		escolherArquivo.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "*.txt";
			}

			@Override
			public boolean accept(File f) {
				String filename = f.getName();
				return filename.endsWith(".txt");
			}
		});
		escolherArquivo.showOpenDialog(Principal.janela);
		JTextArea jTextArea = new JTextArea();
		jTextArea.setLineWrap(true);

		try {
			FileReader reader = new FileReader(escolherArquivo.getSelectedFile().toString());
			BufferedReader reader2 = new BufferedReader(reader);
			String linha;
			while ((linha = reader2.readLine()) != null) {
				texto = String.format("%s\n%s", texto, linha);
			}

			reader.close();
			reader2.close();
		} catch (FileNotFoundException e) {
			System.out.printf("\nErro: %s", e.getMessage());
		} catch (IOException e) {
			System.out.printf("\nErro: %s", e.getMessage());
		}

		jTextArea.setText(texto);

		Arquivo arquivo = new Arquivo(escolherArquivo.getSelectedFile().toString(), Janela.arquivos.getTabCount());
		arquivo.setTexto(jTextArea);

		JScrollPane pane = new JScrollPane(arquivo.getTexto());
		Janela.arquivos.addTab(escolherArquivo.getName(escolherArquivo.getSelectedFile()), pane);
		Janela.arquivos.setSelectedIndex(Janela.arquivos.getTabCount() - 1);
		Janela.arquivos.setTabComponentAt(Janela.arquivos.getTabCount() - 1, new ComponenteDaAba(Janela.arquivos));

		arquivosAbertos.add(arquivo);
	}

	public static List<Arquivo> listaDeArquivosAbertos() {
		return Collections.unmodifiableList(arquivosAbertos);
	}

	public static void imprimirArquivo() {
		try {
			Desktop desktop = Desktop.getDesktop();
			Arquivo arquivo = arquivosAbertos.get(Janela.arquivos.getSelectedIndex());
			String caminho = arquivo.getCaminho();
			File file = new File(caminho);
			desktop.print(file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Principal.janela, "O arquivo não pode ser impresso!", "Aviso",
					JOptionPane.WARNING_MESSAGE);
		} catch (NullPointerException e) {
			System.out.printf("Erro: %s", e.getMessage());
		}
	}
}
