import java.util.HashMap;
import java.util.Map;

public class TrieEx {
    static class TrieNode {
        // 자식 노드 맵
        private Map<Character, TrieNode> childNode = new HashMap<>();

        private boolean isLastChar;

        private Map<Character, TrieNode> getChildNode() {
            return childNode;
        }

        public boolean isLastChar() {
            return isLastChar;
        }

        public void setLastChar(boolean lastChar) {
            isLastChar = lastChar;
        }
    }

    static class Trie {
        // 루트노드
        private TrieNode rootNode;

        public Trie() {
            this.rootNode = new TrieNode();
        }

        public void insert(String word) {
            TrieNode curNode = this.rootNode;

            for (int i = 0; i < word.length(); i++) {
                curNode = curNode.getChildNode().computeIfAbsent(word.charAt(i), c -> new TrieNode());
            }
            curNode.setLastChar(true);
        }

        public boolean contain(String word) {
            TrieNode curNode = this.rootNode;

            for (int i = 0; i < word.length(); i++) {
                char character = word.charAt(i);
                TrieNode node = curNode.getChildNode().get(character);

                if (node == null)
                    return false;
                curNode = node;
            }

            return curNode.isLastChar();
        }

        public void delete(String word) {
            delete(this.rootNode, word, 0);
        }

        private void delete(TrieNode curNode, String word, int index) {

            char character = word.charAt(index);

            if (!curNode.getChildNode().containsKey(character))
                throw new Error("There is no [" + word + "] in this Trie.");

            TrieNode childNode = curNode.getChildNode().get(character);
            index++;

            if (index == word.length()) {

                if (!childNode.isLastChar())
                    throw new Error("There is no [" + word + "] in this Trie.");

                childNode.setLastChar(false);

                if (childNode.getChildNode().isEmpty())
                    curNode.getChildNode().remove(character);
            } else {
                delete(childNode, word, index);

                if (!childNode.isLastChar() && childNode.getChildNode().isEmpty())
                    curNode.getChildNode().remove(character);
            }
        }
    }
}
