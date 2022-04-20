package dao.daoutil;

import dao.impl.StudentDao;
import dao.impl.StudentDaoImpl;

public abstract class DaoContext {
    static {
        try {
            System.out.println("Loading driver...");
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver loading failed");
            e.printStackTrace();
        }
    }

    private static StudentDao studentDao;

    public static StudentDao autowired(String qualifier, String scope) {
        if (!scope.equals("singleton") && !scope.equals("prototype")) {
            throw new RuntimeException("Invalid scope of bean " + scope);
        }
        switch (qualifier) {
            case "StudentDao":
                return getStudentDaoSQL(scope);
            default:
                throw new RuntimeException("Can not find bean for autowiring: " + qualifier);
        }
    }

    private static StudentDao getStudentDaoSQL(String scope) {

        if (scope.equals("prototype")) {
            return new StudentDaoImpl();
        }
        if ( studentDao == null) {
            studentDao = new StudentDaoImpl();
        }
        return studentDao;
    }
}