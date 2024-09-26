parser grammar PatternParser;

options { superClass=AbstractParser; tokenVocab=PatternTokens; }

@header {
	import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
	import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.pattern.PatternReaderProperties;
}

@members {
	private PatternReaderProperties properties = PatternReaderProperties.createDefault();

	public void setProperties(PatternReaderProperties props) {
    	if (props == null)
    		throw new IllegalArgumentException("props must not be null");
    	properties = props;
    }

    public PatternReaderProperties getProperties() {
    	return properties;
    }
}

initFormula 
	: formula EOF
	;

initTerm 
	: term EOF
	;

//correct input: $* and A@$ and $* and B@(#A->exists x 'R'(x)) and $*


// FORMULAE
// Operator preference: STAR > name AT > infix relation > NEG, quantifiers
// not guaranteed, that every AND and OR has more than one formula
// READ name can be a forest or a tree

formula 
	: superformula   
	| subformula     
	;

superformula 
	: // Alternative
	  first=subformula (PLUS further+=subformula)+                                            # Alternative
    | // MultiConstraint
      first=subformula (MULTI further+=subformula)+                                           # MultiConstraint
    | // AND
      firsts=subformulaOrS (AND ( furthers+=subformulaOrS | NOFORMULA ) )+                    # AndLeadingSubformula
    | ( NOFORMULA AND )+ firsts=subformulaOrS (AND ( furthers+=subformulaOrS | NOFORMULA ) )* # AndLeadingNoFormula
    | // OR
      firsts=subformulaOrS (OR ( furthers+=subformulaOrS | NOFORMULA ) )+                     # OrLeadingSubformula
    | ( NOFORMULA OR )+ firsts=subformulaOrS (OR ( furthers+=subformulaOrS | NOFORMULA ) )*   # OrLeadingNoFormula
    | // IMPLIES
      first=subformula IMPLIES second=subformula                                              # Implies	
    | // EQUIV
      first=subformula EQUIV second=subformula                                                # Equiv
	;
	
subformulaOrS
    : subformula   
    | subformulae  
    ;

subformula 
	: namedFormula      
	| namedSubformula   
	| atomarSubformula  
    | prefixSubformula  
    | infixSubformula   
	;
	
notInfixSubformula
    : namedFormula
    | namedSubformula
    | atomarSubformula  
    | prefixSubformula  
    ;

subformulae
    : ( | name AT )  sub=atomarSubformula STAR   # SubformulaStar
    | sub=namedSubformula STAR                   # NamedSubformulaStar
    | STAR                                       # AnyStarSubs       // short for $*
    | STAR name                                  # AnyNameStarSubs   // short for name@$*
    ;

atomarSubformula
    : TRUE                                                            # True
	| FALSE                                                           # False 
	| OBRACKET formula CBRACKET                                       # FormulaInBracket
	| OPAREN   formula CPAREN                                         # FormulaInParen
	// prefix relation symbol
	| iSymbol                                                         # PrefixRelationSymbol
	| iSymbol OBRACKET ( firsts=termOrS ( ARGUMENTSEP furthers+=termOrS )* )? CBRACKET
	  			                                                      # PrefixRelationSymbolBrackets
    | iSymbol OPAREN ( firsts=termOrS ( ARGUMENTSEP furthers+=termOrS )* )? CPAREN
	          	                                                      # PrefixRelationSymbolParentheses
    | ANYFORMULA                                                      # AnyFormulaAtomarSub
    ;
    
namedFormula
    // named formula
    : name AT subNI=subformulaWithName
    ;
    
namedSubformula
    : ANYFORMULA name                  # AnyFormulaWithNameAtomarSub    // short for name@$
    | READ name                        # ReadNameAtomarSub
    ;
    
subformulaWithName
    : atomarSubformula
    | prefixSubformula
    ;
    
prefixSubformula
    : NEG sub=subformula              # Negation
	| FORALL iSymbol sub=subformula   # UniversalQuantifier
	| EXISTS iSymbol sub=subformula   # ExistentialQuantifier
	| COMPLEMENT sub=subformula       # Complement
	| CONTAINS sub=subformula         # ContainsDescendant
    ;
    
infixSubformula
    // infix relation symbol
    : firstPrefix=notInfixTerm sym=RELINFIXISYMBOL secondPrefix=notInfixTerm	  	             # WithoutInfixRelation
	  // this alternative is used for better error messages,
	  // but a RelationAtomPattern should be constructed.
	| {! properties.getInfixProperties().areInfixRelationsRestricted()}?
	  firstPrefix=notInfixTerm ( sym=ISYMBOL | sym=FUNINFIXISYMBOL ) secondPrefix=notInfixTerm   # InfixRelationSymbol
    ;
    


// TERMS

termOrS
    : term   
    | terms  
    ;

term 
	: namedTerm
	| atomarTerm   
	| prefixTerm   
	| infixTerm    
	;
	
notInfixTerm 
	: namedTerm
	| atomarTerm   
	| prefixTerm   
	;

terms
	: ( | name AT ) sub=atomarTerm STAR   # SubTermStar
	| sub=namedTerm STAR                  # NamedSubTermStar
    | STAR                                # AnyStarTerms      // short for $*
    | STAR name                           # AnyNameStarTerms  // short for name@$*
	;

atomarTerm
    // prefix function symbol
	: iSymbol                                                                           # PrefixFunctionSymbol
	| iSymbol OBRACKET ( first=termOrS ( ARGUMENTSEP further+=termOrS )* )? CBRACKET    # PrefixFunctionSymbolBrackets
    | iSymbol OPAREN ( first=termOrS ( ARGUMENTSEP further+=termOrS )* )? CPAREN        # PrefixFunctionSymbolParentheses
	// if infix symbol shall be used where only prefix symbol is allowed,
	// brackets have to be added
	| OBRACKET term CBRACKET                                                            # TermInBrackets
	| OPAREN term CPAREN                                                                # TermInParen
	| ANYFORMULA                                                                        # AnyFormulaAtomarTerm 
	;

prefixTerm
	: // named formula
      name AT subNI=atomarTerm
	;
	
namedTerm
    : ANYFORMULA name       # AnyFormulaWithNameAtomarTerm    // short for name@$
    | READ name             # ReadNameAtomarTerm
    ;

infixTerm
    // infix function symbol
	: first=notInfixTerm sym=FUNINFIXISYMBOL second=notInfixTerm	                    # WithoutInfixFunction
	// this alternative is used for better error messages,
	// but a FunctionTermPattern should be constructed.
	| {! properties.getInfixProperties().areInfixFunctionsRestricted()}?
	  first=notInfixTerm ( sym=ISYMBOL | sym=RELINFIXISYMBOL ) second=notInfixTerm      # InfixFunctionSymbol
	;



// ISymbols

iSymbol 
    : SQUOTE name SQUOTE   # FixedName
    | name                 # AnyName
    ;
    
name 
    : (sym=ISYMBOL             
      | sym=RELINFIXISYMBOL      
      | sym=FUNINFIXISYMBOL 
      ) DISTINCT*      
    ;