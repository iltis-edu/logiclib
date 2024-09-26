package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralPatternFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.PredicateFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AlternativeContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AndLeadingNoFormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AndLeadingSubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AnyFormulaAtomarSubContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AnyFormulaAtomarTermContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AnyFormulaWithNameAtomarSubContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AnyFormulaWithNameAtomarTermContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AnyNameContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AnyNameStarSubsContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AnyNameStarTermsContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AnyStarSubsContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AnyStarTermsContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AtomarSubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.AtomarTermContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.ComplementContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.ContainsDescendantContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.EquivContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.ExistentialQuantifierContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.FalseContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.FixedNameContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.FormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.FormulaInParenContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.ImpliesContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.MultiConstraintContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.NameContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.NamedFormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.NamedSubTermStarContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.NamedSubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.NamedSubformulaStarContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.NamedTermContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.NegationContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.NotInfixSubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.NotInfixTermContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.OrLeadingNoFormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.OrLeadingSubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.PrefixFunctionSymbolContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.PrefixTermContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.ReadNameAtomarSubContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.ReadNameAtomarTermContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.SubTermStarContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.SubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.SubformulaOrSContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.SubformulaStarContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.SubformulaWithNameContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.TermContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.TermInParenContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.TermOrSContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.TrueContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.UniversalQuantifierContext;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.pattern.PatternReaderProperties;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFault;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.general.SymbolToken;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFault;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 * Used to create predicate logical pattern out of the parse tree that was constructed by {@link
 * PatternParser}.
 */
