parser grammar ModalFormulaParser;

// IMPORTANT: There is no generated parser class for this grammar. Use ModalFormulaSetParser instead because it imports
// this grammar internally!
// The corresponding construction visitor to this class can still be used.

options { tokenVocab=ModalFormulaTokens; }

initModalFormula : formula EOF;

formula
	: superFormula           # SuperFormulaLINK
	| subFormula             # SubFormulaLINK
	;

superFormula
    : first=subFormula (AND further+=subFormula)+       # SuperFormulaMultiple
    | superFormulaWithoutAnd                            # SuperFormulaSingle
    ;

superFormulaWithoutOr
    : first=subFormula (AND further+=subFormula)+       # SuperFormulaWithoutOrMultiple
    | superFormulaWithoutAndOr                          # SuperFormulaWithoutOrSingle
    ;

superFormulaWithoutAnd
    : first=subFormula (OR further+=subFormula)+        # SuperFormulaWithoutAndMultiple
    | superFormulaWithoutAndOr                          # SuperFormulaWithoutAndSingle
    ;

superFormulaWithoutAndOr
    : first=subFormula IMPLIES second=subFormula        # SuperFormulaWithoutAndOrImplies
    | first=subFormula EQUIV second=subFormula          # SuperFormulaWithoutAndOrEquiv
    | superFormulaError                                 # SuperFormulaWithoutAndOrError
    ;

superFormulaError
    : firstSub=subFormula AND secondSuperWA=superFormulaWithoutAnd  # SuperFormulaErrorAND
    | firstSub=subFormula OR secondSuperWO=superFormulaWithoutOr    # SuperFormulaErrorOR
    | firstSub=subFormula IMPLIES secondSuper=superFormula          # SuperFormulaErrorIMPLIES
    | firstSub=subFormula EQUIV secondSuper=superFormula            # SuperFormulaErrorEQUIV
    ;

subFormula
	: TRUE                              # TRUE
	| FALSE                             # FALSE
	| var=VARIABLE                      # VARIABLE
	| NEG subFormula                    # NEGATION
	| BOX subFormula                    # BOX
	| DIAMOND subFormula                # DIAMOND
	// new expressions
	| OBRACKET formula CBRACKET         # BRACKETS
    | OPAREN formula CPAREN             # PARENS
	;
