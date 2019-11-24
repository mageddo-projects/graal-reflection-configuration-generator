import com.acme.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FruitTest {

	@Test
	void mustGenerateConfigFile(){
		// arrange

		// act
		final String reflectJson = TestUtils.getResourceAsString("/META-INF/reflect.json");

		// assert
		assertEquals(TestUtils.getResourceAsString("/fruit-test/001.json"), reflectJson);
	}
}
