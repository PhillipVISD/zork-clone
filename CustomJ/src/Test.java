import Dynamic.DynamicPL;

public class Test {
	public static void main(String[] args) {
		System.out.println("Hello");
	}

	public static DynamicPL hello(DynamicPL pl) {
		String word = pl.verb;
		pl.setOut("Hello " + word, pl.player);
		return pl;
	}
}