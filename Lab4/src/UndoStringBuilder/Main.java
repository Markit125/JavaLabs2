package UndoStringBuilder;

public class Main {
    public static void main(String[] args) {
        UndoStringBuilder undoSB = new UndoStringBuilder();
        undoSB.append("Hello World");
        undoSB.append("!");

        for (int i = 0; i < 3; i++) {
            System.out.println(undoSB);
            undoSB.undo();
        }

        undoSB.append("My name is ");
        System.out.println(undoSB);
        undoSB.append("Mark");
        System.out.println(undoSB);
        undoSB.undo();
        undoSB.append("MARK");
        System.out.println(undoSB);
    }
}
