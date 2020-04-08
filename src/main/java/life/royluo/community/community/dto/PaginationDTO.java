package life.royluo.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * Roy
 * 2020-03-05
 */


@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    //是否显示上一页按钮
    private boolean showPrevious;
    //是否显示跳转第一页按钮
    private boolean showFirstPage;
    //是否显示跳转下一页按钮
    private boolean showNext;
    //是否显示跳转最后一页按钮
    private boolean showEndPage;
    //当前页数
    private Integer page;
    //页面数组
    private List<Integer> pages = new ArrayList<>();
    //总页数
    private Integer totalPage;

    /**
     *
     * @param totalCount 行数
     * @param page 当前页
     * @param size 显示多少行
     * @return offset = size*(page-1)当前页数据开头行数
     */
    public Integer setPagination(Integer totalCount, Integer page, Integer size) {


        //总页数 这里totalCount和size都是Integer，所以要转换一下，不然totalCount / size=默认向下取余
        totalPage = (int)Math.ceil(totalCount.doubleValue() / size);
        //当页面page大于最大页面值时或者<=0时候，默认最大页面值或第一页
        if(page > totalPage){
            page = totalPage;
        }else if (page <= 0){
            page = 1;
        }
        this.page=page;
        //利用分页公式 当前页面第一数据的库行数 = 页面行数*（当前页数-1）
        Integer offset = size*(page-1);
        //页数框显示数量
        for (int i=-3;i<=3;i++){
            if(page+i <= totalPage && page+i > 0){
                pages.add(page+i);
            }
        }
        //当前是第一页的时候，showFirstPage按钮为不显示
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //当前为下一页totalPage的时候showNext按钮不显示
        if(page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        //是否显示showFirstPage按钮
        if(pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //是否显示最后一页
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }

        return offset;

    }
}
