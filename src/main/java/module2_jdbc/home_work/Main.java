package module2_jdbc.home_work;

import module2_jdbc.home_work.entity.Company;
import module2_jdbc.home_work.dao.jdbc.CompaniesJdbcDAO;
import module2_jdbc.home_work.dao.model.CompaniesDAO;

public class Main {
    public static void main(String[] args) {

        // Testing CompaniesDAO
        System.out.println("Create instance of CompaniesDAO...");
        System.out.println();
        CompaniesDAO companiesDAO = new CompaniesJdbcDAO();
        System.out.println("Getting all companies from Data Base:");
        companiesDAO.getAllCompanies().forEach(System.out::println);
        System.out.println();

        System.out.println("***************************************");
        System.out.println("Getting company by name - nix:");
        System.out.println(companiesDAO.getByName("nix"));
        System.out.println();


        System.out.println("***************************************");
        System.out.println("Trying to add new companies:");
        companiesDAO.addCompany(new Company("TEst1"));
        companiesDAO.addCompany(new Company("TEst2"));
        companiesDAO.addCompany(new Company("TEst3"));
        System.out.println();
        System.out.println("Getting all companies from Data Base:");
        companiesDAO.getAllCompanies().forEach(System.out::println);
        System.out.println();

        System.out.println("***************************************");
        System.out.println("Getting company by id: 4");
        System.out.println(companiesDAO.getByID(4));
        System.out.println("Update company by id: 4");
        companiesDAO.updateByID(4, new Company("AbraCadabra & co"));
        System.out.println("Getting company by id: 4");
        System.out.println(companiesDAO.getByID(4));
        System.out.println();

        System.out.println("***************************************");
        System.out.println("Delete all companies which names contain word \"Test\":");
        companiesDAO.deleteByName("Test");
        System.out.println("Getting all companies from Data Base:");
        companiesDAO.getAllCompanies().forEach(System.out::println);
        System.out.println();

        System.out.println("***************************************");
        System.out.println("Getting all Projects for company named \"NIX Solution\": ");
        companiesDAO.getCompaniesProjects(companiesDAO.getByName("Nix")).forEach(System.out::println);
        System.out.println();

        System.out.println("***************************************");
        System.out.println("Getting all developers who work on company \"NIX Solution\": ");
        companiesDAO.getAllDevelopers("Nix").forEach(System.out::println);
        System.out.println();

        // End of testing CompaniesDAO

    }

}
