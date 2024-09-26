package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class EquivalenceTransformations {

    public static InvertibleTransformation REPLACE_IMPLICATION_ALL;
    public static InvertibleTransformation REPLACE_IMPLICATION;
    public static InvertibleTransformation REPLACE_IMPLICATION_N;
    public static InvertibleTransformation CREATE_IMPLICATION_N;
    public static InvertibleTransformation CREATE_IMPLICATION;
    public static InvertibleTransformation CONTRAPOSITION;
    public static InvertibleTransformation REPLACE_EQUIVALENCE;
    public static InvertibleTransformation REPLACE_EQUIVALENCE_ALTERNATIVE;
    public static InvertibleMetaTransformation CREATE_EQUIVALENCE_N;
    public static InvertibleMetaTransformation CREATE_EQUIVALENCE;
    public static InvertibleTransformation REPLACE_EQUIVALENCE_N;
    public static InvertibleTransformation REPLACE_EQUIVALENCE_ALTERNATIVE_N;
    public static InvertibleTransformation SWAP_NEGATION_IN_EQUIVALENCE;
    public static InvertibleTransformation MOVE_NEGATION_INTO_EQUIVALENCE;

    public static InvertibleTransformation ADD_DOUBLE_NEGATION;
    public static InvertibleTransformation REMOVE_DOUBLE_NEGATION;

    public static InvertibleTransformation ADD_DOUBLE_NEGATION_N;
    public static InvertibleTransformation REMOVE_DOUBLE_NEGATION_N;

    public static InvertibleTransformation PUSH_NEGATION_OVER_BOX;
    public static InvertibleTransformation PULL_NEGATION_OVER_BOX;
    public static InvertibleTransformation PUSH_NEGATION_OVER_DIAMOND;
    public static InvertibleTransformation PULL_NEGATION_OVER_DIAMOND;
    public static InvertibleTransformation PULL_DUALITY;
    public static InvertibleMetaTransformation PUSH_DUALITY;

    public static SequentialTransformation PUSH_NEGATION_OVER_CONJUNCTION;
    public static SequentialTransformation PULL_NEGATION_OVER_CONJUNCTION;
    public static SequentialTransformation PUSH_NEGATION_OVER_DISJUNCTION;
    public static SequentialTransformation PULL_NEGATION_OVER_DISJUNCTION;
    public static MetaTransformation PUSH_DEMORGAN;
    public static MetaTransformation PULL_DEMORGAN;

    public static SequentialTransformation PUSH_NEGATION_OVER_DISJUNCTION_SHORTCUT;
    public static SequentialTransformation PULL_NEGATION_OVER_DISJUNCTION_SHORTCUT;
    public static SequentialTransformation PUSH_NEGATION_OVER_CONJUNCTION_SHORTCUT;
    public static SequentialTransformation PULL_NEGATION_OVER_CONJUNCTION_SHORTCUT;
    public static MetaTransformation PUSH_DEMORGAN_SHORTCUT;
    public static MetaTransformation PULL_DEMORGAN_SHORTCUT;

    public static InvertibleNAryTransformation PUSH_NEGATION_OVER_CONJUNCTION_N;
    public static NAryTransformation PULL_NEGATION_OVER_CONJUNCTION_N;
    public static InvertibleNAryTransformation PUSH_NEGATION_OVER_DISJUNCTION_N;
    public static NAryTransformation PULL_NEGATION_OVER_DISJUNCTION_N;
    public static NAryTransformation PUSH_DEMORGAN_N;
    public static NAryTransformation PULL_DEMORGAN_N;

    public static InvertibleTransformation COMMUTE_CONJUNCTION;
    public static InvertibleTransformation COMMUTE_DISJUNCTION;
    public static InvertibleTransformation COMMUTE;

    public static NAryTransformation COMMUTE_CONJUNCTION_N;
    public static NAryTransformation COMMUTE_DISJUNCTION_N;
    public static NAryTransformation COMMUTE_N;

    public static Transformation REMOVE_PARENTHESES_FROM_CONJUNCTION;
    public static Transformation REMOVE_PARENTHESES_FROM_DISJUNCTION;
    public static Transformation REMOVE_PARENTHESES;

    public static NAryTransformation REMOVE_PARENTHESES_FROM_CONJUNCTION_N;
    public static NAryTransformation REMOVE_PARENTHESES_FROM_DISJUNCTION_N;
    public static NAryTransformation REMOVE_PARENTHESES_N;

    public static SequentialTransformation DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION;
    public static Transformation UNDISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION;
    public static SequentialTransformation DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER;
    public static MetaTransformation DISTRIBUTE_LEFT_CONJUNCTION;
    public static SequentialTransformation UNDISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER;
    public static MetaTransformation UNDISTRIBUTE_LEFT_CONJUNCTION;
    public static SequentialTransformation DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER;
    public static MetaTransformation DISTRIBUTE_RIGHT_CONJUNCTION;
    public static SequentialTransformation UNDISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER;
    public static MetaTransformation UNDISTRIBUTE_RIGHT_CONJUNCTION;
    public static MetaTransformation DISTRIBUTE_CONJUNCTION;
    public static MetaTransformation UNDISTRIBUTE_CONJUNCTION;
    public static SequentialTransformation DISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION;
    public static SequentialTransformation UNDISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION;
    public static SequentialTransformation DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER;
    public static MetaTransformation DISTRIBUTE_LEFT_DISJUNCTION;
    public static SequentialTransformation UNDISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER;
    public static MetaTransformation UNDISTRIBUTE_LEFT_DISJUNCTION;
    public static SequentialTransformation DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER;
    public static MetaTransformation DISTRIBUTE_RIGHT_DISJUNCTION;
    public static SequentialTransformation UNDISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER;
    public static MetaTransformation UNDISTRIBUTE_RIGHT_DISJUNCTION;
    public static MetaTransformation DISTRIBUTE_DISJUNCTION;
    public static MetaTransformation UNDISTRIBUTE_DISJUNCTION;
    public static SequentialTransformation DISTRIBUTE_BOX_OVER_CONJUNCTION;
    public static SequentialTransformation UNDISTRIBUTE_BOX_OVER_CONJUNCTION;
    public static MetaTransformation DISTRIBUTE_BOX;
    public static MetaTransformation UNDISTRIBUTE_BOX;
    public static SequentialTransformation DISTRIBUTE_DIAMOND_OVER_DISJUNCTION;
    public static SequentialTransformation UNDISTRIBUTE_DIAMOND_OVER_DISJUNCTION;
    public static InvertibleTransformation DISTRIBUTE_DIAMOND_OVER_IMPLICATION;
    public static InvertibleTransformation UNDISTRIBUTE_DIAMOND_OVER_IMPLICATION;
    public static MetaTransformation DISTRIBUTE_DIAMOND;
    public static MetaTransformation UNDISTRIBUTE_DIAMOND;
    public static MetaTransformation DISTRIBUTE;
    public static MetaTransformation UNDISTRIBUTE;

    public static SequentialTransformation DISTRIBUTE_LEFT_PART_OF_DISJUNCTION;
    public static SequentialTransformation DISTRIBUTE_MIDDLE_PART_OF_DISJUNCTION;
    public static SequentialTransformation DISTRIBUTE_RIGHT_PART_OF_DISJUNCTION;
    public static MetaTransformation DISTRIBUTE_PART_OF_DISJUNCTION;

    public static SequentialTransformation DISTRIBUTE_LEFT_PART_OF_CONJUNCTION;
    public static SequentialTransformation DISTRIBUTE_MIDDLE_PART_OF_CONJUNCTION;
    public static SequentialTransformation DISTRIBUTE_RIGHT_PART_OF_CONJUNCTION;
    public static MetaTransformation DISTRIBUTE_PART_OF_CONJUNCTION;

    public static InvertibleNAryTransformation DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION_N;
    public static InvertibleNAryTransformation DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER_N;
    public static InvertibleNAryTransformation DISTRIBUTE_LEFT_CONJUNCTION_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_LEFT_CONJUNCTION_N;
    public static InvertibleNAryTransformation DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER_N;
    public static InvertibleNAryTransformation DISTRIBUTE_RIGHT_CONJUNCTION_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_RIGHT_CONJUNCTION_N;
    public static InvertibleNAryTransformation DISTRIBUTE_CONJUNCTION_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_CONJUNCTION_N;
    public static InvertibleNAryTransformation DISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION_N;
    public static InvertibleNAryTransformation DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER_N;
    public static InvertibleNAryTransformation DISTRIBUTE_LEFT_DISJUNCTION_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_LEFT_DISJUNCTION_N;
    public static InvertibleNAryTransformation DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER_N;
    public static InvertibleNAryTransformation DISTRIBUTE_RIGHT_DISJUNCTION_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_RIGHT_DISJUNCTION_N;
    public static InvertibleNAryTransformation DISTRIBUTE_DISJUNCTION_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_DISJUNCTION_N;
    public static InvertibleNAryTransformation DISTRIBUTE_N;
    public static InvertibleNAryTransformation UNDISTRIBUTE_N;

    public static InvertibleTransformation NEGATE_TRUE;
    public static InvertibleTransformation NEGATE_FALSE;
    public static InvertibleTransformation NEGATE_CONSTANT;
    public static InvertibleTransformation UNNEGATE_TRUE;
    public static InvertibleTransformation UNNEGATE_FALSE;
    public static InvertibleTransformation UNNEGATE_CONSTANT;

    public static MetaTransformation TAUTOLOGY;
    public static NAryTransformation TAUTOLOGY_N;
    public static MetaTransformation CONTRADICTION;
    public static NAryTransformation CONTRADICTION_N;

    public static Transformation NEUTRALITY_TOP_N;
    public static Transformation NEUTRALITY_BOTTOM_N;
    public static Transformation NEUTRALITY_TOP;
    public static Transformation NEUTRALITY_BOTTOM;
    public static MetaTransformation NEUTRALITY;
    public static Transformation DOMINATION_TOP;
    public static Transformation DOMINATION_BOTTOM;
    public static MetaTransformation DOMINATION;
    public static InvertibleTransformation IDEMPOTENCE_CONJUNCTION;
    public static InvertibleTransformation IDEMPOTENCE_DISJUNCTION;
    public static InvertibleTransformation IDEMPOTENCE;
    public static InvertibleTransformation ABSORPTION_CONJUNCTION;
    public static InvertibleTransformation ABSORPTION_DISJUNCTION;

    public static NAryTransformation IDEMPOTENCE_CONJUNCTION_N;
    public static NAryTransformation IDEMPOTENCE_DISJUNCTION_N;
    public static NAryTransformation IDEMPOTENCE_N;

    // Algorithms
    public static Transformation NEGATION_NORMALFORM;
    public static Transformation CONJUNCTIVE_NORMALFORM;
    public static Transformation DISJUNCTIVE_NORMALFORM;
    public static Transformation SANITIZED_FORM;

    static {
        initialize();
    }

    public static void initialize() {
        // REPLACE IMPLICATION/EQUIVALENCE --------------------------
        REPLACE_IMPLICATION = invertibleTransformation("$X → $Y", "¬$X ∨ $Y");
        REPLACE_IMPLICATION_N = invertibleTransformationN("$X → $Y", "¬$X ∨ $Y");

        REPLACE_IMPLICATION_ALL =
                meta(
                        invertibleTransformation("*U∧($X → $Y)∧*V", "*U∧(¬$X ∨ $Y)∧*V"),
                        invertibleTransformation("*U∨($X → $Y)∨*V", "*U∨(¬$X ∨ $Y)∨*V"),
                        REPLACE_IMPLICATION);

        CREATE_IMPLICATION = REPLACE_IMPLICATION.getInverse();
        CREATE_IMPLICATION_N = REPLACE_IMPLICATION_N.getInverse();
        CONTRAPOSITION = invertibleTransformation("$X → $Y", "¬$Y → ¬$X");
        REPLACE_EQUIVALENCE = invertibleTransformation("$X ↔ $Y", "($X∧$Y) ∨ (¬$X∧¬$Y)");
        REPLACE_EQUIVALENCE_ALTERNATIVE = invertibleTransformation("$X ↔ $Y", "($X→$Y) ∧ ($Y→$X)");
        REPLACE_EQUIVALENCE_N = invertibleTransformationN("$X ↔ $Y", "($X∧$Y) ∨ (¬$X∧¬$Y)");
        REPLACE_EQUIVALENCE_ALTERNATIVE_N =
                invertibleTransformationN("$X ↔ $Y", "($X→$Y) ∧ ($Y→$X)");
        CREATE_EQUIVALENCE =
                new InvertibleMetaTransformation(
                        REPLACE_EQUIVALENCE.getInverse(),
                        REPLACE_EQUIVALENCE_ALTERNATIVE.getInverse());
        SWAP_NEGATION_IN_EQUIVALENCE =
                meta(
                        invertibleTransformation("$X↔¬$Y", "¬$X↔$Y"),
                        invertibleTransformation("¬$X↔$Y", "$X↔¬$Y"));
        MOVE_NEGATION_INTO_EQUIVALENCE =
                meta(
                        invertibleTransformation("¬($X↔$Y)", "¬$X↔$Y"),
                        invertibleTransformation("¬$X↔$Y", "¬($X↔$Y)"));

        // DOUBLE NEGATION ------------------------------------------
        ADD_DOUBLE_NEGATION = invertibleTransformation("$X", "¬¬$X");
        REMOVE_DOUBLE_NEGATION = ADD_DOUBLE_NEGATION.getInverse();

        ADD_DOUBLE_NEGATION_N = invertibleTransformationN("$X", "¬¬$X");
        REMOVE_DOUBLE_NEGATION_N = ADD_DOUBLE_NEGATION_N.getInverse();

        // DUALITY OF MODAL OPERATORS -------------------------------
        PUSH_NEGATION_OVER_BOX = invertibleTransformation("¬☐$X", "◇¬$X");
        PULL_NEGATION_OVER_BOX = PUSH_NEGATION_OVER_BOX.getInverse();
        PUSH_NEGATION_OVER_DIAMOND = invertibleTransformation("¬◇$X", "☐¬$X");
        PULL_NEGATION_OVER_DIAMOND = PUSH_NEGATION_OVER_DIAMOND.getInverse();
        PUSH_DUALITY = meta(PUSH_NEGATION_OVER_BOX, PUSH_NEGATION_OVER_DIAMOND);
        PULL_DUALITY = PUSH_DUALITY.getInverse();

        // DUALITY BY DE MORGAN -------------------------------------
        // ¬(*X∧...) -> (*X¬$X∨...)
        PUSH_NEGATION_OVER_CONJUNCTION =
                sequential(
                        invertibleTransformation("¬(*X∧...)", "(*X∨...)"),
                        childrenPattern("($*∨...)", "$X", "¬$X"));
        // (*X¬$X∨...) -> ¬(*X∧...)
        PULL_NEGATION_OVER_DISJUNCTION =
                sequential(
                        childrenPattern("((¬$)*∨...)", "¬$X", "$X"),
                        invertibleTransformation("(*X∨...)", "¬(*X∧...)"));
        // ¬(*X∨...) -> (*X¬$X∧...)
        PUSH_NEGATION_OVER_DISJUNCTION =
                sequential(
                        invertibleTransformation("¬(*X∨...)", "(*X∧...)"),
                        childrenPattern("($*∧...)", "$X", "¬$X"));
        // (*X¬$X∧...) -> ¬(*X∨...)
        PULL_NEGATION_OVER_CONJUNCTION =
                sequential(
                        childrenPattern("((¬$)*∧...)", "¬$X", "$X"),
                        invertibleTransformation("(*X∧...)", "¬(*X∨...)"));
        PUSH_DEMORGAN = meta(PUSH_NEGATION_OVER_CONJUNCTION, PUSH_NEGATION_OVER_DISJUNCTION);
        PULL_DEMORGAN = meta(PULL_NEGATION_OVER_CONJUNCTION, PULL_NEGATION_OVER_DISJUNCTION);

        // (*X∧...) -> ¬(*X¬$X∨...)
        PUSH_NEGATION_OVER_CONJUNCTION_SHORTCUT =
                sequential(
                        childrenPattern("($*∧...)", "$X", "¬$X"),
                        invertibleTransformation("(*X∧...)", "¬(*X∨...)"));
        // ¬(*X¬$X∨...) -> (*X∧...)
        PULL_NEGATION_OVER_DISJUNCTION_SHORTCUT =
                sequential(
                        invertibleTransformation("¬(*X∨...)", "(*X∧...)"),
                        childrenPattern("((¬$)*∧...)", "¬$X", "$X"));
        // (*X∨...) -> ¬(*X¬$X∧...)
        PUSH_NEGATION_OVER_DISJUNCTION_SHORTCUT =
                sequential(
                        childrenPattern("($*∨...)", "$X", "¬$X"),
                        invertibleTransformation("(*X∨...)", "¬(*X∧...)"));
        // ¬(*X¬$X∧...) -> (*X∨...)
        PULL_NEGATION_OVER_CONJUNCTION_SHORTCUT =
                sequential(
                        invertibleTransformation("¬(*X∧...)", "(*X∨...)"),
                        childrenPattern("((¬$)*∨...)", "¬$X", "$X"));
        PUSH_DEMORGAN_SHORTCUT =
                meta(
                        PUSH_NEGATION_OVER_CONJUNCTION_SHORTCUT,
                        PUSH_NEGATION_OVER_DISJUNCTION_SHORTCUT);
        PULL_DEMORGAN_SHORTCUT =
                meta(
                        PULL_NEGATION_OVER_CONJUNCTION_SHORTCUT,
                        PULL_NEGATION_OVER_DISJUNCTION_SHORTCUT);

        // not possible with reworked patterns (not needed?)
        //		PUSH_NEGATION_OVER_CONJUNCTION_N = invertibleTransformationN("¬(+*X∧...)",
        // "(*X¬+$X∨...)");
        //		PULL_NEGATION_OVER_DISJUNCTION_N = PUSH_NEGATION_OVER_CONJUNCTION_N.getInverse();
        //		PUSH_NEGATION_OVER_DISJUNCTION_N = invertibleTransformationN("¬(+*X∨...)",
        // "(*X¬+$X∧...)");
        //		PULL_NEGATION_OVER_CONJUNCTION_N = PUSH_NEGATION_OVER_DISJUNCTION_N.getInverse();
        //		PUSH_DEMORGAN_N = meta(PUSH_NEGATION_OVER_CONJUNCTION_N,
        // PUSH_NEGATION_OVER_DISJUNCTION_N);
        //		PULL_DEMORGAN_N = meta(PULL_NEGATION_OVER_CONJUNCTION_N,
        // PULL_NEGATION_OVER_DISJUNCTION_N);

        // COMMUTATION (binary) -------------------------------------
        COMMUTE_CONJUNCTION = invertibleTransformation("$X∧$Y", "$Y∧$X");
        //		COMMUTE_CONJUNCTION_N = invertibleTransformationN("*X∧+$U∧*Y∧+$V∧*Z",
        // "*X∧+$V∧*Y∧+$U∧*Z");
        COMMUTE_DISJUNCTION = invertibleTransformation("$X∨$Y", "$Y∨$X");
        //		COMMUTE_DISJUNCTION_N = invertibleTransformationN("*X∨+$U∨*Y∨+$V∨*Z",
        // "*X∨+$V∨*Y∨+$U∨*Z");
        COMMUTE = meta(COMMUTE_CONJUNCTION, COMMUTE_DISJUNCTION);
        //		COMMUTE_N = meta(COMMUTE_CONJUNCTION_N, COMMUTE_DISJUNCTION_N);

        // ASSOCIATIVITY --------------------------------------------
        REMOVE_PARENTHESES_FROM_CONJUNCTION = transformation("*X∧(*Y∧...)∧*Z", "*X∧*Y∧*Z");
        //		REMOVE_PARENTHESES_FROM_CONJUNCTION_N = transformationN("*X∧(+*Y∧...)∧*Z", "*X∧+*Y∧*Z");
        REMOVE_PARENTHESES_FROM_DISJUNCTION = transformation("*X∨(*Y∨...)∨*Z", "*X∨*Y∨*Z");
        //		REMOVE_PARENTHESES_FROM_DISJUNCTION_N = transformationN("*X∨(+*Y∨...)∨*Z", "*X∨+*Y∨*Z");

        REMOVE_PARENTHESES =
                meta(REMOVE_PARENTHESES_FROM_CONJUNCTION, REMOVE_PARENTHESES_FROM_DISJUNCTION);
        //		REMOVE_PARENTHESES_N = meta(REMOVE_PARENTHESES_FROM_CONJUNCTION_N,
        // REMOVE_PARENTHESES_FROM_DISJUNCTION_N);

        // DISTRIBUTION ---------------------------------------------
        // Conjunction ....................................
        // (*X∧...) ∨ $Y -> *X($X∨$Y)∧...
        DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER =
                sequential(
                        childrenPattern(new TreePath().child(0), "($*∧...) ∨ $Y", "$X", "$X∨$Y"),
                        transformation("(*X∧...)∨$", "(*X∧...)"));

        // *X($X∨$Y)∧... -> (*X∧...) ∨ $Y
        UNDISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER =
                sequential(
                        invertibleTransformation("X@(($∨$Y)*∧...)", "$X∨$Y"),
                        childrenPattern(
                                new TreePath().child(0), "(($∨$)*∧...) ∨ $Y", "$X∨$Y", "$X"));

        // $X ∨ (*Y∧...) -> *Y($X∨$Y)∧...
        DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER =
                sequential(
                        childrenPattern(new TreePath().child(1), "$X ∨ ($*∧...)", "$Y", "$X∨$Y"),
                        transformation("$∨(*Y∧...)", "(*Y∧...)"));

        // *Y($X∨$Y)∧... -> $X ∨ (*Y∧...)
        UNDISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER =
                sequential(
                        invertibleTransformation("Y@(($X∨$)*∧...)", "$X∨$Y"),
                        childrenPattern(
                                new TreePath().child(1), "$X ∨ (($∨$)*∧...)", "$X∨$Y", "$Y"));

        // (*X∧...) ∨ (*Y∧...) -> (*X*Y($X∨$Y))∧...
        DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION =
                sequential(
                        guard("($*∧...)∨($*∧...)"),
                        DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER,
                        children(DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER),
                        fixpoint(REMOVE_PARENTHESES));

        // (*X*Y($X∨$Y))∧... -> (*X∧...) ∨ (*Y∧...)
        UNDISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION =
                sequential(
                        targetedIteration(
                                "(($∨$)*∧...)*∧...",
                                transformation(
                                        "A@(($∨$)*∧...)*∧Y@($X∨$)∧Z@($X∨$)*∧B@($∨$)*",
                                        "$A∧($Y∧*Z)∧*B")),
                        children(UNDISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER),
                        UNDISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER);

        DISTRIBUTE_LEFT_CONJUNCTION =
                meta(
                        DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION,
                        DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER);
        UNDISTRIBUTE_LEFT_CONJUNCTION =
                meta(
                        UNDISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION,
                        UNDISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER);

        DISTRIBUTE_RIGHT_CONJUNCTION =
                meta(
                        DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION,
                        DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER);
        UNDISTRIBUTE_RIGHT_CONJUNCTION =
                meta(
                        UNDISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION,
                        UNDISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER);

        DISTRIBUTE_CONJUNCTION = meta(DISTRIBUTE_LEFT_CONJUNCTION, DISTRIBUTE_RIGHT_CONJUNCTION);
        // Sanitizing should be down afterwards
        UNDISTRIBUTE_CONJUNCTION =
                meta(UNDISTRIBUTE_LEFT_CONJUNCTION, UNDISTRIBUTE_RIGHT_CONJUNCTION);

        // Disjunction ....................................
        // (*X∨...) ∧ $Y -> *X($X∧$Y)∨...
        DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER =
                sequential(
                        childrenPattern(new TreePath().child(0), "($*∨...) ∧ $Y", "$X", "$X∧$Y"),
                        transformation("(*X∨...)∧$", "(*X∨...)"));

        // *X($X∧$Y)∨... -> (*X∨...) ∧ $Y
        UNDISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER =
                sequential(
                        invertibleTransformation("X@(($∧$Y)*∨...)", "$X∧$Y"),
                        childrenPattern(
                                new TreePath().child(0), "(($∧$)*∨...) ∧ $Y", "$X∧$Y", "$X"));

        // $X ∧ (*Y∨...) -> *Y($X∧$Y)∨...
        DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER =
                sequential(
                        childrenPattern(new TreePath().child(1), "$X ∧ ($*∨...)", "$Y", "$X∧$Y"),
                        transformation("$∧(*Y∨...)", "(*Y∨...)"));

        // *Y($X∧$Y)∨... -> $X ∧ (*Y∨...)
        UNDISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER =
                sequential(
                        invertibleTransformation("Y@(($X∧$)*∨...)", "$X∧$Y"),
                        childrenPattern(
                                new TreePath().child(1), "$X ∧ (($∧$)*∨...)", "$X∧$Y", "$Y"));

        // (*X∨...) ∧ (*Y∨...) -> (*X*Y($X∧$Y))∨...
        DISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION =
                sequential(
                        guard("($*∨...)∧($*∨...)"),
                        DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER,
                        children(DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER),
                        fixpoint(REMOVE_PARENTHESES_FROM_DISJUNCTION));

        // (*X*Y($X∧$Y))∨... -> (*X∨...) ∧ (*Y∨...)
        UNDISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION =
                sequential(
                        targetedIteration(
                                "(($∧$)*∨...)*∨...",
                                transformation(
                                        "A@(($∧$)*∨...)*∨Y@($X∧$)∨Z@($X∧$)*∨B@($∧$)*",
                                        "$A∨($Y∨*Z)∨*B")),
                        children(UNDISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER),
                        UNDISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER);

        DISTRIBUTE_LEFT_DISJUNCTION =
                meta(
                        DISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION,
                        DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER);
        UNDISTRIBUTE_LEFT_DISJUNCTION =
                meta(
                        UNDISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION,
                        UNDISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER);

        DISTRIBUTE_RIGHT_DISJUNCTION =
                meta(
                        DISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION,
                        DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER);
        UNDISTRIBUTE_RIGHT_DISJUNCTION =
                meta(
                        UNDISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION,
                        UNDISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER);

        DISTRIBUTE_DISJUNCTION = meta(DISTRIBUTE_LEFT_DISJUNCTION, DISTRIBUTE_RIGHT_DISJUNCTION);
        UNDISTRIBUTE_DISJUNCTION =
                meta(UNDISTRIBUTE_LEFT_DISJUNCTION, UNDISTRIBUTE_RIGHT_DISJUNCTION);

        // Box ...........................................
        // ☐(*Y∧...) -> *Y(☐$Y)∧...
        DISTRIBUTE_BOX_OVER_CONJUNCTION =
                sequential(
                        invertibleTransformation("☐(*Y∧...)", "(*Y∧...)"),
                        childrenPattern("($*∧...)", "$Y", "☐$Y"));

        // *Y(☐$Y)∧... -> ☐(*Y∧...)
        UNDISTRIBUTE_BOX_OVER_CONJUNCTION =
                sequential(
                        childrenPattern("((☐$)*∧...)", "☐$Y", "$Y"),
                        invertibleTransformation("(*Y∧...)", "☐(*Y∧...)"));

        DISTRIBUTE_BOX = meta(DISTRIBUTE_BOX_OVER_CONJUNCTION);
        UNDISTRIBUTE_BOX = meta(UNDISTRIBUTE_BOX_OVER_CONJUNCTION);

        // ◇(*Y∨...) -> *Y(◇$Y)∨...
        DISTRIBUTE_DIAMOND_OVER_DISJUNCTION =
                sequential(
                        invertibleTransformation("◇(*Y∨...)", "(*Y∨...)"),
                        childrenPattern("($*∨...)", "$Y", "◇$Y"));

        // *Y(◇$Y)∨... -> ◇(*Y∨...)
        UNDISTRIBUTE_DIAMOND_OVER_DISJUNCTION =
                sequential(
                        childrenPattern("((◇$)*∨...)", "◇$Y", "$Y"),
                        invertibleTransformation("(*Y∨...)", "◇(*Y∨...)"));

        DISTRIBUTE_DIAMOND_OVER_IMPLICATION = invertibleTransformation("◇($X→$Y)", "☐$X→◇$Y");
        UNDISTRIBUTE_DIAMOND_OVER_IMPLICATION = DISTRIBUTE_DIAMOND_OVER_IMPLICATION.getInverse();

        DISTRIBUTE_DIAMOND =
                meta(DISTRIBUTE_DIAMOND_OVER_DISJUNCTION, DISTRIBUTE_DIAMOND_OVER_IMPLICATION);
        UNDISTRIBUTE_DIAMOND =
                meta(UNDISTRIBUTE_DIAMOND_OVER_DISJUNCTION, UNDISTRIBUTE_DIAMOND_OVER_IMPLICATION);

        DISTRIBUTE =
                meta(
                        DISTRIBUTE_CONJUNCTION,
                        DISTRIBUTE_DISJUNCTION,
                        DISTRIBUTE_BOX,
                        DISTRIBUTE_DIAMOND);
        UNDISTRIBUTE =
                meta(
                        UNDISTRIBUTE_CONJUNCTION,
                        UNDISTRIBUTE_DISJUNCTION,
                        UNDISTRIBUTE_BOX,
                        UNDISTRIBUTE_DIAMOND);

        // transformations needed for normal form algorithms
        DISTRIBUTE_LEFT_PART_OF_DISJUNCTION =
                sequential(
                        transformation("(*X∧...)∨$Y∨*Z", "((*X∧...)∨$Y)∨*Z"),
                        DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER.forPath(new TreePath().child(0)));

        DISTRIBUTE_MIDDLE_PART_OF_DISJUNCTION =
                sequential(
                        transformation("*A∨$X∨(*Y∧...)∨*Z", "*A∨($X∨(*Y∧...))∨*Z"),
                        children(optional(DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER)));

        DISTRIBUTE_RIGHT_PART_OF_DISJUNCTION =
                sequential(
                        transformation("*X∨$Y∨(*Z∧...)", "*X∨($Y∨(*Z∧...))"),
                        children(optional(DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER)));

        DISTRIBUTE_PART_OF_DISJUNCTION =
                meta(
                        DISTRIBUTE_LEFT_PART_OF_DISJUNCTION,
                        DISTRIBUTE_MIDDLE_PART_OF_DISJUNCTION,
                        DISTRIBUTE_RIGHT_PART_OF_DISJUNCTION);

        DISTRIBUTE_LEFT_PART_OF_CONJUNCTION =
                sequential(
                        transformation("(*X∨...)∧$Y∧*Z", "((*X∨...)∧$Y)∧*Z"),
                        DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER.forPath(new TreePath().child(0)));
        DISTRIBUTE_MIDDLE_PART_OF_CONJUNCTION =
                sequential(
                        transformation("*A∧$X∧(*Y∨...)∧*Z", "*A∧($X∧(*Y∨...))∧*Z"),
                        children(optional(DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER)));
        DISTRIBUTE_RIGHT_PART_OF_CONJUNCTION =
                sequential(
                        transformation("*X∧$Y∧(*Z∨...)", "*X∧($Y∧(*Z∨...))"),
                        children(optional(DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER)));

        DISTRIBUTE_PART_OF_CONJUNCTION =
                meta(
                        DISTRIBUTE_LEFT_PART_OF_CONJUNCTION,
                        DISTRIBUTE_MIDDLE_PART_OF_CONJUNCTION,
                        DISTRIBUTE_RIGHT_PART_OF_CONJUNCTION);

        // n-ary
        // Conjunction ....................................
        //		DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION_N = invertibleTransformationN("(*X∧...) ∨
        // (*Y∧...)",
        //				"(*X*Y($X∨$Y))∧...");
        //		// UNDISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION_N =
        //		// DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION_N.getInverse();
        //
        //		DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER_N = invertibleTransformationN("(*X∧...) ∨ $Y",
        // "*X($X∨$Y)∧...");
        //
        //		// UNDISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER_N =
        //		// DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER_N.getInverse();
        //		DISTRIBUTE_LEFT_CONJUNCTION_N = meta(DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION_N,
        //				DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER_N);
        //		UNDISTRIBUTE_LEFT_CONJUNCTION_N = DISTRIBUTE_LEFT_CONJUNCTION_N.getInverse();
        //
        //		DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER_N = invertibleTransformationN("$X ∨ (*Y∧...)",
        // "*Y($X∨$Y)∧...");
        //		UNDISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER_N =
        // DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER_N.getInverse();
        //		DISTRIBUTE_RIGHT_CONJUNCTION_N = meta(DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION_N,
        //				DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER_N);
        //		UNDISTRIBUTE_RIGHT_CONJUNCTION_N = DISTRIBUTE_RIGHT_CONJUNCTION_N.getInverse();
        //
        //		DISTRIBUTE_CONJUNCTION_N = meta(DISTRIBUTE_LEFT_CONJUNCTION_N,
        // DISTRIBUTE_RIGHT_CONJUNCTION_N);
        //		UNDISTRIBUTE_CONJUNCTION_N = DISTRIBUTE_CONJUNCTION_N.getInverse();
        //
        //		// Disjunction ....................................
        //		DISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION_N = invertibleTransformationN("(*X∨...) ∧
        // (*Y∨...)",
        //				"(*X*Y($X∧$Y))∨...");
        //		UNDISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION_N =
        // DISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION_N.getInverse();
        //
        //		DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER_N = invertibleTransformationN("(*X∨...) ∧ $Y",
        // "*X($X∧$Y)∨...");
        //
        //		UNDISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER_N =
        // DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER_N.getInverse();
        //		DISTRIBUTE_LEFT_DISJUNCTION_N = meta(DISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION_N,
        //				DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER_N);
        //		UNDISTRIBUTE_LEFT_DISJUNCTION_N = DISTRIBUTE_LEFT_DISJUNCTION_N.getInverse();
        //
        //		DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER_N = invertibleTransformationN("$X ∧ (*Y∨...)",
        // "*Y($X∧$Y)∨...");
        //		UNDISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER_N =
        // DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER_N.getInverse();
        //		DISTRIBUTE_RIGHT_DISJUNCTION_N = meta(DISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION_N,
        //				DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER_N);
        //		UNDISTRIBUTE_RIGHT_DISJUNCTION_N = DISTRIBUTE_RIGHT_DISJUNCTION_N.getInverse();
        //
        //		DISTRIBUTE_DISJUNCTION_N = meta(DISTRIBUTE_LEFT_DISJUNCTION_N,
        // DISTRIBUTE_RIGHT_DISJUNCTION_N);
        //		UNDISTRIBUTE_DISJUNCTION_N = DISTRIBUTE_DISJUNCTION_N.getInverse();
        //
        //		DISTRIBUTE_N = meta(DISTRIBUTE_CONJUNCTION_N, DISTRIBUTE_DISJUNCTION_N);
        //		UNDISTRIBUTE_N = DISTRIBUTE_N.getInverse();
        //
        // Negate/unnegate constants --------------------------------
        NEGATE_TRUE = invertibleTransformation("⊤", "¬⊥");
        NEGATE_FALSE = invertibleTransformation("⊥", "¬⊤");
        NEGATE_CONSTANT = meta(NEGATE_TRUE, NEGATE_FALSE);
        UNNEGATE_FALSE = NEGATE_TRUE.getInverse();
        UNNEGATE_TRUE = NEGATE_FALSE.getInverse();
        UNNEGATE_CONSTANT = NEGATE_CONSTANT.getInverse();

        // public static InvertibleTransformation ABSORPTION_CONJUNCTION;
        // public static InvertibleTransformation ABSORPTION_DISJUNCTION;
        TAUTOLOGY =
                meta(
                        transformation("*X ∨ $U ∨ *Y ∨ ¬$U ∨ *Z", "⊤"),
                        transformation("*X ∨ ¬$U ∨ *Y ∨ $U ∨ *Z", "⊤"));
        //		TAUTOLOGY_N = meta(invertibleTransformationN("*X∨+$U∨*Y∨¬+$U∨*Z", "*X∨ ⊤∨*Y∨*Z"),
        //				invertibleTransformationN("*X∨¬+$U∨*Y∨+$U∨*Z", "*X∨ ⊤∨*Y∨*Z"));
        CONTRADICTION =
                meta(
                        transformation("*X ∧ $U ∧ *Y ∧ ¬$U ∧ *Z", "⊥"),
                        transformation("*X ∧ ¬$U ∧ *Y ∧ $U ∧ *Z", "⊥"));
        //		CONTRADICTION_N = meta(transformationN("*X∧+$U∧*Y∧¬+$U∧*Z", "*X∧⊥∧*Y∧*Z"),
        //				transformationN("*X∧¬+$U∧*Y∧+$U∧*Z", "*X∧⊥∧*Y∧*Z"));

        //		NEUTRALITY_TOP_N = meta(transformationN("*X∧+$U∧*Y∧⊤∧*Z", "*X∧+$U∧*Y∧*Z"),
        //				transformationN("*X∧⊤∧*Y∧+$U∧*Z", "*X∧*Y∧+$U∧*Z"));
        //		NEUTRALITY_BOTTOM_N = meta(transformationN("*X∨+$U∨*Y∨⊥∨*Z", "*X∨+$U∨*Y∨*Z"),
        //				transformationN("*X∨⊥∨*Y∨+$U∨*Z", "*X∨*Y∨+$U∨*Z"));

        NEUTRALITY_TOP = transformation("*X∧⊤∧*Y", "*X∧*Y");
        NEUTRALITY_BOTTOM = transformation("*X∨⊥∨*Y", "*X∨*Y");
        NEUTRALITY = meta(NEUTRALITY_TOP, NEUTRALITY_BOTTOM);
        DOMINATION_TOP = transformation("*X∨⊤∨*Y", "⊤");
        DOMINATION_BOTTOM = transformation("*X∧⊥∧*Y", "⊥");
        DOMINATION = meta(DOMINATION_TOP, DOMINATION_BOTTOM);

        IDEMPOTENCE_CONJUNCTION =
                invertibleTransformation("*X ∧ $U ∧ *Y ∧ $U ∧ *Z", "*X ∧ $U ∧ *Y ∧ *Z");
        //		IDEMPOTENCE_CONJUNCTION_N = transformationN("*X ∧ +$U ∧ *Y ∧ +$U ∧ *Z", "*X ∧ $U ∧ *Y ∧
        // *Z");
        IDEMPOTENCE_DISJUNCTION =
                invertibleTransformation("*X ∨ $U ∨ *Y ∨ $U ∨ *Z", "*X ∨ $U ∨ *Y ∨ *Z");
        //		IDEMPOTENCE_DISJUNCTION_N = transformationN("*X ∨ +$U ∨ *Y ∨ +$U ∨ *Z", "*X ∨ $U ∨ *Y ∨
        // *Z");
        IDEMPOTENCE = meta(IDEMPOTENCE_CONJUNCTION, IDEMPOTENCE_DISJUNCTION);
        //		IDEMPOTENCE_N = meta(IDEMPOTENCE_CONJUNCTION_N, IDEMPOTENCE_DISJUNCTION_N);

        // ALGORITHMS =========================================================
        NEGATION_NORMALFORM =
                new FixpointAlgorithm(
                        meta(
                                REPLACE_IMPLICATION,
                                REPLACE_EQUIVALENCE,
                                REMOVE_DOUBLE_NEGATION,
                                PUSH_DUALITY,
                                PUSH_DEMORGAN,
                                UNNEGATE_CONSTANT));

        CONJUNCTIVE_NORMALFORM =
                new FixpointAlgorithm(
                        meta(
                                NEGATION_NORMALFORM,
                                NEUTRALITY,
                                DOMINATION,
                                DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER,
                                DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER,
                                REMOVE_PARENTHESES,
                                DISTRIBUTE_PART_OF_DISJUNCTION));

        DISJUNCTIVE_NORMALFORM =
                new FixpointAlgorithm(
                        meta(
                                NEGATION_NORMALFORM,
                                NEUTRALITY,
                                DOMINATION,
                                DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER,
                                DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER,
                                REMOVE_PARENTHESES,
                                DISTRIBUTE_PART_OF_CONJUNCTION));

        // not to be used alone, used in SanitizeFormula
        SANITIZED_FORM =
                new FixpointAlgorithm(
                        meta(
                                REMOVE_DOUBLE_NEGATION,
                                UNNEGATE_CONSTANT,
                                NEUTRALITY,
                                DOMINATION,
                                REMOVE_PARENTHESES));
    }

    private static MetaTransformation meta(Transformation... transformations) {
        return new MetaTransformation(transformations);
    }

    private static InvertibleMetaTransformation meta(InvertibleTransformation... transformations) {
        return new InvertibleMetaTransformation(transformations);
    }

    private static NAryMetaTransformation meta(NAryTransformation... transformations) {
        return new NAryMetaTransformation(transformations);
    }

    private static InvertibleNAryMetaTransformation meta(
            InvertibleNAryTransformation... transformations) {
        return new InvertibleNAryMetaTransformation(transformations);
    }

    private static InvertibleTransformation invertibleTransformation(
            final String match, final String replace) {
        return new UnaryInvertiblePatternTransformation(match, replace);
    }

    private static Transformation transformation(final String match, final String replace) {
        return new UnaryPatternTransformation(match, replace);
    }

    private static InvertibleNAryPatternTransformation invertibleTransformationN(
            final String match, final String replace) {
        return new InvertibleNAryPatternTransformation(match, replace);
    }

    private static NAryPatternTransformation transformationN(
            final String match, final String replace) {
        return new NAryPatternTransformation(match, replace);
    }

    private static SequentialTransformation sequential(Transformation... transformations) {
        return new SequentialTransformation(transformations);
    }

    private static ChildrenTransformation children(Transformation transformation) {
        return new ChildrenTransformation(transformation);
    }

    private static ChildrenPatternTransformation childrenPattern(
            String outer, String match, String replace) {
        return new ChildrenPatternTransformation(outer, match, replace);
    }

    private static ChildrenPatternTransformation childrenPattern(
            TreePath path, String outer, String match, String replace) {

        return new ChildrenPatternTransformation(path, outer, match, replace);
    }

    private static FixpointAlgorithm fixpoint(Transformation transformation) {
        return new FixpointAlgorithm(transformation);
    }

    private static TargetedIterationAlgorithm targetedIteration(
            String target, Transformation transformation) {
        return new TargetedIterationAlgorithm(target, transformation);
    }

    private static MetaTransformation optional(Transformation transformation) {
        return new MetaTransformation(transformation, transformation("$X", "$X"));
    }

    /**
     * @param guard should not contain the pattern name X
     * @return
     */
    private static Transformation guard(String guard) {
        return transformation("X@(" + guard + ")", "$X");
    }
}
