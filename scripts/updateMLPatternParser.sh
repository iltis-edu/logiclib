#!/bin/bash
# configuration: set paths relative to this file
DIR=../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/modal/formula/pattern
GRAMMARNAME=PatternParser.g4
PCKG=de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern
# cd to the folder of this file
cd "${0%/*}" || exit
# execute the parser creation
java -cp "$ANTLRTOOLJAR:%CLASSPATH%" org.antlr.v4.Tool -no-listener -visitor -package $PCKG -lib "$DIR" "$DIR/$GRAMMARNAME"
