package net.sunrise.service;

import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.framework.service.GenericService;
import net.sunrise.domain.entity.Book;

public interface BookService extends GenericService<Book, Long> {
	Book getByIsbn(String isbn) throws ObjectNotFoundException;
	Book getByIsbn13(String isbn13) throws ObjectNotFoundException;
}
