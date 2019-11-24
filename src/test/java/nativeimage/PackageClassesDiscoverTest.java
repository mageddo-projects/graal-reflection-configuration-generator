package nativeimage;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackageClassesDiscoverTest {

	private PackageClassesDiscover packageClassesDiscover = new PackageClassesDiscover();

	@Test
	void mustFindPackageVos(){

		// arrange


		// act
		final Set<Class> classes = packageClassesDiscover.discover("nativeimage.vo");


		// assert
		assertEquals(1, classes.size());
		assertEquals("[class nativeimage.vo.Pojo]", classes.toString());

	}

}
