package com.afrunt.example.neo4j;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private static final String QUERY_FIND = "MATCH (book:Book) WHERE ID(book) = {1} RETURN book";
    private static final String QUERY_FIND_ALL = "MATCH (book:Book) RETURN book";
    private static final String QUERY_CREATE = "CREATE (book:Book {title: {1} , author: {2} }) RETURN book";

    @Resource(lookup = "jdbc/neo4j")
    private DataSource dataSource;

    @Inject
    private Jsonb jsonb;

    public List<Book> findAll() {
        return execute(QUERY_FIND_ALL, bookMapper());
    }

    public Optional<Book> find(long id) {
        return execute(QUERY_FIND, Collections.singletonList(id), bookMapper())
                .stream()
                .findFirst();
    }

    public Book create(Book book) {
        return execute(BookService.QUERY_CREATE, Arrays.asList(book.getTitle(), book.getAuthor()), bookMapper()).get(0);
    }

    private <V> List<V> execute(String cypherQuery, Function<ResultSet, V> mapper) {
        return execute(cypherQuery, Collections.emptyList(), mapper);
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

    private Function<ResultSet, Book> bookMapper() {
        return bookMapper("book");
    }

    private Function<ResultSet, Book> bookMapper(String fieldName) {
        return resultSet -> {
            try {
                return jsonb.fromJson(resultSet.getString(fieldName), Book.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
