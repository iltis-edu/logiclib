parser grammar FormulaParser;

// IMPORTANT: There is no generated parser class for this grammar. Use FormulaSetParser instead because it imports
// this grammar internally!
// The corresponding construction visitor to this class can still be used.

options {tokenVocab=FormulaTokens;}

@header {
	import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.PredicateReaderProperties;
}

@members {
	private PredicateReaderProperties properties = PredicateReaderProperties.createDefault();

	public void setProperties(PredicateReaderProperties props) {
    	if (props == null)
    		throw new IllegalArgumentException("props must not be null");
    	properties = props;
    }

    public PredicateReaderProperties getProperties() {
    	return properties;
    }
}

initFormula
	: formula EOF
	;

initTerm
	: term EOF
	;

formula
	: superformula                              # FormulaSuper
	| subformula                                # FormulaSub
	;

superformula
	: first=subformula (AND further+=subformula)+   # SuperformulaMultipe
	| superformulaWithoutAnd                        # SuperformulaSingle
	;

superformulaWithoutOr
	: first=subformula (AND further+=subformula)+   # SuperformulaWithoutOrMultiple
	| superformulaWithoutAndOr                      # SuperformulaWithoutOrSingle
	;

superformulaWithoutAnd
	: first=subformula (OR further+=subformula)+    # SuperformulaWithoutAndMultiple
	| superformulaWithoutAndOr                      # SuperformulaWithoutAndSingle
	;

superformulaWithoutAndOr
	: first=subformula IMPLIES second=subformula    # SuperformulaWithoutAndOrImplies
	| first=subformula EQUIV second=subformula      # SuperformulaWithoutAndOrEquiv
	| superformulaError                             # SuperformulaWithoutAndOrERROR
	;

superformulaError
	: firstsub=subformula AND secondsuperWA=superformulaWithoutAnd      # SuperformulaErrorAND
	| firstsub=subformula OR secondsuperWO=superformulaWithoutOr        # SuperformulaErrorOR
	| firstsub=subformula IMPLIES secondsuper=superformula              # SuperformulaErrorIMPLIES
	| firstsub=subformula EQUIV secondsuper=superformula                # SuperformulaErrorEQUIV
	;

subformula
	: TRUE                                                                  # SubformulaTrue
	| FALSE                                                                 # SubformulaFalse
	| NEG sub=subformula                                                    # SubformulaNeg
	| FORALL var=iSymbol ( ARGUMENTSEP symbols+=iSymbol )* sub=subformula   # SubformulaForAll
	| EXISTS var=iSymbol ( ARGUMENTSEP symbols+=iSymbol )* sub=subformula   # SubformulaExists
	| OBRACKET formula CBRACKET                                             # SubformulaBracket
	| OPAREN formula CPAREN                                                 # SubformulaParen

	// infix relation symbol
	| firstPrefix=prefixTerm sym=RELINFIXISYMBOL secondPrefix=prefixTerm    # SubformulaInfix
    | {! properties.getInfixProperties().areInfixRelationsRestricted()}?
      firstPrefix=prefixTerm ( sym=ISYMBOL | sym=FUNINFIXISYMBOL ) secondPrefix=prefixTerm
                                                                            # SubformulaInfixUnrestricted
	// prefix relation symbol
	| iSymbol OBRACKET ( first=term ( ARGUMENTSEP further+=term )* )? CBRACKET
	                                                                        # SubformulaPrefixBracket
	| iSymbol OPAREN ( first=term ( ARGUMENTSEP further+=term )* )? CPAREN  # SubformulaPrefixParen
    | iSymbol // IMPORTANT: Do not move this rule higher, this somehow leads to failing tests
                                                                            # SubformulaPrefix
	;

term
	// infix symbol
	: first=prefixTerm sym=FUNINFIXISYMBOL second=prefixTerm            # TermInfix
	| {! properties.getInfixProperties().areInfixFunctionsRestricted()}?
	  first=prefixTerm ( sym=ISYMBOL | sym=RELINFIXISYMBOL ) second=prefixTerm
	                                                                    # TermInfixUnrestricted
	// prefix symbol can be used wherever an infix symbol is allowed
	| prefixTerm                                                        # TermPrefix
	// error rule; is not placed at prefixTerm to avoid ambiguity
	| NEG child=term                                                    # TermNEGError
	;

prefixTerm
	// prefix symbol
	: iSymbol OBRACKET ( first=term ( ARGUMENTSEP further+=term )* )? CBRACKET
	                                                                    # PrefixTermBracketMultiple
	| iSymbol OPAREN ( first=term ( ARGUMENTSEP further+=term )* )? CPAREN
	                                                                    # PrefixTermParenMultiple
	| iSymbol                                                           # PrefixTermISymbol

	// if infix symbol shall be used where only prefix symbol is allowed, brackets have to be added
	| OBRACKET term CBRACKET                                            # PrefixTermBrackets
	| OPAREN term CPAREN                                                # PrefixTermParen
	;
	
iSymbol
	: sym=ISYMBOL
	| sym=RELINFIXISYMBOL
	| sym=FUNINFIXISYMBOL
	;
	