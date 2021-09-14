public class Vertex {
    private char label;  // метка А например
    private boolean wasVisited;
    private boolean inTree;

    public Vertex(final char label) {
        this.label = label;
        wasVisited = false;
    }

    public char getLabel() {
        return this.label;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    public boolean isWasVisited() {
        return this.wasVisited;
    }

    public void setWasVisited(final boolean wasVisited) {
        this.wasVisited = wasVisited;
    }

    public boolean isInTree() {
        return inTree;
    }

    public void setInTree(boolean inTree) {
        this.inTree = inTree;
    }
}
