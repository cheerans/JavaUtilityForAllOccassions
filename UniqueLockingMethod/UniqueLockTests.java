package test.java.com.stock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.Assert;
import main.java.com.stocks.app.UniqueLock;

@RunWith(SpringRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UniqueLockTests {
	
	String TEST_SERVICE_NAME_1 = "TEST_SERVICE_NAME_1";
	String TEST_SERVICE_NAME_2 = "TEST_SERVICE_NAME_2";
	
	@Test
	public void testUniqueLock(){
				
		UniqueLock.releaseLock(TEST_SERVICE_NAME_1);
		UniqueLock.releaseLock(TEST_SERVICE_NAME_2);
		
		if(true == UniqueLock.getLock(TEST_SERVICE_NAME_1)){
			Assert.assertTrue(false == UniqueLock.getLock(TEST_SERVICE_NAME_1));
			Assert.assertTrue(true == UniqueLock.releaseLock(TEST_SERVICE_NAME_1));
			Assert.assertTrue(false == UniqueLock.releaseLock(TEST_SERVICE_NAME_1));
			Assert.assertTrue(true == UniqueLock.getLock(TEST_SERVICE_NAME_1));
		}
	}
	
	@Test
	public void testMultipleUniqueLocks(){		
		
		UniqueLock.releaseLock(TEST_SERVICE_NAME_1);
		UniqueLock.releaseLock(TEST_SERVICE_NAME_2);

		if(true == UniqueLock.getLock(TEST_SERVICE_NAME_1)){
			Assert.assertTrue(false == UniqueLock.getLock(TEST_SERVICE_NAME_1));
			Assert.assertTrue(true == UniqueLock.getLock(TEST_SERVICE_NAME_2));
			Assert.assertTrue(true == UniqueLock.releaseLock(TEST_SERVICE_NAME_1));
			Assert.assertTrue(false == UniqueLock.releaseLock(TEST_SERVICE_NAME_1));
			Assert.assertTrue(true == UniqueLock.releaseLock(TEST_SERVICE_NAME_2));
			Assert.assertTrue(true == UniqueLock.getLock(TEST_SERVICE_NAME_1));
		}
	}	
}
