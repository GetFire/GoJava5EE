package module2_jdbc.my_practice.jdbc;

import module2_jdbc.my_practice.jdbc.exception.JdbcProjectDaoException;
import module2_jdbc.my_practice.model.Project;
import module2_jdbc.my_practice.model.ProjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProjectDAO implements ProjectDAO {
    public JdbcProjectDAO() {
        try {
            loadDriver();
        } catch (JdbcProjectDaoException e) {
            LOGGER.error("Cannot find a driver", e);
        }
    }


    private String url = "jdbc:mysql://localhost:3306/home_work1?autoReconnect=true&useSSL=false";
    private String user = "root";
    private String pass = "admin";
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcProjectDAO.class);


    @Override
    public List<Project> getAll() throws JdbcProjectDaoException {
        List<Project> projectsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT project_name, project_cost, comp_name FROM projectcompany");
            while (resultSet.next()) {
                Project pr = new Project(resultSet.getString("comp_name"),
                        resultSet.getString("project_name"),
                        resultSet.getInt("project_cost"));
                projectsList.add(pr);
            }
            resultSet.close();


        } catch (SQLException e) {
            LOGGER.error("Cannot connect to DB", e);
            throw new JdbcProjectDaoException(e);
        }
        return projectsList;
    }

    @Override
    public Project load(int id) throws JdbcProjectDaoException {
        Project result;
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM projectcompany WHERE project_id=?")) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = createProject(resultSet);
            } else {
                LOGGER.error("Cannot create new Object");
                throw new JdbcProjectDaoException("Cannot find Project with id: " + id);
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot connect to DB", e);
            throw new JdbcProjectDaoException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private Project createProject(ResultSet resultSet) throws SQLException {
        return new Project(resultSet.getString("comp_name"),
                resultSet.getString("project_name"),
                resultSet.getInt("project_cost"));
    }

    public void loadDriver() throws JdbcProjectDaoException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Cannot find driver: com.mysql.jdbc.Driver", e);
            throw new JdbcProjectDaoException(e);
        }
    }

}