package demo.community.mapper;

import demo.community.model.Comment;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id, type, commentator, gmt_create, gmt_modified, content)" +
            "values(#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified},  #{content})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment findById(@Param("id") Long id);

    @Update("update comment set comment_count = comment_count + 1 where id = #{id}")
    void incCommentCount(@Param("id") Long id);

}
