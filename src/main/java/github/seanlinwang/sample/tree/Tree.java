package github.seanlinwang.sample.tree;

import java.util.List;
import java.util.Map;

public class Tree {
	private static class Node {
		private String id;
		private String data;
		private Node parent;
		private List<Node> children;
	}

	private Node root;

	private Map<String, Node> nodeMap;

	public Tree(Node root, Map<String, Node> nodeMap) {
		this.root = root;
		this.nodeMap = nodeMap;
	}
	
	public Node getNode(String nodeId) {
		return this.nodeMap.get(nodeId);
	}

}
