package Manhattan;

public class Punto {
    private final int x;
    private final int y;
    public Punto(int x, int y) {
        this.x = x; this.y = y;
    }
    public int manhattanDistance(Punto laOtra) {
        return Math.abs(this.x - laOtra.x) + Math.abs(this.y - laOtra.y);
    }
}
