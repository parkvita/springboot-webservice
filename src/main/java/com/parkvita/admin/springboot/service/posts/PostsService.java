package com.parkvita.admin.springboot.service.posts;

import com.parkvita.admin.springboot.domain.posts.Posts;
import com.parkvita.admin.springboot.domain.posts.PostsRepository;
import com.parkvita.admin.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    // 게시글 작성
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // 게시글 수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(posts);      //jpaRepository에서 이미 delete 메소드를 지원하고있음
                                            // Entity 파라미터로 삭제할 수 도 있고 deleteById 메소드를 이용하면 ID로도 삭제할 수 있음
                                            // 존재하는 Posts인지 확인을 위해 엔티티 조회 후 그대로 삭제
    }

    @Transactional
    public List<PostsDto> searchPosts(String keyword){
        List<Posts> postsList = postsRepository.findByTitleContaining(keyword);
        List<PostsDto> responseDtoList = new ArrayList<>();

        if (postsList.isEmpty()) return responseDtoList;

        for(Posts posts : postsList){
            responseDtoList.add(this.convertEntityToDto(posts));

        }

        return responseDtoList;
    }

    private static final int BLOCK_PAGE_NUM_COUNT=5;
    private static final int PAGE_POST_COUNT=4;

    @Transactional
    public List<PostsDto> getPostsList(Integer pageNum){
        Page<Posts> page = postsRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));

        List<Posts> postsList = page.getContent();
        List<PostsDto> postsDtoList = new ArrayList<>();

        for(Posts posts : postsList){
            postsDtoList.add(this.convertEntityToDto(posts));
        }

        return postsDtoList;
    }

    @Transactional
    public Long getPostsCount(){
        return postsRepository.count();
    }

    @Transactional
    public Integer[] getPageList(Integer curPageNum){
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
        // 총 게시글 수
        Double postsTotalCount = Double.valueOf(this.getPostsCount());

        //총 게시글 기준으로 계산한 마지막 페이지 번호계산
        Integer totalLastPageNum= (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;

    }


    private PostsDto convertEntityToDto(Posts postsEntity) {
        return PostsDto.builder()
                .id(postsEntity.getId())
                .title(postsEntity.getTitle())
                .content(postsEntity.getContent())
                .author(postsEntity.getAuthor())
                .modifiedDate(postsEntity.getModifiedDate())
                .build();
    }
}
