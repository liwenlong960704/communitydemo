package demo.community.mapper;

import demo.community.enums.CommentTypeEnum;
import demo.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id, type, commentator, gmt_create, gmt_modified, content)" +
            "values(#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified},  #{content})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment findById(@Param("id") Long id);

    @Update("update comment set comment_count = comment_count + 1 where id = #{id}")
    void incCommentCount(@Param("id") Long id);

    @Select("select * from comment where parent_id = #{id} and type = #{type} order by gmt_create desc")
    List<Comment> list(@Param("id") Long id, @Param("type") Integer type);

    @Select("select * from comment where parent_id = #{id} and commentator = #{commentator} order by gmtCreate desc")
    Comment findByCommentator(@Param("id") Long id,@Param("commentator") Long commentator);
}
