parser grammar ModalFormulaSetParser;

import ModalFormulaParser;

options { superClass=AbstractParser; tokenVocab=ModalFormulaTokens; }

@header {
    import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
}

// formula set set

initFormulaSetSet
	: setSet=formulaSetSet EOF
	;

formulaSetSet
	: OBRACE content=formulaSetSetContent CBRACE                # FormulaSetSetBraces
	// missing braces
	// have to placed beneath the first case to deal with ambiguities for input {} and { A }
    | contentNoBraces=formulaSetSetContent                      # FormulaSetSetMissingBraces
	;

formulaSetSetContent
	: /* emtpy rule */                                          # FormulaSetSetContentEmpty
	| formulaSetsOrFormulae                                     # FormulaSetSetContentSetsOrFormulae
	| first=formulaSetsOrFormulaeOrEmpty ( seps+=ARGUMENTSEP further+=formulaSetsOrFormulaeOrEmpty )+
	                                                            # FormulaSetSetContentMultiple
	;

formulaSetsOrFormulaeOrEmpty
	// can be correct: has to be placed on top due to ambiguities
	: formulaSetsOrFormulae                 # FormulaSetsOrFormulaeOrEmptyPresent
	| /* emtpy rule */                      # FormulaSetsOrFormulaeOrEmptyNotPresent
	;

formulaSetsOrFormulae
	// can be correct: has to be placed on top due to ambiguities
	: formulaSetOrFormula                                       # FormulaSetsOrFormulaeSingle
	| firstSet=formulaSetOrFormula furtherSets+=formulaSetOrFormula+
	                                                            # FormulaSetsOrFormulaeMultiple
	;

formulaSetOrFormula
	// correct: has to be placed on top due to ambiguities
	: formulaSetWithBraces                  # FormulaSetOrFormulaBraces
	| formula                               # FormulaSetOrFormulaWithoutBraces
	;


// formula set

initFormulaSet
	: formulaSet EOF
	;

formulaSet
	: formulaSetWithBraces                  # FormulaSetWithBracesLINK
	| // missing braces
	  formulaSetContent                     # FormulaSetWithoutBraces
	;

formulaSetWithBraces
	: OBRACE formulaSetContent CBRACE
	;

formulaSetContent
	: /* empty rule */                                          # FormulaSetContentEmpty
	| formulae                                                  # FormulaSetContentSingle
	| first=formulaeOrEmpty ( seps+=ARGUMENTSEP further+=formulaeOrEmpty )+
	                                                            # FormulaSetContentMultiple
	;

formulaeOrEmpty
	// can be correct: has to be placed on top due to ambiguities
	: formulae                              # FormulaeOrEmptyPresent
	| /* emtpy rule */                      # FormulaeOrEmptyNotPresent
	;

formulae
	// correct: has to be placed on top due to ambiguities
	: formula                               # FormulaeSingle
	| first=formula further+=formula+       # FormulaeMultiple
	;