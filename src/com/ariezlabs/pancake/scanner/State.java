package com.ariezlabs.pancake.scanner;

import java.util.HashMap;

/**
 * @param <T> input type
 */
class State<T> {
    private HashMap<T, State<T>> transitions;
    private State<T> defaultTransition;
    private String label;
    private int type;

    State (String label, int type) {
        this.label = label;
        this.type = type;
        this.transitions = new HashMap<>();
    }

    /**
     * add specific transition on input to state if not yet present
     */
    void putTransition(T input, State<T> to) {
        assert transitions.putIfAbsent(input, to) == to : String.format("transition to state %s on %d already present", to.getLabel(), input);
    }

    /**
     * set a transition taken on any input, shadowed by specific transitions
     * @param defaultTransition state to transition to by default
     */
    void putDefaultTransition(State<T> defaultTransition) {
        assert this.defaultTransition == null : "cannot reassign default transition";
        this.defaultTransition = defaultTransition;
    }

    /**
     * @return state we transition to upon reading in, or null if none defined
     */
    State<T> getTransition(T in) {
        return transitions.getOrDefault(in, defaultTransition);
    }

    String getLabel() {
        return label;
    }

    void setLabel(String label) {
        this.label = label;
    }

    boolean isReadNext() {
        return type == 2;
    }

    boolean accepts() {
        return type >= 1;
    }

    int getType() {
        return type;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getLabel()).append(":").append(getType()).append(":");
        for (T key : transitions.keySet())
            sb.append(key.toString()).append("-").append(transitions.get(key).getLabel());
        return sb.toString();
    }
}
