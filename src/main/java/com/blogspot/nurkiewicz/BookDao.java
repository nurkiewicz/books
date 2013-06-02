package com.blogspot.nurkiewicz;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Tomasz Nurkiewicz
 * @since 6/2/13, 5:16 PM
 */
public interface BookDao extends JpaRepository<Book, Integer> {
}
