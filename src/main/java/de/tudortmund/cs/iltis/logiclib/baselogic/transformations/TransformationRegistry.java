package de.tudortmund.cs.iltis.logiclib.baselogic.transformations;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.*;

public class TransformationRegistry<T extends Tree<T>> {

    public TransformationRegistry() {
        transformationsByName = new HashMap<>();
        transformationGroupsByName = new HashMap<>();
    }

    private Map<String, Transformation<T>> transformationsByName;

    private Map<String, List<Transformation<T>>> transformationGroupsByName;

    public boolean has(String name) {
        return transformationsByName.containsKey(name);
    }

    public Transformation<T> get(String name) {
        return transformationsByName.getOrDefault(name, null);
    }

    public void add(String name, Transformation<T> t) {
        if (has(name)) {
            throw new IllegalArgumentException("Transformation '" + name + "' already registered");
        } else {
            transformationsByName.put(name, t);
        }
    }

    public boolean hasGroup(String groupName) {
        return transformationGroupsByName.containsKey(groupName);
    }

    public List<Transformation<T>> getGroup(String groupName) {
        return transformationGroupsByName.getOrDefault(groupName, null);
    }

    public void addToGroup(String groupName, Transformation<T> t) {
        if (hasGroup(groupName)) {
            List<Transformation<T>> currentGroup = this.getGroup(groupName);
            currentGroup.add(t);
        } else {
            List<Transformation<T>> newGroup = new ArrayList<>();
            newGroup.add(t);
            transformationGroupsByName.put(groupName, newGroup);
        }
    }
}
