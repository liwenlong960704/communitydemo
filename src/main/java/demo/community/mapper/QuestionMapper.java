package demo.community.mapper;

import demo.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title, description, gmt_create, gmt_modified, creator, tag) " +
            "values(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    boolean createQuestion(Question question);

    @Select("select * from question limit #{offset} , #{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer countQuestions();

    @Select("select count(1) from question where creator = #{userId}")
    Integer countQuestionsByUserId(@Param("userId") Integer userId);

    @Select("select * from question where creator = #{userId} limit #{offset} , #{size}")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title = #{title}, description = #{description}, tag = #{tag}, gmt_modified = #{gmtModified} where id = #{id}")
    boolean updateQuestion(Question question);

    @Update("update question set view_count = view_count + 1 where id = #{id}")
    boolean updateViewCount(@Param("id") Integer id);
}
