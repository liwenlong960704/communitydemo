package generate;

import java.io.Serializable;
import lombok.Data;

/**
 * question
 * @author 
 */
@Data
public class Question implements Serializable {
    private Integer id;

    private String title;

    private String description;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer creator;

    private Integer commentCount;

    private Integer likeCount;

    private String tag;

    private Integer viewCount;

    private static final long serialVersionUID = 1L;
}