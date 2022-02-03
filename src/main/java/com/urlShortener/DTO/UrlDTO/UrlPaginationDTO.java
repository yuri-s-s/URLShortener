package com.urlShortener.DTO.UrlDTO;


import java.util.List;

public class UrlPaginationDTO {

    private Integer page;
    private Integer pageSize;
    private int elements;

    private List<UrlResponseDTO> urls;

    public UrlPaginationDTO(Integer page, Integer pageSize, List<UrlResponseDTO> urls) {
        this.page = page;
        this.pageSize = pageSize;
        this.urls = urls;
        this.elements = urls.size();
    }

    public UrlPaginationDTO() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<UrlResponseDTO> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlResponseDTO> urls) {
        this.urls = urls;
    }

    public int getElements() {
        return elements;
    }

    public void setElements(int elements) {
        this.elements = elements;
    }

}
