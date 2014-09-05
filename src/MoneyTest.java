import static org.junit.Assert.*;

import org.junit.Test;


public class MoneyTest {

	@Test
	public void testMultiplication() {
		Money five = Money.dollar(5);
		assertEquals(Money.dollar(10), five.times(2));
		assertEquals(Money.dollar(15), five.times(3));
	}

	@Test
	public void testForEquality() {
		assertTrue(Money.dollar(5).equals(Money.dollar(5)));
		assertFalse(Money.dollar(5).equals(Money.dollar(6)));
		assertFalse(Money.franc(5).equals(Money.dollar(5)));
	}
	
	@Test
	public void testCurrency() {
		assertEquals("USD", Money.dollar(1).currency());
		assertEquals("CHF", Money.franc(1).currency());
	}
	
	@Test
	public void testSimpleAddition() {
		Money five = Money.dollar(5);
		Expression sum = five.plus(five);
		Bank bank = new Bank();
		Money reduced = bank.reduce(sum, "USD");
		assertEquals(reduced, Money.dollar(10));
	}
	
	@Test
	public void testPlusReturnsSum() {
		Money five = Money.dollar(5);
		Money six = Money.dollar(6);
		Expression result = five.plus(six);
		Sum sum = (Sum) result;
		assertEquals(five, sum.augend);
		assertEquals(six, sum.addend);
	}
	
	@Test
	public void testReduceSum() {
		Expression sum = new Sum(Money.dollar(4), Money.dollar(5));
		Bank bank = new Bank();
		Money result = bank.reduce(sum, "USD");
		assertEquals(result, Money.dollar(9));
	}
	
	@Test
	public void testReduceMoneyDifferentCurrency() {
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2);
		Money result = bank.reduce(Money.franc(2), "USD");
		assertEquals(result, Money.dollar(1));
	}
	
	@Test
	public void testIdentityRate() {
		assertEquals(1, new Bank().rate("USD", "USD"));
	}
}
