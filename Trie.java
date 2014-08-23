package com.cobaltgroup.inventoryadmin.vo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Trie data structure links TrieNodes containing individual characters to
 * create a tree-like structure that can be used to keep track of word
 * completions
 *
 */
public class Trie {

    private final TrieNode rootNode; // Root of trie

    /**
     * Construct trie with space character as root
     */
    public Trie() {
        super();
        rootNode = new TrieNode(' ');
    }

    /**
     * Start loading the trie with root and $ delimiter
     *
     * @param phrase The string to load into the trie
     */
    public void load(String phrase) {
        loadRecursive(rootNode, phrase + "$");
    }

    /**
     * Recursively traverse the phrase and create nodes for each character
     *
     * @param node The current node
     * @param phrase The remaining string to be parsed
     */
    private void loadRecursive(TrieNode node, String phrase) {
        if (StringUtils.isBlank(phrase)) {
            // No more characters left to parse
            return;
        }
        char firstChar = phrase.charAt(0);
        node.add(firstChar);
        TrieNode childNode = node.getChildNode(firstChar);
        if (childNode != null) {
            // Recursively evaluate the rest of the word
            loadRecursive(childNode, phrase.substring(1));
        }
    }

    /**
     * Checks whether or not a given string matches any string of consecutive
     * characters contained in the trie
     *
     * @param prefix The given string prefix in which to look for matches
     * @return True if a match is contained in the trie, false otherwise
     */
    public boolean matchPrefix(String prefix) {
        TrieNode matchedNode = matchPrefixRecursive(rootNode, prefix);
        return matchedNode != null;
    }

    /*
     * Helper method for traversing the trie in search of matching strings
     * Param node: The current node being evaluated against the prefix
     * Param prefix: The remaining letters in the prefix to compare against
     * current node
     * Returns: Matching node if a match is found, null otherwise
     */
    private TrieNode matchPrefixRecursive(TrieNode node, String prefix) {
        if (StringUtils.isBlank(prefix)) {
            return node;
        }
        char firstChar = prefix.charAt(0);
        TrieNode childNode = node.getChildNode(firstChar);
        if (childNode == null) {
            // no match at this char, exit
            return null;
        }
        return matchPrefixRecursive(childNode, prefix.substring(1));
    }

    /**
     * If a matched node is found for a given prefix, this method finds and
     * returns all the string completions available for the prefix
     *
     * @param prefix String representing the search query
     * @return Array list of strings representing all possible completions
     *         for the prefix
     */
    public List<String> findCompletions(String prefix) {
        TrieNode matchedNode = matchPrefixRecursive(rootNode, prefix);
        List<String> completions = new ArrayList<String>();
        findCompletionsRecursive(matchedNode, prefix, completions);
        return completions;
    }

    /*
     * Helper method for locating all the string completions available for the
     * given prefix
     * Param node: The current node being evaluated
     * Param prefix: The string in which to add possible completion characters
     * Param completions: The current list of possible completions
     */
    private void findCompletionsRecursive(TrieNode node, String prefix, List<String> completions) {
        if (node == null) {
            // our prefix did not match anything, just return
            return;
        }
        if (node.getNodeValue() == '$') {
            // end reached, append prefix into completions list. Do not append
            // the trailing $, that is only to distinguish words like ann and anne
            // into separate branches of the tree.
            completions.add(prefix.substring(0, prefix.length() - 1));
            return;
        }
        Collection<TrieNode> childNodes = node.getChildren();
        for (TrieNode childNode : childNodes) {
            char childChar = childNode.getNodeValue();
            findCompletionsRecursive(childNode, prefix + childChar, completions);
        }
    }

    /**
     * Overrided toString for printing the contents of the trie
     */
    @Override
    public String toString() {
        return "Trie:" + rootNode.toString();
    }
}
