package ru.klishin.springcourse.Project2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klishin.springcourse.Project2Boot.models.Book;
import ru.klishin.springcourse.Project2Boot.models.Person;
import ru.klishin.springcourse.Project2Boot.repositories.LibraryRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LibraryService {

    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Book> findAll(Integer page, Integer booksPerPage, String sortByYear) {
        if (page != null && booksPerPage != null && "true".equals(sortByYear)) {

            return libraryRepository.findAll(PageRequest.of(page, booksPerPage,
                    Sort.by("yearOfPublishing"))).getContent();

        }
        if (page != null && booksPerPage != null && !"true".equals(sortByYear)) {

            return libraryRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();

        }
        if (page == null && booksPerPage == null && "true".equals(sortByYear)) {

            return libraryRepository.findAll(Sort.by("yearOfPublishing"));

        }

        return libraryRepository.findAll();
    }

    public Book findById(int id) {
        Optional<Book> foundBook = libraryRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Book search(String searchLine) {
        for (Book book : libraryRepository.findAll()) {
            if(!searchLine.isEmpty() && book.getTitle().startsWith(searchLine))
                return book;
        }
        return null;
    }

    @Transactional
    public void save(Book book) {
        libraryRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = libraryRepository.findById(id).get();

        updatedBook.setBookId(id);
        updatedBook.setPerson(bookToBeUpdated.getPerson());
        updatedBook.setAppointDate(bookToBeUpdated.getAppointDate());

        libraryRepository.save(updatedBook);
    }

    @Transactional
    public void appointBook(Person person, Book updatedBook) {
        updatedBook.setPerson(person);
        updatedBook.setAppointDate(new Date());
        libraryRepository.save(updatedBook);
    }

    @Transactional
    public void freeingBook(Book updatedBook) {
        updatedBook.setPerson(null);
        updatedBook.setAppointDate(null);
        libraryRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        libraryRepository.deleteById(id);
    }
}
