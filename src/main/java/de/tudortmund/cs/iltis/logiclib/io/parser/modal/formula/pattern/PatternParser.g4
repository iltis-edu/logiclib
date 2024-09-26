parser grammar PatternParser;

options { superClass=AbstractParser; tokenVocab=PatternTokens; }

@header {
    import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
}

initFormula 
	: formula EOF
	;

//correct input: $* and A@$ and $* and B@(#A->$) and $*


// FORMULAE
// Operator preference: STAR > name AT > NEG, BOX, DIAMOND
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
	;

subformulae
    : ( | name AT )  sub=atomarSubformula STAR   # Star
    | sub=namedSubformula STAR                   # NamedStar
    | STAR                                       # AnyStar       // short for $*
    | STAR name                                  # AnyNameStar   // short for name@$*
    ;

atomarSubformula
    : TRUE                                                            # True
	| FALSE                                                           # False 
	| ( OBRACKET formula CBRACKET    
	  | OPAREN   formula CPAREN
	  )                                                               # FormulaInParen
	| // variable
	  iSymbol                                                         # Variable
    | ANYFORMULA                                                      # AnyFormula
    ;
    
namedFormula
    : // named formula
      name AT subNI=subformulaWithName
    ;
    
namedSubformula
    : ANYFORMULA name                              # AnyFormulaWithName    // short for name@$
    | READ name                                    # ReadName
    ;
    
subformulaWithName
    : atomarSubformula
    | prefixSubformula
    ;
    
prefixSubformula
    : NEG sub=subformula              # Negation
	| BOX sub=subformula              # Box
	| DIAMOND sub=subformula          # Diamond
	| COMPLEMENT sub=subformula       # Complement
	| CONTAINS sub=subformula         # ContainsDescendant
    ;	

// ISymbols

iSymbol 
    : SQUOTE name SQUOTE   # FixedName
    | name                 # AnyName
    ;
    
name
	: sym=ISYMBOL DISTINCT*
	;