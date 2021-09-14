import java.util.ArrayList;
import java.util.List;

public class Path {
    private int distance;
    private List<Integer> parent;

    public Path(int distance) {
        this.distance = distance;
        this.parent = new ArrayList<>();
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<Integer> getParent() {
        return parent;
    }

    public void setParent(List<Integer> parent) {
        this.parent = parent;
    }
}
