package net.sunrise.service;

import net.sunrise.domain.entity.Book;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.service.GenericService;

public interface BookService extends GenericService<Book, Long> {
	Book getByIsbn(String isbn) throws ObjectNotFoundException;
	Book getByIsbn13(String isbn13) throws ObjectNotFoundException;
}
