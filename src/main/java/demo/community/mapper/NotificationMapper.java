package demo.community.mapper;

import demo.community.model.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification(notifier, receiver, outer_id, type, gmt_create, status) " +
            "values(#{notifier}, #{receiver}, #{outerId}, #{type}, #{gmtCreate}, #{status})")
    void insert(Notification notification);

    @Select("select count(1) from notification where receiver = #{userId} and status = 0")
    Long countUnreadNotification(@Param("userId") Long userId);

    @Select("select count(1) from notification where receiver = #{userId}")
    Integer countNotification(@Param("userId") Long userId);

    @Select("select * from notification where receiver = #{userId} limit #{offset}, #{size}")
    List<Notification> listByReceiver(@Param("userId") Long userId,@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);
}
