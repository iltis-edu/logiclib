package de.tudortmund.cs.iltis.logiclib.modal.formula;

/*
public class MatchTest {

	public MatchTest() {
		A = new Variable("A");
	}

	@Test
	public void testNegations() {
		ModalFormula any = ModalFormula.ANY;
		ModalFormula n1 = ModalFormula.ANY.not();
		ModalFormula n2 = ModalFormula.ANY.not().not();

		ModalFormula n1A = A.not();
		ModalFormula n2A = A.not().not();

		assertTrue(any.matches(A));
		assertTrue(any.matches(n1A));
		assertTrue(any.matches(n2A));

		assertFalse(n1.matches(A));
		assertTrue(n1.matches(n1A));
		assertTrue(n1.matches(n2A));

		assertFalse(n2.matches(A));
		assertFalse(n2.matches(n1A));
		assertTrue(n2.matches(n2A));
	}

	@Test
	public void testConjunction() {
		ModalFormula andANA = A.and(A.not());

		final ModalFormula ANY = ModalFormula.ANY;

		ModalFormula any = ANY;
		ModalFormula pAW = A.and(ANY);
		ModalFormula pWNA = ANY.and(A.not());
		ModalFormula pWNW = ANY.and(ANY.not());
		ModalFormula pWW = ANY.and(ANY);
		ModalFormula pNWW = ANY.not().and(ANY);

		assertTrue(any.matches(andANA));
		assertTrue(pAW.matches(andANA));
		assertTrue(pWNA.matches(andANA));
		assertTrue(pWNW.matches(andANA));
		assertTrue(pWW.matches(andANA));
		assertFalse(pNWW.matches(andANA));
	}

	@Test
	public void testMatchNegatedConjunction() {
		ModalFormula notAndAA = A.and(A).not();
		ModalFormula patNotAnd = ModalFormula.ANY.and().not();

		assertTrue(patNotAnd.matches(notAndAA));
	}

	@Test
	public void testMatchCollector() {
		ModalFormula psi = A.not().not();
		Set<SubformulaPath> paths1 = psi.getAllMatches(ModalFormula.ANY.not());
		assertEquals(paths1.size(), 2);
		assertTrue(paths1.contains(new SubformulaPath()));
		assertTrue(paths1.contains(new SubformulaPath("-0")));

		ModalFormula phi = A.and(A.not().or(A.not().not()));
		Set<SubformulaPath> paths2 = phi.getAllMatches(ModalFormula.ANY.not());
		assertEquals(paths2.size(), 3);
		assertTrue(paths2.contains(new SubformulaPath("-1-0")));
		assertTrue(paths2.contains(new SubformulaPath("-1-1")));
		assertTrue(paths2.contains(new SubformulaPath("-1-1-0")));
	}

	private Variable A;

}
*/
