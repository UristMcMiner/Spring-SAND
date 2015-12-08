package de.dhbw_mannheim.sand.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.model.PostAttachment;

@Repository
public interface PostAttachmentRepository extends JpaRepository<PostAttachment,Integer>{

	PostAttachment findByIdAndDeleted(int id, int deleted);
	
	@Query ("select a from PostAttachment a where a.post.id=?1 and a.deleted=0")
	List<PostAttachment> findByPostId(int id);
	
}
