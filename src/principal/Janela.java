package principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class Janela extends JFrame {
	public static JMenu configuracoes;
	public static JMenu arquivo;
	public static JMenuBar barraDeOpcoes;
	public static JTabbedPane arquivos;
	public static int temaAtual = 0;

	public Janela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);
		setTitle("Editor de Texto");

		JMenuItem novo = new JMenuItem("Novo");
		novo.addActionListener((e) -> {
			ManipulacoesComArquivos.criarNovoArquivo();
		});

		JMenuItem salvar = new JMenuItem("Salvar");
		salvar.addActionListener((e) -> {
			ManipulacoesComArquivos.salvarArquivoAtual();
		});

		JMenuItem salvarComo = new JMenuItem("Salvar Como");
		salvarComo.addActionListener((e) -> {
			ManipulacoesComArquivos.salvarNovoArquivo();
		});

		JMenuItem abrirArquivo = new JMenuItem("Abrir");
		abrirArquivo.addActionListener((e) -> {
			ManipulacoesComArquivos.abrirArquivo();
		});

		JMenuItem imprimirArquivo = new JMenuItem("Imprimir");
		imprimirArquivo.addActionListener((e) -> {
			ManipulacoesComArquivos.imprimirArquivo();
		});

		JMenuItem fonte = new JMenuItem("Fonte");
		fonte.addActionListener((e) -> {

			GraphicsEnvironment f = GraphicsEnvironment.getLocalGraphicsEnvironment();

			Font[] fontes = f.getAllFonts();
			String[] nomesDasFontes = new String[fontes.length];

			for (int i = 0; i < fontes.length; i++) {
				nomesDasFontes[i] = fontes[i].getFontName();
			}

			JList<String> listaDeFontes = new JList<String>(nomesDasFontes);
			listaDeFontes.setMaximumSize(new Dimension(200, 30));

			listaDeFontes.setSelectedIndex(0);

			JScrollPane scrollPane = new JScrollPane(listaDeFontes);
			scrollPane.setBounds(new Rectangle(200, 60, 200, 250));

			JSpinner alterarOTamanhoDaFonte = new JSpinner(new SpinnerNumberModel(18, 8, 100, 1));
			alterarOTamanhoDaFonte.setBounds(new Rectangle(275, 10, 50, 30));

			JButton botaoParaAlterarAFonte = new JButton("Alterar Fonte");
			botaoParaAlterarAFonte.setBounds(new Rectangle(225, 330, 150, 30));
			botaoParaAlterarAFonte.setFocusPainted(false);

			botaoParaAlterarAFonte.addActionListener((g) -> {

				int posicaoAtual = arquivos.getSelectedIndex();
				for (Arquivo arquivo : ManipulacoesComArquivos.listaDeArquivosAbertos()) {
					if (arquivo.getPosicaoNaAba() == posicaoAtual) {
						arquivo.getTexto().setFont(new Font(nomesDasFontes[listaDeFontes.getSelectedIndex()],
								Font.PLAIN, (int) alterarOTamanhoDaFonte.getValue()));
					}
				}
			});

			JPanel painelAlterarFonte = new JPanel();
			painelAlterarFonte.setLayout(null);
			painelAlterarFonte.add(alterarOTamanhoDaFonte);
			painelAlterarFonte.add(scrollPane);
			painelAlterarFonte.add(botaoParaAlterarAFonte);

			JDialog alterarFonte = new JDialog(this);
			alterarFonte.setTitle("Editor de Texto");
			alterarFonte.setSize(new Dimension(600, 420));
			alterarFonte.setResizable(false);
			alterarFonte.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			alterarFonte.setLayout(new BorderLayout());

			alterarFonte.add(painelAlterarFonte);

			alterarFonte.setVisible(true);

		});

		configuracoes = new JMenu("Configurações");
		arquivo = new JMenu("Arquivo");

		arquivo.add(novo);
		arquivo.add(salvar);
		arquivo.add(salvarComo);
		arquivo.add(abrirArquivo);
		arquivo.add(imprimirArquivo);

		configuracoes.add(fonte);

		barraDeOpcoes = new JMenuBar();

		barraDeOpcoes.setBackground(Color.white);

		barraDeOpcoes.add(arquivo);
		barraDeOpcoes.add(configuracoes);

		arquivos = new JTabbedPane();

		arquivos.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		arquivos.setFocusable(false);

		add(BorderLayout.CENTER, arquivos);
		add(BorderLayout.NORTH, barraDeOpcoes);

		setVisible(true);
	}
}
