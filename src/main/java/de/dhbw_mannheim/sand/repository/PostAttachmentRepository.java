package de.dhbw_mannheim.sand.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.model.PostAttachment;

public interface PostAttachmentRepository extends JpaRepository<PostAttachment,Integer>{

	PostAttachment findByIdAndDeleted(int id, int deleted);
	
}
