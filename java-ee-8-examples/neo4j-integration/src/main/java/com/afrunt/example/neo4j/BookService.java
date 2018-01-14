package com.afrunt.example.neo4j;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @author Andrii Frunt
 */
@Singleton
@Startup
@TransactionManagement
public class BookService {
    @Resource(lookup = "jdbc/neo4j")
    private DataSource dataSource;

    @Inject
    private Jsonb jsonb;

    public List<Book> findAll() {
        return execute("MATCH (book:Book) RETURN book", new ArrayList<>(), createBookMapper());
    }

    public Optional<Book> find(long id) {
        return execute("MATCH (book:Book) WHERE ID(book) = {1} RETURN book", Collections.singletonList(id), createBookMapper())
                .stream()
                .findFirst();
    }

    public Book create(Book book) {
        return execute(
                "CREATE (book:Book {title: {1} , author: {2} }) RETURN book",
                Arrays.asList(book.getTitle(), book.getAuthor()),
                createBookMapper()
        )
                .iterator()
                .next();
    }

    @PostConstruct
    public void init() {
        try (Connection connection = dataSource.getConnection()) {
            Statement create = connection.createStatement();
            create.executeUpdate("CREATE (you:Book {title:'Effective Java', author:'Joshua Bloch'}) RETURN you");

            Statement match = connection.createStatement();
            match.execute(
                    "MATCH  (you:Book) " +
                            "RETURN you");

            ResultSet rs = match.getResultSet();
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(jsonb.fromJson(rs.getString("you"), Book.class));
            }
            System.out.println("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <V> List<V> execute(String cypherQuery, List<Object> params, Function<ResultSet, V> mapper) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(cypherQuery);
            IntStream.range(0, params.size()).forEachOrdered(index -> {
                try {
                    stmt.setObject(index + 1, params.get(index));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            stmt.execute();

            List<V> results = new ArrayList<>();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                results.add(mapper.apply(rs));
            }

            return results;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Function<ResultSet, Book> createBookMapper() {
        return createBookMapper("book");
    }

    private Function<ResultSet, Book> createBookMapper(String fieldName) {
        return resultSet -> {
            try {
                return jsonb.fromJson(resultSet.getString(fieldName), Book.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
}