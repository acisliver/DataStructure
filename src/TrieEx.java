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
                // 해당 자식노드가 없을 경우 자식노드를 만든다.
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

            // 마지막 글자여야함
            // PIE가 등록되어있는데 PI를 탐색했을 경우 true가 나오면 안됨
            return curNode.isLastChar();
        }

        // 하위 노드로 내려가며 해당 노드를 탐색하고
        // 다시 올라오며 노드를 삭제
        // 콜백 형식
        public void delete(String word) {
            delete(this.rootNode, word, 0);
        }

        private void delete(TrieNode curNode, String word, int index) {

            char character = word.charAt(index);

            // 삭제할 노드가 존재 X
            if (!curNode.getChildNode().containsKey(character))
                throw new Error("There is no [" + word + "] in this Trie.");

            // 마지막 글자의 노드까지 탐색
            TrieNode childNode = curNode.getChildNode().get(character);
            index++;

            if (index == word.length()) {   // 마지막 노드까지 탐색 완료, 현재 삭제를 시작하는 노드

                // 삭제를 시작하는 노드가 마지막 글자여야 한다.
                // if문이 true인 경우 노드로는 존재하지만 단어가 아닌 경우이다.
                if (!childNode.isLastChar())
                    throw new Error("There is no [" + word + "] in this Trie.");

                childNode.setLastChar(false);

                // 삭제를 시작하는 노드가 자식을 가지고 있지 않아야 한다(이 단어를 포함하는 더 긴 단어가 없으면)
                if (childNode.getChildNode().isEmpty())
                    curNode.getChildNode().remove(character);
            } else {
                delete(childNode, word, index); // callback

                // 삭제 중 자식 노드가 없고 현재 노드로 끝나는 다른 단어가 없어야 한다.
                if (!childNode.isLastChar() && childNode.getChildNode().isEmpty())
                    curNode.getChildNode().remove(character);
            }
        }
    }
}
