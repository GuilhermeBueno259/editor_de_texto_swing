package principal;

public class Principal {

	public static Janela janela;

	public static void main(String[] args) {
		for (String string : args) {
			System.out.println(string);
		}
		janela = new Janela();
	}
}
