package de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralPatternFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.ShortcutParsingFault;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.AlternativeContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.AndLeadingNoFormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.AndLeadingSubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.AnyFormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.AnyFormulaWithNameContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.AnyNameContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.AnyNameStarContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.AnyStarContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.AtomarSubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.BoxContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.ComplementContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.ContainsDescendantContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.DiamondContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.EquivContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.FalseContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.FixedNameContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.FormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.FormulaInParenContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.ImpliesContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.MultiConstraintContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.NameContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.NamedFormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.NamedStarContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.NamedSubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.NegationContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.OrLeadingNoFormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.OrLeadingSubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.ReadNameContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.StarContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.SubformulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.SubformulaOrSContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.SubformulaWithNameContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.TrueContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.VariableContext;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern.PatternReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.general.SymbolToken;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 * Used to create modal logical pattern out of the parse tree that was constructed by {@link
 * PatternParser}.
 */
public class PatternParserConstructionVisitor
        extends PatternParserBaseVisitor<TreePattern<ModalFormula>> {

    /**
     * {@code names} is used to annotate nodes of the parse tree with a name if it was specified for
     * them.
     */
    private final ParseTreeProperty<IndexedSymbol> names;

    private final ParsingCreator creator;
    private final PatternReaderProperties properties;
    private final VisitorErrorHandler errorHandler;

    public PatternParserConstructionVisitor(
            ParsingCreator creator,
            PatternReaderProperties properties,
            VisitorErrorHandler errorHandler) {
        this.names = new ParseTreeProperty<>();
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

    @Override
    protected TreePattern<ModalFormula> aggregateResult(
            TreePattern<ModalFormula> aggregate, TreePattern<ModalFormula> nextResult) {

        if (nextResult == null) {
            return aggregate;
        }

        return nextResult;
    }

    @SuppressWarnings("unchecked")
    private TreePattern<ModalFormula> create(PatternCreatorType type) {
        return (TreePattern<ModalFormula>) creator.create(type);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<ModalFormula> create(PatternCreatorType type, IndexedSymbol name) {

        return (TreePattern<ModalFormula>) creator.create(type, name);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<ModalFormula> create(
            PatternCreatorType type, TreePattern<ModalFormula> subPattern) {

        return (TreePattern<ModalFormula>) creator.create(type, subPattern);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<ModalFormula> create(
            PatternCreatorType type, IndexedSymbol name, TreePattern<ModalFormula> subPattern) {

        return (TreePattern<ModalFormula>) creator.create(type, name, subPattern);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<ModalFormula> create(
            PatternCreatorType type,
            TreePattern<ModalFormula> firstPattern,
            TreePattern<ModalFormula> secondPattern) {

        return (TreePattern<ModalFormula>) creator.create(type, firstPattern, secondPattern);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<ModalFormula> create(
            PatternCreatorType type,
            IndexedSymbol name,
            TreePattern<ModalFormula> firstPattern,
            TreePattern<ModalFormula> secondPattern) {

        return (TreePattern<ModalFormula>) creator.create(type, name, firstPattern, secondPattern);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<ModalFormula> create(
            PatternCreatorType type, List<TreePattern<ModalFormula>> subPatterns) {

        List<ParsablyTyped> castedPatterns = new ArrayList<>(subPatterns);

        return (TreePattern<ModalFormula>) creator.create(type, castedPatterns);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<ModalFormula> create(
            PatternCreatorType type,
            IndexedSymbol name,
            List<TreePattern<ModalFormula>> subPatterns) {

        List<ParsablyTyped> castedPatterns = new ArrayList<>(subPatterns);

        return (TreePattern<ModalFormula>) creator.create(type, name, castedPatterns);
    }

    private void inspectNodeWithoutContent(ParseTree tree) {
        IndexedSymbol name = getName(tree);
        ParseTree child = tree.getChild(0);

        if (name != null) {
            setName(child, name);
        }
    }

    @Override
    public TreePattern<ModalFormula> visitFormula(@NotNull FormulaContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<ModalFormula> visitSubformulaOrS(@NotNull SubformulaOrSContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<ModalFormula> visitSubformula(@NotNull SubformulaContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<ModalFormula> visitSubformulaWithName(
            @NotNull SubformulaWithNameContext ctx) {
        inspectNodeWithoutContent(ctx);
        return visitChildren(ctx);
    }

    @Override
    public TreePattern<ModalFormula> visitAlternative(@NotNull AlternativeContext ctx) {
        return subPatternHelper(
                ctx.first,
                ctx.further.stream()
                        .map(item -> (ParserRuleContext) item)
                        .collect(Collectors.toList()), // We have to cast this for whatever reason
                ctx,
                PatternCreatorType.ALTERNATIVE);
    }

    @Override
    public TreePattern<ModalFormula> visitMultiConstraint(@NotNull MultiConstraintContext ctx) {
        return subPatternHelper(
                ctx.first,
                ctx.further.stream()
                        .map(item -> (ParserRuleContext) item)
                        .collect(Collectors.toList()), // We have to cast this for whatever reason
                ctx,
                PatternCreatorType.MULTI_CONSTRAINT);
    }

    @Override
    public TreePattern<ModalFormula> visitContainsDescendant(
            @NotNull ContainsDescendantContext ctx) {
        SubformulaContext subCtx = ctx.sub;
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.CONTAINS_DESCENDANT, name, visit(subCtx));
        }

        return create(PatternCreatorType.CONTAINS_DESCENDANT, visit(subCtx));
    }

    @Override
    public TreePattern<ModalFormula> visitAndLeadingSubformula(
            @NotNull AndLeadingSubformulaContext ctx) {
        return subPatternHelper(
                ctx.firsts,
                ctx.furthers.stream()
                        .map(item -> (ParserRuleContext) item)
                        .collect(Collectors.toList()), // We have to cast this for whatever reason
                ctx,
                PatternCreatorType.CONJUNCTION);
    }

    @Override
    public TreePattern<ModalFormula> visitAndLeadingNoFormula(
            @NotNull AndLeadingNoFormulaContext ctx) {
        return subPatternHelper(
                ctx.firsts,
                ctx.furthers.stream()
                        .map(item -> (ParserRuleContext) item)
                        .collect(Collectors.toList()), // We have to cast this for whatever reason
                ctx,
                PatternCreatorType.CONJUNCTION);
    }

    @Override
    public TreePattern<ModalFormula> visitOrLeadingSubformula(
            @NotNull OrLeadingSubformulaContext ctx) {
        List<TreePattern<ModalFormula>> subPatterns = new ArrayList<>();
        SubformulaOrSContext firstsCtx = ctx.firsts;
        subPatterns.add(visit(firstsCtx));

        for (SubformulaOrSContext subCtx : ctx.furthers) {
            subPatterns.add(visit(subCtx));
        }

        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.DISJUNCTION, name, subPatterns);
        }

        return create(PatternCreatorType.DISJUNCTION, subPatterns);
    }

    @Override
    public TreePattern<ModalFormula> visitOrLeadingNoFormula(
            @NotNull OrLeadingNoFormulaContext ctx) {
        return subPatternHelper(
                ctx.firsts,
                ctx.furthers.stream()
                        .map(item -> (ParserRuleContext) item)
                        .collect(Collectors.toList()), // We have to cast this for whatever reason
                ctx,
                PatternCreatorType.DISJUNCTION);
    }

    @Override
    public TreePattern<ModalFormula> visitImplies(@NotNull ImpliesContext ctx) {
        SubformulaContext firstCtx = ctx.first;
        SubformulaContext secondCtx = ctx.second;
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.IMPLICATION, name, visit(firstCtx), visit(secondCtx));
        }

        return create(PatternCreatorType.IMPLICATION, visit(firstCtx), visit(secondCtx));
    }

    @Override
    public TreePattern<ModalFormula> visitEquiv(@NotNull EquivContext ctx) {
        SubformulaContext firstCtx = ctx.first;
        SubformulaContext secondCtx = ctx.second;
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.EQUIVALENCE, name, visit(firstCtx), visit(secondCtx));
        }

        return create(PatternCreatorType.EQUIVALENCE, visit(firstCtx), visit(secondCtx));
    }

    @Override
    public TreePattern<ModalFormula> visitStar(@NotNull StarContext ctx) {
        NameContext nameCtx = ctx.name();
        AtomarSubformulaContext subCtx = ctx.sub;

        if (nameCtx != null) {
            IndexedSymbol name = extractName(nameCtx);

            return create(PatternCreatorType.REPEAT_FOREST, name, visit(subCtx));
        }

        return create(PatternCreatorType.REPEAT_FOREST, visit(subCtx));
    }

    @Override
    public TreePattern<ModalFormula> visitNamedStar(@NotNull NamedStarContext ctx) {
        NamedSubformulaContext subCtx = ctx.sub;

        return create(PatternCreatorType.REPEAT_FOREST, visit(subCtx));
    }

    @Override
    public TreePattern<ModalFormula> visitAnyStar(@NotNull AnyStarContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(
                    PatternCreatorType.REPEAT_FOREST, name, create(PatternCreatorType.ANY_FORMULA));
        }

        return create(PatternCreatorType.REPEAT_FOREST, create(PatternCreatorType.ANY_FORMULA));
    }

    @Override
    public TreePattern<ModalFormula> visitAnyNameStar(@NotNull AnyNameStarContext ctx) {
        IndexedSymbol name = new IndexedSymbol(ctx.name().getText());

        return create(
                PatternCreatorType.REPEAT_FOREST, name, create(PatternCreatorType.ANY_FORMULA));
    }

    @Override
    public TreePattern<ModalFormula> visitTrue(@NotNull TrueContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.TRUE, name);
        }

        return create(PatternCreatorType.TRUE);
    }

    @Override
    public TreePattern<ModalFormula> visitFalse(@NotNull FalseContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.FALSE, name);
        }

        return create(PatternCreatorType.FALSE);
    }

    @Override
    public TreePattern<ModalFormula> visitFormulaInParen(@NotNull FormulaInParenContext ctx) {
        IndexedSymbol name = getName(ctx);
        ParseTree child = ctx.formula();

        if (name != null) {
            setName(child, name);
        }

        return visit(child);
    }

    @Override
    public TreePattern<ModalFormula> visitVariable(@NotNull VariableContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.VARIABLE, name, visit(ctx.iSymbol()));
        }

        return create(PatternCreatorType.VARIABLE, visit(ctx.iSymbol()));
    }

    @Override
    public TreePattern<ModalFormula> visitAnyFormula(@NotNull AnyFormulaContext ctx) {
        IndexedSymbol name = getName(ctx);

        if (name != null) {

            return create(PatternCreatorType.ANY_FORMULA, name);
        }

        return create(PatternCreatorType.ANY_FORMULA);
    }

    @Override
    public TreePattern<ModalFormula> visitAnyFormulaWithName(
            @NotNull AnyFormulaWithNameContext ctx) {
        if (!properties.areShortcutsAllowed()) {
            ShortcutParsingFault fault =
                    new ShortcutParsingFault(
                            GeneralPatternFaultReason.SHORTCUTS_NOT_ALLOWED,
                            ctx.ANYFORMULA().getSymbol().getLine() - 1,
                            ctx.ANYFORMULA().getSymbol().getCharPositionInLine(),
                            ctx.ANYFORMULA().getSymbol().getText(),
                            extractName(ctx.name()).toString());
            errorHandler.reportFault(fault, "shortcuts not allowed");
        }

        IndexedSymbol name = extractName(ctx.name());

        return create(PatternCreatorType.ANY_FORMULA, name);
    }

    @Override
    public TreePattern<ModalFormula> visitReadName(@NotNull ReadNameContext ctx) {
        IndexedSymbol name = extractName(ctx.name());

        return create(PatternCreatorType.ANY, name);
    }

    @Override
    public TreePattern<ModalFormula> visitNamedFormula(@NotNull NamedFormulaContext ctx) {
        IndexedSymbol name = extractName(ctx.name());
        ParseTree child = ctx.subNI;
        setName(child, name);

        return visit(child);
    }

    @Override
    public TreePattern<ModalFormula> visitNegation(@NotNull NegationContext ctx) {
        IndexedSymbol name = getName(ctx);
        SubformulaContext subCtx = ctx.sub;

        if (name != null) {

            return create(PatternCreatorType.NEGATION, name, visit(subCtx));
        }

        return create(PatternCreatorType.NEGATION, visit(subCtx));
    }

    @Override
    public TreePattern<ModalFormula> visitBox(@NotNull BoxContext ctx) {
        SubformulaContext subCtx = ctx.sub;
        IndexedSymbol name = getName(ctx);

        if (name != null) {

            return create(PatternCreatorType.BOX, name, visit(subCtx));
        }

        return create(PatternCreatorType.BOX, visit(subCtx));
    }

    @Override
    public TreePattern<ModalFormula> visitDiamond(@NotNull DiamondContext ctx) {
        SubformulaContext subCtx = ctx.sub;
        IndexedSymbol name = getName(ctx);

        if (name != null) {

            return create(PatternCreatorType.DIAMOND, name, visit(subCtx));
        }

        return create(PatternCreatorType.DIAMOND, visit(subCtx));
    }

    @Override
    public TreePattern<ModalFormula> visitComplement(@NotNull ComplementContext ctx) {
        SubformulaContext subCtx = ctx.sub;
        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(PatternCreatorType.COMPLEMENT, name, visit(subCtx));
        }

        return create(PatternCreatorType.COMPLEMENT, visit(subCtx));
    }

    @Override
    public TreePattern<ModalFormula> visitFixedName(@NotNull FixedNameContext ctx) {
        IndexedSymbol name = extractName(ctx.name());

        return create(PatternCreatorType.EXACT_NAME, name);
    }

    @Override
    public TreePattern<ModalFormula> visitAnyName(@NotNull AnyNameContext ctx) {
        IndexedSymbol name = extractName(ctx.name());

        return create(PatternCreatorType.ANY_NAME, name);
    }

    /*-----------------------------------------*\
     | Helper methods                          |
    \*-----------------------------------------*/

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

            if (child instanceof TerminalNodeImpl) {
                Token childToken = ((TerminalNodeImpl) child).getSymbol();

                if (childToken.getType() == PatternParser.DISTINCT) {
                    builder.append("[]");
                }
            }
        }

        return new IndexedSymbol(builder.toString());
    }

    private TreePattern<ModalFormula> subPatternHelper(
            ParserRuleContext firsts,
            List<ParserRuleContext> furthers,
            ParserRuleContext ctx,
            PatternCreatorType creatorType) {
        List<TreePattern<ModalFormula>> subPatterns = new ArrayList<>();
        subPatterns.add(visit(firsts));

        for (ParserRuleContext subCtx : furthers) {
            subPatterns.add(visit(subCtx));
        }

        IndexedSymbol name = getName(ctx);

        if (name != null) {
            return create(creatorType, name, subPatterns);
        }

        return create(creatorType, subPatterns);
    }
}
