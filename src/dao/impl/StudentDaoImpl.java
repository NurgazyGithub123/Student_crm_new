package dao.impl;

import dao.daoutil.Log;
import model.Student;

import java.sql.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class StudentDaoImpl implements StudentDao{

    public StudentDaoImpl(){
        Connection connection = null;
        Statement statement = null;
        try {
            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), " connecting to database...");
            connection = getConnection();
            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), " connection succeeded.");

            String ddlQuery = "CREATE TABLE IF NOT EXISTS tb_students(" +
                    "id           BIGSERIAL, " +
                    "first_name   VARCHAR(50)  NOT NULL, " +
                    "last_name     VARCHAR(50)  NOT NULL, " +
                    "email        VARCHAR(100) NOT NULL UNIQUE, " +
                    "phone_number CHAR(13)     NOT NULL, " +
                    "dob          DATE         NOT NULL CHECK(dob < NOW()), " +
                    "date_created TIMESTAMP    NOT NULL DEFAULT NOW(), " +
                    "" +
                    "CONSTRAINT pk_student_id PRIMARY KEY(id), " +
                    "CONSTRAINT chk_student_first_name CHECK(LENGTH(first_name) > 2));";

            statement = connection.createStatement();
            statement.execute(ddlQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<Student> save(List<Student> students) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Student> savedStudent = new ArrayList<>();

        try {
            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), " connecting to database...");
            connection = getConnection();
            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), " connection succeeded.");

            String createQuery = "INSERT INTO tb_students(" +
                    "last_name, first_name, phone_number, date_created, dob, email) " +

                    "VALUES(?, ?, ?, ?, ?, ?)";

//            connection.setAutoCommit(false);
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);

                preparedStatement = connection.prepareStatement(createQuery);
                preparedStatement.setString(1, student.getLastName());
                preparedStatement.setString(2, student.getFirstName());
                preparedStatement.setString(3, student.getPhoneNumber());
                preparedStatement.setTimestamp(4, Timestamp.valueOf(student.getDateCreated()));
                preparedStatement.setDate(5, Date.valueOf(student.getDob()));
                preparedStatement.setString(6, student.getEmail());

//                preparedStatement.addBatch();
                preparedStatement.execute();

//                if (i % 4 == 0 || i == students.size() - 1) {
//                    preparedStatement.executeBatch();
//                    preparedStatement.clearBatch();
//                }
            }

            String readQuery = "SELECT * FROM tb_students";

            close(preparedStatement);
            preparedStatement = connection.prepareStatement(readQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setEmail(resultSet.getString("email"));
                student.setPhoneNumber(resultSet.getString("phone_number"));
                student.setDob(resultSet.getDate("dob").toLocalDate());
                student.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());

                resultSet.next();

                savedStudent.add(student);
            }

        } catch (SQLException e){
            Log.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        }finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return savedStudent;
    }

    @Override
    public List<Student> findAll() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();

        try {
            Log.info(this.getClass().getSimpleName() + " findAll()", Connection.class.getSimpleName(), "Establishing connection");
            connection = getConnection();

            String readQuery = "SELECT * FROM tb_students";

            preparedStatement = connection.prepareStatement(readQuery);

            resultSet = preparedStatement.executeQuery();

            for (int i = 0; i <= students.size() && resultSet.next(); i++) {
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setEmail(resultSet.getString("email"));
                student.setPhoneNumber(resultSet.getString("phone_number"));
                student.setDob(resultSet.getDate("dob").toLocalDate());
                student.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
                students.add(student);
            }
        } catch (Exception e) {
            Log.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClassName(), e.getMessage());
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return students;
    }

}
