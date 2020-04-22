package demo.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String typeName;
    private String notifierName;
    private String outerTitle;
}
