package com.cobaltgroup.inventoryadmin.vo;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Trie nodes are responsible for holding a character and children nodes
 * associated with it. They are used to create a trie structure that can
 * be used to evaluate string completions for a given search query.
 *
 */
public class TrieNode {

    private final Character character; // Saved letter

    private final HashMap<Character, TrieNode> children; // Children of node

    /**
     * Initializes node with given character and creates a map to store children
     *
     * @param c The character in which to store
     */
    public TrieNode(char c) {
        super();
        character = Character.valueOf(c);
        children = new HashMap<Character, TrieNode>();
    }

    /**
     * Gets the character contained by this node
     *
     * @return Character contained in this node
     */
    public char getNodeValue() {
        return character.charValue();
    }

    /**
     * @return Map of children nodes
     */
    public Collection<TrieNode> getChildren() {
        return children.values();
    }

    /**
     * @return Values of children nodes
     */
    public Set<Character> getChildrenNodeValues() {
        return children.keySet();
    }

    /**
     * Adds a child node to the map of children
     *
     * @param c The character of the new child
     */
    public void add(char c) {
        if (children.get(Character.valueOf(c)) == null) {
            // children does not contain c, add a TrieNode
            children.put(Character.valueOf(c), new TrieNode(c));
        }
    }

    /**
     * Searches the children nodes to find a given character
     *
     * @param c Character in which to search for
     * @return The node of the given child
     */
    public TrieNode getChildNode(char c) {
        return children.get(Character.valueOf(c));
    }

    /**
     * Checks whether or not a given character is contained by any of the
     * children nodes
     *
     * @param c Character in which to search for
     * @return True if there is a node that contains the character, false
     *         otherwise
     */
    public boolean contains(char c) {
        return children.get(Character.valueOf(c)) != null;
    }

    /**
     * @return Integer hash code for the character contained in this node
     */
    @Override
    public int hashCode() {
        return character.hashCode();
    }

    /**
     * Compares this node to another based on the character they each contain
     *
     * @return True if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TrieNode)) {
            return false;
        }
        TrieNode that = (TrieNode) obj;
        return getNodeValue() == that.getNodeValue();
    }

    /**
     * @return The string representation of this node
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}