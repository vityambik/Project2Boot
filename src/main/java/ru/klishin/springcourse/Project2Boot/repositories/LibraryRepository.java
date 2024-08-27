package ru.klishin.springcourse.Project2Boot.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klishin.springcourse.Project2Boot.models.Book;

@Repository
public interface LibraryRepository extends JpaRepository<Book, Integer> {
}