public class PatternParserConstructionVisitor
        extends PatternParserBaseVisitor<TreePattern<TermOrFormula>> {

    /**
     * {@code names} is used to annotate nodes of the parse tree with a name if it was specified for
     * them.
     */
    private final ParseTreeProperty<IndexedSymbol> names;

    /**
     * {@code inQuantifiedContextMap} is used to mark nodes of the parse tree if they occur in a
     * quantified context.
     */
    private final ParseTreeProperty<Boolean> inQuantifiedContextMap;

    private final ParsingCreator creator;

    private final PatternReaderProperties properties;
    private final VisitorErrorHandler errorHandler;

    public PatternParserConstructionVisitor(
            ParsingCreator creator,
            PatternReaderProperties properties,
            VisitorErrorHandler errorHandler) {
        this.names = new ParseTreeProperty<>();
        this.inQuantifiedContextMap = new ParseTreeProperty<>();
        this.creator = creator;
        this.properties = properties;
        this.errorHandler = errorHandler;
    }

    private void setName(ParseTree node, IndexedSymbol name) {
        names.put(node, name);
    }

    private IndexedSymbol getName(ParseTree node) {
        IndexedSymbol name = names.get(node);
        names.removeFrom(node);
        return name;
    }

    private void markNodeAsQuantified(ParseTree node) {
        inQuantifiedContextMap.put(node, true);
    }

    private boolean isInQuantifiedContext(ParseTree node) {
        Boolean isInContext = inQuantifiedContextMap.get(node);

        return isInContext != null;
    }

    @Override
    protected TreePattern<TermOrFormula> aggregateResult(
            TreePattern<TermOrFormula> aggregate, TreePattern<TermOrFormula> nextResult) {

        if (nextResult == null) {
            return aggregate;
        }

        return nextResult;
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> create(PatternCreatorType type) {
        return (TreePattern<TermOrFormula>) creator.create(type);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> create(PatternCreatorType type, IndexedSymbol name) {

        return (TreePattern<TermOrFormula>) creator.create(type, name);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> create(
            PatternCreatorType type, TreePattern<TermOrFormula> subPattern) {

        return (TreePattern<TermOrFormula>) creator.create(type, subPattern);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> create(
            PatternCreatorType type, IndexedSymbol name, TreePattern<TermOrFormula> subPattern) {

        return (TreePattern<TermOrFormula>) creator.create(type, name, subPattern);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> create(
            PatternCreatorType type,
            TreePattern<TermOrFormula> firstPattern,
            TreePattern<TermOrFormula> secondPattern) {

        return (TreePattern<TermOrFormula>) creator.create(type, firstPattern, secondPattern);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> create(
            PatternCreatorType type,
            IndexedSymbol name,
            TreePattern<TermOrFormula> firstPattern,
            TreePattern<TermOrFormula> secondPattern) {

        return (TreePattern<TermOrFormula>) creator.create(type, name, firstPattern, secondPattern);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> create(
            PatternCreatorType type, List<TreePattern<TermOrFormula>> subPatterns) {

        List<ParsablyTyped> castedPatterns = new ArrayList<>(subPatterns);

        return (TreePattern<TermOrFormula>) creator.create(type, castedPatterns);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> create(
            PatternCreatorType type,
            IndexedSymbol name,
            List<TreePattern<TermOrFormula>> subPatterns) {

        List<ParsablyTyped> castedPatterns = new ArrayList<>(subPatterns);

        return (TreePattern<TermOrFormula>) creator.create(type, name, castedPatterns);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> createQuantifiedConstant(
            TreePattern<TermOrFormula> namePattern) {

        return (TreePattern<TermOrFormula>)
                creator.create(PatternCreatorType.QUANTIFIED_CONSTANT, namePattern);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> createQuantifiedConstant(
            IndexedSymbol name, TreePattern<TermOrFormula> namePattern) {

        return (TreePattern<TermOrFormula>)
                creator.create(PatternCreatorType.QUANTIFIED_CONSTANT, name, namePattern);
    }

    private void inspectNodeWithoutContent(ParseTree tree) {
        IndexedSymbol name = getName(tree);
        ParseTree child = tree.getChild(0);

        if (name != null) {
            setName(child, name);
        }

        if (isInQuantifiedContext(tree)) {
            markNodeAsQuantified(child);
        }
    }

    @Override
    public TreePattern<TermOrFormula> visitFormula(@NotNull FormulaContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<TermOrFormula> visitSubformulaOrS(@NotNull SubformulaOrSContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<TermOrFormula> visitSubformula(@NotNull SubformulaContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<TermOrFormula> visitNotInfixSubformula(
            @NotNull NotInfixSubformulaContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<TermOrFormula> visitSubformulaWithName(
            @NotNull SubformulaWithNameContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<TermOrFormula> visitTermOrS(@NotNull TermOrSContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<TermOrFormula> visitTerm(@NotNull TermContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<TermOrFormula> visitNotInfixTerm(@NotNull NotInfixTermContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<TermOrFormula> visitAlternative(@NotNull AlternativeContext ctx) {
        List<TreePattern<TermOrFormula>> subPatterns = new ArrayList<>();
        boolean isInQuantifiedContext = isInQuantifiedContext(ctx);
        SubformulaContext firstCtx = ctx.first;

        if (isInQuantifiedContext) {
            markNodeAsQuantified(firstCtx);
        }

        subPatterns.add(visit(firstCtx));

        for (SubformulaContext subCtx : ctx.further) {
            if (isInQuantifiedContext) {
                markNodeAsQuantified(subCtx);
            }

            subPatterns.add(visit(subCtx));
        }

        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.ALTERNATIVE, name, subPatterns);
        }

        return create(PatternCreatorType.ALTERNATIVE, subPatterns);
    }

    @Override
    public TreePattern<TermOrFormula> visitMultiConstraint(@NotNull MultiConstraintContext ctx) {
        List<TreePattern<TermOrFormula>> subPatterns = new ArrayList<>();
        boolean isInQuantifiedContext = isInQuantifiedContext(ctx);
        SubformulaContext firstCtx = ctx.first;

        if (isInQuantifiedContext) {
            markNodeAsQuantified(firstCtx);
        }

        subPatterns.add(visit(firstCtx));

        for (SubformulaContext subCtx : ctx.further) {
            if (isInQuantifiedContext) {
                markNodeAsQuantified(subCtx);
            }

            subPatterns.add(visit(subCtx));
        }

        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.MULTI_CONSTRAINT, name, subPatterns);
        }

        return create(PatternCreatorType.MULTI_CONSTRAINT, subPatterns);
    }

    @Override
    public TreePattern<TermOrFormula> visitContainsDescendant(
            @NotNull ContainsDescendantContext ctx) {
        SubformulaContext subCtx = ctx.sub;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(subCtx);
        }

        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.CONTAINS_DESCENDANT, name, visit(subCtx));
        }

        return create(PatternCreatorType.CONTAINS_DESCENDANT, visit(subCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitAndLeadingSubformula(
            @NotNull AndLeadingSubformulaContext ctx) {
        return superformulaHelper(ctx.firsts, ctx.furthers, ctx, PatternCreatorType.CONJUNCTION);
    }

    @Override
    public TreePattern<TermOrFormula> visitAndLeadingNoFormula(
            @NotNull AndLeadingNoFormulaContext ctx) {
        return superformulaHelper(ctx.firsts, ctx.furthers, ctx, PatternCreatorType.CONJUNCTION);
    }

    @Override
    public TreePattern<TermOrFormula> visitOrLeadingSubformula(
            @NotNull OrLeadingSubformulaContext ctx) {
        return superformulaHelper(ctx.firsts, ctx.furthers, ctx, PatternCreatorType.DISJUNCTION);
    }

    @Override
    public TreePattern<TermOrFormula> visitOrLeadingNoFormula(
            @NotNull OrLeadingNoFormulaContext ctx) {
        return superformulaHelper(ctx.firsts, ctx.furthers, ctx, PatternCreatorType.DISJUNCTION);
    }

    @Override
    public TreePattern<TermOrFormula> visitImplies(@NotNull ImpliesContext ctx) {
        SubformulaContext firstCtx = ctx.first;
        SubformulaContext secondCtx = ctx.second;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(firstCtx);
            markNodeAsQuantified(secondCtx);
        }

        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.IMPLICATION, name, visit(firstCtx), visit(secondCtx));
        }

        return create(PatternCreatorType.IMPLICATION, visit(firstCtx), visit(secondCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitEquiv(@NotNull EquivContext ctx) {
        SubformulaContext firstCtx = ctx.first;
        SubformulaContext secondCtx = ctx.second;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(firstCtx);
            markNodeAsQuantified(secondCtx);
        }

        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.EQUIVALENCE, name, visit(firstCtx), visit(secondCtx));
        }

        return create(PatternCreatorType.EQUIVALENCE, visit(firstCtx), visit(secondCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitSubformulaStar(@NotNull SubformulaStarContext ctx) {
        NameContext nameCtx = ctx.name();
        AtomarSubformulaContext subCtx = ctx.sub;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(subCtx);
        }

        if (nameCtx != null) {
            IndexedSymbol name = extractName(nameCtx);

            return create(PatternCreatorType.REPEAT_FOREST, name, visit(subCtx));
        }

        return create(PatternCreatorType.REPEAT_FOREST, visit(subCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitNamedSubformulaStar(
            @NotNull NamedSubformulaStarContext ctx) {
        NamedSubformulaContext subCtx = ctx.sub;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(subCtx);
        }

        return create(PatternCreatorType.REPEAT_FOREST, visit(subCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitAnyStarSubs(@NotNull AnyStarSubsContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(
                    PatternCreatorType.REPEAT_FOREST, name, create(PatternCreatorType.ANY_FORMULA));
        }

        return create(PatternCreatorType.REPEAT_FOREST, create(PatternCreatorType.ANY_FORMULA));
    }

    @Override
    public TreePattern<TermOrFormula> visitAnyNameStarSubs(@NotNull AnyNameStarSubsContext ctx) {
        IndexedSymbol name = new IndexedSymbol(ctx.name().getText());

        return create(
                PatternCreatorType.REPEAT_FOREST, name, create(PatternCreatorType.ANY_FORMULA));
    }

    @Override
    public TreePattern<TermOrFormula> visitTrue(@NotNull TrueContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.TRUE, name);
        }

        return create(PatternCreatorType.TRUE);
    }

    @Override
    public TreePattern<TermOrFormula> visitFalse(@NotNull FalseContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.FALSE, name);
        }

        return create(PatternCreatorType.FALSE);
    }

    @Override
    public TreePattern<TermOrFormula> visitFormulaInBracket(
            PatternParser.FormulaInBracketContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundFormulae(ParenthesesType.BRACKETS)) {
            reportParenthesesParsingFault(
                    PredicateFormulaFaultReason.PARENTHESES_AROUND_FORMULAE_NOT_ALLOWED,
                    ctx.OBRACKET().getSymbol(),
                    ctx.formula().getText(),
                    ParenthesesType.BRACKETS);
        }

        IndexedSymbol name = getName(ctx);
        ParseTree child = ctx.formula();

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(child);
        }

        if (name != null) {
            setName(child, name);
        }

        return visit(child);
    }

    @Override
    public TreePattern<TermOrFormula> visitFormulaInParen(@NotNull FormulaInParenContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundFormulae(ParenthesesType.PARENTHESES)) {
            reportParenthesesParsingFault(
                    PredicateFormulaFaultReason.PARENTHESES_AROUND_FORMULAE_NOT_ALLOWED,
                    ctx.OPAREN().getSymbol(),
                    ctx.formula().getText(),
                    ParenthesesType.PARENTHESES);
        }

        IndexedSymbol name = getName(ctx);
        ParseTree child = ctx.formula();

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(child);
        }

        if (name != null) {
            setName(child, name);
        }

        return visit(child);
    }

    @Override
    public TreePattern<TermOrFormula> visitPrefixRelationSymbol(
            PatternParser.PrefixRelationSymbolContext ctx) {
        return prefixSymbolHelper(
                ctx.iSymbol(), null, new LinkedList<>(), ctx, PatternCreatorType.RELATION);
    }

    @Override
    public TreePattern<TermOrFormula> visitPrefixRelationSymbolBrackets(
            PatternParser.PrefixRelationSymbolBracketsContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundArguments(ParenthesesType.BRACKETS)) {
            reportParenthesesParsingFault(
                    PredicateFormulaFaultReason.PARENTHESES_AROUND_ARGUMENTS_NOT_ALLOWED,
                    ctx.OBRACKET().getSymbol(),
                    ctx.iSymbol().getText(),
                    ParenthesesType.BRACKETS);
        }

        return prefixSymbolHelper(
                ctx.iSymbol(), ctx.firsts, ctx.furthers, ctx, PatternCreatorType.RELATION);
    }

    @Override
    public TreePattern<TermOrFormula> visitPrefixRelationSymbolParentheses(
            @NotNull PatternParser.PrefixRelationSymbolParenthesesContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundArguments(ParenthesesType.PARENTHESES)) {
            reportParenthesesParsingFault(
                    PredicateFormulaFaultReason.PARENTHESES_AROUND_ARGUMENTS_NOT_ALLOWED,
                    ctx.OPAREN().getSymbol(),
                    ctx.iSymbol().getText(),
                    ParenthesesType.PARENTHESES);
        }

        return prefixSymbolHelper(
                ctx.iSymbol(), ctx.firsts, ctx.furthers, ctx, PatternCreatorType.RELATION);
    }

    @Override
    public TreePattern<TermOrFormula> visitAnyFormulaAtomarSub(
            @NotNull AnyFormulaAtomarSubContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {

            return create(PatternCreatorType.ANY_FORMULA, name);
        }

        return create(PatternCreatorType.ANY_FORMULA);
    }

    @Override
    public TreePattern<TermOrFormula> visitAnyFormulaWithNameAtomarSub(
            @NotNull AnyFormulaWithNameAtomarSubContext ctx) {
        if (!properties.areShortcutsAllowed()) {
            errorHandler.reportFault(
                    GeneralPatternFaultReason.SHORTCUTS_NOT_ALLOWED,
                    ctx.ANYFORMULA().getSymbol(),
                    ctx.name().getText(),
                    "shortcuts not allowed");
        }

        IndexedSymbol name = new IndexedSymbol(ctx.name().getText());

        return create(PatternCreatorType.ANY_FORMULA, name);
    }

    @Override
    public TreePattern<TermOrFormula> visitReadNameAtomarSub(
            @NotNull ReadNameAtomarSubContext ctx) {
        IndexedSymbol name = new IndexedSymbol(ctx.name().getText());

        return create(PatternCreatorType.ANY, name);
    }

    @Override
    public TreePattern<TermOrFormula> visitNamedFormula(@NotNull NamedFormulaContext ctx) {
        IndexedSymbol name = extractName(ctx.name());
        ParseTree child = ctx.subNI;
        setName(child, name);

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(child);
        }

        return visit(child);
    }

    @Override
    public TreePattern<TermOrFormula> visitNegation(@NotNull NegationContext ctx) {
        IndexedSymbol name = getName(ctx);
        SubformulaContext subCtx = ctx.sub;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(subCtx);
        }

        if (name != null) {

            return create(PatternCreatorType.NEGATION, name, visit(subCtx));
        }

        return create(PatternCreatorType.NEGATION, visit(subCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitUniversalQuantifier(
            @NotNull UniversalQuantifierContext ctx) {
        SubformulaContext subCtx = ctx.sub;
        markNodeAsQuantified(subCtx);

        TreePattern<TermOrFormula> varPattern =
                create(PatternCreatorType.VARIABLE, visit(ctx.iSymbol()));

        IndexedSymbol name = getName(ctx);

        if (name != null) {

            return create(PatternCreatorType.UNIVERSAL_QUANTIFIER, name, varPattern, visit(subCtx));
        }

        return create(PatternCreatorType.UNIVERSAL_QUANTIFIER, varPattern, visit(subCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitExistentialQuantifier(
            @NotNull ExistentialQuantifierContext ctx) {
        SubformulaContext subCtx = ctx.sub;
        markNodeAsQuantified(subCtx);

        TreePattern<TermOrFormula> varPattern =
                create(PatternCreatorType.VARIABLE, visit(ctx.iSymbol()));

        IndexedSymbol name = getName(ctx);

        if (name != null) {

            return create(
                    PatternCreatorType.EXISTENTIAL_QUANTIFIER, name, varPattern, visit(subCtx));
        }

        return create(PatternCreatorType.EXISTENTIAL_QUANTIFIER, varPattern, visit(subCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitComplement(@NotNull ComplementContext ctx) {
        SubformulaContext subCtx = ctx.sub;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(subCtx);
        }

        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.COMPLEMENT, name, visit(subCtx));
        }

        return create(PatternCreatorType.COMPLEMENT, visit(subCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitWithoutInfixRelation(
            @NotNull PatternParser.WithoutInfixRelationContext ctx) {
        NotInfixTermContext firstCtx = ctx.firstPrefix;
        NotInfixTermContext secondCtx = ctx.secondPrefix;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(firstCtx);
            markNodeAsQuantified(secondCtx);
        }

        List<TreePattern<TermOrFormula>> subPatterns = new ArrayList<>();
        IndexedSymbol name = new IndexedSymbol(ctx.sym.getText());
        subPatterns.add(create(PatternCreatorType.EXACT_NAME, name));
        subPatterns.add(visit(firstCtx));
        subPatterns.add(visit(secondCtx));

        IndexedSymbol patternName = getName(ctx);

        if (patternName != null) {
            return create(PatternCreatorType.RELATION, name, subPatterns);
        }

        // pattern does not check whether infix or not, this will be done by signature
        return create(PatternCreatorType.RELATION, subPatterns);
    }

    @Override
    public TreePattern<TermOrFormula> visitInfixRelationSymbol(
            PatternParser.InfixRelationSymbolContext ctx) {
        NotInfixTermContext firstCtx = ctx.firstPrefix;
        NotInfixTermContext secondCtx = ctx.secondPrefix;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(firstCtx);
            markNodeAsQuantified(secondCtx);
        }

        List<TreePattern<TermOrFormula>> subPatterns = new ArrayList<>();
        IndexedSymbol name = new IndexedSymbol(ctx.sym.getText());
        subPatterns.add(create(PatternCreatorType.EXACT_NAME, name));
        subPatterns.add(visit(firstCtx));
        subPatterns.add(visit(secondCtx));

        IndexedSymbol patternName = getName(ctx);

        if (patternName != null) {
            return create(PatternCreatorType.RELATION, name, subPatterns);
        }

        // pattern does not check whether infix or not, this will be done by signature
        return create(PatternCreatorType.RELATION, subPatterns);
    }

    @Override
    public TreePattern<TermOrFormula> visitSubTermStar(@NotNull SubTermStarContext ctx) {
        AtomarTermContext subCtx = ctx.sub;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(subCtx);
        }

        NameContext nameCtx = ctx.name();

        if (nameCtx != null) {
            IndexedSymbol name = extractName(nameCtx);

            return create(PatternCreatorType.REPEAT_FOREST, name, visit(subCtx));
        }

        return create(PatternCreatorType.REPEAT_FOREST, visit(subCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitNamedSubTermStar(@NotNull NamedSubTermStarContext ctx) {
        NamedTermContext subCtx = ctx.sub;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(subCtx);
        }

        return create(PatternCreatorType.REPEAT_FOREST, visit(subCtx));
    }

    @Override
    public TreePattern<TermOrFormula> visitAnyStarTerms(@NotNull AnyStarTermsContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(
                    PatternCreatorType.REPEAT_FOREST, name, create(PatternCreatorType.ANY_TERM));
        }

        return create(PatternCreatorType.REPEAT_FOREST, create(PatternCreatorType.ANY_TERM));
    }

    @Override
    public TreePattern<TermOrFormula> visitAnyNameStarTerms(@NotNull AnyNameStarTermsContext ctx) {
        IndexedSymbol name = new IndexedSymbol(ctx.name().getText());

        return create(PatternCreatorType.REPEAT_FOREST, name, create(PatternCreatorType.ANY_TERM));
    }

    @Override
    public TreePattern<TermOrFormula> visitPrefixFunctionSymbol(PrefixFunctionSymbolContext ctx) {
        return prefixSymbolHelper(
                ctx.iSymbol(), null, new LinkedList<>(), ctx, PatternCreatorType.FUNCTION);
    }

    @Override
    public TreePattern<TermOrFormula> visitPrefixFunctionSymbolBrackets(
            PatternParser.PrefixFunctionSymbolBracketsContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundArguments(ParenthesesType.BRACKETS)) {
            reportParenthesesParsingFault(
                    PredicateFormulaFaultReason.PARENTHESES_AROUND_ARGUMENTS_NOT_ALLOWED,
                    ctx.OBRACKET().getSymbol(),
                    ctx.iSymbol().getText(),
                    ParenthesesType.BRACKETS);
        }

        return prefixSymbolHelper(
                ctx.iSymbol(), ctx.first, ctx.further, ctx, PatternCreatorType.FUNCTION);
    }

    @Override
    public TreePattern<TermOrFormula> visitPrefixFunctionSymbolParentheses(
            @NotNull PatternParser.PrefixFunctionSymbolParenthesesContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundArguments(ParenthesesType.PARENTHESES)) {
            reportParenthesesParsingFault(
                    PredicateFormulaFaultReason.PARENTHESES_AROUND_ARGUMENTS_NOT_ALLOWED,
                    ctx.OPAREN().getSymbol(),
                    ctx.iSymbol().getText(),
                    ParenthesesType.PARENTHESES);
        }

        return prefixSymbolHelper(
                ctx.iSymbol(), ctx.first, ctx.further, ctx, PatternCreatorType.FUNCTION);
    }

    @Override
    public TreePattern<TermOrFormula> visitTermInBrackets(PatternParser.TermInBracketsContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundTerms(ParenthesesType.BRACKETS)) {
            reportParenthesesParsingFault(
                    PredicateFormulaFaultReason.PARENTHESES_AROUND_TERMS_NOT_ALLOWED,
                    ctx.OBRACKET().getSymbol(),
                    ctx.term().getText(),
                    ParenthesesType.BRACKETS);
        }

        IndexedSymbol name = getName(ctx);
        ParseTree child = ctx.term();

        if (name != null) {
            setName(child, name);
        }

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(child);
        }

        return visit(child);
    }

    @Override
    public TreePattern<TermOrFormula> visitTermInParen(@NotNull TermInParenContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundTerms(ParenthesesType.PARENTHESES)) {
            reportParenthesesParsingFault(
                    PredicateFormulaFaultReason.PARENTHESES_AROUND_TERMS_NOT_ALLOWED,
                    ctx.OPAREN().getSymbol(),
                    ctx.term().getText(),
                    ParenthesesType.PARENTHESES);
        }

        IndexedSymbol name = getName(ctx);
        ParseTree child = ctx.term();

        if (name != null) {
            setName(child, name);
        }

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(child);
        }

        return visit(child);
    }

    @Override
    public TreePattern<TermOrFormula> visitAnyFormulaAtomarTerm(
            @NotNull AnyFormulaAtomarTermContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {

            return create(PatternCreatorType.ANY_TERM, name);
        }

        return create(PatternCreatorType.ANY_TERM);
    }

    @Override
    public TreePattern<TermOrFormula> visitPrefixTerm(@NotNull PrefixTermContext ctx) {
        IndexedSymbol name = extractName(ctx.name());
        ParseTree child = ctx.subNI;
        setName(child, name);

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(child);
        }

        return visit(child);
    }

    @Override
    public TreePattern<TermOrFormula> visitAnyFormulaWithNameAtomarTerm(
            @NotNull AnyFormulaWithNameAtomarTermContext ctx) {
        if (!properties.areShortcutsAllowed()) {
            errorHandler.reportFault(
                    GeneralPatternFaultReason.SHORTCUTS_NOT_ALLOWED,
                    ctx.ANYFORMULA().getSymbol(),
                    ctx.name().getText(),
                    "shortcuts not allowed");
        }

        IndexedSymbol name = new IndexedSymbol(ctx.name().getText());

        return create(PatternCreatorType.ANY_TERM, name);
    }

    @Override
    public TreePattern<TermOrFormula> visitReadNameAtomarTerm(
            @NotNull ReadNameAtomarTermContext ctx) {
        IndexedSymbol name = new IndexedSymbol(ctx.name().getText());

        return create(PatternCreatorType.ANY, name);
    }

    @Override
    public TreePattern<TermOrFormula> visitWithoutInfixFunction(
            @NotNull PatternParser.WithoutInfixFunctionContext ctx) {
        List<TreePattern<TermOrFormula>> subPatterns = new ArrayList<>();
        IndexedSymbol name = new IndexedSymbol(ctx.sym.getText());
        NotInfixTermContext firstCtx = ctx.first;
        NotInfixTermContext secondCtx = ctx.second;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(firstCtx);
            markNodeAsQuantified(secondCtx);
        }

        subPatterns.add(create(PatternCreatorType.EXACT_NAME, name));
        subPatterns.add(visit(firstCtx));
        subPatterns.add(visit(secondCtx));

        IndexedSymbol patternName = getName(ctx);

        if (patternName != null) {
            return create(PatternCreatorType.FUNCTION, name, subPatterns);
        }

        // pattern does not check whether infix or not, this will be done by signature
        return create(PatternCreatorType.FUNCTION, subPatterns);
    }

    @Override
    public TreePattern<TermOrFormula> visitInfixFunctionSymbol(
            PatternParser.InfixFunctionSymbolContext ctx) {
        List<TreePattern<TermOrFormula>> subPatterns = new ArrayList<>();
        IndexedSymbol name = new IndexedSymbol(ctx.sym.getText());
        NotInfixTermContext firstCtx = ctx.first;
        NotInfixTermContext secondCtx = ctx.second;

        if (isInQuantifiedContext(ctx)) {
            markNodeAsQuantified(firstCtx);
            markNodeAsQuantified(secondCtx);
        }

        subPatterns.add(create(PatternCreatorType.EXACT_NAME, name));
        subPatterns.add(visit(firstCtx));
        subPatterns.add(visit(secondCtx));

        IndexedSymbol patternName = getName(ctx);

        if (patternName != null) {
            return create(PatternCreatorType.FUNCTION, name, subPatterns);
        }

        // pattern does not check whether infix or not, this will be done by signature
        return create(PatternCreatorType.FUNCTION, subPatterns);
    }

    @Override
    public TreePattern<TermOrFormula> visitFixedName(@NotNull FixedNameContext ctx) {
        IndexedSymbol name = extractName(ctx.name());

        return create(PatternCreatorType.EXACT_NAME, name);
    }

    @Override
    public TreePattern<TermOrFormula> visitAnyName(@NotNull AnyNameContext ctx) {
        IndexedSymbol name = extractName(ctx.name());

        return create(PatternCreatorType.ANY_NAME, name);
    }

    private IndexedSymbol extractName(NameContext ctx) {
        SymbolToken token = (SymbolToken) ctx.sym;
        StringBuilder builder = new StringBuilder(token.getSymbol().toSafeTextString());

        for (ParseTree child : ctx.children) {
            if (child instanceof Token) {
                Token childToken = (Token) child;

                if (childToken.getType() == PatternParser.DISTINCT) {
                    builder.append("[]");
                }
            }
        }

        return new IndexedSymbol(builder.toString());
    }

    // HELPER METHODS

    private TreePattern<TermOrFormula> superformulaHelper(
            ParserRuleContext first,
            List<SubformulaOrSContext> furthers,
            ParserRuleContext ctx,
            PatternCreatorType patternType) {
        List<TreePattern<TermOrFormula>> subPatterns = new ArrayList<>();
        boolean isInQuantifiedContext = isInQuantifiedContext(ctx);

        if (isInQuantifiedContext) {
            markNodeAsQuantified(first);
        }

        subPatterns.add(visit(first));

        for (ParserRuleContext subCtx : furthers) {
            if (isInQuantifiedContext) {
                markNodeAsQuantified(subCtx);
            }

            subPatterns.add(visit(subCtx));
        }

        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(patternType, name, subPatterns);
        }

        return create(patternType, subPatterns);
    }

    private TreePattern<TermOrFormula> prefixSymbolHelper(
            PatternParser.ISymbolContext iSymbol,
            TermOrSContext firsts,
            List<TermOrSContext> furthers,
            @NotNull ParserRuleContext ctx,
            PatternCreatorType patternType) {
        List<TreePattern<TermOrFormula>> subPatterns = new ArrayList<>();
        boolean isInQuantifiedContext = isInQuantifiedContext(ctx);
        subPatterns.add(visit(iSymbol));

        if (firsts != null) {
            if (isInQuantifiedContext) {
                markNodeAsQuantified(firsts);
            }

            subPatterns.add(visit(firsts));
        }

        for (TermOrSContext furtherCtx : furthers) {
            if (isInQuantifiedContext) {
                markNodeAsQuantified(furtherCtx);
            }

            subPatterns.add(visit(furtherCtx));
        }

        IndexedSymbol name = getName(ctx);

        if (name != null) {
            if (isInQuantifiedContext && subPatterns.size() == 1) {
                return createQuantifiedConstant(name, subPatterns.get(0));
            }

            return create(patternType, name, subPatterns);
        }

        if (isInQuantifiedContext && subPatterns.size() == 1) {
            return createQuantifiedConstant(subPatterns.get(0));
        }

        return create(patternType, subPatterns);
    }

    private void reportParenthesesParsingFault(
            ParsingFaultReason reason, Token token, String faultText, ParenthesesType type) {

        ParsingFault fault =
                new ParenthesesParsingFault(
                        reason,
                        token.getLine() - 1,
                        token.getCharPositionInLine(),
                        token.getText(),
                        faultText,
                        type);

        errorHandler.reportFault(fault, "parentheses not allowed");
    }
}
