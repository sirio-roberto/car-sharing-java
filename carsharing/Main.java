package carsharing;

public class Main {

    public static void main(String[] args) {
        String dbName = getDbNameFromArgs(args);

        App app = new App(dbName);
        app.run();
    }

    private static String getDbNameFromArgs(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if ("-databaseFileName".equals(args[i])) {
                return args[i + 1];
            }
        }
        return "";
    }
}