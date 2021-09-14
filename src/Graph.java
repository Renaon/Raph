import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Graph {
    private final int MAX_VERTS = 10;
    private Vertex vertexArr[];
    private int adjMat[][]; //матрица смежности
    private int nVer; //кол-во вершин
    private int viewVer;
    private Stack<Integer> stack;
    private List<Path> shortestPaths; // список данных кратчайших путей
    private final int INFINITY = 100000000;
    private int currentVertex; // текущая вершина
    private int startToCurrent; //расстояние до currentVertex

    public Graph(){
        vertexArr = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVer = 0;
        viewVer = 0;
        for (int i = 0; i<MAX_VERTS; i++){
            for (int j = 0; j< MAX_VERTS; j++){
                adjMat[i][j] = INFINITY;
                shortestPaths = new ArrayList<>();
            }
        }
    }

    public void addVertex(char v){
        vertexArr[nVer++] = new Vertex(v);
    }

    public void addEdge(int start, int end, int weight){
        adjMat[start][end] = weight;
    }

    public void display(int v){
        System.out.println(vertexArr[v].getLabel());
    }

    public void  dfs(){
        vertexArr[0].setWasVisited(true);
        display(0);
        stack.push(0);

        while (!stack.empty()){
            int v = getAdjUnvisitedVertex(stack.peek());
            if(v == 1){
                stack.pop();
            }
            else {
                vertexArr[v].setWasVisited(true);
                display(v);
                stack.push(v);
            }
        }
        for (int j = 0; j < nVer; j++) {  // сброс флагов
            vertexArr[j].setWasVisited(false);
        }
    }

    private int getAdjUnvisitedVertex(int v){
        for (int i= 0; i<nVer; i++){
            if(adjMat[v][i] == 1 && vertexArr[i].isWasVisited() == false) return i;
        }
        return  -1;
    }

    public void path(){
        int START = 0;
        vertexArr[START].setInTree(true);
        viewVer = 1;

        for (int i= 0; i < nVer; i++){
            int tmp = adjMat[START][i];
            Path path = new Path(tmp);
            path.getParent().add(0);
            shortestPaths.add(path);
        }

        while (viewVer < nVer){
            int idMin = getMin();
            int minDist = shortestPaths.get(idMin).getDistance();

            if (minDist == INFINITY){
                System.out.println("Есть недостижимые вершины");
                break;
            }
            else {
                currentVertex = idMin;
                startToCurrent = shortestPaths.get(idMin).getDistance();
            }

            vertexArr[currentVertex].setInTree(true);
            viewVer++;
            updateShortestPaths();
        }
        displayPaths();
    }

    public void clean(){
        viewVer = 0;
        for (int i = 0; i<nVer; i++){
            vertexArr[i].setInTree(false);
        }
    }

    private int getMin(){
        int minDist = INFINITY; // за точку старта взята "бесконечная" длина
        int indexMin = 0;
        for (int i=1; i<nVer; i++){
            if (!vertexArr[i].isInTree() && shortestPaths.get(i).getDistance() < minDist){
                minDist = shortestPaths.get(i).getDistance();
                indexMin = i;
            }
        }
        return  indexMin;
    }

    private void updateShortestPaths(){
        int vertexIndex = 1; // стартовая вершина пропускается
        while (vertexIndex < nVer) { // перебор столбцов

            if (vertexArr[vertexIndex].isInTree()) { // если вершина column уже включена в дерево, она пропускается
                vertexIndex++;
                continue;
            }
            // вычисление расстояния для одного элемента sPath
            // получение ребра от currentVert к column
            int currentToFringe = adjMat[currentVertex][vertexIndex];
            // суммирование всех расстояний
            int startToFringe = startToCurrent + currentToFringe;
            // определение расстояния текущего элемента vertexIndex
            int shortPathDistance = shortestPaths.get(vertexIndex).getDistance();

            // сравнение расстояния через currentVertex с текущим расстоянием в вершине с индексом vertexIndex
            if (startToFringe < shortPathDistance) {// если меньше, то у вершины под индексом vertexIndex будет задан новый кратчайший путь
                List<Integer> newParents = new ArrayList<>(shortestPaths.get(currentVertex).getParent());//создаём копию списка родителей вершины currentVert
                newParents.add(currentVertex);// задаём в него и currentVertex как предыдущий
                shortestPaths.get(vertexIndex).setParent(newParents); // соохраняем новый маршут
                shortestPaths.get(vertexIndex).setDistance(startToFringe); // соохраняем новую дистанцию
            }
            vertexIndex++;
        }
    }

    private void displayPaths() { // метод для вывода кратчайших путей на экран
        for (int i = 0; i < nVer; i++) {
            System.out.print(vertexArr[i].getLabel() + " = ");
            if (shortestPaths.get(i).getDistance() == INFINITY) {
                System.out.println("0");
            } else {
                String result = shortestPaths.get(i).getDistance() + " (";
                List<Integer> parents = shortestPaths.get(i).getParent();
                for (int j = 0; j < parents.size(); j++) {
                    result += vertexArr[parents.get(j)].getLabel() + " -> ";
                }
                System.out.println(result + vertexArr[i].getLabel() + ")");
            }
        }
    }

}
