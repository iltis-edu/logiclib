package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau;

import de.tudortmund.cs.iltis.logiclib.io.writer.modal.satisfiability.tableau.ModalTableauTikzWriter;
import de.tudortmund.cs.iltis.logiclib.modal.formula.*;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeStructure;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.ModalInterpretation;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.EquivalenceTransformations;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.Transformation;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.SubscriptCounter;
import de.tudortmund.cs.iltis.utils.general.Data;
import de.tudortmund.cs.iltis.utils.graph.EmptyEdgeLabel;
import de.tudortmund.cs.iltis.utils.graph.Vertex;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModalTableau implements Serializable {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    protected FormulaNode root;
    protected SubscriptCounter stateNamer;
    protected IndexedSymbol latestStateName;
    protected ArrayList<FormulaNode> nodesToProcess;

    // =========================================================================
    // CONSTRUCTOR(S)
    // =========================================================================
    protected ModalTableau() {}

    public ModalTableau(ModalFormula formula) {
        this(formula, true);
    }

    public ModalTableau(ModalFormula formula, boolean autoSaturate) {
        // compute NNF of formula:
        Transformation nnf = EquivalenceTransformations.NEGATION_NORMALFORM;
        ModalFormula formulaInNnf = nnf.apply(formula);

        // initialize state namer:
        this.stateNamer = new SubscriptCounter("s");
        this.latestStateName = this.stateNamer.getFirst();
        // this.lazilyAddedNodes = new LazilyAddedNodes();

        // create initial Kripke structure with single state:
        KripkeState firstState = new KripkeState(this.stateNamer.getFirst());
        this.root = new FormulaNode(firstState, formulaInNnf, new TreePath());

        // start saturation process:
        this.nodesToProcess = Data.<FormulaNode>newArrayList(this.root);

        if (autoSaturate) {
            this.saturate();

            // determine open-/closedness of nodes:
            KripkeStructure structure = new KripkeStructure();
            structure.addVertex(firstState);
            closePaths(this.root, structure, false);
        }
    }

    // =========================================================================
    // METHODS
    // =========================================================================
    // PUBLIC /////////////////////////////////////////////////////////////////
    public TableauNode getTree() {
        return this.root;
    }

    public boolean isOpen() {
        return !this.getOpenLeaves().isEmpty();
    }

    public boolean isClosed() {
        return !this.isOpen();
    }

    public List<ModalInterpretation> getModels() {
        List<FormulaNode> openLeaves = Data.map(this.getOpenLeaves(), node -> (FormulaNode) node);
        return Data.map(
                openLeaves, node -> new ModalInterpretation(node.getStructure(), root.getState()));
    }

    public String toLaTeX() {
        ModalTableauTikzWriter writer = new ModalTableauTikzWriter();
        return writer.write(this);
    }

    // PRIVATE ////////////////////////////////////////////////////////////////
    // changed for each into while, because there were problems with the fail-fast iterator
    protected boolean saturate() {
        boolean changed;

        changed = false;
        int i = 0;
        while (i < this.nodesToProcess.size()) {
            FormulaNode node = this.nodesToProcess.get(i);
            // if the node is closed, it should be removed
            if (node.isClosed()) this.nodesToProcess.remove(node);
            else {
                if (!node.getFormula().isBox()) this.nodesToProcess.remove(node);
                else i++;
                changed = saturate(node);
                if (changed) i = 0; // leave for loop, start with next node from top
            }
        }

        return true;
    }

    // should only return true, if a diamondNode was processed
    protected boolean saturate(FormulaNode node) {
        ModalFormula formula = node.getFormula();

        if (formula instanceof Diamond) return this.saturate(node, (Diamond) formula);
        else if (formula instanceof Box) return this.saturate(node, (Box) formula);
        else if (formula instanceof Conjunction) return this.saturate(node, (Conjunction) formula);
        else if (formula instanceof Disjunction) return this.saturate(node, (Disjunction) formula);
        else if (formula instanceof Negation) return this.saturate(node, (Negation) formula);
        else if (formula instanceof Variable) return this.saturate(node, (Variable) formula);
        else if (formula instanceof True) return this.saturate(node, (True) formula);
        else if (formula instanceof False) return this.saturate(node, (False) formula);
        else throw new IllegalArgumentException("Unexpected type of formula");
    }

    protected boolean saturate(FormulaNode node, Diamond formula) {
        final ModalFormula subformula = formula.getSubformula(0);
        List<FormulaNode> newNodes = new ArrayList<>();

        // create new formula node:
        IndexedSymbol newStateName = this.stateNamer.getNext(this.latestStateName);
        this.latestStateName = newStateName;
        KripkeState state = new KripkeState(newStateName);

        for (TableauNode leaf : node.getLeaves()) {
            FormulaNode parentNode = (FormulaNode) leaf;
            TreePath path = parentNode.getPath().child(0).child(0);
            FormulaNode childNode = new FormulaNode(state.clone(), subformula, path.clone());
            path.pop();

            // connect via edge node:
            EdgeNode edgeNode = new EdgeNode(node.getState().clone(), state.clone(), path.clone());
            edgeNode.addChild(childNode);
            parentNode.addChild(edgeNode);

            newNodes.add(childNode);
        }
        this.nodesToProcess.addAll(newNodes);
        return newNodes.size() > 0;
    }

    protected boolean saturate(FormulaNode node, Box formula) {
        List<TableauNode> edgeNodes =
                Data.filter(this.root.getDescendants(), n -> (n instanceof EdgeNode));
        boolean changed = false;
        for (TableauNode edgeNode : edgeNodes)
            changed |= this.saturateBoxNode((EdgeNode) edgeNode, node);
        return changed;
    }

    protected boolean saturateBoxNode(EdgeNode edgeNode, FormulaNode formulaNode) {
        if (edgeNode.hasBeenExtendedBy(formulaNode)) return false;

        FormulaNode whereToAdd = null;
        if (formulaNode.getDescendants().contains(edgeNode))
            whereToAdd = (FormulaNode) edgeNode.getChild(0);
        else if (edgeNode.getDescendants().contains(formulaNode)) whereToAdd = formulaNode;
        else {
            // This edge node is irrelevant for the box node,
            // add it to the edge node's extended list so that
            // we don't have to consider it again:
            edgeNode.extendBy(formulaNode);
            return false;
        }

        if (edgeNode.getParentState().equals(formulaNode.getState())) {
            this.addToAllLeaves(
                    whereToAdd, edgeNode.getChildState(), formulaNode.getFormula(), false);
            edgeNode.extendBy(formulaNode);
            // returns false to optimize reprocessing boxNodes
            return false;
        } else edgeNode.extendBy(formulaNode);
        return false;
    }

    protected boolean saturate(FormulaNode node, Conjunction formula) {
        this.addToAllLeaves(node, null, formula, true);
        // returns false to optimize reprocessing boxNodes
        return false;
    }

    protected boolean saturate(FormulaNode node, Disjunction formula) {
        this.addToAllLeaves(node, null, formula, false);
        // returns false to optimize reprocessing boxNodes
        return false;
    }

    protected boolean saturate(FormulaNode node, Negation formula) {
        // if the state contradicts the literal, the path should be closed
        Variable var = (Variable) formula.getSubformula(0);
        Literal lit = new Literal(false, var);

        if (node.getState().contradicts(lit)) {
            node.close();
            closeDescendants(node);
            // returns false to optimize reprocessing boxNodes
            return false;
        }

        // no contradiction yet, the valuation of the state should be set on this path
        node.getState().getValuation().define(var, false);
        defineDescendants(node, var, false);
        // returns false to optimize reprocessing boxNodes
        return false;
    }

    protected boolean saturate(FormulaNode node, Variable formula) {
        // if the state contradicts the literal, the path should be closed
        Literal lit = new Literal(true, formula);

        if (node.getState().contradicts(lit)) {
            node.close();
            closeDescendants(node);
            // returns false to optimize reprocessing boxNodes
            return false;
        }

        // no contradiction yet, the valuation of the state should be set on this path
        node.getState().getValuation().define(formula, true);
        defineDescendants(node, formula, true);
        // returns false to optimize reprocessing boxNodes
        return false;
    }

    protected boolean saturate(FormulaNode node, True formula) {
        // returns false to optimize reprocessing boxNodes
        return false; // satisfiable (nothing to do :- )
    }

    protected boolean saturate(FormulaNode node, False formula) {
        // path should be closed
        node.close();
        closeDescendants(node);
        // returns false to optimize reprocessing boxNodes
        return false;
    }

    protected void closeDescendants(FormulaNode node) {
        for (TableauNode descendant : node.getDescendants()) {
            if (descendant instanceof FormulaNode) {
                descendant.close();
            }
        }
    }

    protected void defineDescendants(FormulaNode node, Variable var, boolean value) {
        for (TableauNode descendant : node.getDescendants()) {
            if (descendant instanceof FormulaNode) {
                FormulaNode descendantNode = (FormulaNode) descendant;
                if (descendantNode.getState().getName().equals(node.getState().getName()))
                    descendantNode.getState().getValuation().define(var, value);
            }
        }
    }

    protected void addToAllLeaves(
            FormulaNode node, KripkeState state, ModalFormula formula, boolean shouldBeChained) {

        List<ModalFormula> subformulae = formula.getSubformulae();
        List<TableauNode> leaves = node.getLeaves();
        List<FormulaNode> newNodes = new ArrayList<>();

        for (TableauNode leaf : leaves) {
            if (state == null) state = node.getState();
            if (state == null) state = ((FormulaNode) leaf).getState();

            List<FormulaNode> newSubformulaNodes = new ArrayList<>();

            if (shouldBeChained) {
                newSubformulaNodes.addAll(addChainedNodes(leaf, state, subformulae));
            } else {
                newSubformulaNodes.addAll(addSiblingNodes(leaf, state, subformulae));
            }

            newNodes.addAll(newSubformulaNodes);
        }

        this.nodesToProcess.addAll(newNodes);
    }

    private List<FormulaNode> addSiblingNodes(
            TableauNode leaf, KripkeState state, List<ModalFormula> subformulae) {

        List<FormulaNode> newNodes = new ArrayList<>();
        TreePath path = leaf.getPath();
        int i = 0;

        for (ModalFormula subformula : subformulae) {

            FormulaNode newNode = new FormulaNode(state.clone(), subformula, path.clone().child(i));

            leaf.addChild(newNode);
            newNodes.add(newNode);
            i++;
        }

        return newNodes;
    }

    private List<FormulaNode> addChainedNodes(
            TableauNode leaf, KripkeState state, List<ModalFormula> subformulae) {

        List<FormulaNode> newNodes = new ArrayList<>();
        TableauNode lastNode = leaf;

        for (ModalFormula subformula : subformulae) {

            FormulaNode newNode =
                    new FormulaNode(state.clone(), subformula, lastNode.getPath().clone().child(0));

            lastNode.addChild(newNode);
            newNodes.add(newNode);
            lastNode = newNode;
        }

        return newNodes;
    }

    protected void closePaths(TableauNode node, KripkeStructure structure, boolean closed) {
        structure = structure.clone();
        if (node instanceof EdgeNode) {
            EdgeNode edgeNode = (EdgeNode) node;
            structure.addVertex(edgeNode.getChildState());
            structure.addEdge(edgeNode.getParentState(), edgeNode.getChildState().clone());
            closePaths(edgeNode.getChild(0), structure, closed);
        } else { // node instanceof FormulaNode
            FormulaNode formulaNode = (FormulaNode) node;
            KripkeState tempState = formulaNode.getState();
            assert (tempState != null);
            Vertex<KripkeState, EmptyEdgeLabel> v = structure.getVertex(tempState);
            assert (v != null);
            KripkeState state = v.get();
            assert (state != null);
            ModalFormula formula = formulaNode.getFormula();

            if (formula.isFalse()) closed = true;
            else if (formula.isTrue())
                ; // don't do anything
            else if (formula.isLiteral()) {
                Literal literal = new Literal(formula);
                Variable variable = literal.getVariable();
                Valuation valuation = state.getValuation();
                if (!valuation.isDefinedFor(variable))
                    valuation.define(variable, literal.isPositive());
                else closed |= (valuation.map(variable) != literal.isPositive());
            }

            if (formulaNode.isLeaf()) {
                if (closed) formulaNode.close();
                else formulaNode.setStructure(structure);
            } else
                for (TableauNode child : formulaNode.getChildren())
                    closePaths(child, structure, closed);
        }
    }

    protected List<TableauNode> getOpenLeaves() {
        List<TableauNode> leaves = this.root.getLeaves();
        List<TableauNode> openLeaves = Data.filter(leaves, node -> node.isOpen());
        return openLeaves;
    }

    public ModalTableau clone() {
        ModalTableau clone = new ModalTableau();
        clone.latestStateName = this.latestStateName.clone();
        clone.root = (FormulaNode) this.root.clone();
        clone.stateNamer = this.stateNamer;
        clone.nodesToProcess = (ArrayList<FormulaNode>) this.nodesToProcess.clone();
        return clone;
    }
}
