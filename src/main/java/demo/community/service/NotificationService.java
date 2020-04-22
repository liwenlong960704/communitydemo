package demo.community.service;

import demo.community.dto.NotificationDTO;
import demo.community.dto.PaginationDTO;
import demo.community.enums.NotificationStatusEnum;
import demo.community.enums.NotificationTypeEnum;
import demo.community.exception.CustomizeErrorCode;
import demo.community.exception.CustomizeException;
import demo.community.mapper.NotificationMapper;
import demo.community.mapper.QuestionMapper;
import demo.community.mapper.UserMapper;
import demo.community.model.Comment;
import demo.community.model.Notification;
import demo.community.model.Question;
import demo.community.model.User;
import javafx.scene.control.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public void createNotify(Comment comment, Long receiver, NotificationTypeEnum notificationType, Long OuterId) {
        if(comment.getCommentator().equals(receiver)){
            return;
        }

        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setReceiver(receiver);
        notification.setNotifier(comment.getCommentator());
        notification.setOuterId(OuterId);
        notification.setType(notificationType.getType());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.insert(notification);

    }

    public Long unreadCount(Long userId){
        Long cnt = notificationMapper.countUnreadNotification(userId);
        return cnt;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size){
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();
        Integer notificationCount = notificationMapper.countNotification(userId);
        paginationDTO.setCount(notificationCount);

        Integer pageCount;
        if(notificationCount % size == 0){
            pageCount = notificationCount/size;
        }else {
            pageCount = notificationCount/size + 1;
        }

        if(page < 1){
            page = 1;
        }

        if(page > pageCount){
            page = pageCount;
        }

        paginationDTO.setPagination(pageCount,page);

        if(pageCount == 0){
            return paginationDTO;
        }

        Integer offset = (page - 1)*size;

        List<Notification> notifications = notificationMapper.listByReceiver(userId,offset,size);
        List<NotificationDTO> notificationDTOList = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            User notifier = userMapper.findById(notification.getNotifier());
            notificationDTO.setNotifierName(notifier.getName());
            Question question = questionMapper.getById(notification.getOuterId());
            notificationDTO.setOuterTitle(question.getTitle());
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOList);

        return paginationDTO;

    }

    public NotificationDTO read(Long id, User user){
        Notification notification = notificationMapper.getById(id);
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.read(notification.getId());

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        User notifier = userMapper.findById(notification.getNotifier());
        notificationDTO.setNotifierName(notifier.getName());
        Question question = questionMapper.getById(notification.getOuterId());
        notificationDTO.setOuterTitle(question.getTitle());
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

        return notificationDTO;
    }

}
