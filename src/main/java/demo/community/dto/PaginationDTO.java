package demo.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPreviousPage;
    private boolean showNextPage;
    private boolean showFirstPage;
    private boolean showEndPage;
    private Integer currentPage;
    private Integer pageCount;
    private List<Integer> pages;

    public void setPagination(Integer pageCount, Integer page) {

        pages = new ArrayList<>();

        currentPage = page;

        this.pageCount = pageCount;

        showPreviousPage = true;
        showFirstPage = true;
        showNextPage = true;
        showEndPage = true;

        if(currentPage.equals(1)){
            showFirstPage = false;
            showPreviousPage = false;
        }

        if(currentPage.equals(pageCount)){
            showNextPage = false;
            showEndPage = false;
        }

        pages.add(currentPage);

        for(int i = 1; i <= 2; i++){
            if(currentPage - i > 0){
                pages.add(0,currentPage-i);
            }
            if(currentPage + i <= pageCount){
                pages.add(currentPage+i);
            }
        }

    }
}
