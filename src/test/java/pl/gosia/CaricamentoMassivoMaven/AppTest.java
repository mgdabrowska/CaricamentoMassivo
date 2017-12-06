package pl.gosia.CaricamentoMassivoMaven;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.Test;

import junit.framework.Assert;


/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void testSimpleArrayStream_shouldReturnSum() {
		int[] testArraySum = new int[] { 2, 3, 4 };
		int sum = Arrays.stream(testArraySum).sum();
		Assert.assertEquals(9, sum);
	}

	@Test
	public void testStringArrayStream_shouldConvertToIntegerAndSquareList() {
		String[] testStringArray = new String[] { "1", "2", "3", "4" };
//		Function<Integer, String> convert = t -> t.toString();
//		Predicate<Integer> greaterThan3 = i -> i > 3;
//		Supplier<Integer> return4 = () -> 4;
//		Consumer<String> printString = str -> System.out.println(str);
		

		List<Integer> intList = Arrays.stream(testStringArray)
				.map(str -> Integer.parseInt(str))
				.map(i -> i * i)
				.collect(Collectors.toList());
		org.junit.Assert.assertArrayEquals(new Integer[]{ 1, 4, 9, 16 }, intList.toArray(new Integer[intList.size()]));
	}

	@Test
	public void testIntegerArrayStream_shouldReturnSquareList(){
		int[] testArraySum = new int[] { 2, 3, 4 ,5};

		List<Integer> intList = Arrays.stream(testArraySum).filter(x -> x > 3).map(i -> i*i).boxed().collect(Collectors.toList());

		intList.forEach(i->System.out.println(i));

		org.junit.Assert.assertArrayEquals(new Integer[]{ 16, 25 }, intList.toArray(new Integer[intList.size()]));
	}
}
