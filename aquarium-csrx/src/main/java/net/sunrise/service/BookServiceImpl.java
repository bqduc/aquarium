package net.sunrise.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.framework.model.ExecutionContext;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.sunrise.domain.entity.Book;
import net.sunrise.repository.BookRepository;

@Service
public class BookServiceImpl extends GenericServiceImpl<Book, Long> implements BookService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2757500334847725484L;

	@Inject 
	private BookRepository repository;
	
	protected BaseRepository<Book, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Book getByIsbn(String isbn) throws ObjectNotFoundException {
		return repository.findByIsbn(isbn).orElse(null);
	}

	@Override
	public Book getByIsbn13(String isbn13) throws ObjectNotFoundException {
		return repository.findByIsbn13(isbn13).orElse(null);
	}

	@Override
	public ExecutionContext load(ExecutionContext executionContext) throws ExecutionContextException {
		// TODO Auto-generated method stub
		return super.load(executionContext);
	}
}
