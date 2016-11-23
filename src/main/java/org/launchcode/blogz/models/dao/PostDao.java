package org.launchcode.blogz.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository

//experimenting with Paging and sorting... if that doesn't work, this is the original version.
//public interface PostDao extends CrudRepository<Post, Integer> {
public interface PostDao extends PagingAndSortingRepository<Post, Integer> {

	
		
	
    List<Post> findByAuthor(User author);
    
    // TODO - add method signatures as needed
    
    //findallposts
    List<Post> findAll();
    
    List<Post> findAllByOrderByCreatedDesc();

    Page<Post> findAll(Pageable p);

    
    //find posts by title
    Post findByTitle(String title);
    
    Post findByUid(int uid);
    //etc.
	
}
