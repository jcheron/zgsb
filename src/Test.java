import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.ko.comparators.HierarchicalComparator;
import net.ko.utils.KHierarchicalSort;

public class Test {

	public static void main(String[] args) {
		List<Class<?>> classes = new ArrayList<>();
		classes.addAll(Arrays.asList(ArrayList.class, Test.class, Integer.class, BigDecimal.class, Number.class));
		classes.addAll(Arrays.asList(AbstractList.class, Float.class, Object.class, String.class, Number.class));

		KHierarchicalSort.sort(classes, new HierarchicalComparator<Class<?>>() {
			@Override
			public boolean isParent(Class<?> cls1, Class<?> cls2) {
				boolean result = cls1.isAssignableFrom(cls2);
				System.out.println(cls1 + " " + cls2 + " : " + result);
				return result;
			}
		});
		System.out.println(classes);
	}

}
