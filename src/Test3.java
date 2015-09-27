import java.util.Arrays;
import java.util.List;

public class Test3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<? extends Class<? extends ClassMere>> L = Arrays.asList(ClassFille.class, ClassFille.class);
		Class<? extends ClassMere> m = ClassFille.class;
	}

	public static class ClassMere {
	}

	public static class ClassFille extends ClassMere {
	}

}
