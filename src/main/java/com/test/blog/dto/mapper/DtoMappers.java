package com.test.blog.dto.mapper;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.mapstruct.BeforeMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.test.blog.dto.CommentDTO;
import com.test.blog.dto.PostDTO;
import com.test.blog.dto.UserDTO;
import com.test.blog.model.Comment;
import com.test.blog.model.Post;
import com.test.blog.model.User;

@Mapper( componentModel = "spring" )
public abstract class DtoMappers {
	
	@Mappings( { @Mapping( source = "email", target = "email" ), @Mapping( source = "firstName", target = "firstName" ),
	@Mapping( source = "lastName", target = "lastName" ), @Mapping( source = "id", target = "id" )  } )
	public abstract UserDTO userToUserDTO( User user );
	
	@Mappings( { @Mapping( source = "title", target = "title" ), @Mapping( source = "text", target = "text" ), @Mapping( source = "creationDate", target = "creationDate" ),
	@Mapping( source = "authorId", target = "authorId" ), @Mapping( source = "id", target = "id" ),
	@Mapping( target = "commentsList", expression = "java( commentToCommentDTO( post.getCommentsList() ) )" )  } )
	public abstract PostDTO postToPostDTO( Post post );
	
	@Mappings( { @Mapping( source = "text", target = "text" ), @Mapping( source = "creationDate", target = "creationDate" ),
	@Mapping( source = "authorId", target = "authorId" ), @Mapping( source = "id", target = "id" ), @Mapping( source = "postId", target = "postId" ),
	@Mapping( source = "updateDate", target = "updateDate" )} )
	public abstract CommentDTO commentToCommentDTO( Comment comment );
	
	public abstract List<CommentDTO> commentToCommentDTO( List<Comment> commentList );



}
